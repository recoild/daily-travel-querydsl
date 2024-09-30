//package com.fisa.dailytravel.post.fasade;
//
//import com.fisa.dailytravel.post.dto.PostRequest;
//import com.fisa.dailytravel.post.service.PostServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Component
//public class RedissonLockPostFacade {
//    private final RedissonClient redissonClient;
//    private final PostServiceImpl postService;
//
//    public String savePostUseRedisson(String uuid, PostRequest postRequest) throws IOException {
//        String lockName = "savePost:lock";
//        RLock lock = redissonClient.getLock(lockName);
//
//        try {
//            if (!lock.tryLock(15, 1, TimeUnit.SECONDS))
//                return lockName + " Fail";
//            else
//                return postService.savePost(uuid, postRequest);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            if (lock != null && lock.isLocked())
//                lock.unlock();
//        }
//
//        return lockName;
//    }
//}
