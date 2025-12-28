package com.linguanova.idiomago.external.cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    public static String cloudName;
    public static String apiKey;
    public static String apiSecret;

    public CloudinaryConfig(
            @Value("${CLOUDINARY_CLOUD_NAME}") String name,
            @Value("${CLOUDINARY_API_KEY}") String key,
            @Value("${CLOUDINARY_API_SECRET}") String secret) {
        CloudinaryConfig.cloudName = name;
        CloudinaryConfig.apiKey = key;
        CloudinaryConfig.apiSecret = secret;
    }
}
