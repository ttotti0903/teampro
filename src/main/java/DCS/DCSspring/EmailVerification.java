package DCS.DCSspring;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
@Service
public class EmailVerification {

    // 이메일 서버 설정
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "rbcjfdl132@gmail.com"; // 발신 이메일 주소
    private static final String SMTP_PASSWORD = "eteb oqio jpqs fpzx"; // 발신 이메일 비밀번호

    public static void sendVerificationEmail(String toEmail, String verificationCode,String toname) {
        System.out.println(toEmail);
        // SMTP 서버 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        // 세션 생성
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            // 이메일 메시지 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("MJU_DCS launch code");
            message.setText("인증 코드: " + verificationCode);

            // 이메일 전송
            Transport.send(message);
            System.out.println("인증 이메일이 전송되었습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("인증 이메일을 보내는 중 오류가 발생했습니다.");
        }
        return;
    }



    /*public static void main(String[] args) {
        // 사용자에게 전송할 인증 코드 생성
        String verificationCode = "123456"; // 실제로는 무작위로 생성되어야 합니다.

        // 이메일 전송
        sendVerificationEmail("recipient@example.com", verificationCode);
    }*/
}
