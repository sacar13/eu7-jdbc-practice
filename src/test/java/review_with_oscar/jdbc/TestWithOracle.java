package review_with_oscar.jdbc;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestWithOracle {

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @BeforeMethod
    public void  connectToDB(){
        String dbUrl = "jdbc:oracle:thin:@18.212.211.128:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        String query = "select first_name,last_name,salary from employees";

        try {
            connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @AfterMethod
    public void  closeDB(){
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest() throws SQLException {
        while (resultSet.next()){
            System.out.println(resultSet.getObject(1)+"|"+resultSet.getObject(2)+"|"+resultSet.getObject(3));

        }
    }

    @Test
    public void verifyExample() throws SQLException {
        //get Steven King salary and verify it as 24000

        resultSet.next();
        int actualSalary = resultSet.getInt(3);
        int expectedSalary = 24000;

        System.out.println("actualSalary = " + actualSalary);
        System.out.println("expectedSalary = " + expectedSalary);

        Assert.assertEquals(actualSalary,expectedSalary);
    }

    @Test
    public void listOfMapExample(){
        Map<String,String> rowOneData = new HashMap<>(); // insertion order is not kept also it accept null value
        rowOneData.put("first_name","Steven");
        rowOneData.put("last_name","King");
        rowOneData.put("salary","24000");
        System.out.println("rowOneData = " + rowOneData);

        Map<String,String> rowTwoData = new HashMap<>(); // insertion order is not kept also it accept null value
        rowTwoData.put("first_name","Neena");
        rowTwoData.put("last_name","Kochhar");
        rowTwoData.put("salary","17000");
        System.out.println("rowTwoData = " + rowTwoData);

        List<Map<String,String>> listOfMap = new ArrayList<>();
        listOfMap.add(rowOneData);
        listOfMap.add(rowTwoData);

        //get Neena's salary
        System.out.println("listOfMap.get(1).get(\"salary\") = " + listOfMap.get(1).get("salary"));
    }

    @Test
    public void metaDataExample() throws SQLException {
        DatabaseMetaData dbMetaData = connection.getMetaData();

        System.out.println("dbMetaData.getDriverName() = " + dbMetaData.getDriverName());
        System.out.println("dbMetaData.getDatabaseProductName() = " + dbMetaData.getDatabaseProductName());
        System.out.println("dbMetaData.getDatabaseProductVersion() = " + dbMetaData.getDatabaseProductVersion());


        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int columnCount = rsMetaData.getColumnCount();
        String columnName = rsMetaData.getColumnName(1);
        System.out.println("columnCount= "+columnCount);
        System.out.println("columnName = " + columnName);
    }

    @Test
    public void dynamicMapMethod() throws SQLException {
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        List<Map<String,Object>> listOfQueryResult = new ArrayList<>();


        //row : resultSet.next();
        //columnCount = rsMetaData.getColumnCount();
        int columnCount = rsMetaData.getColumnCount();
        // columnName : rsMetaData.getColumnName(); represented by key
        //how to read data from the cell : resultSet.getObject(); represented value

        while (resultSet.next()){
            Map<String,Object> rowMap = new HashMap<>();
            for (int i = 1; i < columnCount; i++) {
                 rowMap.put(rsMetaData.getColumnName(i),resultSet.getObject(i));
            }
            listOfQueryResult.add(rowMap);
        }

        for (Map<String, Object> eachRow : listOfQueryResult) {
            System.out.println("eachRow = " + eachRow);
        }
    }

}
