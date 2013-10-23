<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, control.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
  	<head>
  	    <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/docs.css" rel="stylesheet">
    <link href="js/google-code-prettify/prettify.css" rel="stylesheet">
    
    <script>
		function open_win()
		{
			//window.name = "main";
			window.open("new_order.html","_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=400");
			
		}
	</script>
	   
	</head>
    <body>
   
    
    <div class="container-fluid">
    	<div class="row-fluid"><h1>Starbucks <span class="muted">Cashier</span></h1></div>
  		<div class="row-fluid">
    		<div class="span8">
    			<span class="pull-right">
    			
    			<button type="submit" class="btn btn-primary" onclick="open_win()" >New Order</button>
    			
    			</span>
      			<table class="table table table-striped">
      					<thead>
  							<th><h4>Order</h4></th>
  							<th><h4>Payment</h4></th>
  							<th></th>
  							<th></th>
  							<th></th>
  							<th></th>
  						</thead>
  						<tbody>
						<% 
							ServletContext c = getServletContext();
							List<String> ids = (List<String>) c.getAttribute("open_orders");
							Map<String, String> pyments = (Map<String, String>) c.getAttribute("payments");
							if ( ids != null){
								
								for (String id : ids ){
						
						%>
								<tr>
		  							<td><a href="http://localhost:8080/starbucks/rest/cashier/order/<%=id%>" target=_blank><%=id%></a></td>
									<td>
									<% if (pyments.containsKey(id)){ %>
									<a href="http://localhost:8080/starbucks/rest/cashier/payment/<%=id%>" target=_blank>	<%=id%></a>
									<% 	
									}
									%>
									</td>
									<form action="Control" method="get" >
										<input type=hidden name=id value=<%=id%>>
										<td><button type="submit" class="btn btn-danger" name=op value="cancel">Cancel</button></td>
		  							</form>
		  							<form action="update.jsp" method="get" >
		  								<input type=hidden name=id value=<%=id%>>
		  							<td><button type="submit" class="btn btn-info" name=op value="update">Update</button></td>
		  							
		  							</form>
		  							<form action="pay.jsp" method="get" >
											<input type=hidden name=id value=<%=id%>>
		  									<td><button type="submit" class="btn btn-warning" name=op value="pay">Pay</button></td>
		  							</form>
		  							<form action="Control" method="get" >
		  							<td><button type="submit" class="btn btn-success" name=op value="option">Option</button></td>
		  							</form>
		  						</tr>		
						<%
								}
							}
								
						%>
  						
  					
  						
  						</tbody>
					</table>
					 
    		</div>
    		<div class="span4" >
    			<legend>Response</legend>
    				<% 
    				ServletContext context = getServletContext();
      				String message = "no response";
      				if (context.getAttribute("response") != null ){
      					message = (String) context.getAttribute("response");
      					message = message.replaceAll("<.", "&lt;");
      					message = message.replaceAll(">.", "&gt;"); 
      				}
      			%>
      			<div class="well " style="height:100%">
      					
      					<%=message %>
      					
      			
      			</div>
    		</div>
	  </div>
	</div>
    </body>
</html>