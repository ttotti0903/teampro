package DCS.DCSspring.Domain;

public class Rating {
    private Member member;



    private Long member_id;
    private int[] score;
    private double score_avg;
    private int study_num;
    private int score_num;
    private boolean met;
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public Rating(){
        score = new int[5];
        for(int i = 0; i < 5; i++){
            score[i] = 0;
        }
        score_avg = 0;
        member_id = 0L;
        study_num = 0;
        score_num = 0;
        met = false;
    }

    public Long getMember_id() {
        return member_id;
    }

    public int[] getScore() {
        return score;
    }

    public double getScore_avg() {
        return score_avg;
    }

    public int getStudy_num() {
        return study_num;
    }

    public int getScore_num() {
        return score_num;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public void setScore_list(int[] score) {
        this.score = score;
    }

    public void setScore_avg(double score_avg) {
        this.score_avg = score_avg;
    }

    public void setStudy_num(int study_num) {
        this.study_num = study_num;
    }

    public void setScore_num(int score_num) {
        this.score_num = score_num;
    }

    public boolean isMet() {
        return met;
    }

    public void setMet(boolean met) {
        this.met = met;
    }

    public void addScore(int n) {
        if (n < 1 || n > 5)
            return;
        score[n-1]++;
        score_num++;

        if (score_num >= 10 && study_num >= 3){
            met = true;
            calAvg();
        }



    }

    public void addStudyNum(){
        study_num++;
    }

    public void calAvg(){
        int temp = 0;
        int left_outside = score_num/10;
        int right_outside = left_outside;

        for(int i = 0; i < 5; i++)
            temp += score[i] * (i + 1);

        for(int i = 0; left_outside > 0; i++) {
            if (score[i] >= left_outside) {
                temp -= (i + 1) * left_outside;
                left_outside = 0;
            } else {
                temp -= (i + 1) * score[i];
                left_outside -= score[i];
            }
        }

        for(int i = 4; right_outside > 0; i--){
            if(score[i] >= right_outside){
                temp -= (i+1) * right_outside;
                right_outside = 0;
            } else {
                temp -= (i+1)*score[i];
                right_outside -= score[i];
            }
        }
        score_avg = temp / (double)score_num - left_outside*2;
    }
}
