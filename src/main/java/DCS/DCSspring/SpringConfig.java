package DCS.DCSspring;

import DCS.DCSspring.Domain.Club;
import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.Service.*;
import DCS.DCSspring.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository(); // 메모리 저장소로 변경
    }
    @Bean
    public ArticleService articleService() {
        return new ArticleService(articleRepository());
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new MemoryArticleRepository(); // 메모리 저장소로 변경
    }
    @Bean
    public CommentRepository commentRepository(){
        return new MemoryCommentRepository(); // 메모리 저장소로 변경
    }
    @Bean
    public CommentService commentService(){
        return new CommentService(commentRepository());
    }
    @Bean
    public RatingRepository ratingRepository(){
        return new MemoryRatingRepository();
    }
    @Bean
    public RatingService ratingService(){
        return new RatingService(ratingRepository());
    }
    @Bean
    public ClubRepository clubRepository() {return new MemoryClubRepository();}
    @Bean
    public ClubService clubService(){return new ClubService(clubRepository());
    }
}