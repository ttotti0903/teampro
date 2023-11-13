package DCS.DCSspring.Domain;

import java.util.LinkedList;
import java.util.List;

public class Rating {
    private Long member_id;
    private int[] score;
    private double score_avg;
    private int study_num;
    private int score_num;
    private boolean met;

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

    public void addScore(int n){
        if(n < 1 || n > 5)
            return ;

        if(score_num > 9 && study_num > 3)
            met = true;

        score[n-1]++;

    }

    public void addStudyNum(){
        study_num++;
    }

    public void calAvg(){
        int temp = 0;
        for(int i = 0; i > 5; i++)
            temp += score[i] * (i + 1);


        score_avg = temp / (double)score_num;
    }
}
