package com.caspar.caswechat.util.general.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 
 * @author caspar
 * @since 2017-5-15
 * @version v1.0.0
 */
public class HttpRequester {

	private CloseableHttpClient httpClient;
	private RequestConfig requestConfig;
	private DefaultResponseHandler responseHandler;

	private final static Map<String, String> DEFAULT_HEADERS_FORM_UTF8 = new HashMap<String, String>();
	static {
		DEFAULT_HEADERS_FORM_UTF8.put(HttpHeaders.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		DEFAULT_HEADERS_FORM_UTF8.put(HttpHeaders.CONTENT_ENCODING,
				StandardCharsets.UTF_8.name());
	}

	private final static Map<String, String> DEFAULT_HEADERS_JSON_UTF8 = new HashMap<String, String>();
	static {
		DEFAULT_HEADERS_JSON_UTF8.put(HttpHeaders.CONTENT_TYPE,
				"application/json");
		DEFAULT_HEADERS_JSON_UTF8.put(HttpHeaders.CONTENT_ENCODING,
				StandardCharsets.UTF_8.name());
	}

	public static HttpRequester createDefault() {
		return new HttpRequester(5000, 5000, 5000,
				StandardCharsets.UTF_8.name());
	}

	public HttpRequester(int connectionRequestTimeout, int connectionTimeout,
			int socketTimeout, String defaultCharset) {
		super();
		init(connectionRequestTimeout, connectionTimeout, socketTimeout,
				defaultCharset, null);
	}

	public HttpRequester(int connectionRequestTimeout, int connectionTimeout,
			int socketTimeout, String defaultCharset, String proxyServer) {
		super();
		init(connectionRequestTimeout, connectionTimeout, socketTimeout,
				defaultCharset, proxyServer);
	}

	// 构建
	private void init(int connectionRequestTimeout, int connectionTimeout,
			int socketTimeout, String defaultCharset, String proxyServer) {
		httpClient = HttpClients.createDefault();

		Builder requestBuilder = RequestConfig.custom();
		requestBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
		requestBuilder.setConnectTimeout(connectionTimeout);
		requestBuilder.setSocketTimeout(socketTimeout);
		if (StringUtils.isNotBlank(proxyServer)) {
			requestBuilder.setProxy(HttpHost.create(proxyServer));
		}
		requestBuilder.build();
		requestConfig = requestBuilder.build();
		responseHandler = new DefaultResponseHandler(
				Charset.forName(defaultCharset));
	}

	public Response post(String url,Map<String, Object> param) {
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (httpResponse == null) {
			return null;
		}
		Response response = null;
		try {
			response = new Response(httpResponse.getStatusLine()
					.getStatusCode(), EntityUtils.toString(httpResponse
					.getEntity()));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public Response get(String url, Map<String, String> headers,
			ResponseHandler<?> responseHandler) {

		HttpGet httpRequestBean = null;
		CloseableHttpResponse closeableHttpResponse = null;

		try {

			httpRequestBean = new HttpGet(url);
			httpRequestBean.setConfig(requestConfig);
			// 设置header
			if (MapUtils.isNotEmpty(headers)) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpRequestBean.addHeader(entry.getKey(), entry.getValue());
				}
			}

			return httpClient.execute(httpRequestBean, this.responseHandler);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			HttpClientUtils.closeQuietly(closeableHttpResponse);
			closeableHttpResponse = null;
		}

	}

	public Response get(String url, Map<String, String> headers) {
		return get(url, headers, responseHandler);
	}

	public Response get(String url) {
		return get(url, null, responseHandler);
	}

}
