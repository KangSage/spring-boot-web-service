package com.web.board.book.web.dto;

import com.web.board.book.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
  private Long id;
  private String title;
  private String content;
  private String author;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
  }
}
