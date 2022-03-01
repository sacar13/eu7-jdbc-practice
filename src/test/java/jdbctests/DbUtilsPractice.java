package jdbctests;

import org.testng.annotations.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

public class DbUtilsPractice {

    @Test
    public void test1(){
        //create connections
        DBUtils.createConnection();

        //using method to get results as a list of maps
        List<Map<String, Object>> queryResult = DBUtils.getQueryResultMap("select * from employees");


        //printing the result
        for (Map<String, Object> map : queryResult) {
            System.out.println(map.toString());
        }

        //close connections
        DBUtils.destroy();
    }

    @Test
    public  void  test2(){
        DBUtils.createConnection();

        Map<String, Object> rowMap = DBUtils.getRowMap("select first_name,last_name,salary,job_id from employees\n" +
                "where employee_id = 100");

        System.out.println(rowMap.toString());


        DBUtils.destroy();
    }
}
