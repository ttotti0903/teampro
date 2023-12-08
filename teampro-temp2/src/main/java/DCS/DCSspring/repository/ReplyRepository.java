package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Comment;
import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.Domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply save(Reply reply);
    List<Reply> findByCommentId(Long id);

}
