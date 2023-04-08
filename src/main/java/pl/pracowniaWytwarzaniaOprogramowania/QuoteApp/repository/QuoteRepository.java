package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;

import java.util.List;

@Repository
public class QuoteRepository {
    @Autowired
    JdbcTemplate jdbcTemplate1;

    public List<Quote> getAll() {
        return jdbcTemplate1.query("SELECT id, author, quote, posted FROM Quote",
            BeanPropertyRowMapper.newInstance(Quote.class));
    }

    public void addQuote(Quote quote) {
        jdbcTemplate1.update("INSERT INTO Quote (author, quote, posted) VALUES (?, ?, ?);",quote.getAuthor(), quote.getQuote(), quote.getPosted());
    }

    public Quote getRandomQuote() {
        return jdbcTemplate1.queryForObject("SELECT * FROM Quote ORDER BY RAND() LIMIT 1", BeanPropertyRowMapper.newInstance(Quote.class));
    }

    public List<Quote> getAllPostedByID(int id) {
        return jdbcTemplate1.query("SELECT * FROM Quote where posted=?",BeanPropertyRowMapper.newInstance(Quote.class),id);
    }

    public Quote getQuoteById( int id) {
        return jdbcTemplate1.queryForObject("SELECT * FROM Quote WHERE id=?", BeanPropertyRowMapper.newInstance(Quote.class), id);
    }

    public int updateQuote( Quote quote) {
        return jdbcTemplate1.update("UPDATE Quote SET author=?, quote=?, posted=? WHERE id=? ",
                quote.getAuthor(), quote.getQuote(), quote.getPosted(), quote.getId());
    }

    public void deleteQuote(int id) {
        jdbcTemplate1.update("DELETE FROM Quote WHERE id=?", id);
    }







}


