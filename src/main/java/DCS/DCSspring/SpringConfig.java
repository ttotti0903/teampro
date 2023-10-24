package DCS.DCSspring;

import DCS.DCSspring.Service.ArticleService;
import DCS.DCSspring.Service.MemberService;
import DCS.DCSspring.repository.ArticleRepository;
import DCS.DCSspring.repository.MemberRepository;
import DCS.DCSspring.repository.MemoryArticleRepository;
import DCS.DCSspring.repository.MemoryMemberRepository;
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
}