package com.volunteer.springbootmongo.service.firebase.post;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.upoad.UploadService;
import com.volunteer.springbootmongo.service.jwt.JwtUserDetailsService;
import com.volunteer.springbootmongo.service.user.UserService;
import org.bson.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
    final String COLLECTION_NAME = "Post";

    @Autowired
    private UploadService uploadService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;
    public ResponseObject savePost(Post post, MultipartFile file, HttpServletRequest request) throws Exception {


        Firestore dbFileStore = FirestoreClient.getFirestore();


      ApiFuture<DocumentReference> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME).add(post);
      String id = collectionApiFuture.get().getId();
      String url = uploadService.uploadPostImage(file,id);
          post.setMainimage(url);
          post.setId(id);
          Long dateTime = System.currentTimeMillis();
          post.setDatecreated(dateTime.toString());
          post.setType(Post.type.QG);
      collectionApiFuture.get().set(post);
      //Append post to users
      String username = jwtUserDetailsService.getUsernameByToken(request);
      userService.insertPost(id,username);
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
        List<Post> listPost = posts.get().get().toObjects(Post.class).stream().toList();
        return  listPost;
    }
}
