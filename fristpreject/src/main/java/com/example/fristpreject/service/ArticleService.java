package com.example.fristpreject.service;

import com.example.fristpreject.dto.ArticleForm;
import com.example.fristpreject.entity.Article;
import com.example.fristpreject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // 해당 서비스로 인식하여 스프링 부트 객체를 생성(등록)
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> index() {
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();

        // 2. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나 id가 다른 경우)
        if(target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답
            return null;
        }
        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null) {
            return null;
        }

        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메서드를 하나로 묶고 문제가 생기면 처음으로 돌아가겠다라는 애노테이션
    public List<Article> createArticles(List<ArticleForm> dtos) {

        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

//        List<Article> articleList = new ArrayList<>();
//        for(ArticleForm dto : dtos) {
//            Article article = dto.toEntity();
//            articleList.add(article);
//        }

        // entity 묶음을 DB로 저장

        articleList.stream()
                .forEach(article -> articleRepository.save(article));

//        for(Article article : articleList) {
//            articleRepository.save(article);
//        }

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalStateException("결제 실패")
        );

        // 결과값 반환
        return articleList;
    }
}