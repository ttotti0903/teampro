package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Club;
import DCS.DCSspring.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }
    public Long join(Club club){
        clubRepository.save(club);
        return club.getClubid();
    }
    public List<Club> findClubs(){return clubRepository.findAll();}
    public Optional<Club> findClubById(Long id) {
        return clubRepository.findById(id);
    }

    public List<Club> findArticleByTitle(String title) {
        return clubRepository.findByTitle(title);
    }

    public List<Club> sort_by_deadlineInt() {
        return clubRepository.sort_by_deadlineInt();
    }

    public List<Club> deleteArticle(Long id) {
        clubRepository.deleteClub(id);
        return clubRepository.findAll();
    }

    public List<Club> SelcetFive() {
        return clubRepository.SelcetFive();
    }
}
