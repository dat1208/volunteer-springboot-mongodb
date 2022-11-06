package com.volunteer.springbootmongo.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Identity;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization(){

        FileInputStream serviceAccount = null;
        try {
            serviceAccount = serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("volunteer-app-c93c9.appspot.com")
                .build();
        FirebaseApp.initializeApp(options);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
