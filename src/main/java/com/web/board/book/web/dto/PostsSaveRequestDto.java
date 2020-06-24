package com.web.board.book.web.dto;

import com.web.board.book.domain.posts.Posts;
import com.web.board.book.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostsSaveRequestDto {

  private String title;
  private String content;
  private String author;

  public Posts toEntity(User user) {
    return Posts.builder()
        .title(title)
        .content(content)
        .author(author)
        .user(user)
        .build();
  }
}
