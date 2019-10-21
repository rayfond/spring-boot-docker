package net.bittx.h2.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@MapperScan(basePackages = "net.bittx.h2.dao.mapper")
public class Conf {
    /*@Bean // http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {

            }
        };
    }*/
}
