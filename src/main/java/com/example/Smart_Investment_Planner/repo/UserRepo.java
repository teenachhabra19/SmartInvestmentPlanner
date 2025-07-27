package com.example.Smart_Investment_Planner.repo;

import com.example.Smart_Investment_Planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);

}
