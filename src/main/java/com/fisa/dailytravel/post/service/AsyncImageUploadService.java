package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.ImageRepository;
import com.fisa.dailytravel.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncImageUploadService {
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    private final String imageDirectory = "post";

    @Async("customTaskExecutor")
    @Transactional
    public CompletableFuture<String> uploadImagesAsync(List<MultipartFile> imageFilesData, Post post) throws Exception {
        System.out.println("Executing method asynchronously - " + Thread.currentThread().getName());
        System.out.println("Image upload count: " + imageFilesData.size());
        List<Image> images = new ArrayList<>();
        int i = 0;
        for (MultipartFile imageFile : imageFilesData) {
            try {
                String imageUrl = s3Uploader.uploadImage(imageDirectory, imageFile);

                images.add(Image.builder()
                        .imageNo(i++)
                        .postId(post.getId())
                        .imagePath(imageUrl)
                        .build());
            } catch (Exception e) {
                // Postman 에서만 발생하는 오류는 아래와 같다.
                // Postman 오류 : java.nio.file.NoSuchFileException: ~~~~.tmp
                // 해당 오류를 catch 하여 로그 출력으로 처리.

                e.printStackTrace();
            }
        }

        if (!images.isEmpty()) {
            post.setThumbnail(images.get(0).getImagePath());
            postRepository.save(post);
            imageRepository.saveAll(images);
        }

        return new AsyncResult<>("Image upload completed").completable();
    }
}
