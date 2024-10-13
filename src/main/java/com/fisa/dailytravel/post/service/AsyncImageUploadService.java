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
    public CompletableFuture<String> uploadImagesAsync(int imgNo, byte[] imageFileData, String fileName, String contentType, Post post) throws Exception {
        System.out.println("Executing method asynchronously - " + Thread.currentThread().getName());
        try {
            String imageUrl = s3Uploader.uploadImage(imageDirectory, imageFileData, fileName, contentType);

            if (imgNo == 0) {
                post.setThumbnail(imageUrl);
            }
//            Thread.sleep(3000);
            postRepository.save(post);
            imageRepository.save(Image.builder()
                    .imageNo(imgNo)
                    .postId(post.getId())
                    .imagePath(imageUrl)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new AsyncResult<>("Image upload completed").completable();
    }
}
