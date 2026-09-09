package br.com.luan.diariorn.reporter.service;

import br.com.luan.diariorn.reporter.client.WordpressClient;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressPostResponse;
import br.com.luan.diariorn.reporter.dto.wordpress.WordpressUserResponse;
import br.com.luan.diariorn.reporter.exception.InvalidPeriodException;
import br.com.luan.diariorn.reporter.exception.JournalistNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ReporterService {

    private final WordpressClient wordpressClient;

    public WordpressPostResponse[] getPostsByJournalist(
            String username,
            OffsetDateTime begin,
            OffsetDateTime end
    ) {
        validatePeriod(begin, end);

        WordpressUserResponse journalist =
                getJournalist(username);

        return wordpressClient.getPosts(
                journalist.id(),
                begin,
                end
        );
    }

    private WordpressUserResponse getJournalist(
            String username
    ) {
        WordpressUserResponse[] users =
                wordpressClient.getUsers(username);
        if (users == null || users.length == 0) {
            throw new JournalistNotFoundException(
                    "Journalist not found: " + username
            );
        }
        return users[0];
    }

    private void validatePeriod(
            OffsetDateTime begin,
            OffsetDateTime end
    ) {
       if (!begin.isBefore(end)) {
           throw new InvalidPeriodException(
                   "Begin date/time must be before end date/time"
           );
       }
    }

}