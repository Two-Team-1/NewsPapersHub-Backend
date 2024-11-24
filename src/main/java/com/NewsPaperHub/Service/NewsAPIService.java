package com.NewsPaperHub.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.NewsPaperHub.DTO.ArticleDTO;
import com.NewsPaperHub.DTO.NewsAPIResponse;

@Service
public class NewsAPIService {
    
    @Value("${newsapi.base-url}")
    private String baseUrl;

    @Value("${newsapi.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch Articles by Keyword and Date
    public List<ArticleDTO> fetchArticlesByKeyword(String keyword, String fromDate, String toDate) {
        String url = baseUrl + "/everything?q=" + keyword +
                     "&from=" + fromDate +
                     "&to=" + toDate +
                     "&apiKey=" + apiKey;

        ResponseEntity<NewsAPIResponse> response = restTemplate.getForEntity(url, NewsAPIResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getArticles();
        } else {
            throw new RuntimeException("Failed to fetch articles by keyword: " + response.getStatusCode());
        }
    }

    // Fetch Articles by Category
    public List<ArticleDTO> fetchArticlesByCategory(String category) {
        String url = baseUrl + "/top-headlines?category=" + category + "&apiKey=" + apiKey;

        ResponseEntity<NewsAPIResponse> response = restTemplate.getForEntity(url, NewsAPIResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getArticles();
        } else {
            throw new RuntimeException("Failed to fetch articles by category: " + response.getStatusCode());
        }
    }
}
