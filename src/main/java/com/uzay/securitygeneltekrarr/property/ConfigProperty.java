package com.uzay.securitygeneltekrarr.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix ="degiskenler")
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperty {

    private String isim;

    private List<String> sehirler;

    private List<String> ulke;

    private Map<String,String> okul;




}
