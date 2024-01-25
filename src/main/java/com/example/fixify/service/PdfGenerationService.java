package com.example.fixify.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationService {

    public ByteArrayInputStream generatePdfFromHtml(String html) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(html, out);

        // Retorna un ByteArrayInputStream que contiene los datos del PDF
        return new ByteArrayInputStream(out.toByteArray());
    }
}