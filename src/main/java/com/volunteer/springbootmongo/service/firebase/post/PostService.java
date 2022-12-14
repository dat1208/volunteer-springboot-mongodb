package com.volunteer.springbootmongo.service.firebase.post;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.type.DateTime;
import com.volunteer.springbootmongo.Donation.Model.DonateHistory;
import com.volunteer.springbootmongo.Donation.Model.DonateModel;
import com.volunteer.springbootmongo.Donation.Repository.DonateRepository;
import com.volunteer.springbootmongo.models.firebase.JoinPostModel;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.models.firebase.JoinPostModelCollection;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.firebase.TNPost;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.repository.UserRepository;
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
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private DonateRepository donateRepository;

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

      if(post.getType().equals(Post.type.QG))
          {
              DonateModel donateModel = new DonateModel(id,username,post.getDateStart(),post.getDateEnd(),true,post.getTitle(),post.getContent());
              donateModel.set_id(id);
              donateRepository.save(donateModel);
          }


      return new ResponseObject(HttpStatus.CREATED.toString(),post);
    }

    public ResponseObject join(String idPost, HttpServletRequest request) throws Exception {

        String username = jwtUserDetailsService.getUsernameByToken(request);
        JoinPostModel newJoin = new JoinPostModel(username, new Date());

        //Check user joined
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnPostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);

        ApiFuture<DocumentSnapshot> future = tnPostDoc.get();
        DocumentSnapshot documentSnapshot = future.get();

        JoinPostModelCollection joinPostModelCollection = documentSnapshot.toObject(JoinPostModelCollection.class);

        if (joinPostModelCollection.getJoinPostModelList() != null) {
            for (JoinPostModel value : joinPostModelCollection.getJoinPostModelList()) {
                if (value.getUsername().equals(username))
                    return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.name(), "JOIN_FAIL");
            }
            joinPostModelCollection.getJoinPostModelList().add(newJoin);
            tnPostDoc.update("joinPostModel",joinPostModelCollection);
        } else {
            JoinPostModelCollection newJoinPostModelCollection = new JoinPostModelCollection();
            List<JoinPostModel> list = new ArrayList<>();
            list.add(newJoin);
            newJoinPostModelCollection.setJoinPostModelList(list);
            ApiFuture<WriteResult> apiFuture = tnPostDoc.set(newJoinPostModelCollection);
        }

        try {
            User user = userRepository.findUserByEmail(username).get();
            if (user.getIdActivitiesList() == null) {
                List<String> activitiesList = new ArrayList<>();
                activitiesList.add(idPost);
                user.setIdActivitiesList(activitiesList);
            } else {
                user.getIdActivitiesList().add(idPost);
            }
            userRepository.save(user);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return new ResponseObject(HttpStatus.OK.toString(), "JOIN_OK");
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
            return result/1000+" gi??y tr?????c";
        } else if(result < 3600000){
            return result/1000/60+" ph??t tr?????c";
        } else if(result < 86400000){
            return result/1000/60/60+" gi??? tr?????c";
        } else if(result < 2592000000L){
            return result/1000/60/24 +" ng??y tr?????c";
        } else return result/1000/60/24/365 +" n??m tr?????c";
    }
    public void addCollectionTNPost(String id){

        Firestore dbFileStore = FirestoreClient.getFirestore();
        List<JoinPostModel> modelList = new ArrayList<>();
        TNPost tnPost = new TNPost(modelList);
        ApiFuture<WriteResult> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(id).create(tnPost);

    }
    public int getCurrentUsers(String idPost) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnpostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);
        List<JoinPostModel> modelList = new ArrayList<>();
        TNPost tnPost = new TNPost(modelList);
        try {
            modelList = tnpostDoc.get().get().toObject(TNPost.class).getJoinPostModel().stream().toList();
            tnPost.setJoinPostModel(modelList);
        } catch(Exception ex) {

        }
        System.out.println(modelList.stream().count());
        return (int) tnPost.getJoinPostModel().stream().count();
    }
    public List<String> getAvtCurrentUsers(String idPost){
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference tnpostDoc = dbFileStore.collection(COLLECTION_NAME_TNPOST).document(idPost);
        List<JoinPostModel> modelList = new ArrayList<>();

        List<String> listAvt = new ArrayList<>();
        try {
            modelList = tnpostDoc.get().get().toObject(TNPost.class).getJoinPostModel().stream().toList();
        } catch(Exception ex) {

        }

        if (modelList == null)
            return listAvt;

        for (JoinPostModel model:
                modelList) {
            listAvt.add(userService.getAvatar(model.getUsername()));
            if(listAvt.stream().count() >= 3)
                break;
        }
        return listAvt;
    }

    public List<Post> getpost(int limit, int begin) throws ExecutionException, InterruptedException {
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
            if(post.getType().equals(Post.type.QG)){
                post.setCurrentMoney(getCurrentMoney(post.getId()));
            }
            post.setIdOwner(userService.getId(post.getOwner()));
        }
        if(listPost.size() < begin || listPost.size() < begin+limit)
            return listPost;
        else {
            List<Post> listlimit = new ArrayList<>();
            int count = 0;

            for (int i = begin; i < begin+limit; i++) {
                listlimit.add(listPost.get(i));
            }
            return listlimit;
        }
    }

    private int getCurrentMoney(String id) {
        int total = 0;
        if(donateRepository.findDonateModelBy_id(id).isPresent()) {
            DonateModel donateModel = donateRepository.findDonateModelBy_id(id).get();
            List<DonateHistory> donateHistoryList = donateModel.getDonateHistoryList();
            for (DonateHistory d : donateHistoryList) {
                total += Integer.parseInt(d.getAmountDonate());
            }
        }
        return total;
    }
}
