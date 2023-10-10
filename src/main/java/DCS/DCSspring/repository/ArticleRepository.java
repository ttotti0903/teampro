package DCS.DCSspring.repository;

import DCS.DCSspring.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
