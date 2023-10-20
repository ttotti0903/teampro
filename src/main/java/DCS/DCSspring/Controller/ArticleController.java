package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    }

