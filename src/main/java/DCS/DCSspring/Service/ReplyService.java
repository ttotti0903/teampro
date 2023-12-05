package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Comment;
import DCS.DCSspring.Domain.Reply;
import DCS.DCSspring.repository.ReplyRepository;

import java.util.List;

public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<Reply> findCommentByCommentid(Long id){
        return replyRepository.findByCommentId(id);
    }
    public Long join(Reply reply){
        replyRepository.save(reply);
        return reply.getReplyid();
    }
}