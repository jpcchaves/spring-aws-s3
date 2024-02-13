package com.springaws.springaws.application.service.s3;

public interface S3Service {
    void putObject(String bucketName, String key, byte[] file);
    byte[]  getObject(String bucketName, String key);
}
