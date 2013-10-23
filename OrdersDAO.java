package starbucks;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public enum OrdersDAO {
	    instance;

	    private Map<String, Order> orders = new LinkedHashMap<String, Order>();

	    private OrdersDAO() {

	        Order o = new Order();
	        o.setCoffee("Latte");
	        o.setCost(4.00);
	        o.setAdditions("soy");
	        o.setID("11111");
	        o.setStatus("ordered");
	        o.getPayment().setID("11111");
	        System.out.print(o);
	        orders.put("11111", o);
	        
	    }
	    public Map<String, Order> getOrders(){
	        return orders;
	   }
}
	    
