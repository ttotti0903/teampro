package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Domain.Club;
import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Service.ClubService;
import DCS.DCSspring.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ClubController {
    private final ClubService clubService;
    private final MemberService memberService;

    public ClubController(ClubService clubService, MemberService memberService) {
        this.clubService = clubService;
        this.memberService = memberService;
    }

    @GetMapping(value = "/clubList")
    public String list(Model model) {
        System.out.println("클럽리스트매핑됨");
        List<Club> clubs = clubService.findClubs();

        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("중간확인1");
        for (Club club : clubs) {
            LocalDateTime articleDateTime = club.getDateTime();
            Duration duration = Duration.between(currentTime, articleDateTime);
            if(duration.isNegative()){
                delete(club.getClubid());
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

                club.setRemainingTime(remainingTime);
            }

        }
        System.out.println("클럽리스트반환");
        model.addAttribute("clubs", clubs);
        return "club/clubList";
    }
    @GetMapping("/clubss/{id}/delete")
    public String delete(@PathVariable Long id) {
        System.out.println("delete 매핑됨");
        clubService.deleteArticle(id);
        return "redirect:/ClubList";
    }
    @GetMapping(value = "/newClub")
    public String createArticle(HttpServletRequest request){
        System.out.println("로그인 후 동아리모집 공고 생성 매핑 됨.");
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        if(id != null){
            return "club/newClub";
        }
        else{
            return "redirect:/login";
        }
    }
    @PostMapping(value = "/createclub")
    public String create(@RequestParam String title, @RequestParam int max, @RequestParam String content, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("time") @DateTimeFormat(pattern = "HH:mm") LocalTime time, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long temp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(temp);
        System.out.println("동아리 모집 공고 작성 매핑됨.");
        System.out.println(member.getName());
        Club club = new Club();
        club.setTitle(title);
        club.setContent(content);
        club.setMax_recruit(max);
        club.setDate(date);
        club.setTime(time);
        club.setAuthor(member);
        club.setDateTime(LocalDateTime.of(date,time));
        club.change_deadline_date_to_int();
        clubService.join(club);
        System.out.println(club.getMax_recruit());
        return "club/createclub";
    }
    @GetMapping("/club/{id}")
    public String show(@PathVariable Long id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long temp = (Long) session.getAttribute("id");
        System.out.println("club/id 매핑됨");

        Optional<Club> club = clubService.findClubById(id);
        model.addAttribute("club", club);

        if(club.get().getAuthor().getId() == temp){
            model.addAttribute("isAuthor", true);
            model.addAttribute("applicants", club.get().getApplicant());
            model.addAttribute("accepts", club.get().getAcceptmember());
        } else {
            model.addAttribute("isAuthor", false);
            if(club.get().isApplied(memberService.findOne(temp))){
                model.addAttribute("isApplied", false);
            } else {
                model.addAttribute("isApplied", true);
            }
        }
        System.out.println("club/showclub 반환하기 ");
        return "club/showclub";
    }
    @PostMapping(value="/user_applied")
    public String userApplied(@RequestParam("clubid") Long clubid, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long temp = (Long) session.getAttribute("id");
        System.out.println("user_applied 매핑됨");

        Optional<Club> club = clubService.findClubById(clubid);
        club.get().addApplicant(memberService.findOne(temp));
        System.out.println("club/showclub 반환됨");
        return "redirect:/showclub";
    }
    @PostMapping(value="/accept")
    public String Accept(@RequestParam("memberid") Long memberId, @RequestParam("clubid") Long clubId, HttpServletRequest request){
        System.out.println("accept 매핑");
        HttpSession session = request.getSession();
        Long temp = (Long) session.getAttribute("id");

        Member member = memberService.findOne(memberId);
        Optional<Club> club = clubService.findClubById(clubId);
        club.get().Confirm(member);
        return "club/showclub";
    }
    @PostMapping(value = "/reject")
    public String Reject(@RequestParam("memberid") Long memberId, @RequestParam("clubid") Long clubId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long temp = (Long) session.getAttribute("id");

        Optional<Club> club = clubService.findClubById(clubId);
        Member member = memberService.findOne(memberId);

        club.get().deleteApplicant(member);
        return"club/showclub";
    }
}
