

import java.io.IOException;
import java.net.URI;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Servlet implementation class control
 */
public class control extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public control() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		String op = request.getParameter("op");
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		String r = "no response";
		
		String id = request.getParameter("id");
		
		if (op!=null){
			if (op.equals("prepare")){
				ClientResponse resp =service.path("barista").path("prepare").path(id).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
				r=resp.getEntity(String.class);
				
			}else if (op.equals("check")){
				ClientResponse resp =service.path("barista").path("check").path(id).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
				r=resp.getEntity(String.class);
			}else if (op.equals("release")){
				ClientResponse resp =service.path("barista").path("release").path(id).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
				r=resp.getEntity(String.class);
			}
		}
		
		context.setAttribute("response", r);
		RequestDispatcher next = request.getRequestDispatcher("Barista.jsp");
		next.forward(request, response);
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/starbucks/rest").build();
	}



}
