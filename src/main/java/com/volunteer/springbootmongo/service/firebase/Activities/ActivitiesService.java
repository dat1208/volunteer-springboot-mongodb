package com.volunteer.springbootmongo.service.firebase.Activities;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author "KhaPhan" on 21-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Service
public class ActivitiesService {
    private final String COLLECTION_NAME_POST = "Post";
    private final String COLLECTION_NAME_TN_POST = "TNPost";
    @Autowired
    private UserRepository userRepository;
    private String getJsonFromObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public ResponseEntity<?> getActivitiesCurrentUser(String userID) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        List<String> activitiesListID = userRepository.findById(userID).get().getIdActivitiesList();
        List<ActivitiesModel> responseActivitiesList = new ArrayList<>();
        for (String id : activitiesListID) {
            DocumentReference documentReference = dbFileStore.collection(COLLECTION_NAME_POST).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot documentSnapshot = future.get();
            Post post = documentSnapshot.toObject(Post.class);

            responseActivitiesList.add(new ActivitiesModel(
                post.getId(),
                    post.getOwner(),
                    post.getAvtOwner(),
                    post.getTitle(),
                    post.getTimeago(),
                    post.getAddress(),
                    post.getCurrentUsers(),
                    false,
                    getJsonFromObject(new QRModel(userID, post.getId()))
            ));
        }
        if(responseActivitiesList.isEmpty()) {
            return ResponseEntity.ok().body("NO_ACC");
        }
        return ResponseEntity.ok().body(responseActivitiesList);
    }
}
