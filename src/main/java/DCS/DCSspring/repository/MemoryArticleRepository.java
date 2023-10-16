package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Article;

import java.util.*;

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
    public Optional<Article> findByTitle(String Title) {
        return store.values().stream()
                .filter(member -> member.getTitle().equals(Title))
                .findAny();
    }

    @Override
    public Optional<Article> findByContent(String content) {
        return store.values().stream()
                .filter(member -> member.getContent().equals(content))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store.values());

    }

    public void clearStore(){
        store.clear();
    }
}
