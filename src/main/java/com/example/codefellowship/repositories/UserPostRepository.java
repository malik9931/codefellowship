package com.example.codefellowship.repositories;

import com.example.codefellowship.models.UserPost;
import org.springframework.data.repository.CrudRepository;

import java.lang.ref.Reference;
import java.util.List;

public interface UserPostRepository extends CrudRepository<UserPost,Integer> {
    List<UserPost> findByUserId (Integer id);
}
