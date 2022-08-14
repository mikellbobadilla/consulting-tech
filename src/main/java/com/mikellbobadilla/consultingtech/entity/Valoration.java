package com.mikellbobadilla.consultingtech.entity;

import javax.persistence.*;

@Entity
@Table(name = "valorations")
public class Valoration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "id_user")
  private AppUser idUser;
  @ManyToOne
  @JoinColumn(name = "id_post")
  private Post idPost;
  private Boolean value;

  public Valoration() {
  }

  public Valoration(Long id, AppUser idUser, Post idPost, Boolean value) {
    this.id = id;
    this.idUser = idUser;
    this.idPost = idPost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AppUser getIdUser() {
    return idUser;
  }

  public void setIdUser(AppUser idUser) {
    this.idUser = idUser;
  }

  public Post getIdPost() {
    return idPost;
  }

  public void setIdPost(Post idPost) {
    this.idPost = idPost;
  }
}