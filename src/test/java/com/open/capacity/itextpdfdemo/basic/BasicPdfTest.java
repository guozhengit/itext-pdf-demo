package com.open.capacity.itextpdfdemo.basic;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * iText 基础功能测试类
 * 测试 PDF 文档创建和基本文本操作
 */
public class BasicPdfTest {

    @TempDir
    Path tempDir;

    private String outputDir;

    @BeforeEach
    void setUp() {
        outputDir = tempDir.toString() + File.separator;
    }

    @Test
    void testCreateSimplePdf() throws IOException {
        String dest = outputDir + "simple.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("Hello, iText PDF!"));
            document.add(new Paragraph("这是一个简单的PDF文档测试"));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "PDF文件不应该为空");
    }

    @Test
    void testCreatePdfWithStyledText() throws IOException {
        String dest = outputDir + "styled_text.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            // 添加标题
            Paragraph title = new Paragraph("iText 样式文本测试")
                    .setFontSize(20)
                    .setFontColor(ColorConstants.BLUE)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            
            // 添加带样式的段落
            Paragraph styledParagraph = new Paragraph()
                    .add(new Text("这是一个").setFontColor(ColorConstants.BLACK))
                    .add(new Text("带有样式").setFontColor(ColorConstants.RED).setBold())
                    .add(new Text("的段落文本").setFontColor(ColorConstants.BLUE).setItalic());
            document.add(styledParagraph);
            
            // 添加不同对齐方式的文本
            document.add(new Paragraph("左对齐文本").setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("居中文本").setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("右对齐文本").setTextAlignment(TextAlignment.RIGHT));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "样式PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "样式PDF文件不应该为空");
    }

    @Test
    void testCreatePdfWithMultiplePages() throws IOException {
        String dest = outputDir + "multiple_pages.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            // 第一页
            document.add(new Paragraph("第一页内容"));
            
            // 添加新页面
            document.add(new Paragraph("").setPageBreakBefore(true));
            document.add(new Paragraph("第二页内容"));
            
            // 第三页
            document.add(new Paragraph("").setPageBreakBefore(true));
            document.add(new Paragraph("第三页内容"));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "多页PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "多页PDF文件不应该为空");
    }

    @Test
    void testCreatePdfWithCustomFont() throws IOException {
        String dest = outputDir + "custom_font.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            // 使用内置字体
            PdfFont helvetica = PdfFontFactory.createFont();
            
            Paragraph paragraph = new Paragraph("使用自定义字体的文本")
                    .setFont(helvetica)
                    .setFontSize(14);
            document.add(paragraph);
            
            // 添加不同大小的文本
            for (int size = 8; size <= 20; size += 2) {
                document.add(new Paragraph("字体大小: " + size + "pt")
                        .setFont(helvetica)
                        .setFontSize(size));
            }
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "自定义字体PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "自定义字体PDF文件不应该为空");
    }
}