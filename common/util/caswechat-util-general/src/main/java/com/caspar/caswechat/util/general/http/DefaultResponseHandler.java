package com.caspar.caswechat.util.general.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
/**
 * 
 *      
 * @author caspar
 * @since 2017-5-15
 * @version   v1.0.0
 */
public class DefaultResponseHandler implements ResponseHandler<Response> {
	
	private Charset charset;
	public DefaultResponseHandler(Charset charset){
		this.charset = charset;
	}
	
	
	@Override
	public Response handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		
		InputStream is = null;
		
		try{
			
			is = response.getEntity().getContent();
			Header headerEncoding = response.getEntity().getContentEncoding();
			
			if(    headerEncoding != null
				&& headerEncoding.getValue() != null
				&& !StringUtils.equalsIgnoreCase(headerEncoding.getValue(),charset.name())
			){
				charset = Charset.forName(headerEncoding.getValue());
			}
			
			String content = IOUtils.toString(is,charset);
			
			return new Response( response.getStatusLine().getStatusCode() ,content );
		
		}finally{
			
			IOUtils.closeQuietly(is);
			
		}
	}

}
