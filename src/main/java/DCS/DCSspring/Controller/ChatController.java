package DCS.DCSspring.Controller;

import DCS.DCSspring.Domain.ChatRoom;
import DCS.DCSspring.Service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    public ChatController(){
        chatService = new ChatService();
    }
    @RequestMapping("/chatlist")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList",roomList);
        return "chat/list";
    }


    @PostMapping("/chat/createRoom")  //방을 만들었으면 해당 방으로 가야지.
    public String createRoom(Model model, @RequestParam String name, String username) {
        ChatRoom room = chatService.createRoom(name);
        model.addAttribute("room",room);
        model.addAttribute("username",username);
        return "chat/room";  //만든사람이 채팅방 1빠로 들어가게 됩니다
    }

    @GetMapping("/chatRoom/{roomId}")
    public String chatRoom(Model model, @PathVariable String roomId) {
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);
        return "chat/room";
    }

}