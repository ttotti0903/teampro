package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Comment;
import DCS.DCSspring.Domain.Member;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByArticleId(Long id);
    Optional<Comment> findById(Long id);

}
