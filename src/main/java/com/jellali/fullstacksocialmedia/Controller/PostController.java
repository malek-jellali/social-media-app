package com.jellali.fullstacksocialmedia.Controller;

import com.jellali.fullstacksocialmedia.Configuration.CustomUserDetails;
import com.jellali.fullstacksocialmedia.Entities.Post;
import com.jellali.fullstacksocialmedia.Service.PostService;
import com.jellali.fullstacksocialmedia.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }



    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "add";
    }

    @PostMapping("/add")
    public String addPost( Post post , Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        var existingUser = userService.findByUsername(user.getUsername());
        post.setUser(existingUser);
        post.setCreatedAt(LocalDateTime.now());
        postService.createPost(post);
        List<Post> userPosts = postService.getAllPosts();
        model.addAttribute("posts", userPosts);
        return "redirect:/home";
    }





    @GetMapping("/search")
    public String getPostsByUsername(@RequestParam String username, Model model) {
        var user = userService.findByUsername(username);
        List<Post> userPosts = postService.getUserPostsByUsername(username);
        model.addAttribute("posts", userPosts);
        model.addAttribute("username", username);
        return "search";
    }


    @GetMapping("/home")
    public String getAllPosts(Model model) {
        List<Post> userPosts = postService.getAllPosts();
        if (userPosts.isEmpty()) {
            System.out.println("No posts found.");
        } else {
            userPosts.forEach(post -> System.out.println("Post: " + post.getContent()));
        }
        model.addAttribute("posts", userPosts);
        return "home";
    }







}
