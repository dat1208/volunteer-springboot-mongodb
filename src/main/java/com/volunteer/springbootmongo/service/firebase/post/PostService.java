package com.volunteer.springbootmongo.service.firebase.post;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.firebase.TNPost;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.upoad.UploadService;
import com.volunteer.springbootmongo.service.jwt.JwtUserDetailsService;
import com.volunteer.springbootmongo.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
    final String COLLECTION_NAME_POST = "Post";
    final String COLLECTION_NAME_TNPOST = "TNPost";

    @Autowired
    private UploadService uploadService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;
    public ResponseObject savePost(Post post, MultipartFile file, HttpServletRequest request) throws Exception {
      Firestore dbFileStore = FirestoreClient.getFirestore();
      ApiFuture<DocumentReference> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME_POST).add(post);
      String username = jwtUserDetailsService.getUsernameByToken(request);
      String id = collectionApiFuture.get().getId();
      String url = uploadService.uploadPostImage(file,id);
          post.setMainimage(url);
          post.setId(id);
          Long dateTime = System.currentTimeMillis();
          post.setDatecreated(dateTime.toString());
          post.setOwner(username);
          post.setAvtOwner(userService.getAvatar(username));
          post.setNameOwner(userService.getUserByUsername(username).getFirstname()+" "+userService.getUserByUsername(username).getLastname());
      if(post.getType().equals(Post.type.TN) && post.getTotalUsers() == 0)
          return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "TN post need totalUsers");
      if(post.getType().equals(Post.type.QG)  && post.getTotalMoney() == 0)
            return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "QG post need totalMoney");
      if(post.getType().equals(Post.type.HP) && post.getTotalMoney() == 0)
          return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "HP post need totalMoney");
      collectionApiFuture.get().set(post);
      //Append post to users

      userService.insertPost(id,username);
      //Create collection to save users join TN post
      if(post.getType().equals(Post.type.TN))
            addCollectionTNPost(id);
      return new ResponseObject(HttpStatus.CREATED.toString(),post);
    }

    public ResponseObject join(String idPost, HttpServletRequest request) throws Exception {
        String username = jwtUserDetailsService.getUsernameByToken(request);
        //Check user joined
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnpostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);
        List<String> listUsers = null;
        try {
            listUsers = tnpostDoc.get().get().toObject(TNPost.class).getListUsers();
        } catch (Exception ex) {
        }
        if(listUsers != null)
        {
            for (String un :
                    listUsers) {
                if (un.equals(username))
                    return new ResponseObject(HttpStatus.CONFLICT.toString(), "users_joined");
            }
            listUsers.add(username);
            ApiFuture<WriteResult> future = tnpostDoc.update("listUsers", listUsers);
        }
        else if(listUsers == null){
            List<String> initListUsers = new ArrayList<>();
            initListUsers.add(username);
            ApiFuture<WriteResult> future = tnpostDoc.update("listUsers", initListUsers);
        }

        return new ResponseObject(HttpStatus.OK.toString(), "successful");
    }
    public ResponseObject getPostDetail(String name) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFileStore.collection(COLLECTION_NAME_POST).document(name);
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
    public void addCollectionTNPost(String id){

        Firestore dbFileStore = FirestoreClient.getFirestore();
        List<String> listUsers = new ArrayList<>();
        TNPost tnPost = new TNPost();
        ApiFuture<WriteResult> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(id).create(tnPost);

    }
    public int getCurrentUsers(String idPost) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnpostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);
        List<String> listUsers = new ArrayList<>();
        try {
            listUsers = tnpostDoc.get().get().toObject(TNPost.class).getListUsers();
        } catch(Exception ex) {

        }

        if (listUsers == null)
            return 0;
        return (int) listUsers.stream().count();
    }
    public List<String> getAvtCurrentUsers(String idPost){
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnpostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);
        List<String> listUsers = new ArrayList<>();
        List<String> listAvt = new ArrayList<>();
        try {
            listUsers = tnpostDoc.get().get().toObject(TNPost.class).getListUsers();
        } catch(Exception ex) {

        }

        if (listUsers == null)
            return listAvt;

        for (String user:
             listUsers) {
            listAvt.add(userService.getAvatar(user));
            if(listAvt.stream().count() >= 3)
                break;
        }
        return listAvt;
    }
    public List<Post> getAll() throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        CollectionReference posts = dbFileStore.collection(COLLECTION_NAME_POST);
        List<Post> listPost = posts.get().get().toObjects(Post.class).stream().toList();
        for (Post post:listPost) {
            if(post.getDatecreated() != null)
            post.setTimeago(calTimeAgo(Long.valueOf((post.getDatecreated()))));
            if(post.getType().equals(Post.type.TN)){
                post.setCurrentUsers(getCurrentUsers(post.getId()));
                post.setAvtCurrentUsers(getAvtCurrentUsers(post.getId()));
            }
        }
        return  listPost;
    }
}
