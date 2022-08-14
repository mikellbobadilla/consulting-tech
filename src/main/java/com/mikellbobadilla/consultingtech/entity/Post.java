package com.mikellbobadilla.consultingtech.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  private Date date;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private AppUser idUser;

  public Post() {
  }

  public Post(Long id, String title, String content, Date date, AppUser idUser) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.date = date;
    this.idUser = idUser;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public AppUser getIdUser() {
    return idUser;
  }

  public void setIdUser(AppUser idUser) {
    this.idUser = idUser;
  }

}
