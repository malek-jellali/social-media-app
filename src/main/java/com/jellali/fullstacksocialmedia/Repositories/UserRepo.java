package com.jellali.fullstacksocialmedia.Repositories;

import com.jellali.fullstacksocialmedia.Entities.Post;
import com.jellali.fullstacksocialmedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Post> {
    User findByUsername(String username);

    User save(User userDto);

}
