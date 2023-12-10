package DCS.DCSspring.Domain;

import java.util.HashSet;

public class Member {
    private Long id;
    private String email;
    private String name;
    private String major; //1 = 컴공, 2 = 기공, 3 = 화공 등등...
    private String password;
    private Rating rating;

    
    private final HashSet<Long> blacklist = new HashSet<>();

    private final HashSet<Long> blacklisted = new HashSet<>();
    public void addToBlacklisted(Long id) {
        blacklisted.add(id);
    }
    public HashSet<Long> getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(Long id) {
        blacklisted.add(id);
    }
    public void addToBlacklist(Long id) {
        blacklist.add(id);
    }

    public boolean isBlacklisted(Long id) {
        return blacklist.contains(id);
    }

    public HashSet<Long> getBlacklist() {
        return blacklist;
    }    
    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    private String chatRoomId = null;

    public Member(){
        rating = new Rating();
    }

    public Rating getRating() {
        return rating;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    private int grade;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


}
