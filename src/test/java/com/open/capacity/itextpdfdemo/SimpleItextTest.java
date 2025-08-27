package com.open.capacity.itextpdfdemo;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 简化的 iText 测试类 - 适配 Java 8 环境
 * 测试核心功能：文档创建、文本、表格等
 */
public class SimpleItextTest {

    @TempDir
    Path tempDir;

    @Test
    void testBasicPdfCreation() throws IOException {
        String dest = tempDir.toString() + File.separator + "basic_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            // 添加标题
            document.add(new Paragraph("iText 7 核心功能测试")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE));
            
            // 添加基本文本
            document.add(new Paragraph("这是一个使用 iText 7 创建的PDF文档。"));
            document.add(new Paragraph("测试环境: Java 8"));
            document.add(new Paragraph("创建时间: " + new java.util.Date()));
            
            // 添加分隔线
            document.add(new Paragraph("").setMarginTop(20));
            
            // 测试基本表格
            document.add(new Paragraph("表格功能测试:").setBold());
            Table table = new Table(3);
            
            // 表头
            table.addHeaderCell(new Cell().add(new Paragraph("功能")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("状态")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("描述")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            
            // 数据行
            table.addCell("PDF创建");
            table.addCell("✓ 成功");
            table.addCell("基础PDF文档创建功能");
            
            table.addCell("文本添加");
            table.addCell("✓ 成功");
            table.addCell("支持多种文本样式");
            
            table.addCell("表格创建");
            table.addCell("✓ 成功");
            table.addCell("支持表格布局");
            
            table.addCell("颜色支持");
            table.addCell("✓ 成功");
            table.addCell("支持文字和背景颜色");
            
            document.add(table);
            
            // 添加功能清单
            document.add(new Paragraph("").setMarginTop(20));
            document.add(new Paragraph("iText 7 核心功能清单:").setBold());
            document.add(new Paragraph("• PDF文档创建和基本操作"));
            document.add(new Paragraph("• 文本内容添加和格式化"));
            document.add(new Paragraph("• 表格创建和样式设置"));
            document.add(new Paragraph("• 图片插入和处理"));
            document.add(new Paragraph("• 页面布局和排版"));
            document.add(new Paragraph("• 水印和页眉页脚"));
            document.add(new Paragraph("• PDF合并和拆分"));
            document.add(new Paragraph("• 表单创建和处理"));
            document.add(new Paragraph("• 数字签名支持"));
            document.add(new Paragraph("• PDF/A标准支持"));
        }
        
        // 验证文件创建成功
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "PDF文件应该被成功创建");
        assertTrue(pdfFile.length() > 0, "PDF文件不应该为空");
        
        System.out.println("✅ 基础PDF测试成功！");
        System.out.println("📁 文件位置: " + dest);
        System.out.println("📄 文件大小: " + pdfFile.length() + " 字节");
    }

    @Test
    void testStyledContent() throws IOException {
        String dest = tempDir.toString() + File.separator + "styled_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            // 标题
            document.add(new Paragraph("文本样式测试")
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));
            
            // 不同样式的文本
            document.add(new Paragraph("普通文本"));
            document.add(new Paragraph("粗体文本").setBold());
            document.add(new Paragraph("斜体文本").setItalic());
            document.add(new Paragraph("红色文本").setFontColor(ColorConstants.RED));
            document.add(new Paragraph("蓝色文本").setFontColor(ColorConstants.BLUE));
            document.add(new Paragraph("大号文本").setFontSize(16));
            document.add(new Paragraph("小号文本").setFontSize(8));
            
            // 对齐测试
            document.add(new Paragraph("左对齐文本").setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("居中文本").setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("右对齐文本").setTextAlignment(TextAlignment.RIGHT));
            
            // 复合样式
            document.add(new Paragraph("复合样式文本")
                    .setBold()
                    .setItalic()
                    .setFontColor(ColorConstants.GREEN)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "样式测试PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "样式测试PDF文件不应该为空");
        
        System.out.println("✅ 样式测试成功！");
        System.out.println("📁 文件位置: " + dest);
    }

    @Test
    void testComplexTable() throws IOException {
        String dest = tempDir.toString() + File.separator + "complex_table_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("复杂表格测试")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            // 创建4列表格
            Table table = new Table(4);
            
            // 跨列标题
            Cell titleCell = new Cell(1, 4)
                    .add(new Paragraph("2024年销售数据报告"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.DARK_GRAY)
                    .setFontColor(ColorConstants.WHITE);
            table.addCell(titleCell);
            
            // 表头
            table.addHeaderCell(new Cell().add(new Paragraph("产品")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("季度")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("销量")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("金额")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            
            // 数据行
            String[] products = {"产品A", "产品B", "产品C"};
            String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
            
            for (String product : products) {
                for (String quarter : quarters) {
                    table.addCell(product);
                    table.addCell(quarter);
                    table.addCell(String.valueOf((int)(Math.random() * 1000 + 100)));
                    table.addCell("¥" + String.format("%.2f", Math.random() * 50000 + 10000));
                }
            }
            
            // 合计行
            Cell totalLabelCell = new Cell(1, 2)
                    .add(new Paragraph("总计"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.YELLOW)
                    .setBold();
            table.addCell(totalLabelCell);
            
            table.addCell(new Cell().add(new Paragraph("12,450")).setBold());
            table.addCell(new Cell().add(new Paragraph("¥2,180,000.00")).setBold());
            
            document.add(table);
            
            // 添加说明
            document.add(new Paragraph("").setMarginTop(20));
            document.add(new Paragraph("表格说明:")
                    .setBold()
                    .setFontColor(ColorConstants.BLUE));
            document.add(new Paragraph("• 支持跨列单元格"));
            document.add(new Paragraph("• 支持背景色和字体色"));
            document.add(new Paragraph("• 支持文本对齐"));
            document.add(new Paragraph("• 支持粗体等样式"));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "复杂表格PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "复杂表格PDF文件不应该为空");
        
        System.out.println("✅ 复杂表格测试成功！");
        System.out.println("📁 文件位置: " + dest);
    }
}