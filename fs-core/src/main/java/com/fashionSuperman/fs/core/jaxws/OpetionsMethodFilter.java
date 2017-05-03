package com.fashionSuperman.fs.core.jaxws;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.specimpl.BuiltResponse;

public class OpetionsMethodFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(requestContext.getMethod().equals("OPTIONS")){
			Response response = new BuiltResponse();
			response = Response.status(204)
					.header("Access-Control-Allow-Method", "GET,POST")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With")
					.entity("")
					.build();
//			response.status(204);
//			Headers<String> metadata = new Headers<>();
//			metadata.add("Access-Control-Allow-Method", "GET,POST");
//			metadata.add("Access-Control-Allow-Origin", "*");
//			metadata.add("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			requestContext.abortWith(response);
		}
		
	}

}
