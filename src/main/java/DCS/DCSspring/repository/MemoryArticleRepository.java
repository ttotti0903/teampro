package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Article;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryArticleRepository implements ArticleRepository{
    private static Map<Long, Article> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Article save(Article article) {
        article.setarticleId(++sequence);
        store.put(article.getarticleId(),article);
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findByTitle(String Title) {
        return store.values().stream()
                .filter(article -> article.getTitle().contains(Title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findByContent(String content) {
        return store.values().stream()
                .filter(article -> article.getContent().contains(content))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store.values());

    }

    public void clearStore(){
        store.clear();
    }

    public List<Article> sort_by_deadlineInt() {
        return store.values().stream()
                .sorted(Comparator.comparing(Article::getDeadline_int))
                .collect(Collectors.toList());
    }

    public List<Article> deleteArticle(Long id) {
        store.remove(id);
        return new ArrayList<>(store.values());
    }
}
