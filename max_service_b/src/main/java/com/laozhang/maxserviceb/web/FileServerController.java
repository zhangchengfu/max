package com.laozhang.maxserviceb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileServerController {

    private static final Logger log = LoggerFactory.getLogger(FileServerController.class);

    /**
     * 单个附件上传
     * @param file 附件
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object upload(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request) throws Exception {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 获取文件前缀名
            String preName = fileName.substring(0, fileName.lastIndexOf("."));
            log.info("文件的前缀名为：" + preName);
            // 新文件名
            String newName = UUID.randomUUID().toString();

            // 设置文件存储路径
            String path="/upload/" +new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            path += "/"+ newName + suffixName;
            log.info("文件上传路径为：" + path);
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }

            //获取item中的上传文件的输入流
            is = file.getInputStream();
            //创建一个文件输出流
            fos = new FileOutputStream(dest);
            //创建一个缓冲区
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int length = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((length = is.read(buffer))>0){
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录当中
                fos.write(buffer, 0, length);
            }
            //关闭输入流
            is.close();
            //关闭输出流
            fos.close();


            return new ResponseEntity<String>(path, HttpStatus.OK);
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IOException();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 单个附件下载
     * @param path 路径
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public Object downloadFile(@RequestParam(value = "path",required = true) String path,
                               HttpServletRequest request, HttpServletResponse response) {
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
