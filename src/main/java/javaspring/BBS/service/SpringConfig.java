package javaspring.BBS.service;

import jakarta.persistence.EntityManager;
import javaspring.BBS.repository.BulletinBoardRepository;
import javaspring.BBS.repository.JpaBulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SpringConfig {
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }
    @Bean
    public BulletinBoardService bulletinBoardService(){
        return new BulletinBoardService(bulletinBoardRepository());
    }
    @Bean
    public BulletinBoardRepository bulletinBoardRepository(){
        return new JpaBulletinBoardRepository(em);
    }
}