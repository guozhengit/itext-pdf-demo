package com.open.capacity.itextpdfdemo;

import java.io.File;
import com.open.capacity.itextpdfdemo.basic.CreateHelloPdf;
import com.open.capacity.itextpdfdemo.content.AddImageExample;
import com.open.capacity.itextpdfdemo.content.AddTableExample;
import com.open.capacity.itextpdfdemo.content.AddWatermarkExample;
import com.open.capacity.itextpdfdemo.page.MergePdfsExample;

/**
 * iText PDF 演示应用主类
 * 演示各种 PDF 操作功能
 */
public class ItextPdfDemoApplication {

    // 输出目录
    private static final String OUTPUT_DIR = "output/";

    public static void main(String[] args) throws Exception {
        // 创建输出目录
        new File(OUTPUT_DIR).mkdirs();

        // 1. 创建基础PDF
        String helloPdf = OUTPUT_DIR + "hello.pdf";
        CreateHelloPdf.create(helloPdf);

        // 2. 添加图片
        String imagePdf = OUTPUT_DIR + "with_image.pdf";
        AddImageExample.create(imagePdf, "src/main/resources/images/sample.png");

        // 3. 添加表格
        String tablePdf = OUTPUT_DIR + "with_table.pdf";
        AddTableExample.create(tablePdf);

        // 4. 给基础PDF添加水印
        String watermarkPdf = OUTPUT_DIR + "hello_with_watermark.pdf";
        AddWatermarkExample.add(helloPdf, watermarkPdf);

        // 5. 合并PDF
        String mergedPdf = OUTPUT_DIR + "merged.pdf";
        MergePdfsExample.merge(new String[]{helloPdf, imagePdf, tablePdf}, mergedPdf);

        System.out.println("所有演示完成，输出文件在: " + new File(OUTPUT_DIR).getAbsolutePath());
    }

}
