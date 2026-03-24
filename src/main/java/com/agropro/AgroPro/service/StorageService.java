package com.agropro.AgroPro.service;

import com.agropro.AgroPro.enums.ReportType;

import java.io.InputStream;

public interface StorageService {

    void uploadFile(byte[] file, String filename, ReportType reportType);

    InputStream downloadFile(String filename);

    void deleteFile(String filename);

}
