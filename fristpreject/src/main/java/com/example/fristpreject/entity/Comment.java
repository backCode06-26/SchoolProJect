package com.example.fristpreject.entity;

import com.example.fristpreject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 해당 객체 엔티티 여러개가 하나의 Article의 연관된다. 즉, 다대일관계이다.
    @JoinColumn(name = "article_id") // articleId 컬럼에 Article의 대표값을 저장
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public CommentDto toDto() {
        return new CommentDto(this.id, this.article.getId(), this.nickname, this.body);
    }

    public static Comment createComment(CommentDto dto, Article article) {
        if(dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패!, 댓글의 id가 없어야 합니다.");
        }
        if(dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        }

        return new Comment(
            dto.getId(),
            article,
            dto.getNickname(),
            dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        // 객체 갱신
        if(dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if(dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
