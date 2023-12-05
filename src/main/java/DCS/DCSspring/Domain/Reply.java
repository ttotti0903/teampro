package DCS.DCSspring.Domain;

import java.util.ArrayList;
import java.util.List;

public class Reply {
    private Long commentid;
    private Long replyid;
    private Member auther;
    private String content;


    public Long getReplyid() {
        return auther.getId();
    }

    public Member getAuther() {
        return auther;
    }

    public void setAuther(Member auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentid() {
        return commentid;
    }

    public void setCommentid(Long comment_id) {
        this.commentid = comment_id;
    }

    public Long getReply_id() {
        return replyid;
    }

    public void setReply_id(Long reply_id) {
        this.replyid = reply_id;
    }

    public void setReplyid(long l) {
    }
}
