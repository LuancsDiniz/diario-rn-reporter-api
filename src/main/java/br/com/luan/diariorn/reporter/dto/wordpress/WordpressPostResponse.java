package br.com.luan.diariorn.reporter.dto.wordpress;

import java.time.LocalDateTime;

public record WordpressPostResponse(
        Long id,
        LocalDateTime date,
        String slug,
        String link
) {
}
