package com.example.Intellihealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = "com.example.Intellihealth")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
   @Bean
   public ViewResolver viewResolver() {
       InternalResourceViewResolver resolver = new InternalResourceViewResolver();
       resolver.setViewClass(JstlView.class);
       resolver.setPrefix("/WEB-INF/jsp/");
       resolver.setSuffix(".jsp");
       return resolver;
   }

   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
       registry.viewResolver(viewResolver());
   }
}
