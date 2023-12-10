package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.*;
import DCS.DCSspring.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    private final ChatService chatService;
    private final ClubService clubService;
    private final RatingService ratingService;
    private final MemberService memberService;
    @PostMapping({"home" , "/"})
    public String hi(Model model, HttpServletRequest request) {
        List<Article> articles = articleService.SelcetFive();
        List<Club> clubs = clubService.SelcetFive();
        List<Rating> rating = ratingService.findTen();
        HttpSession session = request.getSession();
        Long currentUserId = (Long) session.getAttribute("id");

        if (currentUserId != null) {
            Member currentUser = memberService.findOne(currentUserId);
            System.out.println(currentUserId);
            List<ChatRoom> roomList = chatService.findAllRoom().stream()
                    .filter(room -> Arrays.stream(room.userId)
                            .filter(Objects::nonNull)
                            .noneMatch(blacklistedId -> currentUser.getBlacklist().contains(blacklistedId) || currentUser.getBlacklisted().contains(blacklistedId)))
                    .filter(room -> Arrays.stream(room.userId)
                            .filter(Objects::nonNull)
                            .anyMatch(id -> Objects.equals(memberService.findOne(id).getMajor(), currentUser.getMajor())))
                    .limit(10)
                    .collect(Collectors.toList());

            model.addAttribute("roomList", roomList);
        }
        model.addAttribute("articles", articles);
        model.addAttribute("clubs", clubs);
        model.addAttribute("rating",rating);
        return "/home";
    }

    @GetMapping ({"home" , "/"})
    public String hello(Model model, HttpServletRequest request) {
        List<Article> articles = articleService.SelcetFive();
        List<Club> clubs = clubService.SelcetFive();
        List<Rating> rating = ratingService.findTen();
        HttpSession session = request.getSession();
        Long currentUserId = (Long) session.getAttribute("id");

        if (currentUserId != null) {
            Member currentUser = memberService.findOne(currentUserId);
            System.out.println(currentUserId);
            List<ChatRoom> roomList = chatService.findAllRoom().stream()
                    .filter(room -> Arrays.stream(room.userId)
                            .filter(Objects::nonNull)
                            .noneMatch(blacklistedId -> currentUser.getBlacklist().contains(blacklistedId) || currentUser.getBlacklisted().contains(blacklistedId)))
                    .filter(room -> Arrays.stream(room.userId)
                            .filter(Objects::nonNull)
                            .anyMatch(id -> Objects.equals(memberService.findOne(id).getMajor(), currentUser.getMajor())))
                    .limit(10)
                    .collect(Collectors.toList());

            model.addAttribute("roomList", roomList);
        }
        model.addAttribute("articles", articles);
        model.addAttribute("clubs", clubs);
        model.addAttribute("rating",rating);
        return "/home";
    }
}
