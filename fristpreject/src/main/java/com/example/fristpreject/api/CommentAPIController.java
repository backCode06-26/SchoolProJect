package com.example.fristpreject.api;

import com.example.fristpreject.dto.CommentDto;
import com.example.fristpreject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentAPIController {
    @Autowired
    private CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에 위임
        CommentDto createDto = commentService.create(articleId, dto);

        // 결과 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createDto);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto updateDto = commentService.udpate(id, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long id) {
        // 서비스에게 위임
        CommentDto dto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
