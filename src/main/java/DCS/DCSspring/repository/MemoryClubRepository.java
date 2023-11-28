package DCS.DCSspring.repository;

import DCS.DCSspring.Domain.Article;
import DCS.DCSspring.Domain.Club;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryClubRepository implements ClubRepository{
    private static Map<Long, Club> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Club save(Club club) {
        club.setClubid(sequence++);
        store.put(club.getClubid(), club);
        return club;
    }

    @Override
    public Optional<Club> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Club> findByTitle(String title) {
        return store.values().stream()
                .filter(club -> club.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Club> findByContent(String content) {
        return store.values().stream()
                .filter(club -> club.getContent().contains(content))
                .findAny();
    }

    @Override
    public List<Club> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Club> sort_by_deadlineInt() {
        return store.values().stream()
                .sorted(Comparator.comparing(Club::getDeadline_int))
                .collect(Collectors.toList());
    }

    @Override
    public List<Club> deleteClub(Long id) {
        store.remove(id);
        return new ArrayList<>(store.values());
    }
}
