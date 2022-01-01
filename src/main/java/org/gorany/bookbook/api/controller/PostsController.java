package org.gorany.bookbook.api.controller;

import lombok.RequiredArgsConstructor;
import org.gorany.bookbook.api.dto.PostsResponseDto;
import org.gorany.bookbook.api.dto.PostsSaveRequestDto;
import org.gorany.bookbook.api.dto.PostsUpdateRequestDto;
import org.gorany.bookbook.api.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping({"/", ""})
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
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        postsService.update(id, requestDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Posts를 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        postsService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
