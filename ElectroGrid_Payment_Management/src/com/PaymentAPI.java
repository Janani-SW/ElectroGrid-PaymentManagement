package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	
private static final long serialVersionUID = 1L;

	Payment payObj = new Payment();
    
    public PaymentAPI() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = payObj.insertPay(request.getParameter("receiptNo"),
										request.getParameter("accno"),
										request.getParameter("amount"),
										request.getParameter("year"),
										request.getParameter("month"),
										request.getParameter("cvv"));
										
		response.getWriter().write(output);
	}
	
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try 
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? 
					
			scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			for(String param : params)
			{
				String[] p =param.split("=");
				map.put(p[0], p[1]);
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Map paras = getParasMap(request);
		
		String output = payObj.updatePayment(paras.get("hidePayIdSave").toString(), 
											paras.get("receiptNo").toString(), 
											paras.get("accno").toString(), 
											paras.get("amount").toString(), 
											paras.get("year").toString(), 
											paras.get("month").toString(), 
											paras.get("cvv").toString());
		
		response.getWriter().write(output);
	}
	

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		String output = payObj.deletePay(paras.get("payId").toString());
		
		response.getWriter().write(output);
	}

}
