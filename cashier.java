package starbucks;


import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;




@Path("/cashier")
public class cashier {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	DecimalFormat df = new DecimalFormat("00000");
	static private int id;
	
	public cashier() {
		// TODO Auto-generated constructor stub
	}
	//create a new order
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String createOrder (@FormParam("coffee")  String coffee, 
							 @FormParam("cost") String cost,
							 @FormParam("addition") String addition ){
					
		Order newOrder = new Order();
		newOrder.setCoffee(coffee);
		newOrder.setID(df.format(id));
		System.out.print(cost);
		Double amt = Double.parseDouble(cost);
		newOrder.setCost(amt);
		
		newOrder.setAdditions(addition);
		newOrder.setStatus("ordered");
		newOrder.setCost(calculateCost (newOrder.getCost(), addition));
		newOrder.getPayment().setID(newOrder.getID());
		
		OrdersDAO.instance.getOrders().put(newOrder.getID(), newOrder);
		System.out.println(newOrder);
		id = id+1;
		System.out.println(id);
		
		return "Order ID: "+ newOrder.getID() +" Cost: "+ newOrder.getCost() + "Link to payment resource: http://localhost:8080/starbucks/rest/cashier/payment/"+ newOrder.getID();
		
	}
	//return details of a particular order
	@GET
	@Path("/order/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String getOrder(@PathParam("id") String id){
		System.out.print(id);
		return "<html><body>" + OrdersDAO.instance.getOrders().get(id) + "</body></html>";
	}
	
	//return details of a particular order
	@GET
	@Path("/payment/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String getPayment(@PathParam("id") String id){
		System.out.print(id);
		return "<html><body>" + PaymentsDAO.instance.getPayments().get(id) + "</body></html>";
	}
	
	//return list of orders	
	@GET
	@Path("/order")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Order> getOrders(){
			List<Order> orders = new ArrayList<Order>();
			
			for ( String key : OrdersDAO.instance.getOrders().keySet() ){
				if (!OrdersDAO.instance.getOrders().get(key).getStatus().equals("released")){
					orders.add(OrdersDAO.instance.getOrders().get(key));
				}
			}
		return orders;
	}
	
	//return list of payments
	@GET
	@Path("/payment")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Payment> getPayments(){
			List<Payment> payments = new ArrayList<Payment>();
			payments.addAll(PaymentsDAO.instance.getPayments().values());
		return payments;
	}
	

	
	//update order
	@PUT
	@Path("/order/update/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateOrder(@PathParam("id") String id, @FormParam("addition") String addition){
		
		if (addition != null && OrdersDAO.instance.getOrders().containsKey(id)){
				Order o = OrdersDAO.instance.getOrders().get(id);
			
			if (!o.getStatus().equalsIgnoreCase("making") && !PaymentsDAO.instance.getPayments().containsKey(id)){
				o.setAdditions(addition);
				o.setCost(calculateCost (o.getCost(), addition));
				return "Order ID: "+ o.getID() +" Cost: "+ o.getCost();
			}
		}
		
		return "Update unsucessful. Either customer has already paid or the Barista has started making the order.";
	}
	
	
	//cancel order
	@DELETE
	@Path("/order/cancel/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String cancelOrder(@PathParam("id") String id){
		
		if (OrdersDAO.instance.getOrders().containsKey(id)){
			//you can't cancel wan order that doesn't exist
			if (!OrdersDAO.instance.getOrders().get(id).getStatus().equalsIgnoreCase("making") && !PaymentsDAO.instance.getPayments().containsKey(id)	){
				//you can't cancel when the barista has picked up the order or you have paid
				OrdersDAO.instance.getOrders().remove(id);
				return "cancelled";
			}
		}
		
		return "Cannot be cancelled. Either Barista has started making or customer has already paid.";
	}
	
	//make payment
	@PUT
	@Path("/order/pay/{id}")
	public String makePayment(@PathParam("id") String id, @FormParam("type") String type, 
														  @FormParam("detail") String detail){
		
		if (OrdersDAO.instance.getOrders().containsKey(id)){
			//you can't pay for an order that does not exist
			if (!PaymentsDAO.instance.getPayments().containsKey(id) ){
				//if you have not paid yet
				Payment p = OrdersDAO.instance.getOrders().get(id).getPayment();
				Double amt = OrdersDAO.instance.getOrders().get(id).getCost();
				p.setAmount(amt);
				p.setCard_details(detail);
				p.setType(type);
				
				PaymentsDAO.instance.getPayments().put(id, p);
				return "Payment successful";
			}
			
		}
		
		return "Payment has no effect.";
	}
	

	@OPTIONS
	@Path("/*")
	public Response options(){
		
		
		Response res = Response.noContent().build();
		
		res.getMetadata().add("Allow", "GET");
		res.getMetadata().add("Allow", "PUT");
		res.getMetadata().add("Allow", "OPTIONS");
		res.getMetadata().add("Allow", "DELETE");
		res.getMetadata().add("Allow", "POST");
		
		return res;
	}
	
	//=========
	//HELPER
	//=========
	private Double calculateCost (Double amt,  String addition){
			Double total=amt;
			//skim additions are free!
			if (addition.equalsIgnoreCase("soy") ||
					addition.equalsIgnoreCase("shot") ||
					addition.equalsIgnoreCase("syrup") ){
				total += 0.5;		
			}
		return total;
	}
	
}
