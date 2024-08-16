package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.dto.PostPreviewResponse;
import com.fisa.dailytravel.post.models.Image;
import com.fisa.dailytravel.post.models.Post;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void signin(UserCreateRequest userCreateRequest) throws Exception {
        User user = userRepository.findByUuid(userCreateRequest.getUuid());
        Optional<User> userOptional = Optional.ofNullable(user);

        if (userOptional.isEmpty()) {
            user = new User();
            String nickname = "user_" + new Date().getTime();

            user.setUuid(userCreateRequest.getUuid());
            user.setNickname(nickname);
            user.setEmail(userCreateRequest.getEmail());
            user.setProfileImagePath(userCreateRequest.getPicture());
            user.setIsDeleted(false);
        }

        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserGetResponse getUser(String uuid) throws Exception {
        User user = userRepository.findByUuid(uuid);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isEmpty()) {
            return null;
        }

        PageRequest posts = PageRequest.of(0, 4);
        Page<Post> postList = likeRepository.findFavoritePostsByUserId(user.getId(), posts);

        List<PostPreviewResponse> likePreview = postList.stream()
                .map(post -> PostPreviewResponse.of(
                        post,
                        post.getImages().stream().map(Image::getImagePath).collect(Collectors.toList()),
                        post.getPostHashtags().stream().map(hashtag -> hashtag.getHashtag().getHashtagName()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        //get recent 1 post of mine
        List<Post> myRecentPost = postRepository.findLatestPostByUserId(user.getId(), PageRequest.of(0, 1));

        PostPreviewResponse myRecentPostPreview = PostPreviewResponse.of(
                myRecentPost.get(0),
                myRecentPost.get(0).getImages().stream().map(Image::getImagePath).collect(Collectors.toList()),
                myRecentPost.get(0).getPostHashtags().stream().map(hashtag -> hashtag.getHashtag().getHashtagName()).collect(Collectors.toList())
        );

        return UserGetResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .likedPosts(likePreview)
                .recentPost(myRecentPostPreview)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isDeleted(user.getIsDeleted())
                .build();
    }

    @Transactional
    @Override
    public UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException {
        User user = userRepository.findByUuid(uuid);
        Optional<User> userOptional = Optional.ofNullable(user);

        if (userOptional.isEmpty())
            return null;

        user.setNickname(userUpdateRequest.getNickname());

        MultipartFile imageFile = userUpdateRequest.getProfileImageFile();

        Optional<MultipartFile> imageFileOptional = Optional.ofNullable(imageFile);

        if (imageFileOptional.isPresent()) {
            String imageUrl = s3Uploader.uploadImage("user", user.getNickname(), user.getId(), imageFile);
            user.setProfileImagePath(imageUrl);
        }

        userRepository.save(user);

        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
