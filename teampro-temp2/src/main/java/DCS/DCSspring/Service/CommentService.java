package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Domain.Comment;
import DCS.DCSspring.repository.CommentRepository;

import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public List<Comment> findCommentByArticleid(Long id){
        return commentRepository.findByArticleId(id);
    }
    public Long join(Comment comment){
        commentRepository.save(comment);
        return comment.getArticleid();
    }
    public Comment findOne(Long commentid){
        return commentRepository.findById(commentid).get();
    }
}
