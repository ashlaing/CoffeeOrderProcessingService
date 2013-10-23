package starbucks;

public class Payment {
	
	private String type="";
	private Double amount=0.0;
	private String card_details="";
	private String ID;


	public Payment() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getCard_details() {
		return card_details;
	}

	public void setCard_details(String card_details) {
		this.card_details = card_details;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString(){
		
		return "Payment ID: " + this.getID() + "<br>" +
			   "Type: " + this.getType() + "<br>" +
			   "Amount: " + this.getAmount() +  "<br>" +
			   "Details: "+ this.getCard_details() +"<br>";
		
	}


}
