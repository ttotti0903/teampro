    package DCS.DCSspring.Domain;

    import lombok.Builder;
    import lombok.Getter;
    import org.springframework.web.socket.WebSocketSession;

    import java.util.HashSet;
    import java.util.Set;
    @Getter
    public class ChatRoom {
        private String roomId;
        private String name;
        public int userNum = 0;
        public int maxUser;
        public Long userId[];
        private Set<WebSocketSession> sessions = new HashSet<>();


        @Builder
        public ChatRoom(String roomId, String name) {
            this.roomId = roomId;
            this.name = name;
        }
    }
