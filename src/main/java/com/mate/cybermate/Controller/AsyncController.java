package com.mate.cybermate.Controller;

import com.mate.cybermate.Service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public String goAsync(){
        for(int i=0;i<1000;i++) {
            asyncService.onAsync(i);
        }

        return "async";

    }

}
