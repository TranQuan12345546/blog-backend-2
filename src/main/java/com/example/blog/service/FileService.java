package com.example.blog.service;

import com.example.blog.entity.Image;
import com.example.blog.entity.User;
import com.example.blog.repository.ImageRepository;
import com.example.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private ImageRepository imageRepository;

    private UserRepository userRepository;


    public List<Image> getFilesOfUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found user");
                });
        return imageRepository.findByUser_Id(id);
    }

    public void deleteImageUserLogin(Integer id) {
        int userLogin = 1;
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found file");
                });
        if (image.getUser().getId() == userLogin) {
            imageRepository.delete(image);
        } else {
            throw new RuntimeException("Not file of user with id: " + userLogin);
        }
    }

    public Image uploadImage(MultipartFile file) {

        // Upload file
        User user = userRepository.findById(1).orElse(null);
        try {
            Image image = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .user(user)
                    .build();

            imageRepository.save(image);

            return image;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file");
        }
    }

    public Image getImage(int id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not found file");
        });
        return image;
    }

}
