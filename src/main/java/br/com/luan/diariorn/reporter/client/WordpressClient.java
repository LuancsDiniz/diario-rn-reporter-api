package br.com.luan.diariorn.reporter.client;

import br.com.luan.diariorn.reporter.dto.wordpress.WordpressPostResponse;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class WordpressClient {

    private final RestClient restClient;

    public WordpressUserResponse[] getUsers(String username) {
        return restClient
                .get().uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParam("search", username)
                        .build())
                .retrieve()
                .body(WordpressUserResponse[].class);

    }

    public WordpressPostResponse[] getPosts(
            Long autorId,
            OffsetDateTime begin,
            OffsetDateTime end
    ) {

        String after = begin.toInstant().toString();
        String before = end.toInstant().toString();

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts")
                        .queryParam("author", autorId)
                        .queryParam("after", after)
                        .queryParam("before", before)
                        .build())
                .retrieve()
                .body(WordpressPostResponse[].class);
    }

}
