import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.service.EduSubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    @Autowired
    EduSubjectService eduSubjectService;

    @Test
    public void read(){
        String fileName = "/Users/annesui/Documents/simple.xlsx";
        EasyExcel.read(fileName, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
    }

    @Test
    public void write(){
        String fileName = "/Users/annesui/Documents/simple.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        List<SubjectData> data = new ArrayList<>();
        data.add(new SubjectData("1","2"));
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, SubjectData.class).sheet("模板").doWrite(data);

    }
}
