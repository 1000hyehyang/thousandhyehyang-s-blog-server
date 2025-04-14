package com.example.blog.storage.r2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
public class R2StorageService {

    private final S3Client s3Client;

    @Value("${cloud.r2.bucket}")
    private String bucket;

    public R2StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
                file.getInputStream(), file.getSize()
        ));

        return bucketUrl(fileName);
    }

    private String bucketUrl(String key) {
        return String.format("https://%s.%s/%s", bucket,
                s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(key)).host(),
                key);
    }
}
