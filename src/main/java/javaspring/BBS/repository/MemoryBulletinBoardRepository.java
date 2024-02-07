package javaspring.BBS.repository;

import javaspring.BBS.domain.BulletinBoard;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class MemoryBulletinBoardRepository implements BulletinBoardRepository {
    private static final Map<Long, BulletinBoard> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public BulletinBoard save(BulletinBoard bulletinBoard){
        bulletinBoard.setId(++sequence);
        store.put(bulletinBoard.getId(),bulletinBoard);
        return bulletinBoard;
    }


    @Override
    public Optional<BulletinBoard> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    @Override
    public  BulletinBoard edit_bulletinboard(Long id, BulletinBoard bulletinBoard){
        store.remove(id);
        store.put(bulletinBoard.getId(), bulletinBoard);
        return bulletinBoard;
    }
    @Override
    public Optional<BulletinBoard> findByTitle(String title) {
        return store.values().stream()
                .filter(bulletinBoard -> bulletinBoard.getTitle().equals(title))
                .findAny();
    }

    @Override
    public Optional<BulletinBoard> findByContent(String content) {
        return store.values().stream()
                .filter(bulletinBoard -> bulletinBoard.getContent().equals(content))
                .findAny();
    }

    @Override
    public List<BulletinBoard> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore(){store.clear();}

}
