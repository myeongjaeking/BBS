package javaspring.BBS.controller;

import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.service.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
public class FileController {
    private final BulletinBoardService bulletinBoardService;

    @Autowired
    public FileController(BulletinBoardService bulletinBoardService){
        this.bulletinBoardService = bulletinBoardService;
    }

    private String getFilePathById(Long id){
        Optional<BulletinBoard>optionalBulletinBoard = bulletinBoardService.findOne(id);
        if(optionalBulletinBoard.isPresent()){
            BulletinBoard bulletinBoard = optionalBulletinBoard.get();
            return  bulletinBoard.getFileUrl();
        }
        return null;
    }
    @GetMapping("/bulletinboards/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id")Long id)throws IOException{
        String filePath = getFilePathById(id);

        if(filePath==null){
            return ResponseEntity.notFound().build();
        }
        InputStream inputStream = new FileInputStream(new File(filePath));

        InputStreamResource resource = new InputStreamResource(inputStream);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
}
