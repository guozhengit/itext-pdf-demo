package  com.open.capacity.itextpdfdemo.advanced;


import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfa.PdfADocument;

import java.io.FileInputStream;
import java.io.IOException;

public class CreatePdfAExample {
    public static void create(String dest, String iccProfilePath) throws IOException {
        // 创建PDF/A文档
        PdfWriter writer = new PdfWriter(dest);
        PdfADocument pdf = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_1B,
                new PdfOutputIntent("Custom", "", "http://www.color.org",
                        "sRGB IEC61966-2.1", new FileInputStream(iccProfilePath)));

        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("这是一个PDF/A-1B合规文档"));
            document.add(new Paragraph("PDF/A是用于长期归档的PDF格式"));
        }

        System.out.println("PDF/A文档创建成功: " + dest);
    }
}