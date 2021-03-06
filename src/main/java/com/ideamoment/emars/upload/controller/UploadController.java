package com.ideamoment.emars.upload.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.AliyunOSSConstants;
import com.ideamoment.emars.upload.dao.OssFileMapper;
import com.ideamoment.emars.utils.JsonData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

//    private static final String FILE_HOST = "http://emars-dev.oss-cn-hangzhou.aliyuncs.com/";

    private static final String FILE_HOST = "http://emars-prod.oss-cn-beijing.aliyuncs.com/";

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private OssFileMapper ossFileMapper;

    @RequestMapping(value = "/upload", method = { RequestMethod.POST })
    @ResponseBody
    public JsonData<List<UploadFileMeta>> upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            List<UploadFileMeta> files = new ArrayList<UploadFileMeta>();
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
                            UploadFileMeta meta = new UploadFileMeta();
                            meta.setName(fileName);

                            File newFile = new File(fileName);
                            FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
                            outStream.write(file.getBytes());
                            outStream.close(); // 关闭文件输出流
                            file.transferTo(newFile);
                            // 上传到阿里云
                            String path = upload(newFile);
                            meta.setPath(path);
                            newFile.delete();
                            files.add(meta);
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

    @RequestMapping(value = "/importAllFiles", method = { RequestMethod.GET })
    @ResponseBody
    @Transactional
    public JsonData importAllFiles() {
        String rootPath = "D:/emars_files";

        Collection<File> files = FileUtils.listFilesAndDirs(new File(rootPath), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        int c = 0;
        for(File file : files) {
            if(file.isFile() && !file.getName().equals(".DS_Store")) {
                System.out.println(file.getAbsolutePath());
                String fileName = file.getName();
                String filePath = file.getAbsolutePath();
                filePath = filePath.replace(rootPath, "");
//                if(c == 0) {
                    String ossPath = upload(file);
                    System.out.println(ossPath);
                    ossFileMapper.insertOssFile(filePath, fileName, ossPath);
//                }
                c++;
            }
        }
        System.out.println("导入完成，共 " + c + " 个文件");
        return JsonData.SUCCESS;
    }

    public String upload(File file) {
        if (file == null) {
            return null;
        }
        try {
            UUID uuid  =  UUID.randomUUID();
            String uuidKey = UUID.randomUUID().toString();

            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String dateStr = df.format(new Date());

            // 创建文件路径
            String fileUrl = dateStr + "/" + uuidKey;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(AliyunOSSConstants.BUCKET_NAME, fileUrl, file));
            if (null != result) {
                return FILE_HOST + fileUrl;
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        }
        return null;
    }

    class UploadFileMeta {
        private String name;
        private String path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
