package com.sheikplastic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "empresa")
public class EmpresaProperties {
    private String razaoSocial;
    private String nomeFantasia;
    private String site;
    private String email;
}
