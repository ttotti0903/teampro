package DCS.DCSspring.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
    private Long commentid;
    private String auther;
    private String content;
    private LocalDateTime date;
    private Long articleid;
    private ArrayList<Reply> replyList;

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getArticleid() {
        return articleid;
    }

    public void setArticleid(Long articleid) {
        this.articleid = articleid;
    }

    public ArrayList<Reply> getReply() {
        return replyList;
    }

    public void setReply(ArrayList<Reply> reply) {
        this.replyList = reply;
    }
}
