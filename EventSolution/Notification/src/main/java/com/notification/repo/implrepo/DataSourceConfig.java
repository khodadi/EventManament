package com.notification.repo.implrepo;

import com.infra.utils.InfraUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivateKey;


/**
 * @author m.shahrestanaki @createDate 4/8/2023
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.notification.repo",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@Slf4j
public class DataSourceConfig {
    final ApplicationContext resourceLoader;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${dataBase.password.encrypted}")
    private String encryptedPass;
    @Value("${dataBase.password.keyAndPathLocation}")
    private String keyAndPathLocation;

    public DataSourceConfig(ApplicationContext  resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "getDataSource")
    @Primary
    public DataSource getDataSource() {
        String passwordDecode = null;
        try {
            if(Boolean.valueOf(encryptedPass).equals(true)){
                File file = resourceLoader.getResource(keyAndPathLocation).getFile();
                InputStream filePr = new FileInputStream(file);
                log.info("\n database private key file is ready for read ");
                PrivateKey privateKey = InfraUtility.getPrivateKey(filePr);
                passwordDecode = InfraUtility.decodePassword(privateKey, password);
            }else{
                passwordDecode = password;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        if (passwordDecode != null) {
            try {
                dataSourceBuilder.url(url);
                dataSourceBuilder.username(username);
                dataSourceBuilder.password(passwordDecode);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("getDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.notification.entity")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(
            final @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean notificationManagerFactory) {
        return new JpaTransactionManager(notificationManagerFactory.getObject());
    }

}
