package com.volunteer.springbootmongo.service.posts;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.volunteer.springbootmongo.models.firebase.Posts;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PostsService {
    final String COLLECTION_NAME = "Volunteer_Database";
    public String savePost(Posts post) throws ExecutionException, InterruptedException {


        Firestore dbFileStore = FirestoreClient.getFirestore();

      ApiFuture<WriteResult> collectionApiFuture = dbFileStore.collection(COLLECTION_NAME).document(post.getTitle()).set(post);
      return collectionApiFuture.get().getUpdateTime().toString();
    }

    public Posts getPostDetail(String name) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFileStore.collection(COLLECTION_NAME).document(name);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Posts posts = null;
        if(document.exists()){
            posts = document.toObject(Posts.class);
            return posts;
        }else {
            return null;
        }

    }
}
