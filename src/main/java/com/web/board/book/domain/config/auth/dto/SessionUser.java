package com.web.board.book.domain.config.auth.dto;

import com.web.board.book.domain.user.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
  private String name;
  private String email;
  private String picture;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
  }
}
