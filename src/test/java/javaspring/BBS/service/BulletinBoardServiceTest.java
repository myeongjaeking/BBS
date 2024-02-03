package javaspring.BBS.service;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.repository.BulletinBoardRepository;
import javaspring.BBS.repository.MemoryBulletinBoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BulletinBoardServiceTest {
    BulletinBoardService bulletinBoardService;
    MemoryBulletinBoardRepository memoryBulletinBoardRepository;
    @BeforeEach
    public void beforeEach(){
        memoryBulletinBoardRepository = new MemoryBulletinBoardRepository();
        bulletinBoardService = new BulletinBoardService(memoryBulletinBoardRepository);
    }
    @AfterEach
    public void afterEach(){
        memoryBulletinBoardRepository.clearStore();
    }
    @Test
    public void 글작성() throws Exception{
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setTitle("spring");
        String title = bulletinBoardService.create(bulletinBoard);

        Optional<BulletinBoard> optionalBulletinBoard = memoryBulletinBoardRepository.findByTitle(title);
        BulletinBoard findTitle = optionalBulletinBoard.get();
        assertEquals(bulletinBoard.getTitle(),findTitle.getTitle());
    }
    @Test
    public void 중복_글작성_예외() throws Exception{
        BulletinBoard bulletinBoard1 = new BulletinBoard();
        bulletinBoard1.setTitle("spring");

        BulletinBoard bulletinBoard2 = new BulletinBoard();
        bulletinBoard2.setTitle("spring");

        bulletinBoardService.create(bulletinBoard1);
        IllegalStateException e =assertThrows(IllegalStateException.class,()->bulletinBoardService.create(bulletinBoard2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 제목입니다.");

    }
}