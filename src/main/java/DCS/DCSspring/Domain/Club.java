package DCS.DCSspring.Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Club {
    private Long clubid;    //공고 고유 아이디
    private String title;   //제목
    private String content; //내용
    private LocalDate deadline_date;//마감일
    private LocalTime deadline_time;//마감시간
    private String remainingTime;
    private int deadline_int;
    private LocalDateTime dateTime;
    private int max_recruit;    //모집인원
    private int current_confirm = 0;
    private Member author;      //작성자
    private List<Member> applicant; //신청자
    private List<Member> acceptmember; //가입된 신청자

    public List<Member> getAcceptmember() {
        return acceptmember;
    }

    public void setAcceptmember(List<Member> acceoptmember) {
        this.acceptmember = acceoptmember;
    }

    public Club(){
        applicant = new ArrayList<>();
        acceptmember = new ArrayList<>();
    }

    public Long getClubid() {
        return clubid;
    }

    public void setClubid(Long clubid) {
        this.clubid = clubid;
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
    public LocalDate getDate(){
        return deadline_date;
    }

    public LocalTime getTime(){
        return deadline_time;
    }

    public LocalDate getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(LocalDate deadline_date) {
        this.deadline_date = deadline_date;
    }

    public LocalTime getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(LocalTime deadline_time) {
        this.deadline_time = deadline_time;
    }
    public void setDate(LocalDate date){
        deadline_date = date;
    }

    public void setTime(LocalTime time){
        deadline_time = time;
    }

    public String getRemainingTime() {
        return remainingTime;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getDeadline_int() {
        return deadline_int;
    }

    public void setDeadline_int(int deadline_int) {
        this.deadline_int = deadline_int;
    }
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }
    public void change_deadline_date_to_int(){
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = deadline_time.getMinute();
        this.deadline_int = year*100000000 + month*1000000 + day*10000 + hour*100;
    }

    public int getCurrent_confirm() {
        return current_confirm;
    }

    public void setCurrent_confirm(int current_confirm) {
        this.current_confirm = current_confirm;
    }

    public int getMax_recruit() {
        return max_recruit;
    }

    public void setMax_recruit(int max_recruit) {
        this.max_recruit = max_recruit;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public List<Member> getApplicant() {
        return applicant;
    }

    public void setApplicant(List<Member> applicant) {
        this.applicant = applicant;
    }
    public void addApplicant(Member member){
        if(isApplied(member))
            applicant.add(member);
    }
    public void deleteApplicant(Member member){
        applicant.remove(member);
        System.out.println("deleteApplicant 실행후 applicant: " + applicant.size());
    }
    public void addAcceptMember(Member member){
        acceptmember.add(member);
        System.out.println("acceptmember 실행후 acceptmember:" + acceptmember.size());
    }

    public void Confirm(Member member) {
        System.out.println("Confirm 실행");
        current_confirm++;
        addAcceptMember(member);
        deleteApplicant(member);
    }
    public boolean isApplied(Member member){
        if(applicant.contains(member) || acceptmember.contains(member)){
            return false;
        } else {
            return true;
        }
    }
    /* 생성시 설정할 값: 제목, 내용, 마감일자, 모집 인원
    그외 필요한 것: 신청 인원들, 인원 현황, 작성자*/
}
