package org.gorany.bookbook.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @WebMvcTest
 * - JPA 기능은 동작하지 않는다.
 * - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에만 집중할 수 있는 어노테이션
 * - @Controller, @ControllerAdvice 사용 가능
 * - 단, @Service, @Repository등은 사용할 수 없다.
 * */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
@Disabled
class HelloControllerTest {

    @Autowired
    /**
     * 웹 API 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * HTTP GET,POST 등에 대해 API 테스트 가능
     * */
    MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Hello Test")
    void Hello_Test() throws Exception {
        //given
        String hello = "hello";
        //when

        //then
        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 GET 요청
             //mvc.perform()의 결과를 검증
            .andExpect(status().isOk()) //200 상태
            .andExpect(content().string(hello)); //응답 본문의 내용을 검증
    }

    @Test
    @DisplayName("헬로 디티오 컨트롤러 테스트")
    void HelloControllerDtoTest() throws Exception {
        //given
        String name = "Test";
        int amount = 10000;
        //when
        //then
        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", Integer.toString(amount))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.amount").value(amount));
    }
}