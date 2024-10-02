package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.like.repository.LikeRepository;
import com.fisa.dailytravel.post.repository.PostRepository;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserGetResponse;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;
import com.fisa.dailytravel.user.exceptions.UserNotFoundException;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

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
        Optional<User> user = userRepository.findByUuid(userCreateRequest.getUuid());

        if (user.isEmpty()) {
            User newUser = new User();
            String nickname = "user_" + new Date().getTime();

            newUser.setUuid(userCreateRequest.getUuid());
            newUser.setNickname(nickname);
            newUser.setEmail(userCreateRequest.getEmail());
            newUser.setProfileImagePath(userCreateRequest.getPicture());
            newUser.setIsDeleted(false);

            userRepository.save(newUser);
        }
    }

    @Transactional
    @Override
    public UserGetResponse getUserFeed(String uuid) throws Exception {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));

        return null;
//        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
//
//        PageRequest posts = PageRequest.of(0, 4);
//        Page<Post> postList = likeRepository.findFavoritePostsByUserId(user.getId(), posts);
//
//
//        List<PostPreviewResponse> likePreview = postList.stream()
//                .map(post -> PostPreviewResponse.of(
//                        post,
//                        post.getImages().stream().map(Image::getImagePath).collect(Collectors.toList()),
//                        post.getPostHashtags().stream().map(hashtag -> hashtag.getHashtag().getHashtagName()).collect(Collectors.toList())
//                ))
//                .collect(Collectors.toList());
//
//        //get recent 1 post of mine
//        Page<Post> myRecentPosts = userRepository.findLatestPostsByUuid(user.getUuid(), PageRequest.of(0, 4));
//        PostPreviewResponse myRecentPostPreview = null;
//        if (myRecentPosts.getTotalElements() > 0) {
//            Post recentPost = myRecentPosts.getContent().get(0);
//            myRecentPostPreview = PostPreviewResponse.of(
//                    recentPost,
//                    recentPost.getImages().stream().map(Image::getImagePath).collect(Collectors.toList()),
//                    recentPost.getPostHashtags().stream().map(hashtag -> hashtag.getHashtag().getHashtagName()).collect(Collectors.toList())
//            );
//        }
//
//        return UserGetResponse.builder()
//                .email(user.getEmail())
//                .nickname(user.getNickname())
//                .profileImagePath(user.getProfileImagePath())
//                .likedPosts(likePreview)
//                .recentPost(myRecentPostPreview)
//                .createdAt(user.getCreatedAt())
//                .updatedAt(user.getUpdatedAt())
//                .isDeleted(user.getIsDeleted())
//                .build();
    }

    @Transactional
    @Override
    public UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));

        user.setNickname(userUpdateRequest.getNickname());

        MultipartFile imageFile = userUpdateRequest.getProfileImageFile();

        Optional<MultipartFile> imageFileOptional = Optional.ofNullable(imageFile);

        if (imageFileOptional.isPresent()) {
            String imageUrl = s3Uploader.uploadImage("user/"+user.getUuid(), imageFile);
            user.setProfileImagePath(imageUrl);
        }

        userRepository.save(user);

        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }

    @Transactional
    @Override
    public void deleteUser(String uuid) throws Exception {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));

        user.setIsDeleted(true);

        userRepository.save(user);
    }
}
