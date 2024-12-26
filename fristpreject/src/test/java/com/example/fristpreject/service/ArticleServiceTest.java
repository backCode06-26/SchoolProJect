package com.example.fristpreject.service;

import com.example.fristpreject.dto.ArticleForm;
import com.example.fristpreject.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 부트랑 연동하여 통합 테스트 하겠다는 것
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article article1 = new Article(1L, "가", "가내용");
        Article article2 = new Article(2L, "나", "나내용");
        Article article3 = new Article(3L, "다", "다내용");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(article1, article2, article3));

        // 결과
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가", "가내용");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패__존재하지_않는_데이터() {
        // 예상
        Long id = -1L;
        Article expected = null;

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공__title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패__id가_포함된_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공__존재하는_id와_title와_content_있는_dto_입력() {
        // 예상
        Long id = 1L;
        ArticleForm dto = new ArticleForm(id, "가가가가", "가가내용");
        Article expected = new Article(id, "가가가가", "가가내용");

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_성공__존재하는_id와_title만_있는_dto_입력() {
        // 예상
        Long id = 1L;
        ArticleForm dto = new ArticleForm(id, "가가가가",null);
        Article expected = new Article(id, "가가가가", "가내용");

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_실패__존재하지_않는_id의_dto_입력() {
        // 예상
        Long id = 4L;
        ArticleForm dto = new ArticleForm(id, "가가가가", "가내용");
        Article expected = null;

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    void delete_성공__존재하는_id_입력() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가", "가내용");


        // 실제
        Article article = articleService.delete(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete_실패__존재하지_않는_id_입력() {
        // 예상
        Long id = 4L;
        Article expected = null;


        // 실제
        Article article = articleService.delete(id);

        // 비교
        assertEquals(expected, article);
    }
}
