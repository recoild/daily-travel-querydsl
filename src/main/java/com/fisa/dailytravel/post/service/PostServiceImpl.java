//package com.fisa.dailytravel.post.service;
//
//import com.fisa.dailytravel.comment.dto.CommentPageRequest;
//import com.fisa.dailytravel.comment.dto.CommentResponse;
//import com.fisa.dailytravel.comment.service.CommentService;
//import com.fisa.dailytravel.global.config.S3Uploader;
//import com.fisa.dailytravel.post.dto.PostPagingRequest;
//import com.fisa.dailytravel.post.dto.PostPagingResponse;
//import com.fisa.dailytravel.post.dto.PostPreviewResponse;
//import com.fisa.dailytravel.post.dto.PostRequest;
//import com.fisa.dailytravel.post.dto.PostResponse;
//import com.fisa.dailytravel.post.dto.PostSearchPagingRequest;
//import com.fisa.dailytravel.post.models.Hashtag;
//import com.fisa.dailytravel.image.models.Image;
//import com.fisa.dailytravel.post.models.Post;
//import com.fisa.dailytravel.post.models.PostHashtag;
//import com.fisa.dailytravel.post.repository.HashTagRepository;
//import com.fisa.dailytravel.image.repository.ImageRepository;
//import com.fisa.dailytravel.post.repository.PostHashtagRepository;
//import com.fisa.dailytravel.post.repository.PostRepository;
//import com.fisa.dailytravel.user.exceptions.UserNotFoundException;
//import com.fisa.dailytravel.user.models.User;
//import com.fisa.dailytravel.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class PostServiceImpl implements PostService {
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//    private final CommentService commentService;
//    private final ImageRepository imageRepository;
//    private final HashTagRepository hashTagRepository;
//    private final PostHashtagRepository postHashtagRepository;
//    private final S3Uploader s3Uploader;
//
//    @Override
//    @Transactional
//    public String savePost(String uuid, PostRequest postRequest) throws IOException {
//        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
//
//        Post post = Post.builder()
//                .title(postRequest.getTitle())
//                .content(postRequest.getContent())
//                .placeName(postRequest.getPlaceName()).likesCount(0)
//                // .thumbnail(postRequest.getImageFiles().get(0).getOriginalFilename())
////                .latitude(postRequest.getLatitude())
////                .longitude(postRequest.getLongitude())
//                .user(user)
//                .postHashtags(new HashSet<>())
//                .build();
//
//        postRepository.save(post);
//
//        if (postRequest.getImageFiles() != null) {
//            List<Image> imagesNew = new ArrayList<>();
//            for (MultipartFile imageFile : postRequest.getImageFiles()) {
//                String imageUrl = s3Uploader.uploadImage("post", post.getUser().getNickname(), post.getId(), imageFile);
//                imagesNew.add(Image.builder()
//                        .post(post)
//                        .imagePath(imageUrl)
//                        .build());
//            }
//
//            imageRepository.saveAll(imagesNew);
//        }
//
//        postHashtagRepository.deleteByPostId(post.getId());
//
//        List<String> hashtags = postRequest.getHashtags();
//        if (hashtags != null) {
//            Map<String, Hashtag> existingHashtags = hashTagRepository.findAll()
//                    .stream()
//                    .collect(Collectors.toMap(Hashtag::getHashtagName, Function.identity()));
//
//            HashSet<PostHashtag> postHashtags = new HashSet<>();
//            for (String hashtagNew : hashtags) {
//                Hashtag hashtag = existingHashtags.computeIfAbsent(hashtagNew, name -> {
//                    Hashtag newHashtag = new Hashtag();
//                    newHashtag.setHashtagName(name);
//                    return hashTagRepository.save(newHashtag);
//                });
//
//                PostHashtag postHashtag = new PostHashtag();
//                postHashtag.setPost(post);
//                postHashtag.setHashtag(hashtag);
//                postHashtags.add(postHashtag);
//            }
//
//            postHashtagRepository.saveAll(postHashtags);
//        }
//
//        return "게시글 저장 완료";
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PostResponse getPost(String uuid, Long postId, CommentPageRequest commentPageRequest) {
//        Optional<Post> post = postRepository.findById(postId);
//        List<Image> images = imageRepository.findByPostId(postId);
//
//        List<String> imageFiles = new ArrayList<>();
//        List<String> hashtags = new ArrayList<>();
//        List<CommentResponse> comments = new ArrayList<>();
//        String authorProfileImagePath = "";
//
//        boolean mine = false;
//        if (post.isPresent()) {
//            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
//            for (PostHashtag postHashtag : postHashtags) {
//                hashtags.add(postHashtag.getHashtag().getHashtagName());
//            }
//            authorProfileImagePath = post.get().getUser().getProfileImagePath();
//
//            comments = commentService.getComments(postId, commentPageRequest);
//
//            if (post.get().getUser().getUuid().equals(uuid)) {
//                mine = true;
//            }
//        }
//
//        getPostImages(images);
//
//        imageFiles = getPostImages(images);
//
//        return PostResponse.of(post.get(), imageFiles, hashtags, authorProfileImagePath, comments, mine);
//    }
//
//    public void savePostImages(Post post, List<MultipartFile> imageFiles) throws IOException {
//        for (MultipartFile imageFile : imageFiles) {
//            String imageUrl = s3Uploader.uploadImage("post", post.getUser().getNickname(), post.getId(), imageFile);
//
//            imageRepository.save(Image.builder()
//                    .post(post)
//                    .imagePath(imageUrl)
//                    .build());
//        }
//    }
//
//    public List<String> getPostImages(List<Image> images) {
//        List<String> imageFiles = new ArrayList<>();
//
//        for (Image image : images) {
//            imageFiles.add(image.getImagePath());
//        }
//
//        return imageFiles;
//    }
//
//    public void saveHashtag(Post post, List<String> hashtags) {
//        for (String hashtagName : hashtags) {
//            Hashtag hashtag = Hashtag.builder()
//                    .hashtagName(hashtagName)
//                    .build();
//
//            hashTagRepository.save(hashtag);
//            postHashtagRepository.save(PostHashtag.builder()
//                    .post(post)
//                    .hashtag(hashtag)
//                    .build());
//        }
//    }
//
//    public void deleteHashtag(Post post) {
//        List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(post.getId());
//
//        for (PostHashtag postHashtag : postHashtags) {
//            hashTagRepository.deleteById(postHashtag.getHashtag().getId());
//        }
//    }
//
//    @Transactional
//    @Override
//    public PostPagingResponse getAllPosts(String uuid, PostPagingRequest postPagingRequest) {
//        Page<Post> postPageList = postRepository.findAllByOrderByCreatedAtDesc(
//                PageRequest.of(postPagingRequest.getPage(), postPagingRequest.getCount()));
//
//        List<PostPreviewResponse> postPreviewResponses = new ArrayList<>();
//
//        postPageList.stream().forEach(post -> {
//            List<PostHashtag> postHashtag = postHashtagRepository.findByPostId(post.getId());
//
//            List<String> hashtags = new ArrayList<>();
//            for (PostHashtag hashtag : postHashtag) {
//                hashtags.add(hashtag.getHashtag().getHashtagName());
//            }
//
//            List<Image> images = imageRepository.findByPostId(post.getId());
//            // images.add(Image.builder().imagePath(post.getThumbnail()).build());
//
//            postPreviewResponses.add(PostPreviewResponse.of(post, getPostImages(images), hashtags));
//        });
//
//        return PostPagingResponse.builder()
//                .page(postPagingRequest.getPage())
//                .postPreviewResponses(postPreviewResponses)
//                .isEnd(postPageList.isLast())
//                .build();
//    }
//
//    @Transactional
//    @Override
//    public String modifyPost(String uuid, PostRequest postRequest) throws IOException {
//        Post post = postRepository.findPostAndPostHashtagsById(postRequest.getId())
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//
//        post.setTitle(postRequest.getTitle());
//        post.setContent(postRequest.getContent());
//        post.setPlaceName(postRequest.getPlaceName());
////        post.setLatitude(postRequest.getLatitude());
////        post.setLongitude(postRequest.getLongitude());
//
//        postRepository.save(post);
//
//        // 기존 이미지 삭제 후 새로운 이미지 저장
//        List<Image> images = imageRepository.findByPostId(postRequest.getId());
//
//        for (String postImageUrl : getPostImages(images)) {
//            s3Uploader.deleteImage(postImageUrl);
//        }
//        imageRepository.deleteByPostId(post.getId());
//
//        if (postRequest.getImageFiles() != null) {
//            List<Image> imagesNew = new ArrayList<>();
//            for (MultipartFile imageFile : postRequest.getImageFiles()) {
//                String imageUrl = s3Uploader.uploadImage("post", post.getUser().getNickname(), post.getId(), imageFile);
//                imagesNew.add(Image.builder()
//                        .post(post)
//                        .imagePath(imageUrl)
//                        .build());
//            }
//
//            imageRepository.saveAll(imagesNew);
//        }
//
//        // 게시글에 등록된 해시태그 삭제. 이 때, 해시태그 테이블의 원본 해시태그 정보는 삭제 안 함.
//        postHashtagRepository.deleteByPostId(post.getId());
//
//        // 게시글에 새로운 해시태그 등록.
//        List<String> hashtags = postRequest.getHashtags();
//        if (hashtags != null) {
//            Map<String, Hashtag> existingHashtags = hashTagRepository.findAll()
//                    .stream()
//                    .collect(Collectors.toMap(Hashtag::getHashtagName, Function.identity()));
//
//            HashSet<PostHashtag> postHashtags = new HashSet<>();
//            for (String hashtagNew : hashtags) {
//                Hashtag hashtag = existingHashtags.computeIfAbsent(hashtagNew, name -> {
//                    Hashtag newHashtag = new Hashtag();
//                    newHashtag.setHashtagName(name);
//                    return hashTagRepository.save(newHashtag);
//                });
//
//                PostHashtag postHashtag = new PostHashtag();
//                postHashtag.setPost(post);
//                postHashtag.setHashtag(hashtag);
//                postHashtags.add(postHashtag);
//            }
//
//            postHashtagRepository.saveAll(postHashtags);
//        }
//
//        return "게시글 수정 완료";
//    }
//
//    @Transactional
//    @Override
//    public String deletePost(String uuid, Long postId) {
//        Post post = postRepository.findById(postId).get();
//
//        imageRepository.deleteAllByPost(post);
//        deleteHashtag(post);
//        postHashtagRepository.deleteByPost(post);
//        postRepository.deleteById(postId);
//        return "게시글 삭제 완료";
//    }
//
//    @Override
//    public PostPagingResponse searchPosts(String uuid, PostSearchPagingRequest postSearchPagingRequest)
//            throws Exception {
//
//        Pageable pageable = PageRequest.of(postSearchPagingRequest.getPage(), postSearchPagingRequest.getCount());
//        List<Post> postPageList = postRepository.findByContentContaining(postSearchPagingRequest.getSearch(), pageable);
//
//        List<PostPreviewResponse> postPreviewResponses = new ArrayList<>();
//        postPageList.stream().forEach(post -> {
//            List<Image> images = imageRepository.findByPostId(post.getId());
//            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(post.getId());
//            List<String> hashtags = new ArrayList<>();
//            for (PostHashtag postHashtag : postHashtags) {
//                hashtags.add(postHashtag.getHashtag().getHashtagName());
//            }
//            postPreviewResponses.add(PostPreviewResponse.of(post, getPostImages(images), hashtags));
//        });
//
//        return PostPagingResponse.builder()
//                .page(postSearchPagingRequest.getPage())
//                .postPreviewResponses(postPreviewResponses)
//                .isEnd(postPageList.isEmpty())
//                .build();
//    }
//
//}
