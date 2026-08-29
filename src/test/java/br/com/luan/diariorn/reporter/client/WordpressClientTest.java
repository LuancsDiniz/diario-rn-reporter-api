package br.com.luan.diariorn.reporter.client;

import br.com.luan.diariorn.reporter.dto.wordpress.WordpressPostResponse;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class WordpressClientTest {

    private WordpressClient wordpressClient;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://example.com/wp-json/wp/v2");

        mockServer = MockRestServiceServer
                .bindTo(builder)
                .build();

        RestClient restClient = builder.build();

        wordpressClient = new WordpressClient(restClient);
    }

    @Test
    void shouldGetUserByUsername() {
        String json = """
                [
                  {
                    "id": 10,
                    "name": "John Smith",
                    "slug": "john-smith"
                  }
                ]
                """;

        mockServer
                .expect(requestTo(
                        "https://example.com/wp-json/wp/v2/users?search=john"
                ))
                .andRespond(
                        withSuccess(json, MediaType.APPLICATION_JSON)
                );

        WordpressUserResponse[] result =
                wordpressClient.getUsers("john");

        assertNotNull(result);
        assertEquals(1, result.length);

        assertEquals(10L, result[0].id());
        assertEquals("John Smith", result[0].name());
        assertEquals("john-smith", result[0].slug());

        mockServer.verify();
    }

    @Test
    void shouldReturnEmptyArrayWhenUserDoesNotExist() {
        String json = "[]";

        mockServer
                .expect(requestTo(
                        "https://example.com/wp-json/wp/v2/users?search=unknown"
                ))
                .andRespond(
                        withSuccess(json, MediaType.APPLICATION_JSON)
                );

        WordpressUserResponse[] result =
                wordpressClient.getUsers("unknown");

        assertNotNull(result);
        assertEquals(0, result.length);

        mockServer.verify();
    }

    @Test
    void shouldGetPostsByAuthorAndPeriod() {
        Long authorId = 10L;

        OffsetDateTime begin =
                OffsetDateTime.parse("2026-08-24T08:00:00-03:00");

        OffsetDateTime end =
                OffsetDateTime.parse("2026-08-24T14:00:00-03:00");

        String json = """
                [
                  {
                    "id": 100,
                    "date": "2026-08-24T10:30:00",
                    "slug": "test-article",
                    "link": "https://example.com/test-article"
                  }
                ]
                """;

        mockServer
                .expect(requestTo(
                        "https://example.com/wp-json/wp/v2/posts" +
                                "?author=10" +
                                "&after=2026-08-24T11:00:00Z" +
                                "&before=2026-08-24T17:00:00Z"
                ))
                .andRespond(
                        withSuccess(json, MediaType.APPLICATION_JSON)
                );

        WordpressPostResponse[] result = wordpressClient.getPosts(
                authorId,
                begin,
                end
        );

        assertNotNull(result);
        assertEquals(1, result.length);

        assertEquals(100L, result[0].id());
        assertEquals("test-article", result[0].slug());
        assertEquals(
                "https://example.com/test-article",
                result[0].link()
        );

        mockServer.verify();
    }
}
