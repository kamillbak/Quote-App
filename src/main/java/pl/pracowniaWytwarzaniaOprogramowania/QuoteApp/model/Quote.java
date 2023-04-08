package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class Quote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String author;
    private String quote;
    private int posted;

    public Quote(int id, String author, String quote, int posted) {
        this.id = id;
        this.author = author;
        this.quote = quote;
        this.posted = posted;
    }

    public Quote(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getPosted() {
        return posted;
    }

    public void setPosted(int posted) {
        this.posted = posted;
    }
}





