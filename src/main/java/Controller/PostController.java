package Controller;

import Configuration.CustomUserDetails;
import Entities.Post;
import Service.PostService;
import Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = postService. getAllPosts();
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        var existingUser = userService.findByUsername(user.getUsername());
        post.setUser(existingUser);
        postService.createPost(post);
        return "redirect:/";
    }

    @GetMapping("/userposts/{username}")
    public String getPostsByUsername(@PathVariable String username, Model model) {
        var user = userService.findByUsername(username);
        List<Post> userPosts = postService.getUserPostsByUsername(username);
        model.addAttribute("posts", userPosts);
        model.addAttribute("username", username); // To display the username in the view
        return "user-posts"; // Name of the view template to display the posts
    }

}
