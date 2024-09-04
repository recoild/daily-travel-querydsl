package com.fisa.dailytravel.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageRequest {
    private int page = 0; // 기본 페이지 값 : 0
    private int count = 20; // 기본 페이지 당 댓글 수 : 20

    public void setPage(int page) { // page 값이 0보다 작을 경우 0으로 설정
        this.page = Math.max(0, page);
        log.info("setPage called with value: " + this.page);
    }

    public void setCount(int count) { // count 값이 1보다 작을 경우 1로 설정
        this.count = Math.max(1, count);
        log.info("setCount called with value: " + this.count);
    }
}
