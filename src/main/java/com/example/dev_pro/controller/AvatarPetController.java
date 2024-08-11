package com.example.dev_pro.controller;

import com.example.dev_pro.model.AvatarPet;
import com.example.dev_pro.service.AvatarPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatar")
@Tag(name = "API для работы с аватарками питомцев")
@RequiredArgsConstructor
public class AvatarPetController {

    private final AvatarPetService service;


    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла на жесткий диск и в базу данных")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {

        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        service.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    @Operation(summary = "Выгрузка файла из базы данных")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        AvatarPet avatar = service.findAvatarByPetId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    @Operation(summary = "Выгрузка файла с жесткого диска")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AvatarPet avatar = service.findAvatarByPetId(id);
        Path path = Path.of(avatar.getFilePath());

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            bis.transferTo(bos);
        }
    }
}
