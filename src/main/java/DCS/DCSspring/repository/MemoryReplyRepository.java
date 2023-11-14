package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Reply;

import java.util.HashMap;
import java.util.Map;

public class MemoryReplyRepository {
    private static Map<Long, Reply> store = new HashMap<>();
    private static long sequence = 0L;
}
