$(document).on("click", "#btnSave", function(event)
{
	$("alertSuccess").text("");
	$("alertSuccess").hide("");
	$("alertError").text("");
	$("alertError").hide("");
	
	var status = validatePaymentForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	var type = ($("#hidePayIdSave").val() == "") ? "POST" : "PUT";
	
	$ajax(
		{
			url : "PaymentAPI",
			type : type,
			data : $("formPayment").serialize(),
			dataType : "text",
			complete : function(response, status)
			{
				onPaySaveComplete(response.responseText, status);
			}
			
									
			
		});
	
});



function onPaySaveComplete(response, status)
	{
		if(status == "success")
		{
			var resultSet = JSON.parse(response);
					
			if (resultSet.status.trim() == "success")
			{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
						
				$("#divPayGrid").html(resultSet.data);
			} else if(resultSet.status.trim() == "error")
			{
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
			} else if(status == "error")
			{
				$("#alterError").text("Error while saving.");
				$("#alterError").show();
			} else
			{
				$("#alterError").text("Unknown error while saving.");
				$("#alterError").show();
			}
				
			$("hidePayIdSave").val("");
			$("#formPay")[0].reset();
	}
	
	
	
	
$(document).on("click", ".btnEdit", function(event)
{
	$("#hidePayIdSave").val($(this).data("payId"));
	$("#receiptNo").val($(this).closet("tr").find('td:eq(0)').text());
	$("#accno").val($(this).closet("tr").find('td:eq(1)').text());
	$("#amount").val($(this).closet("tr").find('td:eq(2)').text());
	$("#year").val($(this).closet("tr").find('td:eq(3)').text());
	$("#month").val($(this).closet("tr").find('td:eq(4)').text());
	$("#cvv").val($(this).closet("tr").find('td:eq(5)').text());
});



$(document).on("click", ".btnDelete", function(event)
{
	$.ajax(
		{
			url : "PaymnetAPI",
			type : "DELETE",
			data : "payId=" +$(this).data("payId"),
			dataType : "text",
			complete : function(rresponse, status)
			{
				onPaymentDeleteComplete(response.responseText, status);
			}	
		});
});
			
		