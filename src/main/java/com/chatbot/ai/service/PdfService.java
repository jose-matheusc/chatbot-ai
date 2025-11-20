package com.chatbot.ai.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

    /**
     * Backward-compatible overload that writes a generic PDF to the given path.
     */
    public void createPdf(String path) throws IOException {
        createPdf("This is a generated PDF.", path);
    }

    /**
     * Create a nicely formatted PDF with the provided body content at the given path.
     * @param content body text to include (will be wrapped and may span multiple pages)
     * @param path full file path where the PDF will be saved
     * @throws IOException on IO errors
     */
    public void createPdf(String content, String path) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDRectangle media = page.getMediaBox();
            float margin = 50;
            float usableWidth = media.getWidth() - 2 * margin;

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            int pageNumber = 1;

            // Header band
            float headerHeight = 80f;
            contentStream.setNonStrokingColor(new Color(30, 144, 255)); // Dodger blue
            contentStream.addRect(0, media.getHeight() - headerHeight, media.getWidth(), headerHeight);
            contentStream.fill();

            // Title inside header
            contentStream.beginText();
            contentStream.setNonStrokingColor(Color.WHITE);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            float titleX = margin;
            float titleY = media.getHeight() - 48; // vertically centered in header
            contentStream.newLineAtOffset(titleX, titleY);
            contentStream.showText("PDF Report — Spring Boot + PDFBox");
            contentStream.endText();

            // Subtitle / small caption in header
            contentStream.beginText();
            contentStream.setNonStrokingColor(Color.WHITE);
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            contentStream.newLineAtOffset(titleX, titleY - 20);
            contentStream.showText("Generated with Apache PDFBox");
            contentStream.endText();

            // Reset color for main body
            contentStream.setNonStrokingColor(Color.BLACK);

            // Draw a subtle separator line below header
            contentStream.setStrokingColor(new Color(200, 200, 200));
            contentStream.moveTo(margin, media.getHeight() - headerHeight - 10);
            contentStream.lineTo(media.getWidth() - margin, media.getHeight() - headerHeight - 10);
            contentStream.stroke();

            // Body title
            float cursorY = media.getHeight() - headerHeight - 40;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.newLineAtOffset(margin, cursorY);
            contentStream.showText("Overview");
            contentStream.endText();

            // Date
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.newLineAtOffset(media.getWidth() - margin - 120, cursorY + 2);
            contentStream.showText("Date: " + date);
            contentStream.endText();

            // Body paragraph(s) (wrapped + multipage)
            float bodyStartY = cursorY - 30;
            float fontSize = 11;
            float leading = fontSize + 4;
            float bottomY = margin + 40; // leave some space for footer

            List<String> lines = wrapText(PDType1Font.HELVETICA, fontSize, content, usableWidth);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.setLeading(leading);
            contentStream.newLineAtOffset(margin, bodyStartY);

            float currentY = bodyStartY;
            for (String line : lines) {
                // If next line would go below bottom, new page
                if (currentY - leading < bottomY) {
                    contentStream.endText();
                    drawFooter(contentStream, media, margin, pageNumber);
                    contentStream.close();

                    pageNumber++;
                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    media = page.getMediaBox();

                    contentStream = new PDPageContentStream(doc, page);

                    // draw header on each new page (simpler variant)
                    contentStream.setNonStrokingColor(new Color(30, 144, 255));
                    contentStream.addRect(0, media.getHeight() - headerHeight, media.getWidth(), headerHeight);
                    contentStream.fill();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream.newLineAtOffset(margin, media.getHeight() - 50);
                    contentStream.showText("PDF Report");
                    contentStream.endText();

                    // separator line
                    contentStream.setStrokingColor(new Color(200, 200, 200));
                    contentStream.moveTo(margin, media.getHeight() - headerHeight - 10);
                    contentStream.lineTo(media.getWidth() - margin, media.getHeight() - headerHeight - 10);
                    contentStream.stroke();

                    // reset for new page body
                    cursorY = media.getHeight() - headerHeight - 30;
                    currentY = cursorY;
                    contentStream.beginText();
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                    contentStream.setLeading(leading);
                    contentStream.newLineAtOffset(margin, cursorY);
                }

                contentStream.showText(line);
                contentStream.newLine();
                currentY -= leading;
            }

            contentStream.endText();

            // Footer last page
            drawFooter(contentStream, media, margin, pageNumber);
            contentStream.close();

            doc.save(path);
        }
    }

    // Simple greedy text wrapper based on font metrics
    private List<String> wrapText(PDType1Font font, float fontSize, String text, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        if (text == null) return lines;
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String test = line.isEmpty() ? word : line + " " + word;
            float size = font.getStringWidth(test) / 1000 * fontSize;
            if (size > maxWidth) {
                if (line.isEmpty()) {
                    // single long word; force split
                    lines.add(word);
                } else {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                }
            } else {
                if (line.isEmpty()) line = new StringBuilder(word);
                else line.append(' ').append(word);
            }
        }
        if (!line.isEmpty()) lines.add(line.toString());
        return lines;
    }

    private void drawFooter(PDPageContentStream contentStream, PDRectangle media, float margin, int pageNumber) throws IOException {
        String footerLeft = "Generated by PdfService";
        String footerRight = "Page " + pageNumber;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        contentStream.setNonStrokingColor(new Color(80, 80, 80));
        contentStream.newLineAtOffset(margin, margin - 10);
        contentStream.showText(footerLeft);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        float rightX = media.getWidth() - margin - 50;
        contentStream.newLineAtOffset(rightX, margin - 10);
        contentStream.showText(footerRight);
        contentStream.endText();
    }
}
