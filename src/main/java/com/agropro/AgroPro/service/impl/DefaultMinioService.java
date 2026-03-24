package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.exception.DeleteStorageServiceException;
import com.agropro.AgroPro.exception.DownloadStorageServiceException;
import com.agropro.AgroPro.exception.UploadStorageServiceException;
import com.agropro.AgroPro.property.MinioProperties;
import com.agropro.AgroPro.service.StorageService;
import io.minio.*;
import io.minio.messages.Tags;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultMinioService implements StorageService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    @PostConstruct
    public void init() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                                .bucket(minioProperties.getBucket())
                        .build());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка инициализации minio", e);
        }
    }


    @Override
    public void uploadFile(byte[] file, String filename, ReportType reportType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(filename)
                        .stream(new ByteArrayInputStream(file), file.length, -1)
                        .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        .tags(Tags.newObjectTags(Map.of("reportType", reportType.name().toLowerCase())))
                    .build());
        } catch (Exception e) {
            throw new UploadStorageServiceException(filename, e);
        }
    }

    @Override
    public InputStream downloadFile(String filename) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(filename)
                            .build());
        } catch (Exception e) {
            throw new DownloadStorageServiceException(filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(filename)
                            .build());
        } catch (Exception e) {
            throw new DeleteStorageServiceException(filename, e);
        }
    }


}
