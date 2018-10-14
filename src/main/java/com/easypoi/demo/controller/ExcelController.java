package com.easypoi.demo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import com.easypoi.demo.entity.Member;
import com.easypoi.demo.entity.MemberFailed;
import com.easypoi.demo.handler.MyVerifyHandler;
import com.easypoi.demo.service.ExcelService;
import com.easypoi.demo.service.MyExcelImportService;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    public static final String[] imgs = new String[]{
//            "http://192.168.25.133/group1/M00/00/01/wKgZhVvC78SAfpWaAAAsAp7EzlE763.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=108228188,2741176027&fm=27&gp=0.jpg",
            "http://192.168.25.133/group1/M00/00/01/wKgZhVvC8OCAJRuiAABeY6xwxBQ960.jpg",
            "http://192.168.25.133/group1/M00/00/01/wKgZhVvC8PGAIo8LAABP3bR1ZnU861.jpg",
            "http://192.168.25.133/group1/M00/00/01/wKgZhVvC8P6AFx5KAAA22wMuH2A112.jpg"
    };

    /**
     * 上传文件到fastdfs
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile file) throws IOException {
        String path = excelService.uploadFile(file);
        return path;
    }

    /**
     * 下载excel
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Member> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new Member((long)i, "张三" + i, i % 2 + "", new Date(), 20 + i + "",
                    "15219873928", "123456" + i, imgs[i]));
        }

        //ExportParams params = new ExportParams();
        //导出的时候指明标题列名和sheet名字
        ExportParams params = new ExportParams("用户数据", "用户数据");


        Workbook workbook = ExcelExportUtil.exportExcel(params, Member.class, list);

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表","UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        workbook.write(response.getOutputStream());
    }

    /**
     * 简单导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/easyImport")
    public void easyImport(MultipartFile file) throws Exception {
        List<Member> list = ExcelImportUtil.importExcel(file.getInputStream(), Member.class, new ImportParams());
        for (Member member : list) {
            System.out.println(member);
        }

//Member{id=0, name='张三0', birthday=Sun Oct 14 16:22:16 CST 2018, sex='0', age='20', phone='15219873928', loginName='1234560', pic='/excel/upload/img\Member\pic3571587826.JPG'}
//Member{id=1, name='张三1', birthday=Sun Oct 14 16:22:16 CST 2018, sex='1', age='21', phone='15219873928', loginName='1234561', pic='/excel/upload/img\Member\pic20436614652.JPG'}
//Member{id=2, name='张三2', birthday=Sun Oct 14 16:22:16 CST 2018, sex='0', age='22', phone='15219873928', loginName='1234562', pic='/excel/upload/img\Member\pic88937536202.JPG'}
//Member{id=3, name='张三3', birthday=Sun Oct 14 16:22:16 CST 2018, sex='1', age='23', phone='15219873928', loginName='1234563', pic='/excel/upload/img\Member\pic57721772929.JPG'}
    }

    /**
     * 简单导入并将图片保存到fastdfs
     * @param file
     * @throws Exception
     */
    @PostMapping("/easyImportWithPic2FastDfs")
    public void easyImportWithPic2FastDfs(MultipartFile file) throws Exception {
        List<Member> list = new MyExcelImportService(excelService).importExcelByIs(file.getInputStream(),
                Member.class, new ImportParams(), false).getList();
        for (Member member : list) {
            System.out.println(member);
        }
//Member{id=0, name='张三0', birthday=Sun Oct 14 16:22:16 CST 2018, sex='0', age='20', phone='15219873928', loginName='1234560',
// pic='http://192.168.25.133/group1/M00/00/01/wKgZhVvDC0iAF1ARAAAr8kxqIkw952.jpg', errorMsg='null'}
//Member{id=1, name='张三1', birthday=Sun Oct 14 16:22:16 CST 2018, sex='1', age='2122', phone='15219873928', loginName='1234561',
// pic='http://192.168.25.133/group1/M00/00/01/wKgZhVvDC0iAHQJJAABeNV1V8aQ713.jpg', errorMsg='null'}
//Member{id=2, name='张三2', birthday=Sun Oct 14 16:22:16 CST 2018, sex='男0', age='22', phone='15219873928', loginName='1234562',
// pic='http://192.168.25.133/group1/M00/00/01/wKgZhVvDC0iAES16AABPz7W67UE613.jpg', errorMsg='null'}
//Member{id=3, name='张三3', birthday=Sun Oct 14 16:22:16 CST 2018, sex='1', age='23', phone='152198739281', loginName='1234563',
// pic='http://192.168.25.133/group1/M00/00/01/wKgZhVvDC0iAc6xrAAA2z_wZM1I312.jpg', errorMsg='null'}
    }

    /**
     * 复杂导入(导入时实现数据校验)
     * @param file
     * @throws Exception
     */
    @PostMapping("/complexImport")
    public void complexImport(MultipartFile file, HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();
        //在有标题列的时候，需要指明标题列在1，默认是0
        params.setTitleRows(1);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new MyVerifyHandler());
        ExcelImportResult importResult = new ExcelImportService().importExcelByIs(file.getInputStream(),
                Member.class, params, true);
        //list里面包含的是所有校验成功的数据
        List<Member> list = importResult.getList();
        if (list != null) {
            for (Member member : list) {
                System.out.println(member);
            }
        }
        System.out.println("-----------------------");

        //failWorkbook和failList里面包含的是所有校验失败的数据
        //我们可以直接将他们返回给前端
        Workbook failWorkbook = importResult.getFailWorkbook();
        List<Member> failList = importResult.getFailList();

        List<MemberFailed> faileds = MemberFailed.members2MemberFaileds(failList);

//        response.setHeader("content-Type", "application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表","UTF-8") + ".xls");
//        response.setCharacterEncoding("UTF-8");
//        failWorkbook.write(response.getOutputStream());

        ExportParams exportParams = new ExportParams();
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, MemberFailed.class, faileds);

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表","UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        workbook.write(response.getOutputStream());
    }

    /**
     * 复杂导入(导入时实现数据校验,返回给ajax请求)
     * 因为ajax请求我们不能直接把错误数据通过response返回给前端。
     *
     * 我们可以把错误数据保存给fastdfs。然后返回给前端错误信息在fastdfs上面的url
     *
     * 前端接收到数据后通过location.href = url;去获取这个错误信息的excel。
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/complexImportResp2Ajax")
    public String complexImportResp2Ajax(MultipartFile file, HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();
        //在有标题列的时候，需要指明标题列在1，默认是0
        params.setTitleRows(1);
        params.setNeedVerfiy(true);
        params.setVerifyHandler(new MyVerifyHandler());
        ExcelImportResult importResult = new ExcelImportService().importExcelByIs(file.getInputStream(),
                Member.class, params, true);
        //list里面包含的是所有校验成功的数据
        List<Member> list = importResult.getList();
        if (list != null) {
            for (Member member : list) {
                System.out.println(member);
            }
        }
        System.out.println("-----------------------");

        //failWorkbook和failList里面包含的是所有校验失败的数据
        //我们可以直接将他们返回给前端
//        Workbook failWorkbook = importResult.getFailWorkbook();
        List<Member> failList = importResult.getFailList();

        List<MemberFailed> faileds = MemberFailed.members2MemberFaileds(failList);

        ExportParams exportParams = new ExportParams();
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, MemberFailed.class, faileds);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        MockMultipartFile file2 =
                new MockMultipartFile("错误信息", "test.xls",
                        ContentType.WILDCARD.toString(), bos.toByteArray());
        String path = excelService.uploadFile(file2);
        return path;

    }
}
