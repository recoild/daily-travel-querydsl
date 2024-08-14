package com.fisa.dailytravel.global.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 이미지 S3에 업로드 후 이미지 URL 반환
    public String uploadImage(String type, String nickname, Long postId, MultipartFile file) throws IOException {
        String s3FileName = nickname + "-postId" + postId + "-" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        if (type.equals("post")) {
            amazonS3.putObject(bucket + "/post", s3FileName, file.getInputStream(), metadata);
        } else if (type.equals("user")) {
            amazonS3.putObject(bucket + "/user", s3FileName, file.getInputStream(), metadata);
        }


        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    // 이미지 삭제
    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
