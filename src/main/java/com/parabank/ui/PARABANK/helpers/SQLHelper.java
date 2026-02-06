package com.parabank.ui.PARABANK.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLHelper {
	
	Connection conn= null;
	public record tableCellDetails(int index, String type, Object value){}
	public  void getConnection(String dbDriverName, String dbPath)
	{
		String url = "jdbc:"+dbDriverName+":"+dbPath;
		try
		{
			this.conn = DriverManager.getConnection(url);
		}
		catch(SQLException ex)
		{
			System.out.print("SQL Connection Error->"+ex.getMessage());
		}
		
	}
	public void closeConnection() throws SQLException
	{
		if(this.conn != null)
		{
			this.conn.close();
		}
	}
	
	public List<Map<String,Object>> executeSimpleSelectQuery(String query) throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		System.out.println(rs);
//		while(rs.next())
//		{
//			System.out.println(rs.getString("USERNAME"));
//			System.out.println(rs.getString("STATUS"));
//		}
		ResultSetMetaData meta = rs.getMetaData();
		int colNum = meta.getColumnCount();
		List<Map<String,Object>> sqlData = new ArrayList<>();
		while(rs.next())
		{
			Map<String,Object> row = new HashMap<>();
			for(int i=1;i<=colNum;i++)
			{
				row.put(meta.getColumnName(i), rs.getObject(i));
			}
			sqlData.add(row);
		}
		System.out.println(sqlData);
		return sqlData;
	}
	
	public void executeDMLQueries(String query) throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		int rows = stmt.executeUpdate(query);
		System.out.println(rows);
	}
	public void executeInserQueries(String query,Map<String,List<tableCellDetails>> data) throws SQLException
	{
		PreparedStatement stmt = this.conn.prepareStatement(query);
		for(String table:data.keySet())
		{
			System.out.println(table);
			List<tableCellDetails> colData = data.get(table);
			for(tableCellDetails col:colData)
			{
				if(col.type().equals("int"))
				{
					stmt.setInt(col.index(),(int) col.value());
				}
				else if(col.type().equals("str"))
				{
					stmt.setString(col.index(),(String) col.value());
				}
				else
				{
					System.out.println("Invalid data type");
				}
			}
		}
		
		stmt.executeUpdate();
	}
	
	
	public static void main(String[] args) throws SQLException
	{
		SQLHelper sqlObj = new SQLHelper();
		String dbDriverNmae= "sqlite";
		String dbPath = System.getProperty("user.dir")+"/src/test/java/resources/DB/parabank.db";
		sqlObj.getConnection(dbDriverNmae, dbPath);
		//Delete row
//		String deleteRowQuery = "DELETE from USER_DETAILS where USERNAME=\"N\";";
//		sqlObj.executeDMLQueries(deleteRowQuery);
		//Drop table;
//		String deleteQuery = "DROP TABLE USER_DETAILS;";
//		sqlObj.executeDMLQueries(deleteQuery);
//		//CREATE TABLE QUERY
//		String createTable = "CREATE TABLE USER_DETAILS (\r\n"
//				+ "  ID INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
//				+ "  USERNAME VARCHER(10),\r\n"
//				+ "STATUS VARCHAR(1)\r\n"
//				+ "\r\n"
//				+ "\r\n"
//				+ ");";
//		sqlObj.executeDMLQueries(createTable);
//		//insert values
//		String insertQuery = "INSERT INTO USER_DETAILS (USERNAME,STATUS) VALUES \r\n"
//				+ "(?,?);";
//		List<tableCellDetails> tableData = new ArrayList<>();
//		tableData.add(new tableCellDetails(1,"str","User2"));
//		tableData.add(new tableCellDetails(2,"str","N"));
//		Map<String,List<tableCellDetails>> data = new LinkedHashMap<>();
//		data.put("USER_DETAILS",tableData );
//		
//		sqlObj.executeInserQueries(insertQuery, data);
		String selectQuery = "SELECT * from USER_DETAILS;";
		List<Map<String,Object>> userDetails = sqlObj.executeSimpleSelectQuery(selectQuery);
		System.out.println(userDetails);
		
	}

}
