package org.gorany.bookbook.api.dto;

import lombok.Getter;
import org.gorany.bookbook.api.domain.posts.Posts;

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
