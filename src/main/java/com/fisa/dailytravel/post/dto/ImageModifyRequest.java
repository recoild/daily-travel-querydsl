package com.fisa.dailytravel.post.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ImageModifyRequest {
    private List<Integer> deletedImagesNo; //삭제할 이미지 번호
    private List<MultipartFile> imageFile; //append 방식으로 추가
}
