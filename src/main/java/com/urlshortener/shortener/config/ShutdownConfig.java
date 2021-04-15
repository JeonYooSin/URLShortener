package com.urlshortener.shortener.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig {

    @Bean
    public ShutdownCustom shutdown() {
        return new ShutdownCustom();
    }
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final ShutdownCustom shutDownCustom) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(shutDownCustom);
        return factory;
    }
}
