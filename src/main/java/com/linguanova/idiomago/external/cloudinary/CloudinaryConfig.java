package com.linguanova.idiomago.external.cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    public static String cloudName;
    public static String apiKey;
    public static String apiSecret;

    public CloudinaryConfig(
            @Value("${cloudinary.cloud.name}") String name,
            @Value("${cloudinary.api.key}")  String key,
            @Value("${cloudinary.api.secret}")  String secret) {
        CloudinaryConfig.cloudName = name;
        CloudinaryConfig.apiKey = key;
        CloudinaryConfig.apiSecret = secret;
    }
}
