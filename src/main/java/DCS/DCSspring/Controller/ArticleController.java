package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("articles", articles);
        return "articles/articleList";
    }
    @PostMapping(value = "create")
    public String create(@RequestParam String title,@RequestParam String content){
            System.out.println("매핑됨.");
            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);

            articleService.join(article);
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
}

