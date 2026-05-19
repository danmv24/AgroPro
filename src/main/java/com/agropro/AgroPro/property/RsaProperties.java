package com.agropro.AgroPro.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
@Getter
@Setter
public class RsaProperties {

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

}
