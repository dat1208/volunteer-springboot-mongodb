package com.volunteer.springbootmongo.service.upoad;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    public String uploadObject(File file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        StorageClient storage = StorageClient.getInstance();
        return null;
    }
}
