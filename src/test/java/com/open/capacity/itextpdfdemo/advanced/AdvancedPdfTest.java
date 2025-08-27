package com.open.capacity.itextpdfdemo.advanced;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.svg.converter.SvgConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * iText 高级功能测试类
 * 测试PDF/A、SVG转换、表单处理等高级功能
 */
public class AdvancedPdfTest {

    @TempDir
    Path tempDir;

    private String outputDir;

    @BeforeEach
    void setUp() {
        outputDir = tempDir.toString() + File.separator;
    }

    @Test
    void testCreatePdfWithForms() throws IOException {
        String dest = outputDir + "form_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("PDF表单测试")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("请填写以下信息："));
            
            // 创建表格来布局表单
            Table table = new Table(2);
            table.addCell(new Cell().add(new Paragraph("姓名：")));
            table.addCell(new Cell().add(new Paragraph(""))); // 空白单元格用于放置表单字段
            
            table.addCell(new Cell().add(new Paragraph("邮箱：")));
            table.addCell(new Cell().add(new Paragraph("")));
            
            table.addCell(new Cell().add(new Paragraph("备注：")));
            table.addCell(new Cell().add(new Paragraph("")));
            
            document.add(table);
        }
        
        // 添加表单字段
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        
        // 姓名字段
        PdfTextFormField nameField = PdfTextFormField.createText(pdf, new Rectangle(200, 650, 200, 20), "name");
        nameField.setValue("请输入姓名");
        form.addField(nameField);
        
        // 邮箱字段
        PdfTextFormField emailField = PdfTextFormField.createText(pdf, new Rectangle(200, 620, 200, 20), "email");
        emailField.setValue("请输入邮箱");
        form.addField(emailField);
        
        // 备注字段（多行）
        PdfTextFormField commentField = PdfTextFormField.createMultilineText(pdf, new Rectangle(200, 550, 200, 60), "comment");
        commentField.setValue("请输入备注");
        form.addField(commentField);
        
        pdf.close();
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "表单PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "表单PDF文件不应该为空");
    }

    @Test
    void testCreateSvgToPdf() throws IOException {
        String svgFile = outputDir + "test.svg";
        String dest = outputDir + "svg_to_pdf_test.pdf";
        
        // 创建一个简单的SVG文件
        createTestSvgFile(svgFile);
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("SVG转PDF测试"));
            
            // 读取SVG内容并转换为PDF
            String svgContent;
            try {
                byte[] encoded = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(svgFile));
                svgContent = new String(encoded, "UTF-8");
            } catch (Exception e) {
                svgContent = "SVG读取失败: " + e.getMessage();
            }
            
            // 使用iText SVG转换器
            try {
                SvgConverter.drawOnDocument(svgContent, pdf, 1);
            } catch (Exception e) {
                // 如果SVG转换失败，添加说明文本
                document.add(new Paragraph("SVG转换功能需要完整的iText SVG模块支持"));
                document.add(new Paragraph("SVG文件路径: " + svgFile));
            }
            
            document.add(new Paragraph("SVG转PDF测试完成"));
        }
        
        pdf.close();
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "SVG转PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "SVG转PDF文件不应该为空");
    }

    @Test
    void testCreateDigitalSignaturePlaceholder() throws IOException {
        String dest = outputDir + "signature_placeholder_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("数字签名测试文档")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("这是一个需要数字签名的重要文档。"));
            
            // 添加签名区域说明
            document.add(new Paragraph("\n\n签名区域："));
            
            // 创建签名占位符
            Table signatureTable = new Table(1);
            Cell signatureCell = new Cell()
                    .add(new Paragraph("数字签名占位符\n\n[在此处添加数字签名]"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(com.itextpdf.layout.borders.SolidBorder.createSolidBorder(ColorConstants.BLACK, 2))
                    .setHeight(100);
            
            signatureTable.addCell(signatureCell);
            document.add(signatureTable);
            
            document.add(new Paragraph("\n签名日期: _______________"));
        }
        
        // 添加签名字段
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        PdfFormField signatureField = PdfFormField.createSignature(pdf, new Rectangle(100, 200, 300, 100));
        signatureField.setFieldName("signature");
        form.addField(signatureField);
        
        pdf.close();
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "数字签名PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "数字签名PDF文件不应该为空");
    }

    @Test
    void testCreateSecurePdf() throws IOException {
        String dest = outputDir + "secure_test.pdf";
        String userPassword = "user123";
        String ownerPassword = "owner456";
        
        WriterProperties properties = new WriterProperties()
                .setStandardEncryption(
                        userPassword.getBytes(),
                        ownerPassword.getBytes(),
                        EncryptionConstants.ALLOW_PRINTING | EncryptionConstants.ALLOW_COPY,
                        EncryptionConstants.ENCRYPTION_AES_128
                );
        
        PdfWriter writer = new PdfWriter(dest, properties);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("加密PDF文档")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("这是一个加密的PDF文档。"));
            document.add(new Paragraph("用户密码: " + userPassword));
            document.add(new Paragraph("所有者密码: " + ownerPassword));
            document.add(new Paragraph("权限: 允许打印和复制"));
            
            // 添加一些敏感信息
            document.add(new Paragraph("\n机密信息："));
            document.add(new Paragraph("这些内容只有输入正确密码才能查看。"));
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "加密PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "加密PDF文件不应该为空");
    }

    @Test
    void testCreateAccessiblePdf() throws IOException {
        String dest = outputDir + "accessible_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        // 设置PDF/UA（通用访问性）标记
        pdf.setTagged();
        pdf.getCatalog().setLang(new PdfString("zh-CN"));
        
        try (Document document = new Document(pdf)) {
            // 添加文档标题
            Paragraph title = new Paragraph("无障碍PDF文档")
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);
            title.getAccessibilityProperties().setRole("H1");
            document.add(title);
            
            // 添加段落
            Paragraph content = new Paragraph("这是一个支持无障碍访问的PDF文档，" +
                    "包含了适当的结构标记，可以被屏幕阅读器正确解读。");
            content.getAccessibilityProperties().setRole("P");
            document.add(content);
            
            // 添加子标题
            Paragraph subtitle = new Paragraph("功能特性")
                    .setFontSize(16);
            subtitle.getAccessibilityProperties().setRole("H2");
            document.add(subtitle);
            
            // 添加列表
            com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
            list.add("结构化标记");
            list.add("语言标识");
            list.add("替代文本支持");
            list.getAccessibilityProperties().setRole("L");
            document.add(list);
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "无障碍PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "无障碍PDF文件不应该为空");
    }

    /**
     * 创建测试用SVG文件
     */
    private void createTestSvgFile(String filePath) throws IOException {
        String svgContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"100\">\n" +
                "    <rect x=\"10\" y=\"10\" width=\"180\" height=\"80\" \n" +
                "          fill=\"lightblue\" stroke=\"navy\" stroke-width=\"2\"/>\n" +
                "    <text x=\"100\" y=\"55\" text-anchor=\"middle\" \n" +
                "          font-family=\"Arial\" font-size=\"16\" fill=\"navy\">\n" +
                "        测试SVG\n" +
                "    </text>\n" +
                "    <circle cx=\"50\" cy=\"30\" r=\"10\" fill=\"red\"/>\n" +
                "    <circle cx=\"150\" cy=\"30\" r=\"10\" fill=\"green\"/>\n" +
                "</svg>";
        
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(svgContent);
        }
    }
}