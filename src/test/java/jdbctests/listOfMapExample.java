package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listOfMapExample {

    String dbUrl = "jdbc:oracle:thin:@18.212.211.128:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void metaDataExample() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("Select * from employees");


        //get the resultSet object metadata crucial very important
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();



        //creating map for 1. row info
        Map<String,Object> row1 = new HashMap<>();
        row1.put("first_name","Steven");
        row1.put("last_name","King");
        row1.put("salary",24000);
        row1.put("job_id","AD_PRES");
        System.out.println(row1.toString());

        //creating map for 2. row info
        Map<String,Object> row2 = new HashMap<>();
        row2.put("first_name","Neana");
        row2.put("last_name","Kochhar");
        row2.put("salary",17000);
        row2.put("job_id","AD_VP");
        System.out.println(row2.toString());
        //getting data with key value
        System.out.println(row2.get("salary"));

        //adding rows to my data
        queryData.add(row1);
        queryData.add(row2);

        //getting the steven lastName directly from the list
        System.out.println(queryData.get(0).get("last_name"));


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void metaDataExample2() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select first_name,last_name,salary,job_id from employees\n" +
                "where rownum<6");


        //get the resultSet object metadata crucial very important
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //move to first row
        resultSet.next();


        //creating map for 1. row info
        Map<String,Object> row1 = new HashMap<>();
        row1.put(rsMetaData.getColumnName(1),resultSet.getString(1));
        row1.put(rsMetaData.getColumnName(2),resultSet.getString(2));
        row1.put(rsMetaData.getColumnName(3),resultSet.getString(3));
        row1.put(rsMetaData.getColumnName(4),resultSet.getString(4));

        System.out.println(row1.toString());

        resultSet.next();

        //creating map for 2. row info
        Map<String,Object> row2 = new HashMap<>();
        row2.put(rsMetaData.getColumnName(1),resultSet.getString(1));
        row2.put(rsMetaData.getColumnName(2),resultSet.getString(2));
        row2.put(rsMetaData.getColumnName(3),resultSet.getString(3));
        row2.put(rsMetaData.getColumnName(4),resultSet.getString(4));

        System.out.println(row2.toString());
        //getting data with key value
        System.out.println(row2.get("salary"));

        //adding rows to my list
        queryData.add(row1);
        queryData.add(row2);

        //getting the steven lastName directly from the list
        System.out.println(queryData.get(0).get("last_name"));


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
