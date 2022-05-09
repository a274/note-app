package com.khlopovskaya.noteapp.repository;

import com.khlopovskaya.noteapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByLogin(String login);
    Long deleteById(int id);
    User findById(int id);
}