package cn.baizhi.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.entity.User;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class EasyPoi {
    public static void exportData(List<User> list){

        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息表"), User.class, list);
            workbook.write(new FileOutputStream(new File("D:/easypoi-user.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
