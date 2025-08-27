
package com.open.capacity.itextpdfdemo.content;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddWatermarkExample {
    public static void add(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(reader, writer);

        // 获取总页数
        int pageCount = pdf.getNumberOfPages();
        for (int i = 1; i <= pageCount; i++) {
            PdfPage page = pdf.getPage(i);
            // 获取页面大小
            float pageWidth = page.getPageSize().getWidth();
            float pageHeight = page.getPageSize().getHeight();

            // 创建画布绘制水印
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdf);
            
            // 使用Canvas添加水印文本
            new Canvas(canvas, page.getPageSize())
                    .showTextAligned(
                            new Paragraph("水印示例")
                                    .setFontColor(ColorConstants.LIGHT_GRAY)
                                    .setFontSize(50),
                            pageWidth / 2, pageHeight / 2,  // 居中位置
                            i,  // 页码
                            TextAlignment.CENTER, null, 45  // 旋转45度
                    );
        }

        pdf.close();
        System.out.println("水印添加成功: " + dest);
    }
}