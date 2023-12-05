package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Domain.Club;
import DCS.DCSspring.Service.ArticleService;
import DCS.DCSspring.Service.ChatService;
import DCS.DCSspring.Service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    private final ChatService chatService;
    private final ClubService clubService;
    @GetMapping ({"home" , "/"})
    public String hello(Model model) {
        List<Article> articles = articleService.SelcetFive();
        List<Club> clubs = clubService.SelcetFive();
        model.addAttribute("articles", articles);
        model.addAttribute("clubs", clubs);
        return "home";
    }
}
