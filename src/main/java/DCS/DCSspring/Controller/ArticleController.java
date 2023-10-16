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

    @GetMapping(value = "/article/list")
    public String list(Model model) {
        List<Article> article = articleService.findArticles();
        model.addAttribute("Article", article);
        return "articles/list";
    }

    @PostMapping(value = "/create")
    public String create(@RequestParam String title,@RequestParam String content,Model model){

            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);

            articleService.join(article);
            return "articles/new";

    }
    @GetMapping(value = "/new")
        public String showArticlesNew(){
            return "articles/new";
        }
    }

