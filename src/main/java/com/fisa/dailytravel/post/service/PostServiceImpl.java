package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.PostHashtag;
import com.fisa.dailytravel.post.repository.HashTagRepository;
import com.fisa.dailytravel.post.repository.ImageRepository;
import com.fisa.dailytravel.post.repository.PostHashtagRepository;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.exceptions.UserNotFoundException;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final AsyncImageUploadService asyncImageUploadService;

    private final String imageDirectory = "post";

    @Override
    @Transactional
    public Post savePost(String uuid, PostRequest postRequest) throws Exception {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));

        //게시글 생성
        Post post = Post.builder()
                .userId(user.getId())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        post = postRepository.save(post);

        if (!postRequest.getHashtags().isEmpty()) {
            //기존 해시태그 목록 검색
            List<Hashtag> existingHashtags = hashTagRepository.findExistingHashtags(postRequest.getHashtags());
            List<PostHashtag> postHashtags = postHashtagRepository.findAllByHashtags(existingHashtags);

            //기존 목록에 없는 해시태그들 생성
            List<Hashtag> newHashtags = new ArrayList<>();
            boolean flag = false;
            for (String hashtagName : postRequest.getHashtags()) {
                for (Hashtag existingHashtag : existingHashtags) {
                    if (existingHashtag.getHashtagName().equals(hashtagName)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    newHashtags.add(Hashtag.builder()
                            .hashtagName(hashtagName)
                            .build());
                }
            }
            hashTagRepository.saveAll(newHashtags);

            //게시글-해시태그 연결
            for (Hashtag hashtag : existingHashtags) {
                PostHashtag postHashtag = PostHashtag.builder()
                        .postId(post.getId())
                        .hashtagId(hashtag.getId())
                        .build();
                postHashtags.add(postHashtag);
            }
            for (Hashtag hashtag : newHashtags) {
                PostHashtag postHashtag = PostHashtag.builder()
                        .postId(post.getId())
                        .hashtagId(hashtag.getId())
                        .build();
                postHashtags.add(postHashtag);
            }
            postHashtagRepository.saveAll(postHashtags);
        }

//        if (!postRequest.getImageFiles().isEmpty()) {
//            //이미지 생성
//            List<MultipartFile> imageFiles = postRequest.getImageFiles();

//            List<Image> imagesNew = new ArrayList<>();

//            int i = 0;
//            for (MultipartFile imageFile : imageFiles) {
//                String imageUrl = s3Uploader.uploadImage(imageDirectory, imageFile);
//                Thread.sleep(2000);
//                imagesNew.add(Image.builder()
//                        .imageNo(i++)
//                        .postId(post.getId())
//                        .imagePath(imageUrl)
//                        .build());
//            }
//
//            imageRepository.saveAll(imagesNew);
//
//            post.setThumbnail(imagesNew.get(0).getImagePath());
//            post = postRepository.save(post);
//        }

        // 이미지 업로드를 비동기 작업으로 처리 (게시글은 즉시 반환)
        if (!postRequest.getImageFiles().isEmpty()) {
//            asyncImageUploadService.uploadImagesAsync(postRequest.getImageFiles(), post);

            List<MultipartFile> imageFiles = postRequest.getImageFiles();
            int i = 0;
            for (MultipartFile imageFile : imageFiles) {
                byte[] imageFileData = imageFile.getBytes();
                String fileName = imageFile.getOriginalFilename();
                String contentType = imageFile.getContentType();
                asyncImageUploadService.uploadImagesAsync(i++, imageFileData, fileName, contentType, post);
            }
        }


        return post;
    }

    @Override
    public PostResponse getPost(String uuid, Long postId) throws Exception {
        PostResponse postResponse = postRepository.getPost(uuid, postId);
        return postResponse;
    }

    @Override
    public Page<PostPreviewResponse> getPosts(String uuid, Pageable pageRequest) throws Exception {
        Page<PostPreviewResponse> postPreviewResponses = postRepository.getPosts(uuid, pageRequest);
        return postPreviewResponses;
    }

}
