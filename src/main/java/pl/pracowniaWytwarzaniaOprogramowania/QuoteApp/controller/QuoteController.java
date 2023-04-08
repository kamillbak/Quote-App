package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.QuoteRepository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.services.PdfGeneratorService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    PdfGeneratorService pdfGeneratorService;

    @Autowired
    QuoteRepository  quoteRepository;

    public QuoteController() {
    }

    @GetMapping("/getAll")
    public List<Quote> getAllQuotes() {
        return  quoteRepository.getAll();
    }

    @PostMapping("/post")
    public void postQuote(@RequestBody Quote quote) {
        quoteRepository.addQuote(quote);
    }

    @GetMapping("/random")
    public void getQuoteInPDF(HttpServletResponse response) throws IOException {
        Quote quote = quoteRepository.getRandomQuote();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=quote_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfGeneratorService.generatePDF(response, quote.getQuote(), quote.getAuthor());

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
