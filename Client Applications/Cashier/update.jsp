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
	
 	<form action=Control method="get" >
 		<input type=hidden name=op value=<%=request.getParameter("op")%>>
 		<input type=hidden name=id value=<%=request.getParameter("id")%> >
 		<legend>Additional</legend>
 		
  		<label class="radio">
  		<input type="radio" name="addition" value="soy" checked>
  			Soy
		</label>
		
		<label class="radio">
  		<input type="radio" name="addition" value="skim" >
  			Skim
		</label>
		
		<label class="radio">
  		<input type="radio" name="addition" value="shot" >
  			Extra Shot
		</label>
		
		<label class="radio">
  		<input type="radio" name="addition" value="syrup" >
  			Vanilla Syrup
		</label>
		
		<span><button class="btn btn-info"  >Add</button></span>
	</form>

</body>
</html>