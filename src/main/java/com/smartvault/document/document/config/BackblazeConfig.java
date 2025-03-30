package com.smartvault.document.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import java.net.URI;

@Configuration
public class BackblazeConfig {

        @Value("${backblaze.application-key-id}")
        private String keyId;

        @Value("${backblaze.application-key}")
        private String applicationKey;

        @Value("${backblaze.endpoint}")
        private String endpoint;

        @Bean
        public S3Client s3Client() {
                if (keyId == null || keyId.trim().isEmpty() ||
                                applicationKey == null || applicationKey.trim().isEmpty() ||
                                endpoint == null || endpoint.trim().isEmpty()) {
                        throw new IllegalStateException(
                                        "Backblaze credentials or endpoint are not properly configured in environment variables");
                }

                return S3Client.builder()
                                .region(Region.US_EAST_1)
                                .endpointOverride(URI.create(endpoint))
                                .credentialsProvider(StaticCredentialsProvider.create(
                                                AwsBasicCredentials.create(keyId, applicationKey)))
                                .build();
        }
}
