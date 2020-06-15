package com.web.board.book.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class HelloResponseDto {

  private final String name;
  private final int amount;

}
