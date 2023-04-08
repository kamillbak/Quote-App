package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void registerUser(User user){
        jdbcTemplate.update("INSERT INTO Users(username, password) VALUES(?,?)", user.getUsername(), user.getPassword());
    }

    public User getUserByUsername(String userName) {
        return jdbcTemplate.queryForObject("SELECT * FROM Users WHERE username=?",BeanPropertyRowMapper.newInstance(User.class), userName);
    }

    public Integer checIfUsernameExist(String userName) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Users WHERE username=?",Integer.class, userName);
    }





}
