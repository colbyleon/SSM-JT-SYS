package com.jt.sys.controller;

import com.jt.common.util.ExportUtils;
import com.jt.common.vo.JsonResult;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/file/")
public class SysFileController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 传送pdf到前端
     */
    @RequestMapping("doDownloadPDF")
    public void doDownloadPDF(HttpServletResponse response){
        try {
            // 从数据库中获取用户数据
            List<SysUser> users = sysUserService.exportFileData();
            // 使用工具类将用户数据处理成pdf二进制数据
            byte[] pdfBytes = ExportUtils.exportUserPDF(users);
            sendData(response, pdfBytes , "user_list.pdf");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("doDownloadExcel")
    public void doDownloadExcel(HttpServletResponse response) {
        try {
            /*从数据库中获取用户数据*/
            List<SysUser> users = sysUserService.exportFileData();
             /*使用工具类将用户数据处理成pdf二进制数据*/
            byte[] pdfBytes = ExportUtils.exportUserExcel(users);
            sendData(response, pdfBytes ,"user_list.xls");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendData(HttpServletResponse response, byte[] pdfBytes, String fileName) throws IOException {
        /*重置response*/
        response.reset();
        response.setContentType("text/html;charset=utf-8"); // 设置编码格式并通知浏览器接收utf-8
        if (fileName.endsWith(".xls")) {
            response.setContentType("application/vnd.ms-excel");
        }
        /*设置http头信息的内容,弹出下载框的关键*/
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        /*设置文件长度*/
        int fileLength = pdfBytes.length;
        response.setContentLength(fileLength);
        if (fileLength != 0) {
            /*创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            servletOS.write(pdfBytes);
            /*刷新输出流缓冲*/
            servletOS.flush();
            /*关闭输出流*/
            servletOS.close();
        }
    }

}
