package org.gorany.bootbook.api.domain.posts;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class PostsRepositoryTest {

    @Autowired PostsRepository postsRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("게시글 저장 불러오기")
    void save_load_test() throws Exception {
        //given
        String title = "Title";
        String content = "Content";
        String author = "gorany@naver.com";

        Posts posts = Posts.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
        Posts save = postsRepository.save(posts);
        em.flush();
        em.clear();
        //when
        List<Posts> result = postsRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getAuthor()).isEqualTo(author);
        assertThat(result.get(0).getContent()).isEqualTo(content);
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getId()).isNotNull();
    }

    @Test
    @DisplayName("BaseTimeEntity 테스트")
    void 날짜가_생겼나() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        String title = "Title";
        String content = "Content";
        String author = "gorany@naver.com";

        Posts posts = Posts.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
        //when
        Posts save = postsRepository.save(posts);
        em.flush();
        em.clear();
        //then
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}