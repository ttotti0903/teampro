package DCS.DCSspring.Domain;

public class Article {
    private Long articleid;
    private String title;
    private String content;

    public Long getarticleId() {
        return articleid;
    }

    public void setarticleId(Long articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
