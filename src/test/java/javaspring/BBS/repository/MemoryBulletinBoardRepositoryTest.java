package javaspring.BBS.repository;

import javaspring.BBS.domain.BulletinBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryBulletinBoardRepositoryTest {
    MemoryBulletinBoardRepository repository = new MemoryBulletinBoardRepository();
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    public void save() {
        BulletinBoard  bulletinBoard = new BulletinBoard();
        bulletinBoard.setTitle("spring");

        repository.save(bulletinBoard);
        BulletinBoard result = repository.findById(bulletinBoard.getId()).get();
        assertThat(bulletinBoard).isEqualTo(result);
    }


    @Test
    void findByTitle() {
        BulletinBoard bulletinBoard1 = new BulletinBoard();
        bulletinBoard1.setTitle("spring1");
        repository.save(bulletinBoard1);

        BulletinBoard bulletinBoard2 = new BulletinBoard();
        bulletinBoard2.setTitle("spring2");
        repository.save(bulletinBoard2);

        BulletinBoard result = repository.findByTitle("spring2").get();
        assertThat(result).isEqualTo(bulletinBoard2);

    }



    @Test
    void findAll() {
        BulletinBoard bulletinBoard1 = new BulletinBoard();
        bulletinBoard1.setTitle("spring1");
        repository.save(bulletinBoard1);

        BulletinBoard bulletinBoard2 = new BulletinBoard();
        bulletinBoard2.setTitle("spring2");
        repository.save(bulletinBoard2);
        BulletinBoard bulletinBoard3 = new BulletinBoard();
        bulletinBoard3.setTitle("spring3");
        repository.save(bulletinBoard3);
        List<BulletinBoard> result = repository.findAll();
        assertThat(result.size()).isEqualTo(3);
    }
}