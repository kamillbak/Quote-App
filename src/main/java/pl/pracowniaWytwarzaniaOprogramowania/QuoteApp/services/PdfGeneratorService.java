package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfGeneratorService {

    public void generatePDF(HttpServletResponse response, String quote, String author) throws IOException {

        Document document = new Document(PageSize.A6);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        // Title
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(16);

        Paragraph paragraphTitle = new Paragraph("Quote for the day", fontTitle);
        paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);


        // Quote
        Font fontQuote = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
        fontQuote.setSize(14);

        Paragraph paragraphQuote = new Paragraph(" ' " + quote + " ' ", fontQuote);
        paragraphQuote.setAlignment(Paragraph.ALIGN_CENTER);

        // Author
        Font fontQAuthor = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontQAuthor.setSize(12);

        Paragraph paragraphAuhtor = new Paragraph(author, fontQAuthor);
        paragraphAuhtor.setAlignment(Paragraph.ALIGN_RIGHT);

        // Spacing
        Paragraph paragraphSpacing = new Paragraph("                                                                                                        " +
                "                                                                                                                                                  "
                , fontQAuthor);

        document.add(paragraphTitle);
        document.add(paragraphSpacing);
        document.add(paragraphSpacing);
        document.add(paragraphQuote);
        document.add(paragraphSpacing);
        document.add(paragraphSpacing);
        document.add(paragraphAuhtor);

        document.close();
    }
}
