package control;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

/**
 * Servlet implementation class Control
 */
public class Control extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Control() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String nextPage = "";
		ServletContext context = getServletContext();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
			if (op!=null){
				
			System.out.print(op);
			
			if(op.equals("cancel") && request.getParameter("id")!=null){
					ClientResponse resp = service.path("cashier").path("order").path("cancel").path(request.getParameter("id")).delete(ClientResponse.class);
					String r = resp.getEntity(String.class);
					context.setAttribute("response", r);
					loadOpenOrders ( service , context );
					loadPayment ( service , context );
			}else if(op.equals("pay") && request.getParameter("id")!=null){
				System.out.print("paying");
				Form f = new Form();
				f.add("type",request.getParameter("type"));
				f.add("type",request.getParameter("detail"));
				
				ClientResponse resp = service.path("cashier").path("order").path("pay").path(request.getParameter("id")).put(ClientResponse.class, f);
				String r = resp.getEntity(String.class);
				context.setAttribute("response", r);
				loadOpenOrders ( service , context );
				loadPayment ( service , context );
			}else if(op.equals("update") && request.getParameter("id")!=null){
				System.out.print("updating");
				Form f = new Form();
				f.add("addition",request.getParameter("addition"));
				
				ClientResponse resp = service.path("cashier").path("order").path("update").path(request.getParameter("id")).put(ClientResponse.class, f);
				String r = resp.getEntity(String.class);
				context.setAttribute("response", r);
				loadOpenOrders ( service , context );
				loadPayment ( service , context );
			}else if(op.equals("option") ){
				
				
				ClientResponse resp = service.path("cashier").accept(MediaType.APPLICATION_XML).options(ClientResponse.class);
				String r = resp.getEntity(String.class);
				System.out.print("response" + r);
				context.setAttribute("response", r);
				loadOpenOrders ( service , context );
				loadPayment ( service , context );
			}
			
				
				RequestDispatcher next = request.getRequestDispatcher("Cashier.jsp");
				next.forward(request, response);
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		ServletContext context = getServletContext();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
			if (op!=null){
				
				//create new resource
				if (op.equals("create") && request.getParameter("coffee")!=null){
					Form form = new Form();
					form.add("coffee", request.getParameter("coffee"));
					form.add("cost", "2.50");
					form.add("addition", "");

					ClientResponse resp = service.path("cashier").accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, form);
					
					String r = resp.getEntity(String.class);
					
					context.setAttribute("response", r);
					
					//get open order list
					loadOpenOrders ( service , context );
					loadPayment ( service , context );
					//System.out.println(r);
					
	
				}
				RequestDispatcher next = request.getRequestDispatcher("Cashier.jsp");
				next.forward(request, response);
			}
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/starbucks/rest").build();
	}
	
	private void loadOpenOrders ( WebResource service , ServletContext context ){
		ClientResponse resp =service.path("cashier").path("order").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		String r=resp.getEntity(String.class);
		
		System.out.print(r);
		try {
			//JSONObject json = new JSONObject(r);
			
			
			JSONArray array = new JSONArray (r);
			
			List<String> ids = new ArrayList<String>();
			for (int i=0 ; i < array.length(); i++ ){
				System.out.println(array.get(i));
				System.out.println(array.getJSONObject(i).get("id"));
				ids.add(array.getJSONObject(i).get("id").toString());
				
			}
			
			context.setAttribute("open_orders", ids);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void loadPayment ( WebResource service , ServletContext context ){
		ClientResponse resp =service.path("cashier").path("payment").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String r=resp.getEntity(String.class);
		try {
			System.out.println("Payments:"+ r);
			JSONArray array = new JSONArray (r);
			Map<String, String> payments = new HashMap<String,String>();
			for (int i=0 ; i < array.length(); i++ ){
				payments.put(array.getJSONObject(i).get("id").toString(), "true");
			}
			
			context.setAttribute("payments", payments);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
