package com.fisa.dailytravel.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        savePostImages(post, imageFiles);

        List<String> hashtags = postRequest.getHashtags();

        saveHashtag(post, hashtags);

        return "게시글 저장 완료";
    }

    @Override
    public PostResponse getPost(String uuid, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        List<Image> images = imageRepository.findByPostId(postId);

        List<String> imageFiles = new ArrayList<>();

        getPostImages(images);

        return PostResponse.of(post.get(), imageFiles);
    }

    public void savePostImages(Post post, List<MultipartFile> imageFiles) throws IOException {
        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = s3Uploader.uploadImage("post", post.getUser().getNickname(), post.getId(), imageFile);

            imageRepository.save(Image.builder()
                    .post(post)
                    .imagePath(imageUrl)
                    .build());
        }
    }

    public List<String> getPostImages(List<Image> images) {
        List<String> imageFiles = new ArrayList<>();

        for (Image image : images) {
            imageFiles.add(image.getImagePath());
        }

        return imageFiles;
    }

    public void saveHashtag(Post post, List<String> hashtags) {
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
    }

    public void deleteHashtag(Post post) {
        List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(post.getId());

        for (PostHashtag postHashtag : postHashtags) {
            hashTagRepository.deleteById(postHashtag.getHashtag().getId());
        }
    }

    @Transactional
    @Override
    public PostPagingResponse getAllPosts(String uuid, PostPagingRequest postPagingRequest) {
        Page<Post> postPageList = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(postPagingRequest.getPage(), postPagingRequest.getCount()));

        List<PostPreviewResponse> postPreviewResponses = new ArrayList<>();

        postPageList.stream().forEach(post -> {
            List<PostHashtag> postHashtag = postHashtagRepository.findByPostId(post.getId());

            List<String> hashtags = new ArrayList<>();
            for (PostHashtag hashtag : postHashtag) {
                hashtags.add(hashtag.getHashtag().getHashtagName());
            }

            List<Image> images = imageRepository.findByPostId(post.getId());

            postPreviewResponses.add(PostPreviewResponse.of(post, getPostImages(images), hashtags));
        });

        return PostPagingResponse.builder()
                .page(postPagingRequest.getPage())
                .postPreviewResponses(postPreviewResponses)
                .isEnd(postPageList.isLast())
                .build();
    }

    @Transactional
    @Override
    public String modifyPost(String uuid, PostRequest postRequest) throws IOException {
        Post post = postRepository.findById(postRequest.getId()).get();

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setPlaceName(postRequest.getPlaceName());
        post.setLatitude(postRequest.getLatitude());
        post.setLongitude(postRequest.getLongitude());

        // 기존 이미지 삭제 후 새로운 이미지 저장
        List<Image> images = imageRepository.findByPostId(postRequest.getId());

        for (String postImageUrl : getPostImages(images)) {
            s3Uploader.deleteImage(postImageUrl);
        }
        imageRepository.deleteAllByPost(post);

        savePostImages(post, postRequest.getImageFiles());

        // 기존 해시태그 삭제 후 새로운 해시태그 저장
        deleteHashtag(post);
        postHashtagRepository.deleteAllByPost(post);

        List<String> hashtags = postRequest.getHashtags();
        saveHashtag(post, hashtags);

        return "게시글 수정 완료";
    }

    @Transactional
    @Override
    public String deletePost(String uuid, Long postId) {
        Post post = postRepository.findById(postId).get();

        imageRepository.deleteAllByPost(post);
        deleteHashtag(post);
        postHashtagRepository.deleteAllByPost(post);
        postRepository.deleteById(postId);
        return "게시글 삭제 완료";
    }
}
