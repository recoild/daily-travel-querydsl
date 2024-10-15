package com.fisa.dailytravel.global.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.fisa.dailytravel.global.dto.ByteResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 이미지 S3에 업로드 후 이미지 URL 반환
    public String uploadImage(String directoryName, MultipartFile file) throws IOException {
        String s3FileName = directoryName + "/" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3FileName, file.getInputStream(), metadata);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    //byte 배열을 받는 함수
    public String uploadImage(String directoryName, byte[] fileData, String fileName, String contentType) throws IOException {
        // S3 파일 이름 생성
        String s3FileName = directoryName + "/" + fileName;

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(fileData.length);

        // byte[] 데이터를 S3에 업로드
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucket,
                s3FileName,
                new ByteArrayInputStream(fileData),
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);

        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    //이미지 다운로드
    public ByteResource downloadImage(String s3url) throws IOException {
        URL url = new URL(s3url);
        String key = url.getPath();

        String fileName = key.substring(key.lastIndexOf(bucket) + bucket.length() + 1);

        S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, fileName));

        S3ObjectInputStream objectInputStream = o.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        String contentType = o.getObjectMetadata().getContentType();
        return new ByteResource(bytes, contentType);
    }

    // 이미지 삭제
    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
