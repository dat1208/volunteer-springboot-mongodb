package com.volunteer.springbootmongo.service.firebase.upoad;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.volunteer.springbootmongo.models.firebase.ImageModel;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadService {
    @Autowired
    private UserService userService;
    public ResponseObject uploadImage(MultipartFile file, String id, String type) throws IOException{
        String wrongUsername = "wrong_username_format";
        String avt_cover = type+".png";
        String users_folder = "users";
        String file_content = "image/png";
        String blob = users_folder+"/"+id+"/"+avt_cover;
        Bucket bucket = StorageClient.getInstance().bucket();
        if(userService.usernameVal(id)){
            bucket.create(blob,file.getInputStream(),file_content).getName();

            Date dateCreated = new Date(bucket.get(blob).getCreateTime());
            Date dateUpdated = new Date(bucket.get(blob).getUpdateTime());

            String url = "https://storage.googleapis.com/volunteer-app-c93c9.appspot.com/"+blob;

            return new ResponseObject((HttpStatus.CREATED.toString()),new ImageModel(id,url, dateCreated, dateUpdated));
        }
        else return new ResponseObject((HttpStatus.NO_CONTENT.toString()),wrongUsername);
    }
    public String uploadPostImage(MultipartFile file, String id) throws IOException{
        String post_folder = "postImage";
        String postImage = "main.png";
        String file_content = "image/png";
        String blob = "postImage"+"/"+id+"/"+postImage;

        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(blob,file.getInputStream(),file_content).getName();

        Date dateCreated = new Date(bucket.get(blob).getCreateTime());
        Date dateUpdated = new Date(bucket.get(blob).getUpdateTime());

        String url = "https://storage.googleapis.com/volunteer-app-c93c9.appspot.com/"+blob;
        return url;
    }
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
