package com.example.fristpreject.controller;

import com.example.fristpreject.dto.ArticleForm;
import com.example.fristpreject.dto.CommentDto;
import com.example.fristpreject.entity.Article;
import com.example.fristpreject.repository.ArticleRepository;
import com.example.fristpreject.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired // ArticleRepository 주입
    private ArticleRepository articleRepository;

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private CommentService commentService;

    // 새 글 작성 폼
    @GetMapping("/article/new")
    public String newArticleForm() {
        return "article/new";
    }

    // 글 생성
    @PostMapping("/article/create")
    public String createArticle(ArticleForm form) {

        // 1. DTO 객체를 변환 -> Entity
        Article article = form.toEntity();

        // DB에 저장
        Article savedArticle = articleRepository.save(article);
        log.info(savedArticle.toString());

        return "redirect:/article/" + savedArticle.getId(); // 글이 생성된 후 목록 페이지로 리다이렉트
    }

    // 특정 글 조회
    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model) {

        // ID로 글을 찾음
        Article article = articleRepository.findById(id).orElse(null);

        // 댓글 모두 찾기
        List<CommentDto> dtos = commentService.comments(id);

        // 모델에 글 추가
        model.addAttribute("article", article);
        model.addAttribute("commentDtos", dtos);

        return "article/show";
    }

    // 모든 글 목록 조회
    @GetMapping("/article")
    public String index(Model model) {

        // 1. 모든 Article 가져오기
        List<Article> articlesList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articlesList", articlesList);

        return "article/index"; // index 뷰 반환
    }

    @GetMapping("/article/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article article = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", article);

        // 뷰 페이지 설정
        return "article/edit";
    }

    @PostMapping("/article/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // DTO Entity 변환
        Article articleEntity = form.toEntity();

        // DB Entity 저장
        // 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 기존 데이터 존재 시 갱신, 자동으로 갱신해줌
        if(target != null) {
            articleRepository.save(articleEntity);
        }

        // 수정 결과 페이지 리다이렉트
        return "redirect:/article/" + articleEntity.getId();
    }

    @GetMapping("/article/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어옵니다.");
        // 1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 삭제한다.
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. 결과 데이터로 리다이렉트한다.
        return "redirect:/article";
    }
}
