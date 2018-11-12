package words;

import words.Word;

public class Noun extends Word {

	private String article;

	//add options for plural nouns?

	public Noun(String article, String word, String definition) {
		super(word, definition);
		this.article = article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getArticle() {
		return article;
	}
}
