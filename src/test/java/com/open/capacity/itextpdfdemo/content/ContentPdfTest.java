package com.open.capacity.itextpdfdemo.content;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * iText 内容操作功能测试类
 * 测试图片、表格、列表等内容元素
 */
public class ContentPdfTest {

    @TempDir
    Path tempDir;

    private String outputDir;

    @BeforeEach
    void setUp() {
        outputDir = tempDir.toString() + File.separator;
    }

    @Test
    void testCreatePdfWithTable() throws IOException {
        String dest = outputDir + "table_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("表格功能测试"));
            
            // 创建3列表格
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1}));
            table.setWidth(UnitValue.createPercentValue(100));
            
            // 添加表头
            Cell headerCell1 = new Cell().add(new Paragraph("ID"))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            Cell headerCell2 = new Cell().add(new Paragraph("名称"))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            Cell headerCell3 = new Cell().add(new Paragraph("数量"))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            
            table.addHeaderCell(headerCell1);
            table.addHeaderCell(headerCell2);
            table.addHeaderCell(headerCell3);
            
            // 添加数据行
            for (int i = 1; i <= 5; i++) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
                table.addCell(new Cell().add(new Paragraph("产品 " + i)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(i * 100))));
            }
            
            document.add(table);
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "表格PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "表格PDF文件不应该为空");
    }

    @Test
    void testCreatePdfWithImage() throws IOException {
        String dest = outputDir + "image_test.pdf";
        
        // 创建一个简单的测试图片
        BufferedImage testImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = testImage.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, 200, 100);
        g2d.setColor(Color.WHITE);
        g2d.drawString("测试图片", 70, 50);
        g2d.dispose();
        
        // 将图片转换为字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(testImage, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("图片功能测试"));
            
            // 添加图片
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image image = new Image(imageData);
            image.scaleToFit(300, 200);
            document.add(image);
            
            document.add(new Paragraph("图片已成功添加到PDF中"));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "图片PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "图片PDF文件不应该为空");
    }

    @Test
    void testCreatePdfWithList() throws IOException {
        String dest = outputDir + "list_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("列表功能测试"));
            
            // 无序列表
            List unorderedList = new List();
            unorderedList.add(new ListItem("第一项"));
            unorderedList.add(new ListItem("第二项"));
            unorderedList.add(new ListItem("第三项"));
            
            document.add(new Paragraph("无序列表:"));
            document.add(unorderedList);
            
            // 有序列表
            List orderedList = new List().setListSymbol("1. ");
            orderedList.add(new ListItem("步骤一"));
            orderedList.add(new ListItem("步骤二"));
            orderedList.add(new ListItem("步骤三"));
            
            document.add(new Paragraph("有序列表:"));
            document.add(orderedList);
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "列表PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "列表PDF文件不应该为空");
    }

    @Test
    void testCreateComplexTable() throws IOException {
        String dest = outputDir + "complex_table_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("复杂表格测试"));
            
            // 创建4列表格
            Table table = new Table(4);
            table.setWidth(UnitValue.createPercentValue(100));
            
            // 添加跨列单元格
            Cell titleCell = new Cell(1, 4)
                    .add(new Paragraph("销售报表"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.DARK_GRAY)
                    .setFontColor(ColorConstants.WHITE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(titleCell);
            
            // 添加表头
            table.addHeaderCell(new Cell().add(new Paragraph("产品")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("季度")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("销量")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("金额")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            
            // 添加数据
            String[] products = {"产品A", "产品B", "产品C"};
            String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
            
            for (String product : products) {
                for (String quarter : quarters) {
                    table.addCell(product);
                    table.addCell(quarter);
                    table.addCell(String.valueOf((int)(Math.random() * 1000)));
                    table.addCell("¥" + String.format("%.2f", Math.random() * 10000));
                }
            }
            
            document.add(table);
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "复杂表格PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "复杂表格PDF文件不应该为空");
    }
}