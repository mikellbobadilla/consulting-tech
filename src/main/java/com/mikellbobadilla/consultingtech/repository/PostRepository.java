package com.mikellbobadilla.consultingtech.repository;

import com.mikellbobadilla.consultingtech.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

  List<Post> findPostByIdUser(Long idUser);
}
