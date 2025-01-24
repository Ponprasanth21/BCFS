package com.bornfire.configuration;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IPSWebApp implements WebMvcConfigurer{
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("AStartpage");
        registry.addViewController("/").setViewName("AStartpage");


        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
       
    }
	
	
	@Bean
	protected javax.servlet.Filter[] metricsFilter() {
		DelegatingFilterProxy delegateFilterProxy = new DelegatingFilterProxy();
		delegateFilterProxy.setTargetBeanName("metricsFilter");
		return new Filter[] { delegateFilterProxy };
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer init() {
	    return new Jackson2ObjectMapperBuilderCustomizer() {
	        @Override
	        public void customize(Jackson2ObjectMapperBuilder builder) {
	            builder.timeZone(TimeZone.getDefault());
	        }
	    };
	}
	
}
