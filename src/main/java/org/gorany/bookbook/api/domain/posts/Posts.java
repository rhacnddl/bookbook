package org.gorany.bookbook.api.domain.posts;

import lombok.*;
import org.gorany.bookbook.api.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Posts extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //VARCHAR -> TEXT
    private String content;

    private String author;

    //비즈니스 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
