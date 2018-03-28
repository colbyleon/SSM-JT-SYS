package com.jt.common.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jt.common.exception.ServiceException;
import com.jt.sys.entity.SysUser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ExportUtils {

    /**
     * 将pdf文件写入内存中，而不是写入文件
     * @return  二进制数据
     */
    public static byte[] exportUserPDF(List<SysUser> users) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();    // 字节流
        Document document = new Document();// 创建document对象（页面默认为A4,10,10,10,10）
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);// 打开输出器
            writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
            document.setPageSize(PageSize.A4);
            document.open();            // 打开pdf
            writeList(document,users);  // 写入pdf
            document.close();           // 关闭文档
            return out.toByteArray();   // 返回二进制数据
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文档处理器正在维护中");
        }
    }

    private static void writeList(Document document, List<SysUser> users) throws Exception {
        float[] widths = {70,70,60,60,60,80,80,80,80};
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(458);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        // 设置表头
        String[] titles = {"用户ID","用户名","邮箱","手机","状态","创建时间","修改时间","创建用户","修改用户"};
        for (String title : titles) {
            writeCell(table, title , 12);
        }
        // 写入用户数据
        for(SysUser user:users){
            // 空值处理
            String id = user.getId()==null? "":user.getId()+"";
            String username = user.getUsername()==null? "":user.getUsername()+"";
            String email = user.getEmail()==null? "":user.getEmail()+"";
            String mobile = user.getMobile()==null? "":user.getMobile()+"";
            String valid = 1 == user.getValid()? "启用":"禁用";
            String ct = user.getCreatedTime()==null? "":user.getCreatedTime().toLocaleString()+"";
            String mt = user.getModifiedTime()==null? "":user.getModifiedTime().toLocaleString()+"";
            String cu = user.getCreatedUser()==null? "":user.getCreatedUser()+"";
            String mu = user.getModifiedUser()==null? "":user.getModifiedUser()+"";

            String[] row = {id,username,email,mobile,valid,ct,mt,cu,mu};
            for (String cell : row) {
                writeCell(table, cell, 10);
            }
        }
        document.add(table);
    }
    // 写入单个格子
    private static void writeCell(PdfPTable table, String cell,int size) throws Exception {
        PdfPCell pdfCell = new PdfPCell(); //表格的单元格
        Paragraph paragraph = new Paragraph(cell, getPdfChineseFont(size));
        pdfCell.setPhrase(paragraph);
        pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pdfCell);
    }
    // 中文字体处理
    private static Font getPdfChineseFont(int size) throws Exception {
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, size, Font.NORMAL);
        return fontChinese;
    }


    public static byte[] exportUserExcel(List<SysUser> users) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();  // 字节流
        try{
            //得到Excel工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建一张表
            HSSFSheet sheet = wb.createSheet("用户表");
            generator(sheet ,users);

            wb.write(out);
            return out.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("服务器异常");
        }
    }

    private static void generator(HSSFSheet sheet,List<SysUser> users) {
        HSSFRow headRow = sheet.createRow(0);
        String[] titles = {"用户ID","用户名","邮箱","手机","状态","创建时间","修改时间","创建用户","修改用户"};
        for (int i =0 ;i<titles.length;i++) {
            headRow.createCell(i).setCellValue(titles[i]);
        }

        // 写入用户数据
        int i = 1;
        for(SysUser user:users){
            // 空值处理
            String id = user.getId()==null? "":user.getId()+"";
            String username = user.getUsername()==null? "":user.getUsername()+"";
            String email = user.getEmail()==null? "":user.getEmail()+"";
            String mobile = user.getMobile()==null? "":user.getMobile()+"";
            String valid = 1 == user.getValid()? "启用":"禁用";
            String ct = user.getCreatedTime()==null? "":user.getCreatedTime().toLocaleString()+"";
            String mt = user.getModifiedTime()==null? "":user.getModifiedTime().toLocaleString()+"";
            String cu = user.getCreatedUser()==null? "":user.getCreatedUser()+"";
            String mu = user.getModifiedUser()==null? "":user.getModifiedUser()+"";

            String[] content = {id,username,email,mobile,valid,ct,mt,cu,mu};
            HSSFRow row = sheet.createRow(i++);
            for (int j = 0; j < content.length; j++) {
                row.createCell(j).setCellValue(content[j]);
            }
        }
    }
}
