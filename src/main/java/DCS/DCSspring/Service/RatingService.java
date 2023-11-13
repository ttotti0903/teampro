package DCS.DCSspring.Service;

import DCS.DCSspring.Domain.Rating;
import DCS.DCSspring.repository.MemberRepository;
import DCS.DCSspring.repository.RatingRepository;

import java.util.List;

public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;
    public RatingService(RatingRepository ratingRepository, MemberRepository memberRepository) {
        this.ratingRepository = ratingRepository;
        this.memberRepository = memberRepository;
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
}
