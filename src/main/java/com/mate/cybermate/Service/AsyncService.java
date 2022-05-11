package com.mate.cybermate.Service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    //비동기로 동작하는 메소드
    @Async
    public void onAsync(int i) {
        logger.info("onAsync i=" + i);
    }
}
