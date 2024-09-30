package com.fisa.dailytravel.image.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.image.dto.ImageCreateRequest;
import com.fisa.dailytravel.image.dto.ImageGetRequest;
import com.fisa.dailytravel.image.dto.ImageGetResponse;
import com.fisa.dailytravel.image.models.Image;
import com.fisa.dailytravel.image.repository.ImageRepository;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ImagePostServiceImpl implements ImageService {
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveImages(ImageCreateRequest imageCreateRequest) throws Exception {
        List<MultipartFile> imageFiles = imageCreateRequest.getImageFiles();
        if (!imageFiles.isEmpty()) {
            List<Image> imagesNew = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                String imageUrl = s3Uploader.uploadImage(imageCreateRequest.getDirectoryName(), imageFile);

                imagesNew.add(Image.builder()
                        .postId(imageCreateRequest.getPostId())
                        .imagePath(imageUrl)
                        .build());
            }

            imageRepository.saveAll(imagesNew);
        }
    }

    @Override
    public List<ImageGetResponse> getImages(ImageGetRequest imageGetRequest) throws Exception {
        List<Image> images = imageRepository.findByPostId(imageGetRequest.getPostId());
        List<ImageGetResponse> imageGetResponses = new ArrayList<>();
        for (Image image : images) {
            imageGetResponses.add(ImageGetResponse.builder()
                    .imagePath(image.getImagePath())
                    .build());
        }
        return imageGetResponses;
    }

    @Transactional
    @Override
    public void updateImages(ImageCreateRequest imageCreateRequest) throws Exception {
        List<MultipartFile> imageFiles = imageCreateRequest.getImageFiles();

        if (!imageFiles.isEmpty()) {
            deleteImages(imageCreateRequest.getPostId());

            List<Image> imagesNew = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                String imageUrl = s3Uploader.uploadImage(imageCreateRequest.getDirectoryName(), imageFile);

                imagesNew.add(Image.builder()
                        .postId(imageCreateRequest.getPostId())
                        .imagePath(imageUrl)
                        .build());
            }

            imageRepository.saveAll(imagesNew);
        }
    }

    @Transactional
    @Override
    public void deleteImages(Long postId) throws Exception {
        List<Image> images = imageRepository.findByPostId(postId);
        for (Image image : images) {
            s3Uploader.deleteImage(image.getImagePath());
        }

        imageRepository.deleteByPostId(postId);
    }

}
