package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {
    void save(Rating rating);

    Optional<Rating> findById(Long id);

    List<Rating> findAll();

    void sort_by_score();

}
