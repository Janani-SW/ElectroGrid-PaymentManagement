package com;

import java.sql.*;

public class Payment {
	
	//Connect to the Database
		public Connection connect()
		{
		Connection con = null;

		try {
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Provide the correct details: DBServer/DBName, username, password
		con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid","root", "");
		//For testing
		System.out.println("Successfully connected to the database");
		}
		catch(Exception e){
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		return con;
		}
		
		
		//insert
		public String insertPay(String receiptNo, String accno, String amount, String year, String month, String cvv)
		{ 
			String output = ""; 
		try
		 { 
			Connection con = connect(); 
		 if (con == null) 
		 { 
			 return "Error while connecting to the database"; 
		 } 
		 
		 	String query = " insert into payment (`payId`,`receiptNo`,`accno`,`amount`,`year`, `month`, `cvv`) values (?, ?, ?, ?, ?, ?, ?)"; 
		 	PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 	preparedStmt.setInt(1, 0); 
		 	preparedStmt.setString(2, receiptNo); 
		 	preparedStmt.setString(3, accno); 
		 	preparedStmt.setDouble(4, Double.parseDouble(amount)); 
		 	preparedStmt.setString(5, year);
		 	preparedStmt.setString(6, month);
		 	preparedStmt.setString(7, cvv);
		 	//execute the statement
		 	preparedStmt.execute(); 
		 	con.close(); 
		 	
		 	String newPayment = readPay();
		 	output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 
		 } 
		catch (Exception e) 
		 { 
			output = "{\"ststus\":\"error\", \"data\":\"Error while inserting.\"}"; 
			System.err.println(e.getMessage()); 
		 } 
			return output; 
		}
		
		
		//read
		public String readPay() 
		{
			String output = "";
			
			try 
			{
				Connection con = connect();
				if(con == null) 
				{
					return "Error while connecting to the database while reading";
				}
				
				output="<table border='1'><tr><th>Bill No</th><th>Account No</th><th>Amount(Rs:)</th><th>Expiration Year</th><th>Expiration Month</th><th>CVV</th><th>update</th><th>Remove</th></tr>";
				
				String query = "SELECT * FROM payment";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				while (rs.next()) {
					String payId = Integer.toString(rs.getInt("payId"));
					String receiptNo = rs.getString("receiptNo");
					String accno = rs.getString("accno");
					String amount = Double.toString(rs.getDouble("amount"));
					String year = rs.getString("year");
					String month = rs.getString("month");
					String cvv = rs.getString("cvv");
					
					output += "<tr><td><input id='hidePayIdEdit' name='hidePayIdEdit' type='hidden' value='" + payId +"'>" + receiptNo + "</td>";
					output += "<td>" + accno + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + year + "</td>";
					output += "<td>" + month + "</td>";
					output += "<td>" + cvv + "</td>";
					
					output += "<td><input name='btnEdit' type='button' value='Edit' "
							+ "class='btnEdit btn btn-secondary'></td>"
							+ "<td><input name='btnDelete' type='button' value='Delete'"
							+ "class='btnDelete btn btn-danger' data-payid='" + payId + "'></td></tr>";
				}
				
				con.close();
				
				
				output+= "</table>";
			} 
			catch(Exception e) 
			{
				output = "Error while reading";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		
		//update
		public String updatePayment (String Id, String receiptNo, String accno, String amount, String year, String month, String cvv)
		{
			String output = "";
			
				try 
				{
					Connection con = connect();
					if(con == null)
					{
						return "Error while connecting to the database for updating";
					}
					
					String query = "UPDATE payment SET receiptNo=?, accno=?, amount=?, year=?, month=?, cvv=? WHERE payId=?";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setString(1, receiptNo);
					preparedStmt.setString(2, accno);
					preparedStmt.setDouble(3, Double.parseDouble(amount));
					preparedStmt.setString(4, year);
					preparedStmt.setString(5, month);
					preparedStmt.setString(6, cvv);
					preparedStmt.setInt(7, Integer.parseInt(Id));
					
					preparedStmt.execute();
					con.close();
					
					String newPayment = readPay();
					output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
					
				}
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\":\"error while updating.\"}";
					System.err.print(e.getMessage());
				}
			
			return output;
		}
		
		
		//delete
		public String deletePay (String payId)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				if(con == null)
				{
					return "Error while connecting to the database for deleting";
				}
				String sql = "DELETE FROM payment WHERE payId=?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(payId));
				pstmt.execute();
				
				con.close();
				
				String newPayment = readPay();
			 	output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 
				
			}
			catch(Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"error while deleting.\"}";
				System.err.print(e.getMessage());
			}
			
			return output;
		}


}
