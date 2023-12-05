package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Club;

import java.util.List;
import java.util.Optional;

public interface ClubRepository {
    Club save(Club club);
    Optional<Club> findById(Long id);
    List<Club> findByTitle(String title);
    Optional<Club> findByContent(String content);
    List<Club> findAll();
    List<Club> sort_by_deadlineInt();
    List<Club> deleteClub(Long id);
    List<Club> SelcetFive();
}
