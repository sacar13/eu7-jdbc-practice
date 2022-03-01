package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class JdbcExample {

    String dbUrl = "jdbc:oracle:thin:@18.212.211.128:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void countNavigate() throws SQLException {
        //Create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //creating statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //run query ang get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //how to find many rows we have for the query
        //first go to last row then get the row with getRow()
        resultSet.last();
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);

        //in order print the information we have to take the cursor all the way uo with beforeFirst() method
        resultSet.beforeFirst();
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }




        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void metaDataExample() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("Select * from employees");

        //get the database related data inside the database object
        DatabaseMetaData dbMetaData = connection.getMetaData();

        System.out.println("User = "+dbMetaData.getUserName());
        System.out.println("Database Product Name = "+dbMetaData.getDatabaseProductName());
        System.out.println("Database Product Version = "+dbMetaData.getDatabaseProductVersion());
        System.out.println("Driver Version = "+dbMetaData.getDriverVersion());


        //get the resultSet object metadata crucial very important
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        //how many columns we have?
        int columnCount = rsMetaData.getColumnCount();
        System.out.println("columnCount = " + columnCount);

        //how to get column names
        System.out.println(rsMetaData.getColumnName(1));
        System.out.println(rsMetaData.getColumnName(2));

        //print all the column names dynamically
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(rsMetaData.getColumnName(i));
        }



        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }


}
