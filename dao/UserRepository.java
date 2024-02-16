package com.museumPlus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.museumPlus.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
