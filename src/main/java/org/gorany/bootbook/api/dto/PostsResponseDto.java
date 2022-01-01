package org.gorany.bootbook.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gorany.bootbook.api.domain.posts.Posts;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public static PostsResponseDto of(Posts posts) {
        return PostsResponseDto.builder()
            .id(posts.getId())
            .title(posts.getTitle())
            .content(posts.getContent())
            .author(posts.getAuthor())
            .build();
    }
}
