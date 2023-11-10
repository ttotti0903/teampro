package DCS.DCSspring.Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Article {
    private Long articleid;
    private String title;
    private String content;
    private LocalDate deadline_date;
    private LocalTime deadline_time;
    private int deadline_int;
    private LocalDateTime dateTime;
    private String remainingTime;
    private Member author;

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

    public void setDate(LocalDate date){
        deadline_date = date;
    }

    public void setTime(LocalTime time){
        deadline_time = time;
    }
    public LocalDate getDate(){
        return deadline_date;
    }

    public LocalTime getTime(){
        return deadline_time;
    }

    public int getDeadline_int(){
        return deadline_int;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void change_deadline_date_to_int(){
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = deadline_time.getMinute();
        this.deadline_int = year*100000000 + month*1000000 + day*10000 + hour*100;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getRemainingTime(){
        return remainingTime;
    }

    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    public void setAuthor(Member member) {
        author = member;
    }
}

