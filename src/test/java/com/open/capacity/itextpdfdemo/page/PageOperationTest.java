package com.open.capacity.itextpdfdemo.page;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * iText 页面操作功能测试类
 * 测试水印、页眉页脚、PDF合并等页面级功能
 */
public class PageOperationTest {

    @TempDir
    Path tempDir;

    private String outputDir;

    @BeforeEach
    void setUp() {
        outputDir = tempDir.toString() + File.separator;
    }

    @Test
    void testAddWatermark() throws IOException {
        // 首先创建一个基础PDF
        String sourcePdf = outputDir + "source.pdf";
        createBasePdf(sourcePdf);
        
        String dest = outputDir + "watermark_test.pdf";
        
        PdfReader reader = new PdfReader(sourcePdf);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(reader, writer);
        
        int pageCount = pdf.getNumberOfPages();
        
        for (int i = 1; i <= pageCount; i++) {
            PdfPage page = pdf.getPage(i);
            Rectangle pageSize = page.getPageSize();
            
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdf);
            
            // 添加水印
            canvas.saveState()
                    .setFillColor(ColorConstants.LIGHT_GRAY)
                    .beginText()
                    .setFontAndSize(PdfFontFactory.createFont(), 50)
                    .setTextMatrix(1, 0, 0, 1, pageSize.getWidth() / 2 - 50, pageSize.getHeight() / 2)
                    .showText("水印")
                    .endText()
                    .restoreState();
        }
        
        pdf.close();
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "水印PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "水印PDF文件不应该为空");
    }

    @Test
    void testAddHeaderFooter() throws IOException {
        String dest = outputDir + "header_footer_test.pdf";
        
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        // 添加页眉页脚事件处理器
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterEventHandler());
        
        try (Document document = new Document(pdf)) {
            // 添加多页内容以测试页眉页脚
            for (int i = 1; i <= 3; i++) {
                document.add(new Paragraph("第 " + i + " 页内容"));
                document.add(new Paragraph("这是一些填充内容，用于测试页眉和页脚功能。"));
                
                // 添加更多内容使页面填满
                for (int j = 0; j < 20; j++) {
                    document.add(new Paragraph("第 " + i + " 页的第 " + (j + 1) + " 行内容。"));
                }
                
                if (i < 3) {
                    document.add(new Paragraph("").setPageBreakBefore(true));
                }
            }
        }
        
        File pdfFile = new File(dest);
        assertTrue(pdfFile.exists(), "页眉页脚PDF文件应该被创建");
        assertTrue(pdfFile.length() > 0, "页眉页脚PDF文件不应该为空");
    }

    @Test
    void testMergePdfs() throws IOException {
        // 创建多个源PDF文件
        String pdf1 = outputDir + "merge_source1.pdf";
        String pdf2 = outputDir + "merge_source2.pdf";
        String pdf3 = outputDir + "merge_source3.pdf";
        String mergedPdf = outputDir + "merged_test.pdf";
        
        // 创建源文件
        createBasePdf(pdf1, "第一个PDF文档");
        createBasePdf(pdf2, "第二个PDF文档");
        createBasePdf(pdf3, "第三个PDF文档");
        
        // 合并PDF
        PdfWriter writer = new PdfWriter(mergedPdf);
        PdfDocument mergedDoc = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(mergedDoc);
        
        // 添加每个源PDF
        PdfDocument pdf1Doc = new PdfDocument(new PdfReader(pdf1));
        merger.merge(pdf1Doc, 1, pdf1Doc.getNumberOfPages());
        pdf1Doc.close();
        
        PdfDocument pdf2Doc = new PdfDocument(new PdfReader(pdf2));
        merger.merge(pdf2Doc, 1, pdf2Doc.getNumberOfPages());
        pdf2Doc.close();
        
        PdfDocument pdf3Doc = new PdfDocument(new PdfReader(pdf3));
        merger.merge(pdf3Doc, 1, pdf3Doc.getNumberOfPages());
        pdf3Doc.close();
        
        mergedDoc.close();
        
        File mergedFile = new File(mergedPdf);
        assertTrue(mergedFile.exists(), "合并PDF文件应该被创建");
        assertTrue(mergedFile.length() > 0, "合并PDF文件不应该为空");
        
        // 验证合并后的页数
        PdfDocument verifyDoc = new PdfDocument(new PdfReader(mergedPdf));
        assertTrue(verifyDoc.getNumberOfPages() >= 3, "合并后的PDF应该至少有3页");
        verifyDoc.close();
    }

    @Test
    void testRotatePages() throws IOException {
        String sourcePdf = outputDir + "rotate_source.pdf";
        String dest = outputDir + "rotated_test.pdf";
        
        // 创建源PDF
        createBasePdf(sourcePdf);
        
        PdfReader reader = new PdfReader(sourcePdf);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(reader, writer);
        
        // 旋转所有页面
        int pageCount = pdf.getNumberOfPages();
        for (int i = 1; i <= pageCount; i++) {
            PdfPage page = pdf.getPage(i);
            page.setRotation(90); // 顺时针旋转90度
        }
        
        pdf.close();
        
        File rotatedFile = new File(dest);
        assertTrue(rotatedFile.exists(), "旋转PDF文件应该被创建");
        assertTrue(rotatedFile.length() > 0, "旋转PDF文件不应该为空");
    }

    /**
     * 创建基础PDF文件用于测试
     */
    private void createBasePdf(String dest) throws IOException {
        createBasePdf(dest, "这是一个测试PDF文档");
    }

    private void createBasePdf(String dest, String content) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph(content));
            document.add(new Paragraph("创建时间: " + new java.util.Date()));
            
            // 添加更多内容
            for (int i = 1; i <= 10; i++) {
                document.add(new Paragraph("第 " + i + " 行内容: " + content));
            }
        }
    }

    /**
     * 页眉页脚事件处理器
     */
    private static class HeaderFooterEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            
            try {
                // 添加页眉
                new Canvas(canvas, pageSize)
                        .showTextAligned(new Paragraph("iText PDF 测试文档")
                                        .setFontSize(12)
                                        .setFontColor(ColorConstants.GRAY),
                                pageSize.getWidth() / 2, pageSize.getTop() - 20,
                                TextAlignment.CENTER);
                
                // 添加页脚
                new Canvas(canvas, pageSize)
                        .showTextAligned(new Paragraph("第 " + pageNumber + " 页")
                                        .setFontSize(10)
                                        .setFontColor(ColorConstants.GRAY),
                                pageSize.getWidth() / 2, pageSize.getBottom() + 20,
                                TextAlignment.CENTER);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}