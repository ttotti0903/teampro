package DCS.DCSspring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import static DCS.DCSspring.EmailVerification.sendVerificationEmail;

@Controller
public class InformationController {

    @GetMapping("/input-form")
    public String showInputForm() {
        return "input-form"; // HTML 폼 페이지 이름
    }

    @PostMapping("/submit")
    public void submitInformation(@RequestParam String name, @RequestParam String email, Model model) {
        // 입력받은 정보를 자바 클래스로 전달 또는 처리
        // 예: Information information = new Information(name, email);
        sendVerificationEmail(email, "123456", name);
        // 모델에 결과를 추가 (선택사항)
        //model.addAttribute("result", "이름: " + name + ", 이메일: " + email);

    }
}
