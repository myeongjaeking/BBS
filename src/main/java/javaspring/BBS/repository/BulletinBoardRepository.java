package javaspring.BBS.repository;

import javaspring.BBS.domain.BulletinBoard;

import java.util.List;
import java.util.Optional;

public interface BulletinBoardRepository {
    BulletinBoard save(BulletinBoard bulletinBoard);
    Optional<BulletinBoard> findById(Long id);
    Optional<BulletinBoard> findByTitle(String title);
    Optional<BulletinBoard> findByContent(String content);
    List<BulletinBoard> findAll();

}
