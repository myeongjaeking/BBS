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
    public void deleteById(Long id) {
        // 삭제할 게시글 가져오기
        BulletinBoard deletedBulletinBoard = store.remove(id);

        // 삭제된 게시글의 ID 이후의 게시글 ID를 1씩 앞으로 당기기
        if (deletedBulletinBoard != null) {
            for (Long i = id + 1; i <= sequence; i++) {
                Long currentId = i;
                Long newId = currentId - 1;
                // 기존 ID를 새로운 ID로 업데이트
                BulletinBoard bulletinBoardToUpdate = store.remove(currentId); //정상적으로 삭제되면, 해당 키와 매핑되는 값을 반환
                if (bulletinBoardToUpdate != null) {
                    bulletinBoardToUpdate.setId(newId);
                    store.put(newId, bulletinBoardToUpdate);
                }
            }
            // 시퀀스 값 조정
            sequence--;
        }
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
