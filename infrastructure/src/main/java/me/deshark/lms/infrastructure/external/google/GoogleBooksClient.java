package me.deshark.lms.infrastructure.external.google;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.service.catalog.BookMetadataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author DE_SHARK
 */
@Component
@RequiredArgsConstructor
public class GoogleBooksClient implements BookMetadataProvider {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s";
    private final RestTemplate restTemplate;
    @Value("${google.books.api.key}")
    private String apiKey;

    @Override
    public BookMetadata fetch(Isbn isbn) {
        URI uri = buildRequestUri(isbn.toString());
        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);
        return parseResponse(isbn, response);
    }

    private URI buildRequestUri(String isbn) {
        return UriComponentsBuilder.fromUriString("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", "isbn:{isbn}")
                .queryParam("key", apiKey)
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(isbn)
                .toUri();
    }

    private BookMetadata parseResponse(Isbn isbn, JsonNode root) {
        JsonNode item = root.path("items").get(0);
        JsonNode volumeInfo = item.path("volumeInfo");

        return BookMetadata.builder()
                .isbn(isbn)
                .title(volumeInfo.path("title").asText("Unknown Title"))
                .build();
    }
}