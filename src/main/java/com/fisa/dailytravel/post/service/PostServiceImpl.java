package com.fisa.dailytravel.post.service;

import com.amazonaws.services.s3.AmazonS3;
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
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3 s3Client;

    @Override
    public String savePost(String uuid, PostRequest postRequest) throws IOException {
        User user = userRepository.findByUuid(uuid);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .placeName(postRequest.getPlaceName()).likesCount(0)
                .thumbnail(postRequest.getImageFiles().get(0).getOriginalFilename())
                .latitude(postRequest.getLatitude())
                .longitude(postRequest.getLongitude())
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
        Optional<Post> post = postRepository.findById(postId);
        List<Image> images = imageRepository.findByPostId(postId);

        List<String> imageFiles = new ArrayList<>();

        String path = post.get().getUser().getNickname() + "-postId" + postId + "-";

        for (Image image : images) {
            URL url = s3Client.getUrl("fisa-dailytravel-bucket-test", path + image.getImagePath());
            imageFiles.add(url.toString());
        }

        return PostResponse.of(post.get(), imageFiles);
    }
}
