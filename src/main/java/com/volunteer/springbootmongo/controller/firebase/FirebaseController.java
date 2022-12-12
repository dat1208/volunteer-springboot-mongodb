package com.volunteer.springbootmongo.controller.firebase;


import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseObject savePost(@RequestBody Post post) throws ExecutionException, InterruptedException {
        return postService.savePost(post);
    }

    @GetMapping("/post/{name}")
    public ResponseObject getPost(@PathVariable String name) throws ExecutionException, InterruptedException {
        return postService.getPostDetail(name);
    }
    @GetMapping("/post/getAll")
    public List<Post> getAll() throws ExecutionException, InterruptedException {
        return postService.getAll();
    }
}
