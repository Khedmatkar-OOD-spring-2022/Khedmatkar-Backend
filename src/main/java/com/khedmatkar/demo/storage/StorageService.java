package com.khedmatkar.demo.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${baseStoragePath}")
    private String baseStoragePath;


    public String storeFile(MultipartFile file) throws IOException {
        createRootDirIfNotExists();
        var path = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();;
        File baseFile = new File(baseStoragePath);
        File destFile = new File(baseFile.getAbsolutePath(), path);
        file.transferTo(destFile);
        return path;
    }

    public Resource loadAsResource(String path) throws FileNotFoundException {
        var file = new File(baseStoragePath, path);
        InputStream in = new FileInputStream(file);
        return new InputStreamResource(in);
    }

    private void createRootDirIfNotExists() {
        var rootDir = new File(baseStoragePath);
        if (!rootDir.exists()) {
            boolean mkdirs = rootDir.mkdirs();
        }
    }
}
