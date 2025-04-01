package me.deshark.lms.infrastructure.external.google;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.service.catalog.BookMetadataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

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

    @Value("${proxy.host:}")
    private String proxyHost;

    @Value("${proxy.port:0}")
    private int proxyPort;

    @PostConstruct
    public void init() {
        if (!proxyHost.isEmpty() && proxyPort > 0) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            requestFactory.setProxy(proxy);
            restTemplate.setRequestFactory(requestFactory);
        }
    }

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

        // 处理作者信息，取第一个作者
        // TODO 后续要处理多个作者的信息
        String author = volumeInfo.path("authors").isArray() && !volumeInfo.path("authors").isEmpty()
                ? volumeInfo.path("authors").get(0).asText("Unknown Author")
                : "Unknown Author";

        // 处理出版日期，如果格式不完整则设为null
        String publishedDateStr = volumeInfo.path("publishedDate").asText(null);
        short year = 0;
        if (publishedDateStr != null && publishedDateStr.length() >= 4) {
            try {
                // 只取年份部分
                year = (short) Integer.parseInt(publishedDateStr.substring(0, 4));
            } catch (NumberFormatException e) {
                // 如果解析失败则保持null
            }
        }

        return BookMetadata.builder()
                .isbn(isbn)
                .title(volumeInfo.path("title").asText("Unknown Title"))
                .author(author)
                .publisher(volumeInfo.path("publisher").asText("Unknown Publisher"))
                .publishedYear(year)
                .build();
    }
}
