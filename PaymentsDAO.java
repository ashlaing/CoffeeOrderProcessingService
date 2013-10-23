package starbucks;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public enum PaymentsDAO {
	    instance;

	    private Map<String, Payment> payments = new LinkedHashMap<String, Payment>();

	    private PaymentsDAO() {
	        
	    }
	    public Map<String, Payment> getPayments(){
	        return payments;
	   }
}