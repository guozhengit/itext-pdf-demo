package  com.open.capacity.itextpdfdemo.basic;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class CreateHelloPdf {
    public static void create(String dest) throws FileNotFoundException {
        // 创建PDF写入器
        PdfWriter writer = new PdfWriter(dest);
        // 创建PDF文档
        PdfDocument pdf = new PdfDocument(writer);
        // 创建布局文档
        try (Document document = new Document(pdf)) {
            // 添加内容
            document.add(new Paragraph("Hello, iText PDF!"));
            document.add(new Paragraph("这是一个简单的PDF文档示例"));
        }
        System.out.println("基础PDF创建成功: " + dest);
    }
}