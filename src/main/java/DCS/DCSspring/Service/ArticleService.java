package DCS.DCSspring.Service;


import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleService {
    private final ArticleRepository articleRepository;



    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long join(Article article){
        articleRepository.save(article);
        return article.getarticleId();
    }

    public List<Article> findArticles(){ //전체 회원 조회.
        return articleRepository.findAll();
    }

    public Optional<Article> findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> findArticleByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    public List<Article> sort_by_deadlineInt() {
        return articleRepository.sort_by_deadlineInt();
    }

    public List<Article> deleteArticle(Long id) {
        articleRepository.deleteArticle(id);
        return articleRepository.findAll();
    }
    
}


