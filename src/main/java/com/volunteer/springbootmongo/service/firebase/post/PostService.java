package com.volunteer.springbootmongo.service.firebase.post;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.Http;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonParser;
import com.google.type.DateTime;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import org.bson.json.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
    final String COLLECTION_NAME = "Post";
    public ResponseObject savePost(Post post) throws ExecutionException, InterruptedException {


        Firestore dbFileStore = FirestoreClient.getFirestore();
        Long dateTime = System.currentTimeMillis();
        post.setDatecreated(dateTime.toString());

      ApiFuture<DocumentReference> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME).add(post);
      String id = collectionApiFuture.get().getId();

      return new ResponseObject(HttpStatus.CREATED.toString(),post);
    }

    public ResponseObject getPostDetail(String name) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFileStore.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Post post = null;
        if(document.exists()){
            post = document.toObject(Post.class);
            String dateTimeConverted = convertMilisToGMT7((Long.parseLong(post.getDatecreated())));
            String timeAgo = calTimeAgo(Long.parseLong(post.getDatecreated()));
            post.setDatecreated(dateTimeConverted);
            post.setTimeago(timeAgo);
            return new ResponseObject(HttpStatus.FOUND.toString(),post);
        }else {
            return new ResponseObject(HttpStatus.NOT_FOUND.toString(),null);
        }

    }
    public String convertMilisToGMT7(Long milisecond){
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        return formatter.format(milisecond).toString();
    }
    public String calTimeAgo(Long milis){


        Long currentTimeMillis = System.currentTimeMillis();
        Long result = (currentTimeMillis - milis);

        if(result < 60000){
            return result/1000+" giây trước";
        } else if(result < 3600000){
            return result/1000/60+" phút trước";
        } else if(result < 86400000){
            return result/1000/60/60+" giờ trước";
        } else if(result < 2592000000L){
            return result/1000/60/24 +" ngày trước";
        } else return result/1000/60/24/365 +" năm trước";
    }

    public List<Post> getAll() throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        CollectionReference posts = dbFileStore.collection(COLLECTION_NAME);
        return  posts.get().get().toObjects(Post.class);
    }
}