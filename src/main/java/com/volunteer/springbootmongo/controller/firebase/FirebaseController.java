package com.volunteer.springbootmongo.controller.firebase;


import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.Activities.ActivitiesService;
import com.volunteer.springbootmongo.service.firebase.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ActivitiesService activitiesService;
    @PostMapping("/post")
    public ResponseObject savePost(@RequestParam("file") MultipartFile file,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "content") String content,
                                   @RequestParam(name = "subtitle",required = false) String subtitle,
                                   @RequestParam(name = "type") Post.type type,
                                   @RequestParam(name = "address") String address,
                                   @RequestParam(name = "totalUsers",required = false,defaultValue = "0") int totalUsers,
                                   @RequestParam(name = "totalMoney",required = false,defaultValue = "0") int totalMoney, HttpServletRequest request) throws Exception {

        return postService.savePost(new Post(content,title,subtitle,address, type,totalMoney,totalUsers),file,request);
    }

    @GetMapping("/post/{name}")
    public ResponseObject getPost(@PathVariable String name) throws ExecutionException, InterruptedException {
        return postService.getPostDetail(name);
    }
    @GetMapping("/post/getpost")
    public List<Post> getpost(@RequestParam(name = "limit") int limit,
                              @RequestParam(name = "begin") int begin) throws ExecutionException, InterruptedException {
        return postService.getpost(limit,begin);
    }
    @PostMapping("/post/join")
    public ResponseObject join(@RequestParam(name = "activitiesID") String idPost, HttpServletRequest request) throws Exception {
        System.out.println(idPost);
        return postService.join(idPost,request);
    }

    @PostMapping("/post/getAct")
    public ResponseEntity<?> getActivitiesCurrentUser(@RequestParam(name = "userID") String userID) {
        try {
            return activitiesService.getActivitiesCurrentUser(userID);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
