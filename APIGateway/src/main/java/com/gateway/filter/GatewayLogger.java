package com.gateway.filter;


import com.gateway.config.ConfigGateway;
import com.gateway.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @Creator 8/6/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Component
@Slf4j
public class GatewayLogger implements GlobalFilter, Ordered {


    private final ConfigGateway configGateway;

    public GatewayLogger(ConfigGateway configGateway) {
        this.configGateway = configGateway;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Mono<Void> retVal = chain.filter(exchange);
        try {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            URI requestURI = request.getURI();
            String scheme = requestURI.getScheme();

            if (scheme.equalsIgnoreCase("http") || (scheme.equalsIgnoreCase("https"))) {
                long startTime = System.currentTimeMillis();
                StringBuffer requestStr = new StringBuffer();

                if (configGateway.isLogRequest()) {
                    requestStr = Utility.gatheringLogRequest(request);
                }
                if (configGateway.isRemoveDeniedHeaders()) {
                    Utility.removeDeniedHeaders(exchange, configGateway.getAllowHeaders());
                    requestStr.append("\nRequest Only Allow Headers:");
                    StringBuffer finalRequestStr = requestStr;
                    request.getHeaders().forEach((key, value) -> finalRequestStr.append(key).append(":").append(value).append(", "));
                }
                log.info(requestStr.toString());
//                if (configGateway.isCheckRequestHeader()) {
//                    if (!Utility.checkInputData(requestStr.toString())) {
//                        return response.writeWith(null);
//                    }
//                }
                ServerWebExchange newExchange = exchange.mutate().request(request).response(gatheringInfoAfterResponse(exchange, startTime)).build();
                return chain.filter(newExchange);
            }
        } catch (Exception e) {
            log.error("error in DetailedRequestResponseLogFilter cased by:\n " + e);
        }
        return retVal;
    }



    @Override
    public int getOrder() {
        return 0;
    }

    private ServerHttpResponseDecorator gatheringInfoAfterResponse(ServerWebExchange exchange, Long startTime) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (configGateway.isLogResponse()) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            try {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                var bodyContent = new String(content, StandardCharsets.UTF_8);
                                StringBuffer logResponseBuffer = Utility.gatheringLogResponse(exchange.getResponse(), bodyContent, exchange.getRequest(), startTime);
                                log.info(logResponseBuffer.toString());
                                return exchange.getResponse().bufferFactory().wrap(content);
                            } finally {
                                DataBufferUtils.release(dataBuffer);
                            }
                        }));
                    }
                } else {
                    log.info("\nResponse Original Path: {} \nResponse time: {} {}: ",
                            exchange.getRequest().getURI().getPath(),
                            (System.currentTimeMillis() - startTime),
                            "(miliSecond)");
                }
                return super.writeWith(body);
            }
        };
    }
}
