package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Article;


import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findByTitle(String title);
    Optional<Article> findByContent(String content);
    List<Article> findAll();

    List<Article> sort_by_deadlineInt();

    List<Article> deleteArticle(Long id);

    List<Article> SelcetFive();
}
