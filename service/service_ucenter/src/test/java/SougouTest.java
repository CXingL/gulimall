import com.atguigu.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Author liuxing
 * @Date 2020/9/7 15:18
 * @Version 1.0
 */
public class SougouTest {


//    https://yapi.rot-system.net/mock/11/api/backstage/parent/register
//    {
//        "name": "deseru",
//            "kana": "esse aliqua incidid",
//            "email": "occaecat elit proident",
//            "password": "quis ut"
//    }
    @Test
    public void testCreateParent() throws Exception {
        String url = "https://api-stg.rot-system.net/api/backstage/parent/register";
        Gson gson = new Gson();
        for (int i = 1; i < 150; i++) {
            HashMap<String, String> parent = new HashMap<>();
            parent.put("name", "p" + i);
            parent.put("kana", "pf" + i);
            parent.put("email", "pf" + i + "@qq.com");
            parent.put("password", "qwer1234");

            String json = gson.toJson(parent);
            String s = HttpClientUtils.post(url, json, "application/json", "UTF-8", 5000, 5000);

            System.out.println(s);
        }
    }

    // /api/backstage/teacher/register
    @Test
    public void testRegisterTeacher() throws Exception {
        String url = "https://api-stg.rot-system.net/api/backstage/teacher/register";
        Gson gson = new Gson();
        for (int i = 1; i < 151; i++) {
            HashMap<String, String> parent = new HashMap<>();
            parent.put("name", "t" + i);
            parent.put("kana", "tf" + i);
            parent.put("email", "tf" + i + "@qq.com");
            parent.put("password", "qwer1234");

            String json = gson.toJson(parent);
            String s = HttpClientUtils.post(url, json, "application/json", "UTF-8", 5000, 5000);

            System.out.println(s);
        }
    }
}
