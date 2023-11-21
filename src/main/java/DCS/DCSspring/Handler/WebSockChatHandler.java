package DCS.DCSspring.Handler;


import DCS.DCSspring.DTO.ChatMessage;
import DCS.DCSspring.Domain.ChatRoom;
import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Service.ChatService;
import DCS.DCSspring.Service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final MemberService memberService;
    private Long logId;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }
    
    /*public void getHttpSessionId(WebSocketSession session) {
        HttpServletRequest request = (HttpServletRequest) session.getAttributes().get("HTTP.REQUEST");
        HttpSession httpSession = request.getSession();
        logId = (Long) httpSession.getAttribute("Id");
    }*/

    @Override
protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    logId = (Long) session.getAttributes().get("logId");
    Member member = memberService.findOne(logId);
    ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
    ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
    Set<WebSocketSession> sessions=room.getSessions();
    chatMessage.setSender(member.getName());
    chatMessage.setMsg(chatMessage.getMsg());
    // Create a new string that only contains 'sender' and 'msg'
    String formattedMessage = chatMessage.getSender() + " : " + chatMessage.getMsg();

    if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
        sessions.add(session);
        chatMessage.setMessage(member.getName() + "님이 입장했습니다.");
        sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)));
    } else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
        sessions.remove(session);
        chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
        sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)));
    } else {
        sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)));
    }
}
    private  void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message){
        sessions.parallelStream().forEach( roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //javascript에서  session.close해서 연결 끊음. 그리고 이 메소드 실행.
        //session은 연결 끊긴 session을 매개변수로 이거갖고 뭐 하세요.... 하고 제공해주는 것 뿐
    }



}