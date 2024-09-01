package com.example.dev_pro.impl;

import com.example.dev_pro.model.AvatarPet;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.AvatarPetRepository;
import com.example.dev_pro.service.AvatarPetService;
import com.example.dev_pro.service.PetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Класс, создающий логику по работе с аватарками питомцев в базе данных
 */

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AvatarPetServiceImpl implements AvatarPetService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final PetService petService;
    private final AvatarPetRepository repository;

    @Override
    public Collection<AvatarPet> findAllAvatars() {
        return repository.findAll();
    }

    @Override
    public AvatarPet findAvatarByPetId(Long petId) {
        log.debug("Was invoked method for find avatar or create avatar");
        return repository.findAvatarByPetId(petId).orElse(new AvatarPet());
    }

    @Override
    public void uploadAvatar(Long petId, MultipartFile avatarFile) throws IOException {
        Pet pet = petService.findPetById(petId);
        Path filePath = Path.of(avatarsDir, petId + "." + getExtension(avatarFile.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            log.info("Converting bytes ....");
            bis.transferTo(bos);
        }

        log.info("File has been uploaded!");
        AvatarPet avatar = findAvatarByPetId(petId);
        avatar.setPet(pet);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(GenerateImagePreview(filePath));

        repository.save(avatar);
        log.info("Avatar has been saved! id = {}, path = {}", avatar.getId(), filePath);
    }

    private byte[] GenerateImagePreview(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public void setAvatarsDir(String avatarsDir) {
        this.avatarsDir = avatarsDir;
    }
}