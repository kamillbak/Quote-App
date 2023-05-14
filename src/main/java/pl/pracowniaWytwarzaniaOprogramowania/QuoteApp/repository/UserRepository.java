package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void registerUser(User user){
        jdbcTemplate.update("INSERT INTO Users(username, password) VALUES(?,?)", user.getUsername(), user.getPassword());
    }

    public User getUserByUsername(String userName) {
//        return jdbcTemplate.queryForObject("SELECT * FROM Users WHERE username=?",BeanPropertyRowMapper.newInstance(User.class), userName);

        String sql = "SELECT * FROM Users WHERE username='" + userName + "'";

        List<User> users = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        if(users.size() == 1) {
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public Integer checIfUsernameExist(String userName) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Users WHERE username=?",Integer.class, userName);
    }





}
