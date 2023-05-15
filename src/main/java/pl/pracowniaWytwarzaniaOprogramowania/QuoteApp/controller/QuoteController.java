package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.QuoteRepository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.services.PdfGeneratorService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 600)
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
    public ResponseEntity<List<Quote>> getAllQuotes() {
        List<Quote> quotes = quoteRepository.getAll();

        if(quotes == null) {
            return new ResponseEntity(null, HttpStatusCode.valueOf(404));
        }
        else {
            return  new ResponseEntity(quoteRepository.getAll(), HttpStatusCode.valueOf(200)) ;
        }
    }

    @PostMapping("/post")
    public ResponseEntity<String> postQuote(@RequestBody Quote quote) {
        int rowsAffected = quoteRepository.addQuote(quote);
        if(rowsAffected >0) {
            return new ResponseEntity("Added rows:" + String.valueOf(rowsAffected), HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity("Failure! Added rows: 0", HttpStatusCode.valueOf(404));
        }
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
    public ResponseEntity<List<Quote>> getAllQuotePostedById(@PathVariable("id") int id) {
        List<Quote> quotes = quoteRepository.getAllPostedByID(id);

        if(quotes == null) {
            return new ResponseEntity(null, HttpStatusCode.valueOf(404));
        }
        else {
            return  new ResponseEntity(quoteRepository.getAll(), HttpStatusCode.valueOf(200)) ;
        }
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
