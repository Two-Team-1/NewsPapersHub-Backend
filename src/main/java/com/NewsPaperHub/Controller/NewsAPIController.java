package com.NewsPaperHub.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NewsPaperHub.DTO.ArticleDTO;
import com.NewsPaperHub.Service.NewsAPIService;

@RestController
@RequestMapping("/api/news")
public class NewsAPIController {

    private final NewsAPIService newsAPIService;

    @Autowired
    public NewsAPIController(NewsAPIService newsAPIService) {
        this.newsAPIService = newsAPIService;
    }

    // Endpoint to search news by keyword and date range
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> getNewsByKeyword(
            @RequestParam String keyword, 
            @RequestParam String fromDate, 
            @RequestParam String toDate) {

        List<ArticleDTO> articles = newsAPIService.fetchArticlesByKeyword(keyword, fromDate, toDate);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = Arrays.asList("business", "entertainment", "general", "health", "science", "sports", "technology");
        return ResponseEntity.ok(categories);
    }
    
    
    // Endpoint to fetch news by category
    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ArticleDTO>> getNewsByCategory(@PathVariable String category) {
        // Valid categories: business, entertainment, general, health, science, sports, technology
        List<ArticleDTO> articles = newsAPIService.fetchArticlesByCategory(category);
        return ResponseEntity.ok(articles);
    }
}
