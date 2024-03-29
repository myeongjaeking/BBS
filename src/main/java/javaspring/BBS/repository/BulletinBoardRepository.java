package javaspring.BBS.repository;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BulletinBoardRepository {
    BulletinBoard save(BulletinBoard bulletinBoard, Long member_id);
    Optional<BulletinBoard> findById(Long id);
    Optional<BulletinBoard> findByTitle(String title);
    Optional<BulletinBoard> findByContent(String content);
    List<BulletinBoard> findAll();
    void deleteById(Long id);
    BulletinBoard edit_bulletinboard(Long id,BulletinBoard bulletinBoard);

}
