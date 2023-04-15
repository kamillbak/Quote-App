package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.security.JWTFilter;

import java.util.Collections;

@SpringBootApplication
public class QuoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteAppApplication.class, args);
	}

	// below definition of filtering requests - JWT
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter( new JWTFilter());
		filterRegistrationBean.setUrlPatterns(Collections.singleton("/quote/*"));
		return filterRegistrationBean;
	}

}
