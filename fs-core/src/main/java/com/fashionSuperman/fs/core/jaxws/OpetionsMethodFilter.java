package com.fashionSuperman.fs.core.jaxws;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

public class OpetionsMethodFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(requestContext.getMethod().equals("OPTIONS")){
			Response response = Response.status(204)
					.header("Access-Control-Allow-Method", "GET,POST")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With")
					.entity("")
					.build();

			requestContext.abortWith(response);
		}
		
	}

}
