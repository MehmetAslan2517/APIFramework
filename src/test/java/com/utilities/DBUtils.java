package com.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    /*  in order to create any database connection
        it gets dbUrl, dbUsername and dbPassword as arguments
        and it returns with connection to DB
    */
    public static void createConnection(String dbUrl,String dbUsername,String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("CONNECTION SUCCESSFUL");
        } catch (SQLException e) {
            System.out.println("Connection has failed!!! "+ e.getMessage());;
        }
    }

    public static void createConnection() {
        String dbUrl = "jdbc:oracle:thin:@54.144.125.207:1521:XE";
        String dbUsername = "hr";
        String dbPassword = "hr";
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
    in order to run any query we use executeQuery method:
      - it accepts valid query as an argument and it allows us to get and use resultSet objects-methods
      - resultSet object contain the result just in cases needed outside the class
     */
    public static void executeQuery(String query) {
        try {                             //allow us to navigate up and down in query result, read only, don’t update the results
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING RESULTSET!!! "+e.getMessage());;
        }
    }
  /*  private static ResultSet executeQuery(String query) {
        try {                             //allow us to navigate up and down in query result, read only, don’t update the results
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING RESULTSET!!! "+e.getMessage());;
        }
        return resultSet;
    }*/

    /*
    If we don't use if(resultSet != null) condition we will get an error from compiler
    because if we don't have any connection compiler does not find any connection, statement and also resultSet object
     */

    public static void destroy() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the row count of the resultSet
     * @return the row number of the resulSet given
     */
    public static int getRowCount() {
        int rowCount=0;
        try{
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        }catch (SQLException e){
            System.out.println("ERROR WHILE GETTING ROW COUNT "+e.getMessage());
        }

        return rowCount;
    }

    /**
     * a method to get column count of the current resultSet
     * @return the column number of the rsmd given
     */

    public static int getColumnCount(){
        int columnCount=0;
        try{
            ResultSetMetaData rsmd = resultSet.getMetaData();
            columnCount = rsmd.getColumnCount();
        }catch (SQLException e){
            System.out.println("ERROR WHILE COUNTING THE COLUMNS  "+e.getMessage());
        }

        return columnCount;
    }

    /**
     * a method that return all column names as List
     * @param query
     * @return List of columns returned in result set
     */
    public static List<String> getColumnNames(String query) {
        executeQuery(query);
        List<String> columns = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN NAMES  "+e.getMessage());
        }
        return columns;
    }





    /**
     * a method that returns all row data and metadata (column names) as a List of Lists
     * @param query
     * @return returns query result in a list of lists where outer list represents
     *         collection of rows and inner lists represent a single row
     */

    public static List<List<Object>> getQueryResultList(String query) {
        executeQuery(query);
        List<List<Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
            }
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING QUERY RESULT LIST  "+e.getMessage());
        }
        return rowList;
    }

    //27 nci videoda son method ile aynı ama buna bir argument daha eklenmeli gibi
    //ekledim rownum 1 diyince 2. satır gözüküyor videodaki daha iyi geldi bana
    //ikisinden de -1 çıkarınca doğru sonuç veriyor
    /**
     * a method to get one specific row data as a List
     * @param query
     * @return returns a list of Strings which represent a row of data.
     */
    public static List<Object> getRowList(String query, int rowNum) {
        rowNum=rowNum-1;
        return getQueryResultList(query).get(rowNum);
    }


    /**
     * a method to get cell (one specific row and specific column) info as an object
     * @param query
     * @return returns a single cell value. If the results in multiple rows and/or
     *         columns of data, only first column of the first row will be returned.
     *         The rest of the data will be ignored
     */
    public static Object getCellValue(String query, int rowNum, int columnIndex) {
        rowNum=rowNum-1;
        columnIndex=columnIndex-1;
        return getQueryResultList(query).get(rowNum).get(columnIndex);
    }

    /*
    a method to get cell (one specific row and specific column) info as a String with column name
     */
    public static String getCellData(String query, int rowNum, String columnName){
        executeQuery(query);
        String  result ="";
        try{
            resultSet.absolute(rowNum);
            result = resultSet.getString(columnName);
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        }catch (SQLException e){
            System.out.println("ERROR WHILE GETTING CELL DATA  "+e.getMessage());
        }
        return result;
    }

    /*
    a method to get cell (one specific row and specific column) info as a String with column index
     */

    public static String getCellData(String query, int rowNum, int columnIndex){
        executeQuery(query);
        String  result ="";
        try{
            resultSet.absolute(rowNum);
            result = resultSet.getString(columnIndex);
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        }catch (SQLException e){
            System.out.println("ERROR WHILE GETTING CELL DATA  "+e.getMessage());
        }
        return result;
    }


    /**
     * a method  to get column data with column name
     * @param query
     * @param column
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, String columnName) {
        executeQuery(query);
        List<Object> columnData = new ArrayList<>();
        try {
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
            while (resultSet.next()) {
                columnData.add(resultSet.getObject(columnName));
            }
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN DATA  "+e.getMessage());
        }
        return columnData;
    }


    /**
     * a method  to get column data with column index
     * @param query
     * @param column
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, int columnIndex) {
        executeQuery(query);
        List<Object> columnData = new ArrayList<>();
        try {
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
            while (resultSet.next()) {
                columnData.add(resultSet.getObject(columnIndex));
            }
            resultSet.beforeFirst(); //this line for that before we try to use another method it allows us to get start from the scratch
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN DATA  "+e.getMessage());
        }
        return columnData;
    }

    /*
    a method that display all data from the given table
    */
    public static void displayAllData(){

        try {
            resultSet.beforeFirst();
            while (resultSet.next()){
                for (int colNum = 1; colNum<=getColumnCount(); colNum++){
                    System.out.print(resultSet.getString(colNum)+"\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("ERROR WHILE DISPLAYING ALL DATA  "+e.getMessage());
        }

    }






    /**
     *
     * @param query
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }


    /**
     *
     * @param query
     * @return returns a map which represent a row of data where key is the column
     *         name. If the query results in multiple rows and/or columns of data,
     *         only first row will be returned. The rest of the data will be ignored
     */
    public static Map<String, Object> getRowMap(String query) {
        return getQueryResultMap(query).get(0);
    }



}
