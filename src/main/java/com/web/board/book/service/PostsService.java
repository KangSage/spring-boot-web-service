package com.web.board.book.service;

import com.web.board.book.domain.posts.Posts;
import com.web.board.book.domain.posts.PostsRepository;
import com.web.board.book.domain.user.User;
import com.web.board.book.domain.user.UserRepository;
import com.web.board.book.web.dto.PostsListResponseDto;
import com.web.board.book.web.dto.PostsResponseDto;
import com.web.board.book.web.dto.PostsSaveRequestDto;
import com.web.board.book.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  public PostsResponseDto findById(Long id) {
    Posts entity = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    return new PostsResponseDto(entity);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository
        .findAllByOrderByIdDesc()
        .stream()
        .map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
    postsRepository.delete(posts);
  }

}
