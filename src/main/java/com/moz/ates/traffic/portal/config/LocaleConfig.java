package com.moz.ates.traffic.portal.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@Configuration 
public class LocaleConfig implements WebMvcConfigurer{ 
	
	@Bean
	public LocaleResolver  localeResolver() {
		
		CookieLocaleResolver resolver = new CookieLocaleResolver(); // resolver.setDefaultLocale(Locale.KOREAN); // 기본값 강제 한국어 설정. 
		resolver.setCookieName("lang");
//		resolver.setDefaultLocale(new Locale("por"));
		resolver.setDefaultLocale(new Locale("eng"));
		return resolver;
	}

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource(
        @Value("${spring.messages.basename}") String basename,
        @Value("${spring.messages.encoding}") String encoding
    ) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basename.split(","));
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }

}

