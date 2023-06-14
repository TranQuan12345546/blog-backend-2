package com.example.blog.controller;

import com.example.blog.entity.Image;
import com.example.blog.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class ImageController {
    private FileService fileService;
    @PostMapping("api/v1/files")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) {
        Image image = fileService.uploadImage(file);
        return ResponseEntity.ok("Upload success");
    }

    @GetMapping("api/v1/files/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        Image image = fileService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());
    }

    @GetMapping("api/v1/users/{id}/files")
    public ResponseEntity<?> getAllImageUserLogin(@PathVariable int id) {
        List<Image> images= fileService.getFilesOfUser(id);
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("api/v1/files/{id}")
    public ResponseEntity<?> deleteImageUserLogin(@PathVariable int id) {
        fileService.deleteImageUserLogin(id);
        return ResponseEntity.ok("delete success");
    }
}
