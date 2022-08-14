package com.mikellbobadilla.consultingtech.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "id_post")
  private Post idPost;
  @ManyToOne
  @JoinColumn(name = "id_user")
  private AppUser idUser;
  private String comment;

  public Comment() {
  }

  public Comment(Long id, Post idPost, AppUser idUser, String comment) {
    this.id = id;
    this.idPost = idPost;
    this.idUser = idUser;
    this.comment = comment;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Post getIdPost() {
    return idPost;
  }

  public void setIdPost(Post idPost) {
    this.idPost = idPost;
  }

  public AppUser getIdUser() {
    return idUser;
  }

  public void setIdUser(AppUser idUser) {
    this.idUser = idUser;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}