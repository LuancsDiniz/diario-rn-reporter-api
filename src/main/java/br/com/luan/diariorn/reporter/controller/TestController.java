package br.com.luan.diariorn.reporter.controller;

import br.com.luan.diariorn.reporter.client.WordpressClient;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressPostResponse;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final WordpressClient wordpressClient;

    @GetMapping("wordpress/user")
    public WordpressUserResponse[] getUsers(
            @RequestParam String username
    ) {
    return wordpressClient.getUsers(username);
    }

    @GetMapping("wordpress/posts")
    public WordpressPostResponse[] getPosts(
            @RequestParam Long author,
            @RequestParam OffsetDateTime after,
            @RequestParam OffsetDateTime before)
     {
        return wordpressClient.getPosts(author, after, before);
    }


}
