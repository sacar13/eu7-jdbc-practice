package jdbctests;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:oracle:thin:@18.212.211.128:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //Create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //creating statement object
        Statement statement = connection.createStatement();

        //run query ang get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from regions");

//        //move pointer to first row
//        resultSet.next();
//        //get the info with column name
//        System.out.println(resultSet.getString("region_name"));
//        //getting info with column index (start from 1)
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt("region_id")+" - "+resultSet.getString("region_name"));
//
//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt("region_id")+" - "+resultSet.getString("region_name"));

        //while loop(next() method returns boolean)
        while(resultSet.next()){
            System.out.println(resultSet.getInt("region_id")+" - "+resultSet.getString("region_name"));
        }

        resultSet = statement.executeQuery("Select first_name,last_name,salary from employees");

        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+" - "+resultSet.getString(2)+" - "+resultSet.getInt(3));
        }

        resultSet = statement.executeQuery("Select * from departments");

        while(resultSet.next()){
            System.out.println(resultSet.getInt(1)+" - "+resultSet.getString(2)+" - "+resultSet.getInt(3)+" - "+resultSet.getInt(4));
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();



    }
}
