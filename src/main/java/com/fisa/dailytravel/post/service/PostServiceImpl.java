package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.dto.PostRequest;
import com.fisa.dailytravel.post.dto.PostResponse;
import com.fisa.dailytravel.post.models.Hashtag;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.models.PostHashtag;
import com.fisa.dailytravel.post.repository.HashTagRepository;
import com.fisa.dailytravel.post.repository.ImageRepository;
import com.fisa.dailytravel.post.repository.PostHashtagRepository;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.controller.models.User;
import com.fisa.dailytravel.user.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final S3Uploader s3Uploader;

    @Override
    public String savePost(String uuid, PostRequest postRequest) throws IOException {
        User user = userRepository.findByUuid(uuid);

        // TODO: thumbnail 경로 이름 수정
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .placeName(postRequest.getPlaceName()).likesCount(0)
                .thumbnail(postRequest.getImageFiles().get(0).getOriginalFilename())
                .latitude(postRequest.getLatitude())
                .longitude(postRequest.getLongitude())
                .createdAt(LocalDate.now())
                .user(user)
                .build();

        postRepository.save(post);

        List<MultipartFile> imageFiles = postRequest.getImageFiles();

        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = s3Uploader.uploadImage("post", user.getNickname(), post.getId(), imageFile);

            imageRepository.save(Image.builder()
                    .post(post)
                    .imagePath(imageUrl)
                    .build());
        }

        List<String> hashtags = postRequest.getHashtag();

        for (String hashtagName : hashtags) {
            Hashtag hashtag = Hashtag.builder()
                    .hashtagName(hashtagName)
                    .build();

            hashTagRepository.save(hashtag);
            postHashtagRepository.save(PostHashtag.builder()
                    .post(post)
                    .hashtag(hashtag)
                    .build());
        }
        return "게시글 저장 완료";
    }

    @Override
    public PostResponse getPost(String uuid, Long postId) {
        return PostResponse.of(postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글 없음")));
    }
}
