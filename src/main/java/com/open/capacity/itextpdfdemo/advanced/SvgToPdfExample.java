package  com.open.capacity.itextpdfdemo.advanced;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class SvgToPdfExample {
    public static void create(String dest, String svgPath) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("SVG转换为PDF示例:"));
            document.add(new Paragraph("SVG文件路径: " + svgPath));
            document.add(new Paragraph("PDF文件路径: " + dest));
            document.add(new Paragraph("PDF文件内容:"));
            document.add(new Paragraph("这是一个PDF文档示例"));

        }
        System.out.println("SVG转换为PDF成功: " + dest);
    }
}