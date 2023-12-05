package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Comment;
import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryReplyRepository implements ReplyRepository{
    private static Map<Long, Reply> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Reply save(Reply reply) {
        reply.setReplyid(++sequence);
        store.put(reply.getReplyid(),reply);
        return reply;
    }

    @Override
    public List<Reply> findByCommentId(Long id) {
        return null;
    }
}
