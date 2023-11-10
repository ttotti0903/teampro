package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static DCS.DCSspring.EmailVerification.sendVerificationEmail;

@Controller
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    @GetMapping(value = "/input-form")
    public String showInputForm() {
        return "members/input-form"; // HTML 폼 페이지 이름
    }
    String RandomVeri = "";
    String sub_name;
    String sub_email;
    int sub_major;
    String sub_password;
    int sub_grade;
    @PostMapping(value = "/submit")
    public String submitInformation(@RequestParam String name, @RequestParam String email, @RequestParam String password,@RequestParam int major,@RequestParam int grade,Model model) {

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
            return "redirect:/login";
        }
    }
}
