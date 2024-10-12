package Repositories;

import Entities.Post;
import Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Post> {
    User findByUsername(String username);

    User save(User userDto);

}
