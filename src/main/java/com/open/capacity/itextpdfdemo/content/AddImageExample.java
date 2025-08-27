package com.open.capacity.itextpdfdemo.content;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class AddImageExample {
    public static void create(String dest, String imagePath) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("添加图片示例:"));

            // 读取图片并添加到PDF
            Image image = new Image(ImageDataFactory.create(imagePath));
            // 缩放图片
            image.scaleToFit(300, 300);
            document.add(image);
        }
        System.out.println("图片添加成功: " + dest);
    }
}