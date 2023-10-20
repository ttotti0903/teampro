package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setarticleId(key.longValue());
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        List<Article> result = jdbcTemplate.query("select * from article where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        List<Article> result = jdbcTemplate.query("select * from article where title = ?", articleRowMapper(), title);
        return result.stream().findAny();
    }

    @Override
    public Optional<Article> findByContent(String content) {
        List<Article> result = jdbcTemplate.query("select * from article where content = ?", articleRowMapper(), content);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setarticleId(rs.getLong("id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            return article;
        };
    }
}
