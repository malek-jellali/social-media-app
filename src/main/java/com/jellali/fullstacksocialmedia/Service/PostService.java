package com.jellali.fullstacksocialmedia.Service;


import com.jellali.fullstacksocialmedia.Entities.Post;
import com.jellali.fullstacksocialmedia.Repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepository ;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }



    public List<Post> getUserPostsByUsername(String username){
         return postRepository.findByUser_Username(username);
    }
    public List<Post> getUsernameOfPost(String username){
         return postRepository.findByUser_Username(username);
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }


}
