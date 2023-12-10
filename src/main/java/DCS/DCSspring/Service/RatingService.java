package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.repository.RatingRepository;

import java.util.List;
import java.util.Optional;

public class RatingService {
    private final RatingRepository ratingRepository;
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Long join(Rating rating){
        ratingRepository.save(rating);
        return rating.getMember_id();
    }

    public Long getMemberId(Rating rating){
        return rating.getMember_id();
    }

    public List<Rating> findRatings(){ //전체 회원 조회.
        return ratingRepository.findAll();
    }
    public void addScore(Long memberId ,int n){
        Rating rating = ratingRepository.findById(memberId).orElse(null);
        rating.addScore(n);
    }

    public Optional<Rating> findbyMemberId(Long memberId){
        return ratingRepository.findById(memberId);
    }
    public void sort() {
        ratingRepository.sort_by_score();
    }

    public List<Rating> findTen(){
        return ratingRepository.findTen();
    }
}
