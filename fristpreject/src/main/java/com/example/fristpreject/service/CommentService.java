package com.example.fristpreject.service;

import com.example.fristpreject.dto.CommentDto;
import com.example.fristpreject.entity.Article;
import com.example.fristpreject.entity.Comment;
import com.example.fristpreject.repository.ArticleRepository;
import com.example.fristpreject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        // 조회: 댓글 목록
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환: 엔티티 -> dto
//        List<CommentDto> commentDtos = new ArrayList<>();
//        for (Comment comment : comments) {
//            CommentDto c = comment.toDto();
//            commentDtos.add(c);
//        }

        // 반환
        return comments.stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());

        /* stream으로 한 번 해보기 */

    }

    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalStateException("댓글 생성 실패"));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto ,article);

        // 댓글 엔티티를 DB로 저장
        commentRepository.save(comment);

        // DTO로 변경하여 반환
        return comment.toDto();
    }

    public CommentDto udpate(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 DTO로 변환 및 반환
        return updated.toDto();
    }

    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("댓글 삭제 실패! 대상이 없습니다."));

        // 댓글 삭제
        commentRepository.delete(target);

        // 삭제 댓글을 DTO로 변환 및 반환
        return target.toDto();
    }
}
