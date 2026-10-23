package com.learn.session.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.session.entity.UserDetailsEntity;

@Repository 
public interface UserRepository extends JpaRepository<UserDetailsEntity, UUID>{
    public UserDetailsEntity getByUsername(String usernamme);
}
