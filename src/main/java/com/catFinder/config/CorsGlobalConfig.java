package com.catFinder.config;

import org.springframework.context.annotation.Configuration;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static java.util.Collections.singletonList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import java.io.IOException;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
// @Configuration
// public class CorsGlobalConfig {

//     @Bean
//     public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {

//         var config = new CorsConfiguration();
//         config.setAllowCredentials(true);

//         config.setAllowedMethods(singletonList("*"));
//         config.setAllowedHeaders(singletonList("*"));
//         config.addAllowedOriginPattern("*");

//         var source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);

//         var bean = new FilterRegistrationBean<CorsFilter>();
//         bean.setFilter(new CorsFilter(source));
//         bean.setOrder(HIGHEST_PRECEDENCE);

//         return bean;

//     }
// }
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsGlobalConfig implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", origin != null && origin.contains("ws") ? "" : origin );
        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin,user-agent,postman-token, X-Requested-With, Authorization, Content-Type, Accept, X-CSRF-TOKEN, cache-control ");
        response.setHeader("Access-Control-Max-Age", "3600");  
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
