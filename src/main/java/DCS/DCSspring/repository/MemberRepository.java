package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
}