package com.krxinuo.config;

import com.krxinuo.entity.SysLogEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class BeanConfig {
    @Bean(name="sysLog")
//    @Scope("request")
    public SysLogEntity generateDemo() {
        return new SysLogEntity();
    }
}