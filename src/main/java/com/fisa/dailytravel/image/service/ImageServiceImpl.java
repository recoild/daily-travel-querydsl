package com.fisa.dailytravel.image.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.image.models.Image;
import com.fisa.dailytravel.image.repository.ImageRepository;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Override
    public void saveImages(Long postId, String uuid, List<MultipartFile> imageFiles) throws Exception {
        if (imageFiles != null) {
            List<Image> imagesNew = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                String imageUrl = s3Uploader.uploadImage("post", uuid, postId, imageFile);
                imagesNew.add(Image.builder()
                        .postId(postId)
                        .imagePath(imageUrl)
                        .build());
            }

            imageRepository.saveAll(imagesNew);
        }
    }

    @Override
    public void updateImages() throws Exception {

    }

    @Override
    public void deleteImages() throws Exception {

    }

    @Override
    public void getImages() throws Exception {

    }
}
