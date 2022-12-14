package com.volunteer.springbootmongo.controller.firebase;


import com.volunteer.springbootmongo.config.JwtTokenUtil;
import com.volunteer.springbootmongo.models.firebase.Post;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.firebase.post.PostService;
import com.volunteer.springbootmongo.service.jwt.JwtUserDetailsService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/post")
    public ResponseObject savePost(@RequestParam("file") MultipartFile file,
                                   @RequestParam(name = "title") String title,
                                   @RequestParam(name = "content") String content,
                                   @RequestParam(name = "subtitle") String subtitle,
                                   @RequestParam(name = "address") String address,
                                   @RequestParam(name = "type") Post.type type) throws ExecutionException, InterruptedException, IOException {
        return postService.savePost(new Post(content,title,subtitle,address,type),file);
    }

    @GetMapping("/post/{name}")
    public ResponseObject getPost(@PathVariable String name) throws ExecutionException, InterruptedException {
        return postService.getPostDetail(name);
    }

    @GetMapping("/post/getAll")
    public List<Post> getAll(HttpServletRequest request) throws Exception {

        System.out.println(jwtUserDetailsService.getUsernameByToken(request));
        return postService.getAll();
    }
}
