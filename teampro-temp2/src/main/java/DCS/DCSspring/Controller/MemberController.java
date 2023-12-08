package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.Service.MemberService;
import DCS.DCSspring.Service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

import static DCS.DCSspring.EmailVerification.sendVerificationEmail;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final RatingService ratingService;

    @Autowired
    public MemberController(MemberService memberService, RatingService ratingService) {
        this.memberService = memberService;
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        List<Rating> ratings = ratingService.findRatings();
        model.addAttribute("members", members);
        model.addAttribute("ratings", ratings);
        return "members/memberList";
    }
    @GetMapping(value = "/input-form")
    public String showInputForm() {
        return "members/input-form"; // HTML 폼 페이지 이름
    }
    String RandomVeri = "";
    String sub_name;
    String sub_email;
    String sub_major;
    String sub_password;
    int sub_grade;
    @PostMapping(value = "/submit")
    public String submitInformation(@RequestParam String name, @RequestParam String email, @RequestParam String password,@RequestParam String major,@RequestParam int grade,Model model) {

        // 입력받은 정보를 자바 클래스로 전달 또는 처리
        // 예: Information information = new Information(name, email);
        email = email + "@mju.ac.kr";
        sub_email = email;
        sub_name = name;
        RandomVeri = "";
        sub_grade = grade;
        //sub_major = major;
        sub_password = password;
        sub_major = major;
        for(int i = 0; i < 6; i++){
            Random random = new Random();
            RandomVeri = RandomVeri + random.nextInt(9);
        }
        sendVerificationEmail(email, RandomVeri, name);
        // 모델에 결과를 추가 (선택사항)
        //model.addAttribute("result", "이름: " + name + ", 이메일: " + email);

        return "members/submit";
    }

    @PostMapping(value = "/members/new")
    public String creat(@RequestParam String verification){
        if(verification.equals(RandomVeri)){
            Member member = new Member();
            member.setName(sub_name);
            member.setEmail(sub_email);
            //member.setMajor(sub_major);
            member.setGrade(sub_grade);
            member.setPassword(sub_password);
            member.setMajor(sub_major);
            memberService.join(member);
            return "redirect:/";

        }

        return "/submit";

    }
    @GetMapping(value = "login")
    public String showLogin(){
        return "members/login";
    }
    @PostMapping(value = "login")
    public String loginPost(@RequestParam String email,@RequestParam String password,HttpServletRequest request){
        Member member = memberService.findOneMember(email);
        memberService.getMemberId(member);
        if(member.getPassword().equals(password)){
            HttpSession session = request.getSession();
            session.setAttribute("id", memberService.getMemberId(member));
            return "redirect:/";
        }
        else{
            return "members/login";
        }
    }
    @GetMapping(value = "temp")
    public String makeTemp(){
        System.out.println("더미만들기");
        Random ran = new Random();
        for(int i = 0; i < 10; i++){
            Member member = new Member();
            String tmpE = "test" + (String.valueOf(i));
            member.setName(tmpE);
            member.setEmail(tmpE);
            member.setPassword(String.valueOf(i));
            member.setGrade(ran.nextInt(4));
            if(i % 4 == 0){
                member.setMajor("기계공학과");
            }
            else if (i % 4 == 1){
                member.setMajor("전기공학과");
            }
            else if (i % 4 == 2){
                member.setMajor("컴퓨터공학과");
            }
            else{
                member.setMajor("화학공학과");
            }
            Rating rating = new Rating();
            rating.setStudy_num(3);
            for(int j = 0; j < 10; j++)
                rating.addScore(ran.nextInt(5)+1);
            ratingService.join(rating);
            memberService.join(member);
            rating.setMember_id(member.getId());
            rating.setMember(member);
            member.setRating(rating);
        }

        Member member1 = new Member();
        member1.setName("평가횟수가 부족한 회원");
        member1.setEmail("iNeedMoreEvaluation");
        member1.setPassword("qwer");
        member1.setGrade(2);
        Rating rating1 = new Rating();
        rating1.setStudy_num(3);
        member1.setMajor("컴퓨터공학과");
        //member1 전공 추가
        for(int i = 0; i < 9; i ++){
            rating1.addScore(ran.nextInt(5)+1);
        }
        rating1.setMember(member1);
        member1.setRating(rating1);

        Member member2 = new Member();
        member2.setName("스터디횟수가 부족한 회원");
        member2.setEmail("iNeedMoreStudy");
        member2.setPassword("qwer");
        member2.setGrade(2);
        Rating rating2 = new Rating();
        rating2.setStudy_num(2);
        //member2 전공 추가
        member2.setMajor("컴퓨터공학과");
        for(int i = 0; i < 10; i ++){
            rating2.addScore(ran.nextInt(5)+1);
        }
        rating2.setMember(member2);
        member2.setRating(rating2);

        ratingService.join(rating1);
        ratingService.join(rating2);
        memberService.join(member1);
        memberService.join(member2);

        rating1.setMember_id(member1.getId());
        rating2.setMember_id(member2.getId());
        return  "redirect:/";
    }

}
