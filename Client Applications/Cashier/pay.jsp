<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/docs.css" rel="stylesheet">
    <link href="js/google-code-prettify/prettify.css" rel="stylesheet">
    
</head>
<body>
	
 	<form action=Control method="get" target="main" >
 		<input type=hidden name=op value=<%=request.getParameter("op")%>>
 		<input type=hidden name=id value=<%=request.getParameter("id")%> >
 		<legend>Payment</legend>
  		<label class="radio">
  		<input type="radio" name="type" value="card" checked>
  			Card
		</label>
		<label class="radio">
  		<input type="radio" name="type" value="cash" >
  			Cash
		</label>
	
		<label class="text">
			Card Details
  		<input type="text" name="detail">
		</label>
		<span><button class="btn btn-info"  >Pay</button></span>
	</form>

</body>
</html>