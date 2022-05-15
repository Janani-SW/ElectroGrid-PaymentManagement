<%@ page import="com.Payment" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="Components/Payment.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<title>Payment Management</title>

</head>
<body>
	<div class="container"> <div class="row"> <div class = "col-6">
	
	<h1> Payment Management </h1>
	
	<form id="formPay" name="formPay" method="post" action="Payment.jsp">
		
		Receipt No :
		<input id="receiptNo" name="receiptNo" type="text" class="form-control form-control-sm">
		
		<br>Account No :
		<input id="accno" name="accno" type="text" class="form-control form-control-sm">
	
		<br>Amount (Rs) :
		<input id="amount" name="amount" type="text" class="form-control form-control-sm">
		
		<br>Expiration Year:
		<input id="year" name="year" type="text" class="form-control form-control-sm">
		
		<br>Expiration Month :
		<input id="month" name="month" type="text" class="form-control form-control-sm">
		
		<br>CVV :
		<input id="cvv" name="cvv" type="text" class="form-control form-control-sm">
	
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input id="hidePayIdSave" name="hidePayIdSave" type="hidden" value="">
	
	</form>
	
	<div id="alertSuccess" class="alert alert-success"> </div>
	<div id="alertError" class="alert alert-danger"> </div>
	
	<br>
	<div id="divPayGrid">
		<%
			Payment payObj = new Payment();
			out.print(payObj.readPay());
		%>
	</div>
	<br><br>
	
	</div> </div> </div>

</body>
</html>