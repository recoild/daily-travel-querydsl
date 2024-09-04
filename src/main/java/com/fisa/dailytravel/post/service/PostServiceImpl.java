package com.fisa.dailytravel.post.service;

import com.fisa.dailytravel.comment.dto.CommentPageRequest;
import com.fisa.dailytravel.comment.dto.CommentResponse;
import com.fisa.dailytravel.comment.mapper.CommentMapper;
import com.fisa.dailytravel.comment.models.Comment;
import com.fisa.dailytravel.comment.repository.CommentRepository;
import com.fisa.dailytravel.comment.service.CommentService;
import com.fisa.dailytravel.comment.service.CommentServiceImpl;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
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

        savePostImages(post, imageFiles);

        List<String> hashtags = postRequest.getHashtags();

        saveHashtag(post, hashtags);

        return "게시글 저장 완료";
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(String uuid, Long postId, CommentPageRequest commentPageRequest) {
        Optional<Post> post = postRepository.findById(postId);
        List<Image> images = imageRepository.findByPostId(postId);

        List<String> imageFiles = new ArrayList<>();
        List<String> hashtags = new ArrayList<>();
        List<CommentResponse> comments = new ArrayList<>();
        String authorProfileImagePath = "";

        if (post.isPresent()) {
            List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
            for (PostHashtag postHashtag : postHashtags) {
                hashtags.add(postHashtag.getHashtag().getHashtagName());
            }
            authorProfileImagePath = post.get().getUser().getProfileImagePath();

            List<Comment> commentList = new ArrayList<>();

//            for (Comment comment : post.get().getComments()) {
//                commentList.add(comment);
//            }
//
//            for (Comment comment : commentList) {
//                comments.add(CommentResponse.of(comment));
//            }

//            comments = post.get().getComments().stream()
//                    .map(commentMapper::commentToCommentResponse)  // 매퍼를 사용하여 변환
//                    .collect(Collectors.toList());

//            comments = commentService.getComments(postId, commentPageRequest);

            // 댓글 가져오기 (페이지네이션 적용)
            comments = commentService.getComments(postId, commentPageRequest);
        }

        getPostImages(images);

        imageFiles = getPostImages(images);

        return PostResponse.of(post.get(), imageFiles, hashtags, authorProfileImagePath, comments);
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
