package org.gorany.bootbook.api.dto;

import lombok.Getter;
import org.gorany.bootbook.api.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts posts) {
        id = posts.getId();
        title = posts.getTitle();
        author = posts.getAuthor();
        modifiedDate = posts.getModifiedDate();
    }
}
