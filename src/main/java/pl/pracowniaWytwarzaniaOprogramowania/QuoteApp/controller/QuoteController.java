package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.QuoteRepository;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    QuoteRepository  quoteRepository;

    @GetMapping("/getAll")
    public List<Quote> getAllQuotes() {
        return  quoteRepository.getAll();
    }

    @PostMapping("/post")
    public void postQuote(@RequestBody Quote quote) {
        quoteRepository.addQuote(quote);
    }

    @GetMapping("/random")
    public Quote getQuoteInPDF() {
        Quote quote = quoteRepository.getRandomQuote();

        return quote;
        // create PDF here
        // return PDF here
    }

    @GetMapping("/getAll/{id}")
    public List<Quote> getAllQuotePostedById(@PathVariable("id") int id) {
        return quoteRepository.getAllPostedByID(id);
    }

    @PatchMapping("/update/{id}")
    public int updateQuote(@PathVariable("id") int id, @RequestBody Quote quoteUpdate) {
        Quote quoteFromDb = quoteRepository.getQuoteById(id);
        if (quoteFromDb == null) {
            return -1;
        }
        else {
            if(quoteUpdate.getQuote() != null) quoteFromDb.setQuote(quoteUpdate.getQuote());
            if(quoteUpdate.getAuthor() != null) quoteFromDb.setAuthor(quoteUpdate.getAuthor());
            quoteRepository.updateQuote(quoteFromDb);
            return 1;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuote(@PathVariable("id") int id) {
        quoteRepository.deleteQuote(id);
    }
}
