package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.repository.MemberRepository;
import DCS.DCSspring.repository.RatingRepository;

import java.util.List;

public class RatingService {
    private final RatingRepository ratingRepository;
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void join(Rating rating){
        ratingRepository.save(rating);
        //return rating.getMember().getId();
    }

    public Long getMemberId(Rating rating){
        return rating.getMember().getId();
    }

    public List<Rating> findRatings(){ //전체 회원 조회.
        return ratingRepository.findAll();
    }
    public void addScore(Long memberId ,int n){
        Rating rating = ratingRepository.findById(memberId).orElse(null);
        rating.addScore(n);
    }
}
