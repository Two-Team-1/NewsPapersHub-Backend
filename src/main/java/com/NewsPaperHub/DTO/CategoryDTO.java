package com.NewsPaperHub.DTO;

import java.util.List;

public class CategoryDTO {
	  private String category;
	    private List<ArticleDTO> articles;
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public List<ArticleDTO> getArticles() {
			return articles;
		}
		public void setArticles(List<ArticleDTO> articles) {
			this.articles = articles;
		}

}
