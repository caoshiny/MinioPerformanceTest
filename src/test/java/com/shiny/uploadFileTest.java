package com.shiny;

import com.shiny.service.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class uploadFileTest {
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testUploadFile() throws Exception {
        // 设置线程数量i
        for(int i = 0; i < 1; i++){
            asyncTask.task1();
        }
        Thread.sleep(10000);
        System.out.println("*****************主线程执行完毕********************");
    }

    @Test
    public void testDownloadFile() throws Exception{
        // 设置线程数量i
        for(int i = 0; i < 50; i++){
            asyncTask.downloadTask();
        }
        Thread.sleep(100000);
        System.out.println("*****************主线程执行完毕********************");
    }
}
