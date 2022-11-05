package com.volunteer.springbootmongo.controller.firebase;


import com.volunteer.springbootmongo.models.firebase.Posts;
import com.volunteer.springbootmongo.service.posts.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
    @Autowired
    private PostsService postsService;

    @PostMapping("/post")
    public String savePost(@RequestBody Posts posts) throws ExecutionException, InterruptedException {
        return postsService.savePost(posts);
    }

    @GetMapping("/post/{name}")
    public Posts getPost(@PathVariable String name) throws ExecutionException, InterruptedException {
        return postsService.getPostDetail(name);
    }
}
