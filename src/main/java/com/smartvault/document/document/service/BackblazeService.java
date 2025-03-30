package com.smartvault.document.document.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;

@Service
public class BackblazeService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String endpoint;
    private final String baseUrl;

    public BackblazeService(
            S3Client s3Client,
            @Value("${backblaze.bucket-name}") String bucketName,
            @Value("${backblaze.endpoint}") String endpoint) {
        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new IllegalStateException("Bucket name is not configured in environment variables");
        }
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalStateException("Endpoint is not configured in environment variables");
        }
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
        this.baseUrl = endpoint + "/" + bucketName + "/";
    }

    public String uploadFile(String key, Path filePath) {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(),
                filePath);

        return getPublicFileUrl(key);
    }

    public String getPublicFileUrl(String key) {
        return baseUrl + key;
    }
}
