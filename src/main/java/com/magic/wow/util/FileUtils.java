package com.magic.wow.util;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * The type File util.
 *
 * @author Phil
 */
public class FileUtils {
    private static final  Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * Upload boolean.
     *
     * @param multipartFile the multipart file
     * @param savePath      the save path
     * @param fileName      the file name
     * @return the boolean
     * @author Phil
     */
    public static boolean upload(MultipartFile multipartFile, String savePath, String fileName) {
        File file = new File(savePath, fileName);
        try {
            if (!(new File(savePath).isDirectory())) {
                new File(savePath).mkdirs();
            }
            multipartFile.transferTo(file);
            logger.info("Upload file success!" + fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true ，否则返回false
     * @author Phil
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.info("Delete file success!" + fileName);
                return true;
            } else {
                logger.error("Delete file error!" + fileName);
                return false;
            }
        } else {
            logger.warn("Delete file not found!" + fileName);
            return false;
        }
    }

    /**
     * Download.
     *
     * @param fileName the file name
     * @param request  the request
     * @param response the response
     * @author Phil
     */
    public static void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        File file;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        try {
            String type = request.getHeader("User-Agent").toLowerCase();
            file = new File(fileName);
            String name = file.getName();
            if (type.contains("msie") || type.contains("edge")) {//IE
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
            } else {//非IE
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("utf-8"), "ISO8859-1"));
            }
//            response.addHeader("Content-Length", "" + file.length()); //org.apache.catalina.connector.ClientAbortException: java.io.IOException 如果客户点击取消下载就会报IO异常
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[1024];
            int length;
            while ((length = bis.read(b)) > 0) {
                bos.write(b, 0, length);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
