<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, com.sun.jersey.api.client.*,
com.sun.jersey.api.client.WebResource,com.sun.jersey.api.client.config.ClientConfig,
com.sun.jersey.api.client.config.DefaultClientConfig, javax.ws.rs.core.UriBuilder,
org.codehaus.jettison.json.JSONArray, org.codehaus.jettison.json.JSONObject, org.codehaus.jettison.json.JSONException,
javax.ws.rs.core.MediaType "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
  	<head>
  	    <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/docs.css" rel="stylesheet">
    <link href="js/google-code-prettify/prettify.css" rel="stylesheet">
	   
	</head>
    <body>
   
    
    <div class="container-fluid">
    	<div class="row-fluid"><h1>Starbucks <span class="muted">Barista</span></h1></div>
  		<div class="row-fluid">
    		<div class="span8">
				<% 
				//get open orders here
				
				ClientConfig conf = new DefaultClientConfig();
				Client client = Client.create(conf);
				WebResource service = client.resource(UriBuilder.fromUri(
						"http://localhost:8080/starbucks/rest").build());
				ClientResponse resp = service.path("barista").path("order").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				List<String> ids = new ArrayList<String>();
				
				try {
					//JSONObject json = new JSONObject(r);
					
					String r = resp.getEntity(String.class);
					JSONArray array = new JSONArray (r);
					
					
					for (int i=0 ; i < array.length(); i++ ){
						System.out.println(array.get(i));
						System.out.println(array.getJSONObject(i).get("id"));
						ids.add(array.getJSONObject(i).get("id").toString());
						
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
				
				%>
				
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
							
							
							
							
							
							
							if ( ids != null){
								
								for (String id : ids ){
						
						%>
								<tr>
		  							<td><a href="http://localhost:8080/starbucks/rest/cashier/order/<%=id%>" target=_blank><%=id%></a></td>
								
									<form action="control" method="get" >
										<input type=hidden name=id value=<%=id%>>
										<td><button type="submit" class="btn btn-danger" name=op value="prepare">Prepare</button></td>
		  							
		  					
		  							<td><button type="submit" class="btn btn-info" name=op value="check">Check Payment</button></td>
		  							
		  							<td><button type="submit" class="btn btn-warning" name=op value="release">Release</button></td>
		  							</form>
		  									
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
      				}
      			%>
      			<div class="well " style="height:50%">
      					<%=message %>
      			
      			</div>
    		</div>
	  </div>
	</div>
    </body>
</html>