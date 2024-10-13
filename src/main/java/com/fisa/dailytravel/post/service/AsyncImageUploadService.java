package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.ImageRepository;
import com.fisa.dailytravel.post.repository.PostRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncImageUploadService {
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    private final String imageDirectory = "post";

    public AsyncImageUploadService(S3Uploader s3Uploader, ImageRepository imageRepository, PostRepository postRepository) {
        this.s3Uploader = s3Uploader;
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
    }

    @Async("customTaskExecutor")
    public CompletableFuture<String> uploadImagesAsync(List<MultipartFile> imageFiles, Post post) throws Exception {
        List<Image> images = new ArrayList<>();
        int i = 0;
        for (MultipartFile imageFile : imageFiles) {

            System.out.println("Executing method asynchronously - " + Thread.currentThread().getName());

            String imageUrl = s3Uploader.uploadImage(imageDirectory, imageFile);
            Thread.sleep(3000);
            images.add(Image.builder()
                    .imageNo(i++)
                    .postId(post.getId())
                    .imagePath(imageUrl)
                    .build());
        }

        imageRepository.saveAll(images);

        // 첫 번째 이미지를 썸네일로 설정
        if (!images.isEmpty()) {
            post.setThumbnail(images.get(0).getImagePath());
            postRepository.save(post);
        }

        return new AsyncResult<>("Image upload completed").completable();
    }
}
