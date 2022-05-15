package com.hunny.reijiproject.controller;

import com.hunny.reijiproject.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @author QiuZhengJie
 * @date 2022/5/11
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${Upload.path}")
    private String basepath;
@PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws Exception
    {
        //判断当前目录是否存在
        File dir=new File(basepath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        System.out.println("上传文件的tostring:"+file.toString());
        String orgfileName=file.getOriginalFilename();
      String suffix=  orgfileName.substring(orgfileName.lastIndexOf("."));
        String uuid= UUID.randomUUID().toString()+suffix;
        file.transferTo(new File(basepath+uuid));
         return R.success(uuid);  //返回文件名称
    }
    /**
       文件下载
     * @param name
     * @param response
     * @return void
     * @author QiuZhengJie

     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response)throws Exception
    {
        FileInputStream fileInputStream=new FileInputStream(new File(basepath+name));
       byte []bytes=new byte[9024];
       int len=0;
       response.setContentType("imag/jpeg");
                ServletOutputStream servletOutputStream= response.getOutputStream();
       int count=0;
       while ((len=fileInputStream.read(bytes))!=-1)
       {
           servletOutputStream.write(bytes);
           servletOutputStream.flush();
           count++;
       }
       log.info("输入流总共读取了{}遍才把文件读取完成",count);
       fileInputStream.close();
       servletOutputStream.close();
    }
}
