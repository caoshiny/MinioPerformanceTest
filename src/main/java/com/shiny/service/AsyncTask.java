package com.shiny.service;

import com.shiny.response.FileUploadResponse;
import com.shiny.utils.MinioUtil;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
@Slf4j
public class AsyncTask {
    @Autowired
    private MinioUtil minioUtil;

    @Async
    public void task1() throws Exception {
        FileUploadResponse response = null;
        String bucketName = "test";
        /**
         * 1个线程执行10次上传任务
         */
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 2; i++){
            // File 转 MultipartFile
            File file = new File("C:\\Users\\USER\\Desktop\\20201202125029_20201202125415.mp4");
            //File file = new File("C:\\Users\\USER\\Desktop\\1.jpg");
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "ContentType.APPLICATION_OCTET_STREAM.toString()", fileInputStream);

            response = minioUtil.uploadFile(multipartFile, bucketName);
        }
    }

    @Async
    public ResponseEntity<byte[]> downloadTask() throws Exception{
        ResponseEntity<byte[]> responseEntity = null;
        InputStream stream = null;
        ByteArrayOutputStream output = null;
        String objectName = "test_1614300647673_2021-02-26_413.jpg";
        //String objectName = "test_1614302632647_2021-02-26_159.mp4";
        try{
            // 获取"myobject"的输入流。
            stream = minioUtil.getObject("test", objectName);
            if (stream == null) {
                System.out.println("文件不存在");
            }
            //用于转换byte
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();

            // 设置header
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Accept-Ranges", "bytes");
            httpHeaders.add("Content-Length", bytes.length + "");
            // objectName = new String(objectName.getBytes("UTF-8"), "ISO8859-1");
            // 把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            httpHeaders.add("Content-disposition", "attachment; filename=" + objectName);
            httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
            // httpHeaders.add("Content-Type", "image/jpeg");
            responseEntity = new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.CREATED);

        } catch (MinioException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (output != null) {
                output.close();
            }
        }
        return responseEntity;
    }
}
