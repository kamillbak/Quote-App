package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.Quote;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.QuoteRepository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.services.PdfGeneratorService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
    public ResponseEntity<List<Quote>> getAllQuotes() {
        List<Quote> quotes = quoteRepository.getAll();

        if(quotes == null) {
            quotes = Collections.<Quote>emptyList();
            return new ResponseEntity(quotes, HttpStatusCode.valueOf(200));
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
    public  ResponseEntity<String> getQuoteInPDF(HttpServletResponse response) throws IOException {
        Quote quote = quoteRepository.getRandomQuote();

        if(quote == null) {
            return new ResponseEntity("There is no quote in DB yet", HttpStatusCode.valueOf(200));
        } else {
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=quote_" + currentDateTime + ".pdf";
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
            response.addHeader("Access-Control-Max-Age", "1728000");
            response.setHeader(headerKey, headerValue);

            pdfGeneratorService.generatePDF(response, quote.getQuote(), quote.getAuthor());

            return new ResponseEntity("PDF with quote sent", HttpStatusCode.valueOf(200));
        }
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<Quote>> getAllQuotePostedById(@PathVariable("id") int id) {
        List<Quote> quotes = quoteRepository.getAllPostedByID(id);

        if(quotes == null) {
            quotes = Collections.<Quote>emptyList();
            return new ResponseEntity(quotes, HttpStatusCode.valueOf(200));
        }
        else {
            return  new ResponseEntity(quotes, HttpStatusCode.valueOf(200)) ;
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateQuote(@PathVariable("id") int id, @RequestBody Quote quoteUpdate) {
        Quote quoteFromDb = quoteRepository.getQuoteById(id);
        if (quoteFromDb == null) {
            return new ResponseEntity("User with that id does not exist", HttpStatusCode.valueOf(404));
        }
        else {
            if(quoteUpdate.getQuote() != null) quoteFromDb.setQuote(quoteUpdate.getQuote());
            if(quoteUpdate.getAuthor() != null) quoteFromDb.setAuthor(quoteUpdate.getAuthor());

            int rowsAffected = quoteRepository.updateQuote(quoteFromDb);

            if(rowsAffected >0){
                return new ResponseEntity("Updated successfully", HttpStatusCode.valueOf(200));
            }
            else {
                return new ResponseEntity("Failure! Updated rows: 0", HttpStatusCode.valueOf(400));
            }

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuote(@PathVariable("id") int id) {
        int rowsAffected = quoteRepository.deleteQuote(id);
        if(rowsAffected >0){
            return new ResponseEntity("Deleted successfully", HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity("Failure! Deleted rows: 0", HttpStatusCode.valueOf(400));
        }
    }
}
