package com.conexia.api.image;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam MultipartFile multipartImage) {
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getName());

        try {
            dbImage.setContent(multipartImage.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not acceptable image");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(imageRepository.save(dbImage).getId());
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource download(@PathVariable Long id) {
        byte[] image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getContent();

        return new ByteArrayResource(image);
    }
}
