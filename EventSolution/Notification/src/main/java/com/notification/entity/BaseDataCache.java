package com.notification.entity;

import com.infra.dto.BaseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Creator :  3/18/2023, Saturday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "BaseDataCache",timeToLive = 60L)
public class BaseDataCache implements Serializable {
    @Id
    private String id;
    private BaseData data;
    @TimeToLive
    private Long expireDate;

}
