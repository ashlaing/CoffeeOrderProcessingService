package starbucks;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	
	private String coffee = "";
	private	Double cost = 0.0;
	private String status = ""; //ordered, making, made, released
	private List<String> additions = new ArrayList<String>();
	private String ID = "";
	private Payment payment = new Payment();
	
	public Order() {
	}

	public List<String> getAdditions() {
		return additions;
	}

	public void setAdditions(String addition ) {
		this.additions.add(addition);
	
	}
	
	public String getCoffee() {
		return coffee;
	}

	public void setCoffee(String coffee) {
		this.coffee = coffee;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	@Override
	public String toString(){
		
		return "Order ID: " + this.getID() + "<br>" +
			   "Coffee: " + this.getCoffee() + "<br>" +
			   "Cost: " + this.getCost() +  "<br>" +
			   "Status: "+ this.getStatus() +"<br>" +
			   "Additions: "+this.getAdditions().toString();
		
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
