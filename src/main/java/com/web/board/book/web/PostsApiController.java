package com.web.board.book.web;

import com.web.board.book.config.auth.LoginUser;
import com.web.board.book.config.auth.dto.SessionUser;
import com.web.board.book.domain.user.User;
import com.web.board.book.service.PostsService;
import com.web.board.book.web.dto.PostsResponseDto;
import com.web.board.book.web.dto.PostsSaveRequestDto;
import com.web.board.book.web.dto.PostsUpdateRequestDto;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto reqeustDto,
      @LoginUser SessionUser user) {
    return postsService.save(reqeustDto, user.getEmail());
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id,
      @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    return postsService.findById(id);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }
}
