package com.web.board.book.domain.config.auth;

import com.web.board.book.domain.user.Role;
import com.web.board.book.domain.user.User;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  public static OAuthAttributes of(String registrationId,
      String userNameAttributeName, Map<String, Object> attributes) {
    log.info("regId: {}", registrationId);
    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofNaver(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get(
        "response");

    log.info("response: {}", response);
    return OAuthAttributes.builder()
        .name((String) response.get("name"))
        .email((String) response.get("email"))
        .picture((String) response.get("profile_image"))
        .attributes(response)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public User toEntity() {
    return User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(Role.GUEST)
        .build();
  }
}
