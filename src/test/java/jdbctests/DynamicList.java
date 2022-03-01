package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicList {

    String dbUrl = "jdbc:oracle:thin:@18.212.211.128:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void metaDataExample2() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from countries");


        //get the resultSet object metadata crucial very important
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //in order make it dynamic ı need number of columns
        int colCount = rsMetaData.getColumnCount();

        //looping through each row inside while loop ı need one empty map
        while (resultSet.next()){
            Map<String,Object> row = new HashMap<>();

            //there is gonna some code to put info with the help of loop
            for (int i = 1; i <= colCount; i++) {
                 row.put(rsMetaData.getColumnName(i),resultSet.getObject(i));
            }
            //add your map to your list
            queryData.add(row);
        }

        //print the result
        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }


}
