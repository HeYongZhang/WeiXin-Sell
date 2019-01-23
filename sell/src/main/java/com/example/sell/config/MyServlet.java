package com.example.sell.config;

import com.example.sell.servlet.CodeServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServlet {

    @Bean
    public ServletRegistrationBean getCodeServlet(){
        return new ServletRegistrationBean(new CodeServlet(),"/rqcoder");
    }
}
