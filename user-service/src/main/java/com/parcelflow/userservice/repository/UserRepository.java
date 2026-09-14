package com.parcelflow.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcelflow.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
