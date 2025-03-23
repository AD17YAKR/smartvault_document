package com.smartvault.document.document.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;

@Service
public class BackblazeService {

    private final S3Client s3Client;

    private static final String BUCKET_NAME = System.getenv("BACKBLAZE_BUCKET_NAME");
    private static final String ENDPOINT = System.getenv("BACKBLAZE_ENDPOINT");

    private static final String BASE_URL = ENDPOINT + "/" + BUCKET_NAME + "/";

    public BackblazeService(S3Client s3Client) {
        if (BUCKET_NAME == null || BUCKET_NAME.trim().isEmpty()) {
            throw new IllegalStateException("Bucket name is not configured in environment variables");
        }
        if (ENDPOINT == null || ENDPOINT.trim().isEmpty()) {
            throw new IllegalStateException("Endpoint is not configured in environment variables");
        }
        this.s3Client = s3Client;
    }

    public String uploadFile(String key, Path filePath) {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build(),
                filePath);

        return getPublicFileUrl(key);
    }

    public String getPublicFileUrl(String key) {
        return BASE_URL + key;
    }
}
