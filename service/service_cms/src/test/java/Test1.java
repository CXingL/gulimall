import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author liuxing
 * @Date 2020/9/3 09:27
 * @Version 1.0
 */
public class Test1 {

    @Test
    public void listTest() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        list1.add(1);
        list1.add(2);
        list1.add(3);

        list2.addAll(list1);

        list1.clear();

        System.out.println(list1);
        System.out.println(list2);
    }
}
