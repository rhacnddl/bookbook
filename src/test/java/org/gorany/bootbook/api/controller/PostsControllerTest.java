package org.gorany.bootbook.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gorany.bootbook.api.domain.posts.Posts;
import org.gorany.bootbook.api.dto.PostsSaveRequestDto;
import org.gorany.bootbook.api.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@WithMockUser(roles = {"USER"})
@Disabled
class PostsControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper mapper;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/api/v1/posts";

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("?????? ?????????")
    void save_test() throws Exception {
        //given
        String title = "Test title";
        String content = "Test content";
        String author = "gorany";
        //when
        /**
         * Object??? JSON?????? ??????
         * */
        String body = mapper.writeValueAsString(
            PostsSaveRequestDto.builder().author(author).content(content).title(title).build()
        );
        //then
        mvc.perform(post(BASE_URL + "/")
                .content(body) //HTTP Body??? ???????????? ?????????
                .contentType(MediaType.APPLICATION_JSON) //????????? ???????????? ????????? ??????
            )
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
    }

    @Test
    @DisplayName("Posts Update Test")
    void Posts_Update_test() throws Exception {
        //given
        createPosts("First title", "First content", "First author");

        Long id = 1L;
        String updateTitle = "Updated Title";
        String updateContent = "Updated Content";
        String body = mapper.writeValueAsString(
            PostsUpdateRequestDto.builder().title(updateTitle).content(updateContent).build());
        //when

        //then
        mvc.perform(put(BASE_URL + "/" + id)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("1"));

        Posts afterResult = em.find(Posts.class, id);
        assertThat(afterResult.getTitle()).isEqualTo(updateTitle);
        assertThat(afterResult.getContent()).isEqualTo(updateContent);
    }

    private void createPosts(String title, String content, String author) throws Exception {
        em.persist(Posts.builder().title(title).content(content).author(author).build());
        em.flush();
        em.clear();
    }
}