package br.com.luan.diariorn.reporter.service;

import br.com.luan.diariorn.reporter.client.WordpressClient;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressPostResponse;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressUserResponse;
import br.com.luan.diariorn.reporter.exception.InvalidPeriodException;
import br.com.luan.diariorn.reporter.exception.JournalistNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RerporterServiceTest {

    @Mock
    private WordpressClient wordpressClient;

    @InjectMocks
    private ReporterService reporterService;

    @Test
    void shouldGetPostByJournalist() {
        String username = "John";

        OffsetDateTime begin =
                OffsetDateTime.parse(
                        "2026-08-24T08:00:00-03:00");

        OffsetDateTime end =
                OffsetDateTime.parse(
                        "2026-08-24T14:00:00-03:00"
                );

        WordpressUserResponse journalist =
                new WordpressUserResponse(
                        10L,
                        "John Smith",
                        "John-Smith"
                );

        WordpressPostResponse post =
                new WordpressPostResponse(
                        100L,
                        LocalDateTime.parse(
                                "2026-08-24T10:30:00"
                        ),
                        "test-article",
                        "https://example.com/test-article"
                );
        when(wordpressClient.getUsers(username))
                .thenReturn(
                        new WordpressUserResponse[]{
                                journalist
                        }
                );
        when(
                wordpressClient.getPosts(
                        10L,
                        begin,
                        end
                )
        ).thenReturn(
                new WordpressPostResponse[]{
                        post
                }
        );

        WordpressPostResponse[] result =
                reporterService.getPostsByJournalist(
                        username,
                        begin,
                        end
                );

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(100L, result[0].id());
        assertEquals(
                "https://example.com/test-article",
                result[0].link()
        );

        verify(wordpressClient)
                .getUsers(username);

        verify(wordpressClient)
                .getPosts(
                        10L,
                        begin,
                        end
                );

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(100L, result[0].id());
        assertEquals(
                "https://example.com/test-article",
                result[0].link()
        );

        verify(wordpressClient)
                .getUsers(username);

        verify(wordpressClient)
                .getPosts(
                        10L,
                        begin,
                        end
                );
    }

    @Test
    void shouldThrowExceptionWhenJournalistDoesNotExist() {
        String username = "unknown";

        OffsetDateTime begin =
                OffsetDateTime.parse(
                        "2026-08-24T08:00:00-03:00"
                );

        OffsetDateTime end =
                OffsetDateTime.parse(
                        "2026-08-24T14:00:00-03:00"
                );

        when(wordpressClient.getUsers(username))
                .thenReturn(
                        new WordpressUserResponse[0]
                );

        JournalistNotFoundException exception =
                assertThrows(
                        JournalistNotFoundException.class,
                        () -> reporterService.getPostsByJournalist(
                                username,
                                begin,
                                end
                        )
                );

        assertEquals(
                "Journalist not found: unknown",
                exception.getMessage()
        );

        verify(wordpressClient)
                .getUsers(username);

        verifyNoMoreInteractions(wordpressClient);
    }

    @Test
    void shouldThrowExceptionWhenPeriodIsInvalid() {
        String username = "john";

        OffsetDateTime begin =
                OffsetDateTime.parse(
                        "2026-08-24T14:00:00-03:00"
                );

        OffsetDateTime end =
                OffsetDateTime.parse(
                        "2026-08-24T08:00:00-03:00"
                );

        InvalidPeriodException exception =
                assertThrows(
                        InvalidPeriodException.class,
                        () -> reporterService.getPostsByJournalist(
                                username,
                                begin,
                                end
                        )
                );

        assertEquals(
                "Begin date/time must be before end date/time",
                exception.getMessage()
        );

        verifyNoInteractions(wordpressClient);
    }


}
