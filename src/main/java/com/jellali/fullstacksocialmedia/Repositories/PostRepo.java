package com.jellali.fullstacksocialmedia.Repositories;

import com.jellali.fullstacksocialmedia.Entities.Post;
import com.jellali.fullstacksocialmedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, User> {
    Post save(Post postDto);

    @Override
    List<Post> findAll();

    List<Post> findByUser_Username(String username);

    List<Post> findAllByOrderByCreatedAtDesc();
}
