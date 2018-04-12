package com.ideamoment.emars.upload.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UploadController {

    private static final String BUCKET_NAME = "emars-dev";
    private static final String FILE_HOST = "http://emars-dev.oss-cn-hangzhou.aliyuncs.com/";

    @Autowired
    private OSSClient ossClient;

    @RequestMapping(value = "/upload", method = { RequestMethod.POST })
    @ResponseBody
    public JsonData<List<String>> upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            List<String> files = new ArrayList<String>();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String fileName = file.getOriginalFilename();
                        // 如果名称不为空,说明该文件存在，否则说明该文件不存在
                        if (fileName.trim() != "") {
                            File newFile = new File(fileName);
                            FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
                            outStream.write(file.getBytes());
                            outStream.close(); // 关闭文件输出流
                            file.transferTo(newFile);
                            // 上传到阿里云
                            files.add(upload(newFile));
                            newFile.delete();
                        }
                    }
                }
            }
            return JsonData.success(files);
        }catch(IOException e) {
            e.printStackTrace();
            return JsonData.error(ErrorCode.UPLOAD_ERROR, ErrorCode.ERROR_MSG.get(ErrorCode.UPLOAD_ERROR));
        }

    }

    public String upload(File file) {
        if (file == null) {
            return null;
        }
        try {
            UUID uuid  =  UUID.randomUUID();
            String uuidKey = UUID.randomUUID().toString();

            DateFormat df = new SimpleDateFormat("yyyymmdd");
            String dateStr = df.format(new Date());

            // 创建文件路径
            String fileUrl = dateStr + "/" + uuidKey;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(BUCKET_NAME, fileUrl, file));
            if (null != result) {
                return FILE_HOST + fileUrl;
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }
        return null;
    }
}
