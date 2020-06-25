package com.web.board.book.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.board.book.domain.user.Role;
import com.web.board.book.domain.user.User;
import com.web.board.book.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
  @Autowired
  PostsRepository postsRepository;

  @Autowired
  UserRepository userRepository;

  @After
  public void cleanup() {
    postsRepository.deleteAll();
  }

  @Before
  public void signupUser() {
    userRepository.save(User.builder()
        .name("강성현")
        .role(Role.USER)
        .email("ksage@knou.ac.kr")
        .build());
  }

  @Test
  public void 게시글저장_불러오기() {

    Optional<User> user = userRepository.findByEmail("ksage@knou.ac.kr");
    // given
    String title = "테스트 게시글";
    String content = "테스트 본문";

    postsRepository.save(Posts.builder()
        .content(content)
        .title(title)
        .author("ksage@knou.ac.kr")
        .user(user.get())
        .build()
    );

    // when
    List<Posts> postsList = postsRepository.findAll();

    // then
    Posts posts = postsList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

  @Test
  public void BaseTimeEntity_등록() {
    // given
    LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
    postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    // when
    List<Posts> postsList = postsRepository.findAll();

    // then
    Posts posts = postsList.get(0);
    System.out.println(">>>>>>> createDAte=" + posts.getCreatedDate() +
        ", modifiedDate=" + posts.getModifiedDate());

    assertThat(posts.getCreatedDate()).isAfter(now);
    assertThat(posts.getModifiedDate()).isAfter(now);
  }
}
