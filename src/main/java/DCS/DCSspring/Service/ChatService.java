package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public void quitMember(Long memberId, ChatRoom room) {
        // Id를 찾아서 제거
        for (int i = 0; i < room.userNum; i++) {
            if (room.userId[i] != null && room.userId[i].equals(memberId)) {
                room.userId[i] = null;
                break; // Id를 찾으면 루프 종료
            }
        }
        for(int i = 0; i < room.maxUser; i++){
            if(room.userId[i] == null){
                System.out.println("0");
            }
            else{
                System.out.println(room.userId[i]);
            }
        }
        // 배열 정리 (null을 제외하고 앞으로 이동)
        Arrays.sort(room.userId, Comparator.nullsLast(Comparator.naturalOrder()));
        for(int i = 0; i < room.maxUser; i++){
            if(room.userId[i] == null){
                System.out.println("0");
            }
            else{
                System.out.println(room.userId[i]);
            }
        }
    }

    public List<Long> findRoomMemberIds(ChatRoom room) {
        return Arrays.stream(room.userId)
                     .filter(Objects::nonNull)
                     .collect(Collectors.toList());
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    public void removeRoom(String roomId) {
        chatRooms.remove(roomId);
    }
}
