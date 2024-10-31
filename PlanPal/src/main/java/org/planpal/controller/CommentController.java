package org.planpal.controller;

import org.planpal.dto.ReviewDTO;
import org.planpal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final ReviewService reviewService;

    @Autowired
    public CommentController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Create Review
            reviewService.save(reviewDTO);

            // Prepare response
            response.put("message", "댓글이 성공적으로 생성되었습니다.");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 기록
            response.put("message", "댓글 생성 중 오류 발생.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateComment(@RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Update Review
            reviewService.update(reviewDTO);

            response.put("message", "댓글이 성공적으로 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 기록
            response.put("message", "댓글 수정 중 오류 발생.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            reviewService.delete(reviewDTO.getReviewId());

            response.put("message", "댓글이 성공적으로 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 기록
            response.put("message", "댓글 삭제 중 오류 발생.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
