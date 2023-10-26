package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/articleList")
    public String list(Model model) {
        System.out.println("매핑됨");
        List<Article> articles = articleService.findArticles();

        LocalDateTime currentTime = LocalDateTime.now();

        for (Article article : articles) {
            LocalDateTime articleDateTime = article.getDateTime();
            Duration duration = Duration.between(currentTime, articleDateTime);
            if(duration.isNegative()){
                delete(article.getarticleId());
            }
            else{
                long daysRemaining = duration.toDays();
                long hoursRemaining = duration.toHours() % 24;
                long minutesRemaining = duration.toMinutes() % 60;
                long secondsRemaining = duration.getSeconds() % 60;
                String remainingTime = daysRemaining + "일 " + hoursRemaining + "시간 " + minutesRemaining + "분 " + secondsRemaining + "초";
                if(daysRemaining == 0){
                    remainingTime = hoursRemaining + "시간 " + minutesRemaining + "분 " + secondsRemaining + "초";
                }
                if(hoursRemaining == 0 && daysRemaining == 0){
                    remainingTime = minutesRemaining + "분 " + secondsRemaining + "초";
                }
                if(hoursRemaining == 0 && daysRemaining == 0 && minutesRemaining == 0){
                    remainingTime = secondsRemaining + "초";
                }


                article.setRemainingTime(remainingTime);
            }

        }

        model.addAttribute("articles", articles);
        return "articles/articleList";
    }
    @PostMapping(value = "create")
    public String create(@RequestParam String title, @RequestParam String content, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("time") @DateTimeFormat(pattern = "HH:mm") LocalTime time){
            System.out.println("게시물 작성 매핑됨.");
            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);
            article.setDate(date);
            article.setTime(time);
            articleService.join(article);
            article.setDateTime(LocalDateTime.of(date,time));
            article.change_deadline_date_to_int();
            System.out.println(article.getDeadline_int());
            System.out.println(article.getDateTime());
            return "articles/create";

    }
    @GetMapping(value = "new")
        public String showArticlesNew(){
            System.out.println("매핑 됨");
            return "articles/new";
        }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        System.out.println("articles/id 매핑됨");
        //1. id를 조회해 데이터 가져오기
        Optional<Article> articles = articleService.findArticleById(id);
        // name이라는 이름으로 value 객체 추가
        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articles);
        //3. 뷰 페이지 반환하기
        System.out.println("articles/show 반환하기 ");
        return "articles/show";
    }

    @PostMapping(value = "articleSearch")
    public String articleSearch(@RequestParam String title, Model model){
        System.out.println("mapping "+title);
        List<Article> articles = articleService.findArticleByTitle(title);
        model.addAttribute("articles", articles);
        return "articles/search";
    }

    @PostMapping(value = "deadLine_nearer")
    public String deadLine_nearer(Model model){
        System.out.println("113");
        List<Article> articles = articleService.sort_by_deadlineInt();
        model.addAttribute("articles", articles);
        return "articles/articleList";
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id){
        System.out.println("delete 매핑됨");
        articleService.deleteArticle(id);
        return "redirect:/articleList";
    }
    
}

