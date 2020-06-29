package com.web.board.book.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.board.book.config.auth.dto.SessionUser;
import com.web.board.book.domain.posts.Posts;
import com.web.board.book.domain.posts.PostsRepository;
import com.web.board.book.domain.user.Role;
import com.web.board.book.domain.user.User;
import com.web.board.book.domain.user.UserRepository;
import com.web.board.book.web.dto.PostsSaveRequestDto;
import com.web.board.book.web.dto.PostsUpdateRequestDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PostsApiControllerTest {
  @LocalServerPort
  private int port;

//  @Autowired
//  private TestRestTemplate restTemplate;
  private final MockHttpSession session = new MockHttpSession();

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .alwaysDo(print())
        .build();
  }

  @After
  public void tearDown() throws Exception {
    postsRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @WithMockUser(roles = "USER")
  public void Posts_등록된다() throws Exception {

    User user = userRepository.save(User.builder()
        .name("홍길동")
        .email("ksage@knou.ac.kr")
        .role(Role.USER)
        .picture("")
        .build());

    session.setAttribute("user", new SessionUser(user));

    // given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();

    String url = new StringBuilder()
        .append("http://localhost:")
        .append(port)
        .append("/api/v1/posts")
        .toString();

    // use mvc when
    mvc.perform(post(url)
        .session(session)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andDo(print())
        .andExpect(status().isOk());

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
  }

  @Test
  @WithMockUser(roles = "USER")
  public void Posts_수정된다() throws Exception {
    // given
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent ="content2";

    PostsUpdateRequestDto requestDto =
        PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

    // use mvc when
    mvc.perform(put(url)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andDo(print())
        .andExpect(status().isOk());

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }
}
