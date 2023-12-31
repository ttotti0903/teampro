package DCS.DCSspring.Controller;

import DCS.DCSspring.DTO.ChatMessage;
import DCS.DCSspring.Domain.ChatRoom;
import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.Service.ChatService;
import DCS.DCSspring.Service.MemberService;
import DCS.DCSspring.Service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MemberService memberService;
    private final RatingService ratingService;

    @RequestMapping("/chatlist")
    public String chatList(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Long currentUserId = (Long) session.getAttribute("id");
        if (currentUserId == null) {
            // 예외 처리 또는 다른 로직 수행
            return "redirect:/login";  // 로그인 페이지로 리다이렉트 예시
        }
        System.out.println(currentUserId);
        Member currentUser = memberService.findOne(currentUserId);

        List<ChatRoom> roomList = chatService.findAllRoom().stream()
                .filter(room -> Arrays.stream(room.userId)
                        .filter(Objects::nonNull)
                        .noneMatch(blacklistedId -> currentUser.getBlacklist().contains(blacklistedId) || currentUser.getBlacklisted().contains(blacklistedId)))
                .filter(room -> Arrays.stream(room.userId)
                        .filter(Objects::nonNull)
                        .anyMatch(id -> Objects.equals(memberService.findOne(id).getMajor(), currentUser.getMajor())))
                .collect(Collectors.toList());

        model.addAttribute("roomList", roomList);
        return "chat/list";
    }


    @PostMapping("/chat/createRoom")  //방을 만들었으면 해당 방으로 가야지.
    public String createRoom(Model model, @RequestParam String name, @RequestParam String maxUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long tmp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(tmp);
        if(member.getChatRoomId() != null){
            return "chat/list";
        }
        ChatRoom room = chatService.createRoom(name);
        model.addAttribute("room",room);
        room.maxUser = Integer.parseInt(maxUser);
        room.userId = new Long[room.maxUser];
        member.setChatRoomId(room.getRoomId());
        room.userId[room.userNum] = tmp;
        room.userNum++;
        return "chat/room";  //만든사람이 채팅방 1빠로 들어가게 됩니다
    }

    @GetMapping("/chatRoom/{roomId}")
    public String chatRoom(Model model, @PathVariable String roomId,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long tmp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(tmp);
        if(member.getChatRoomId() != null){
            return "chat/list";
        }
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);
        if(room.maxUser == room.userNum){
            List<ChatRoom> roomList = chatService.findAllRoom();
            model.addAttribute("roomList",roomList);
            return "chat/list";
        }
        room.userId[room.userNum] = tmp;
        room.userNum++;
        member.setChatRoomId(room.getRoomId());

        return "chat/room";
    }

    @PostMapping("/quit")
    public String quitChatRoom(@RequestBody ChatMessage quitRequest, HttpServletRequest request) {
        // quitRequest에서 필요한 정보를 추출하여 모델에 추가
        System.out.println("quitttttttttttttttttttttttttt");
        HttpSession session = request.getSession();
        Long tmp = (Long) session.getAttribute("id");
        //System.out.println(tmp);
        String roomId = quitRequest.getRoomId();
        ChatRoom room = chatService.findRoomById(roomId);
        chatService.quitMember(tmp,room);
        room.userNum--;

        Long temp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(temp);
        System.out.println("평점을 매겨주는 회워느이 이름: " + member.getName());
        System.out.println("평점을 매겨주는 회원의 평가 전 스터디횟수: " + member.getRating().getStudy_num());
        member.getRating().addStudyNum();
        System.out.println("평점을 매겨주는 회원의 평가 후 스터디횟수: " + member.getRating().getStudy_num());
        // 채팅방 평점을 매기는 페이지로 이동
        return "chat/temp";
    }

    @GetMapping("/estimate")
    public String goEstimate(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Long tmp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(tmp);
        ChatRoom room = chatService.findRoomById(member.getChatRoomId());
        List<Long> ids = chatService.findRoomMemberIds(room);
        List<Member> members = memberService.findMembersByIds(ids);
        System.out.println(members);
        model.addAttribute("members",members);
        member.setChatRoomId(null);
        return "chat/estimate";
    }
    
    @GetMapping("/goHome")
    public String goHome(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long tmp = (Long) session.getAttribute("id");
        Member member = memberService.findOne(tmp);
        member.setChatRoomId(null);
        return "redirect:/";
    }

    @GetMapping("/chat/temp")
    public String temp(){
        return "chat/temp";
    }

    @PostMapping("/estimateComplite")
    public String estimateComplite(HttpServletRequest request) {
        System.out.println("estimateCompite 매핑됨");
        Enumeration<String> parameterNames = request.getParameterNames();
        HttpSession session = request.getSession();//
        Long ownMemberId = (Long)session.getAttribute("id"); //
        Member ownMember = memberService.findOne(ownMemberId);//

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("paramName: " + paramName);
            if (paramName.startsWith("score_")) {
                Long memberId = Long.valueOf(paramName.substring(6));
                System.out.println(memberId);
                int score = Integer.parseInt(request.getParameter(paramName));
                System.out.println(score);
                Member member = memberService.findOne(memberId);//
                if(score == 1){//
                    ownMember.addToBlacklist(memberId);
                    member.addToBlacklisted(ownMemberId);
                }

                Rating rating = ratingService.findbyMemberId(memberId).get();
                System.out.println("전송받은 점수:" + score);
                rating.addScore(score);
                ratingService.sort();
            }
        }

        // 점수 부여가 완료된 후에 리다이렉트할 URL을 반환
        return "redirect:/";
    }
}