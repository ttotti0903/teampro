package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Member;
import DCS.DCSspring.Domain.Rating;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryRatingRepository implements RatingRepository{
    private static List<Rating> store = new ArrayList<Rating>();

    @Override
    public void save(Rating rating){
        store.add(rating);
        sort_by_score();
    }

    @Override
    public Optional<Rating> findById(Long id){
        for(Rating target: store){
            if(target.getMember_id().equals(id))
                return Optional.of(target);
        }
        return null;
    }

    @Override
    public List<Rating> findAll(){
        return store;
    }

    @Override
    public void sort_by_score(){
        Collections.sort(store, Comparator.comparingDouble(Rating::getScore_avg).reversed());

    }
    public List<Rating> findTen() {
        return store.stream()
                .sorted(Comparator.comparing(Rating::getScore_avg).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
