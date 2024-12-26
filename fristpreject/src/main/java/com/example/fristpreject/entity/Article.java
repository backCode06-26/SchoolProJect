package com.example.fristpreject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // DB가 해당 객체를 인식 가능
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Article {

    @Id // 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  DB가 id를 자동생성 할 수 있게 합니다.
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public void patch(Article article) {
        if(article.title != null) {
            this.title = article.title;
        }
        if(article.content != null) {
            this.content = article.content;
        }
    }
}
