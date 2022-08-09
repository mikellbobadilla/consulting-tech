package com.mikellbobadilla.consultingtech.repository;

import com.mikellbobadilla.consultingtech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

 /* @Query(value = "SELECT username FROM app_user WHERE username.name = :name", nativeQuery = true)*/
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}
