package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.post.dto.*;
import com.fisa.dailytravel.post.models.*;
import com.fisa.dailytravel.post.repository.*;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final S3Uploader s3Uploader;
    private final PostDocRepository postDocRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


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

        savePostImages(post, imageFiles); // post에 이미지 저장 -> 해당 post에 맞는 이미지 저장

        List<String> hashtags = postRequest.getHashtags();

        saveHashtag(post, hashtags); // post에 해시태그 저장 -> 해당 post에 맞는 해시태그 저장

        return "게시글 저장 완료";
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(String uuid, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        List<Image> images = imageRepository.findByPostId(postId);

        List<String> imageFiles = new ArrayList<>();
        List<String> hashtags = new ArrayList<>();
        List<CommentResponse> comments = new ArrayList<>();
        String authorProfileImagePath = ""; // 작성자 프로필 이미지 경로

        if (post.isPresent()) {
            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
            for (PostHashtag postHashtag : postHashtags) {
                hashtags.add(postHashtag.getHashtag().getHashtagName()); // post에 저장된 해시태그 가져오기
            }
            authorProfileImagePath = post.get().getUser().getProfileImagePath(); // 작성자 프로필 이미지 경로 가져오기

            List<Comment> commentList = new ArrayList<>();

            for (Comment comment : post.get().getComments()) {
                commentList.add(comment); // post에 저장된 댓글 가져오기
            }

            for (Comment comment : commentList) {
                comments.add(CommentResponse.of(comment)); // 댓글 정보 가져오기
            }
        }

        getPostImages(images); // post에 저장된 이미지 가져오기

        imageFiles = getPostImages(images); // post에 저장된 이미지를 가져와서 imageFiles에 저장

        return PostResponse.of(post.get(), imageFiles, hashtags, authorProfileImagePath, comments); // post, imageFiles, hashtags, authorProfileImagePath, comments 반환
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
            imageFiles.add(image.getImagePath()); // post에 저장된 이미지 가져오기
        }

        return imageFiles; // post에 저장된 이미지 반환
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
            images.add(Image.builder().imagePath(post.getThumbnail()).build());

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

    @Override
    public PostPagingResponse searchPosts(String uuid, PostSearchPagingRequest postSearchPagingRequest) throws Exception {

        Pageable pageable = PageRequest.of(postSearchPagingRequest.getPage(), postSearchPagingRequest.getCount());
        List<Post> postPageList = postRepository.findByContentContaining(postSearchPagingRequest.getSearch(), pageable);

        List<PostPreviewResponse> postPreviewResponses = new ArrayList<>();
        postPageList.stream().forEach(post -> {
            List<Image> images = imageRepository.findByPostId(post.getId());
            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(post.getId());
            List<String> hashtags = new ArrayList<>();
            for (PostHashtag postHashtag : postHashtags) {
                hashtags.add(postHashtag.getHashtag().getHashtagName());
            }
            postPreviewResponses.add(PostPreviewResponse.of(post, getPostImages(images), hashtags));
        });

        return PostPagingResponse.builder()
                .page(postSearchPagingRequest.getPage())
                .postPreviewResponses(postPreviewResponses)
                .isEnd(postPageList.isEmpty())
                .build();
    }

    @Override
    public PostPagingResponse searchPostsWithES(String uuid, PostSearchPagingRequest search) throws Exception {
        Pageable pageable = PageRequest.of(search.getPage(), search.getCount());

        //공백 문자열 "win move" 를 검색 시 spring data는 *win move* 로 검색하여 오류 발생.
        //그러므로, *win* *move* 로 검색하도록 수정
        //postDocRepository.findByPostContentContaining(search.getSearch(), pageable);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("post_content", search.getSearch()))
                .withPageable(pageable)
                .build();

        SearchHits<PostDoc> searchHits = elasticsearchRestTemplate.search(searchQuery, PostDoc.class);

        List<PostDoc> postDocs = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        List<PostPreviewResponse> postPreviewResponses = new ArrayList<>();
        postDocs.forEach(postDoc -> {
            Post post = postRepository.findById(postDoc.getId()).get();
            List<Image> images = imageRepository.findByPostId(post.getId());
            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(post.getId());
            List<String> hashtags = new ArrayList<>();
            for (PostHashtag postHashtag : postHashtags) {
                hashtags.add(postHashtag.getHashtag().getHashtagName());
            }
            postPreviewResponses.add(PostPreviewResponse.of(post, getPostImages(images), hashtags));
        });

        return PostPagingResponse.builder()
                .page(search.getPage())
                .postPreviewResponses(postPreviewResponses)
                .isEnd(postDocs.isEmpty())
                .build();
    }
}
