package com.fisa.dailytravel.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
//@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageRequest {
    private int page = 0;
    private int count = 20;

    public void setPage(int page) {
        this.page = Math.max(0, page);
        log.info("setPage called with value: " + this.page);
    }

    public void setCount(int count) {
        this.count = Math.max(1, count);
        log.info("setCount called with value: " + this.count);
    }
}
