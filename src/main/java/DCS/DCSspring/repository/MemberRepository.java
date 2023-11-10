package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();


}
