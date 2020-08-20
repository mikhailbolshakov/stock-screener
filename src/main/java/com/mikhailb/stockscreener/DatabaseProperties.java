package com.mikhailb.stockscreener;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@Configuration
@ConfigurationProperties("config.config-dev.yml.")
public class DatabaseProperties {

    private String connection;

    public String getConnection() { return connection;}
    public void setConnection(String connection) {this.connection = connection;}

}
