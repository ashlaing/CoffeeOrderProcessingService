package starbucks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/barista")
public class Barista {
	
	@GET
	@Path("/prepare/{id}")
	public String prepare(@PathParam("id") String id){
		Order o = OrdersDAO.instance.getOrders().get(id);
		if (o.getStatus().equalsIgnoreCase("ordered")){
			o.setStatus("making");
			return "Making...";
		}
		return "Barista has already started making.";
	}
	
	@GET
	@Path("/check/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkPayment(@PathParam("id") String id){
		
		if (PaymentsDAO.instance.getPayments().containsKey(id)){
			return PaymentsDAO.instance.getPayments().get(id).toString();
		}
		return "Customer has not yet paid.";
	}
	
	@GET
	@Path("/release/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String release(@PathParam("id") String id){
		Order o = OrdersDAO.instance.getOrders().get(id);
		if (o.getStatus().equalsIgnoreCase("making") && PaymentsDAO.instance.getPayments().containsKey(id)){
			o.setStatus("released");
			return "Released";
		}
		return "Release has no effect. Either you have not made the drink yet or customer has not paid.";
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
	

}
