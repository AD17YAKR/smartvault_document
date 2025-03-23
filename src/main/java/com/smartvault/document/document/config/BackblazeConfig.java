package com.smartvault.document.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class BackblazeConfig {

    // Fetch values directly from environment variables
    private static final String KEY_ID = System.getenv("B2_APPLICATION_KEY_ID");
    private static final String APPLICATION_KEY = System.getenv("B2_APPLICATION_KEY");
    private static final String ENDPOINT = System.getenv("BACKBLAZE_ENDPOINT");

    @Bean
    public S3Client s3Client() {
        if (KEY_ID == null || KEY_ID.trim().isEmpty() ||
                APPLICATION_KEY == null || APPLICATION_KEY.trim().isEmpty() ||
                ENDPOINT == null || ENDPOINT.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Backblaze credentials or endpoint are not properly configured in environment variables");
        }

        return S3Client.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(KEY_ID, APPLICATION_KEY)))
                .build();
    }
}
