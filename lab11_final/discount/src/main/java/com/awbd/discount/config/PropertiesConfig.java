package com.awbd.discount.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("coupon")
@Getter
@Setter
public class PropertiesConfig {
    private String versionId;
    private int month;
    private String socialCategory;
    private String specialities;
}