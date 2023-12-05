package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryCommentRepository implements CommentRepository {
    private static Map<Long, Comment> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Comment save(Comment comment) {
        comment.setCommentid(sequence++);
        store.put(comment.getCommentid(),comment);
        return comment;
    }

    public List<Comment> findByArticleId(Long id) {
        return store.values().stream()
                .filter(comment -> comment.getArticleid().compareTo(id) == 0)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return  Optional.ofNullable(store.get(id));
    }
}
