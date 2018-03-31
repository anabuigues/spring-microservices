package com.anabuigues.webservices.webservices.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anabuigues.webservices.webservices.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
