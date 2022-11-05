package com.volunteer.springbootmongo.controller.upload;


import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.upoad.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("/avatar")
    public ResponseObject upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {

        return uploadService.uploadImage(file,username);
    }
}
