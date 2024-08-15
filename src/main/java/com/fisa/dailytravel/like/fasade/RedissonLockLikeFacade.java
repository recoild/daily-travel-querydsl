package com.fisa.dailytravel.like.fasade;

import com.fisa.dailytravel.like.service.LikeService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonLockLikeFacade {

    private final RedissonClient redissonClient;
    private final LikeService likeService;

    public void likeToggle(Long postId, String uuid) {
        RLock lock = redissonClient.getLock("postLikeLock:" + postId);
        try {
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("lock 획득 실패");
                return;
            }
            likeService.likeToggle(postId, uuid);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public boolean serviceToggle(Long postId, String uuid) {
        RLock lock = redissonClient.getLock("postLikeLock:" + postId);
        boolean isLocked = false;
        try {
            while (!isLocked) {
                // 최대 15초 동안 락을 획득하려 시도, 1초 동안 기다림
                isLocked = lock.tryLock(15, 1, TimeUnit.SECONDS);
                if (!isLocked) {
                    log.info("락 획득 실패, 재시도 중...");
                }
            }

            // 락을 획득한 후, 좋아요 토글 작업 수행
            return likeService.likeToggle(postId, uuid);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock(); // 락 해제
            }
        }
    }
}
