package org.gorany.bootbook.api.service;

import java.util.List;
import javax.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.gorany.bootbook.api.domain.posts.Posts;
import org.gorany.bootbook.api.domain.posts.PostsRepository;
import org.gorany.bootbook.api.dto.PostsListResponseDto;
import org.gorany.bootbook.api.dto.PostsResponseDto;
import org.gorany.bootbook.api.dto.PostsSaveRequestDto;
import org.gorany.bootbook.api.dto.PostsUpdateRequestDto;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsResponseDto findById(Long id) {
        return PostsResponseDto.of(postsRepository.findById(id).orElseThrow(() -> new NoResultException("Posts ID가 "
            + "없습니다.")));
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new NoResultException("Posts ID가 없습니다."));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        postsRepository.delete(
            postsRepository.findById(id).orElseThrow(() -> new NoResultException("Posts ID가 없습니다.")));
        return id;
    }

    public Slice<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().map(PostsListResponseDto::new);
    }
}
