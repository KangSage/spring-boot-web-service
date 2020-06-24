package com.web.board.book.domain.posts;

import com.web.board.book.domain.BaseTimeEntity;
import com.web.board.book.domain.user.User;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "제목은 빈 문자열이 불가합니다.")
  @Column(length = 500, nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private User user;

  @Builder
  public Posts(String title, String content, String author, User user) {
    this.title = title;
    this.content = content;
    this.author = author;
    this.user = user;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

}
