package com.project.smartICT.controllers;

import com.project.smartICT.entities.Post;
import com.project.smartICT.requests.PostCreateRequest;
import com.project.smartICT.requests.PostUpdateRequest;
import com.project.smartICT.responses.AuthResponse;
import com.project.smartICT.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPost){
        return postService.createOnePost(newPost);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId){
        return postService.getOnePost(postId);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost){
        return postService.updateOnePost(postId, updatePost);
    }

    @DeleteMapping("/{postId}")
    public AuthResponse deleteOnePost(@PathVariable Long postId){
        return postService.deleteOnePost(postId);
    }

}
