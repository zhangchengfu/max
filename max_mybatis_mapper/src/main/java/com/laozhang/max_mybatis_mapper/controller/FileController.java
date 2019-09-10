package com.laozhang.max_mybatis_mapper.controller;

import com.laozhang.max_mybatis_mapper.bean.ExportExcelUser;
import com.laozhang.max_mybatis_mapper.common.FileUtil;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 下载
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Object download(@RequestParam("id") String id, HttpServletRequest request,
                                    HttpServletResponse response) {
        InputStream in = null;
        Workbook workbook = null;
        try {
            FileUtil.wrapDownloadResponse(response, "导出用户.xls");
            in = this.getClass().getResourceAsStream("/static/users.xls");
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(in);
        }
        return "success";
    }

    /**
     * 导出模板
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public Object export(@RequestParam("id") String id, HttpServletRequest request,
                         HttpServletResponse response) {
        InputStream in = null;
        Workbook workbook = null;
        try {
            FileUtil.wrapDownloadResponse(response, "test.xls");
            in = this.getClass().getResourceAsStream("/static/test.xls");
            IOUtils.copy(in, response.getOutputStream());
            XLSTransformer transformer = new XLSTransformer();
            List<ExportExcelUser> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ExportExcelUser user = new ExportExcelUser("" + i, 20 + i, "1381234567" + i,"123" + i + "@qq.com");
                list.add(user);
            }
            ArrayList<List<ExportExcelUser>> objects = new ArrayList<>();
            objects.add(list);
            List<String> sheetNames = new ArrayList<>();
            sheetNames.add("Sheet1");
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("list", list);
            workbook = transformer.transformMultipleSheetsList(in, objects, sheetNames, "list",
                    new HashMap<>(), 1);
            /*workbook = transformer.transformXLS(in, para);*/
            workbook.write(response.getOutputStream());
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(in);
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
