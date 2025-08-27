package com.open.capacity.itextpdfdemo.content;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;

public class AddTableExample {
    public static void create(String dest) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("表格示例:"));

            // 创建3列的表格
            Table table = new Table(3);
            // 添加表头
            table.addHeaderCell(new Cell().add(new Paragraph("ID")));
            table.addHeaderCell(new Cell().add(new Paragraph("名称")));
            table.addHeaderCell(new Cell().add(new Paragraph("数量")));

            // 添加数据行
            table.addCell("1");
            table.addCell("产品A");
            table.addCell("100");
            table.addCell("2");
            table.addCell("产品B");
            table.addCell("200");

            document.add(table);
        }
        System.out.println("表格添加成功: " + dest);
    }
}