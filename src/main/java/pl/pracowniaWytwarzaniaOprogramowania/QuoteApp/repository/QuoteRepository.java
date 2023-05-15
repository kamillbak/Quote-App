package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;

import java.util.List;

@Repository
public class QuoteRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Quote> getAll() {
//        return jdbcTemplate1.query("SELECT id, author, quote, posted FROM Quote",
//            BeanPropertyRowMapper.newInstance(Quote.class));

        String sql = "SELECT id, author, quote, posted FROM Quote";

        List<Quote> quotes = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Quote.class));
        if(quotes.size() >0 ) {
            return quotes;
        }
        else {
            return null;
        }
    }

    public int addQuote(Quote quote) {
        int rowsAffected = jdbcTemplate.update("INSERT INTO Quote (author, quote, posted) VALUES (?, ?, ?);",quote.getAuthor(), quote.getQuote(), quote.getPosted());
        return rowsAffected;
    }

    public Quote getRandomQuote() {
        return jdbcTemplate.queryForObject("SELECT * FROM Quote ORDER BY RAND() LIMIT 1", BeanPropertyRowMapper.newInstance(Quote.class));
    }

    public List<Quote> getAllPostedByID(int id) {
//        return jdbcTemplate.query("SELECT * FROM Quote where posted=?",BeanPropertyRowMapper.newInstance(Quote.class),id);

        String sql = "SELECT * FROM Quote where posted='" + String.valueOf(id) + "'";

        List<Quote> quotes = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Quote.class));
        if(quotes.size() > 0) {
            return quotes;
        }
        else {
            return null;
        }
    }

    public Quote getQuoteById( int id) {
       // return jdbcTemplate.queryForObject("SELECT * FROM Quote WHERE id=?", BeanPropertyRowMapper.newInstance(Quote.class), id);

        String sql = "SELECT * FROM Quote WHERE id='" +  String.valueOf(id) + "'";

        List<Quote> quotes = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Quote.class));

        if(quotes.size() == 1 ) {
            return quotes.get(0);
        }
        else {
            return null;
        }
    }

    public int updateQuote( Quote quote) {
        int rowsAffected = jdbcTemplate.update("UPDATE Quote SET author=?, quote=?, posted=? WHERE id=? ",
                quote.getAuthor(), quote.getQuote(), quote.getPosted(), quote.getId());
        return rowsAffected;
    }

    public int deleteQuote(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM Quote WHERE id=?", id);
        return rowsAffected;
    }







}


