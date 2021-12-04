package org.gorany.bootbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.gorany.bootbook.api.dto.PostsResponseDto;
import org.gorany.bootbook.api.dto.PostsSaveRequestDto;
import org.gorany.bootbook.api.dto.PostsUpdateRequestDto;
import org.gorany.bootbook.api.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsController {

    private final PostsService postsService;

    /**
     * Posts를 생성한다.
     *
     * @param requestDto
     * @return id
     * */
    @PostMapping("/")
    public ResponseEntity<Long> save(@RequestBody PostsSaveRequestDto requestDto) {
        return new ResponseEntity<>(postsService.save(requestDto), HttpStatus.OK);
    }

    /**
     * Posts를 조회한다.
     *
     * @param id
     * @return posts
     * */
    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<PostsResponseDto>(postsService.findById(id), HttpStatus.OK);
    }

    /**
     * Posts를 수정한다.
     *
     * @param id
     * @param requestDto
     * */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        postsService.update(id, requestDto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * Posts를 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        postsService.delete(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
