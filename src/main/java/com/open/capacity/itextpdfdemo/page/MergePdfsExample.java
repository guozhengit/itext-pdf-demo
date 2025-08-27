package com.open.capacity.itextpdfdemo.page;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * PDF合并示例
 */
public class MergePdfsExample {
    
    public static void merge(String[] sources, String dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument mergedDoc = new PdfDocument(writer);
        PdfMerger merger = new PdfMerger(mergedDoc);
        
        for (String source : sources) {
            try {
                PdfDocument sourceDoc = new PdfDocument(new PdfReader(source));
                merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
                sourceDoc.close();
            } catch (Exception e) {
                System.err.println("合并文件时出错: " + source + ", 错误: " + e.getMessage());
            }
        }
        
        mergedDoc.close();
        System.out.println("PDF合并成功: " + dest);
    }
}