package test.com.iqb.eatep.front.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.eatep.ec.contract.ssq.bean.PostFile;
import com.iqb.etep.common.mongo.MongoTemp;

/**
 * 
 * Description: HTTP服务工具 类
 * @author baiyapeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月12日    baiyapeng       1.0        1.0 Version 
 * </pre>
 */
@Component
public class HttpUtil {
	
	public static final String LOG_EC_SSQ = "LOG_EC_SSQ";

	private String defaultUserAgent = ""; // 默认消息头
	private boolean autoRedirect = true; // 是否处理302
	private CookieManager cookieManager = new CookieManager(); // Cookie
	
    private EcConfig ecConfig = new EcConfig();
	
    @Autowired
	private MongoTemp mongoTemp;
	
	/**
	 * httpPost
	 * 
	 * @param method
	 * @param path
	 * @param postData
	 * @param signData
	 * @param headerData
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> httpPost(final String method, final String path, final String requestBody, final String signData, final Map<String, String> headerData) throws Exception  {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", UUID.randomUUID());
		try {	
			map.put("request", requestBody);
//			mongoTemp.save(map, LOG_EC_SSQ);
			byte[] postData = EncodeUtil.getBytes(requestBody);
			String url = ecConfig.getSsqHost() + path;
			
			Map<String, String> requestHeaderData = headerData;
			if (requestHeaderData == null) {
				requestHeaderData = new HashMap<String, String>();
			}
			
			requestHeaderData.put("mid", ecConfig.getSsqMid());
			requestHeaderData.put("sign", EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData));
			
			String[] headers = new String[requestHeaderData.size() + 1];
			int i = 0;
			headers[i++] = "Content-Type: application/json; charset=UTF-8";
			for (String name: requestHeaderData.keySet()) {
				String value = EncodeUtil.convertToUtf8(requestHeaderData.get(name));
				String line = name + ": " + EncodeUtil.urlEncode(value);
				headers[i++] = line;
			}
			
			
			Map<String, Object> ret = null;
			if (method.equalsIgnoreCase("POST")) {
				ret = post(url, postData, headers);
			} else {
				throw new IllegalArgumentException("request way do not support except post");
			} 
			
			String response = (String) ret.get("response");
			response = EncodeUtil.convertFromUtf8(response);
			ret.put("response", response);
			map.remove("request");
			map.put("response", response);
//			mongoTemp.save(map, LOG_EC_SSQ);
			return ret;
		} catch (Exception e) {
			map.remove("request");
			map.remove("response");
			map.put("exception", e.getMessage());
//			mongoTemp.save(map, LOG_EC_SSQ);
			throw e; 
		}		
	}
	
	/**
	 * httpPost
	 * 
	 * @param method
	 * @param path
	 * @param postData
	 * @param signData
	 * @param headerData
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> httpPost(final String method, final String path, final String requestBody, final byte[] postData, final String signData, final Map<String, String> headerData) throws Exception  {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", UUID.randomUUID());
		try {						
			map.put("request", requestBody);
			map.put("postData", postData);
//			mongoTemp.save(map, LOG_EC_SSQ);
			String url = ecConfig.getSsqHost() + path;
			
			Map<String, String> requestHeaderData = headerData;
			if (requestHeaderData == null) {
				requestHeaderData = new HashMap<String, String>();
			}
			
			requestHeaderData.put("mid", ecConfig.getSsqMid());
			requestHeaderData.put("sign", EncodeUtil.getRsaSign(ecConfig.getSsqPem(), signData));
			
			String[] headers = new String[requestHeaderData.size() + 1];
			int i = 0;
			headers[i++] = "Content-Type: application/json; charset=UTF-8";
			for (String name: requestHeaderData.keySet()) {
				String value = EncodeUtil.convertToUtf8(requestHeaderData.get(name));
				String line = name + ": " + EncodeUtil.urlEncode(value);
				headers[i++] = line;
			}
			
			
			Map<String, Object> ret = null;
			if (method.equalsIgnoreCase("POST")) {
				ret = post(url, postData, headers);
			} else {
				throw new IllegalArgumentException("request way do not support except post");
			} 
			
			String response = (String) ret.get("response");
			response = EncodeUtil.convertFromUtf8(response);
			ret.put("response", response);
			map.remove("request");
			map.remove("postData");
			map.put("response", response);
//			mongoTemp.save(map, LOG_EC_SSQ);
			return ret;
		} catch (Exception e) {
			map.remove("request");
			map.remove("postData");
			map.remove("response");
			map.put("exception", e.getMessage());
//			mongoTemp.save(map, LOG_EC_SSQ);
			throw e; 
		}		
	}
	
	/**
	 * GET
	 *
	 * @param url
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public Map<String, Object> get(String url) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return get(url, null);
	}
	
	/**
	 * get
	 *
	 * @param url
	 * @param outputBytes
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public Map<String, Object> get(String url, boolean outputBytes) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return request("GET", url, null, null, null, null, outputBytes);
	}

	/**
	 * get
	 *
	 * @param url
	 * @param headers
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public Map<String, Object> get(String url, String[] headers) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return request("GET", url, null, headers);
	}

	/**
	 * get
	 *
	 * @param url
	 * @param sendData: String / byte[] / Map<String, String> / Map<String, byte[]>
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> post(String url, Object sendData) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return post(url, sendData, null);
	}

	/**
	 * post
	 *
	 * @param url
	 * @param sendData: String / byte[] / Map<String, String> / Map<String, byte[]>
	 * @param headers
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> post(String url, Object sendData, String[] headers) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return request("POST", url, sendData, headers);
	}

	/**
	 * 发送请求
	 *
	 * @param url
	 * @param method
	 * @param sendData: String / byte[] / Map<String, String> / Map<String, byte[]>
	 * @param sendFiles: Map<String, File> / Map<String, byte[]> / Map<String, PostFile>
	 * @param headers
	 * @param autoRedirect
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> request(String method, String url, Object sendData, String[] headers) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		return request(method, url, sendData, null, null, headers);
	}

	/**
	 * 发送请求
	 *
	 * @param url
	 * @param method
	 * @param sendData: String / byte[] / Map<String, String> / Map<String, byte[]>
	 * @param sendFiles: Map<String, File> / Map<String, byte[]> / Map<String, PostFile>
	 * @param headers
	 * @param autoRedirect
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> request(String method, String url, Object sendData, Map<String, Object> sendFiles, String[] headers) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		return request(method, url, sendData, sendFiles, null, headers);
	}
	
	/**
	 * 发送请求
	 * 
	 * @param method
	 * @param url
	 * @param sendData
	 * @param sendFiles
	 * @param boundary
	 * @param headers
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> request(String method, String url, Object sendData, Map<String, Object> sendFiles, String boundary, String[] headers) throws IOException, IllegalArgumentException, KeyManagementException, NoSuchAlgorithmException {
		return request(method, url, sendData, sendFiles, boundary, headers, false);
	}

	/**
	 * 发送请求
	 *
	 * @param method
	 * @param url
	 * @param sendData: String / byte[] / Map<String, String> / Map<String, byte[]>
	 * @param sendFiles: Map<String, File> / Map<String, byte[]> / Map<String, PostFile>
	 * @param boundary
	 * @param headers
	 * @param autoRedirect
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> request(String method, String url, Object sendData, Map<String, Object> sendFiles, String boundary, String[] headers, boolean outputBytes) throws IOException, IllegalArgumentException, KeyManagementException, NoSuchAlgorithmException {
		// 检查类型
		boolean valid = true;
		if (sendData != null) {
			if (sendData instanceof String) {
				;
			} else if (sendData instanceof byte[]) {
				;
			} else if (sendData instanceof Map) {
				if (((Map<Object, Object>) sendData).size() == 0) {
					sendData = null;
				} else {
					Set<Object> keysets = ((Map<Object, Object>) sendData).keySet();
					for (Iterator<Object> iter = keysets.iterator(); iter.hasNext();) {
						Object k = iter.next();
						if (!(k instanceof String)) {
							valid = false;
							break;
						}
					}
					if (valid) {
						for (String k : ((Map<String, Object>) sendData).keySet()) {
							Object v = ((Map<String, Object>) sendData).get(k);
							if (v instanceof String) {
								;
							} else if (v instanceof byte[]) {
								;
							} else {
								valid = false;
								break;
							}
						}
					}
				}
			} else {
				valid = false;
			}
			if (!valid) {
				throw new IllegalArgumentException("sendData wrong, only [String / byte[] / Map<String, String> / Map<String, byte[]>]");
			}
		}

		if (sendFiles != null) {
			for (String k : sendFiles.keySet()) {
				Object v = ((Map<String, Object>) sendData).get(k);
				if (v instanceof File) {
					;
				} else if (v instanceof PostFile) {
					;
				} else if (v instanceof byte[]) {
					;
				} else {
					valid = false;
					break;
				}
			}
			if (!valid) {
				throw new IllegalArgumentException("sendFiles wrong, only [Map<String, File> / Map<String, byte[]> / Map<String, PostFile>]");
			}
		}

		int responseCode = -1;
		//Map<String, List<String>> responseHeaders = null;

		method = method.toUpperCase();


		// 1. check sendData, must be Map when sendFiles not empty
		// 2. must have a boundary when sendFiles not empty
		if (sendFiles != null && sendFiles.size() > 0) {
			if (sendData != null && !(sendData instanceof Map)) {
				sendFiles = null;
			}

			if (boundary == null || boundary.length() == 0) {
				boundary = genBoundary((Map<String, ?>) sendData, sendFiles);
			}
		}

		// split boundary
		String hBoundary = "";
		String dBoundary = "";
		if (boundary != null && boundary.length() > 0) {
			hBoundary = "---------------------------" + boundary;
			dBoundary = "--" + hBoundary;
		}

		// get connection...
		HttpURLConnection conn = null;
		conn = getConnection(url, method, headers, boundary);
		
		// send
		try {
			if (dBoundary.length() > 0) {
				if (sendData == null) {
					send(conn, dBoundary, null, sendFiles);
				} else {
					send(conn, dBoundary, (Map<String, Object>) sendData, sendFiles);
				}
			} else {
				send(conn, sendData);
			}
		} catch (IOException e) {	
			conn.disconnect();
			throw e;
		} 


		// get response code
		try {
			responseCode = conn.getResponseCode();
		} catch (IOException e) {
			conn.disconnect();
			throw e;
		} 

		// response headers
		//responseHeaders = conn.getHeaderFields();


		// receive
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("responseCode", responseCode);
						
			if (outputBytes) {
				byte[] response = getResponseBytes(conn);
				result.put("response", response);
			} else {
				String response = getResponse(conn);
				result.put("response", response);
			}
			
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			conn.disconnect();
		}
	}
	
	/**
	 * 生成boundary字符串
	 * 
	 * @param postData
	 * @param postFiles
	 * @return
	 * @throws IOException
	 */
	private String genBoundary(Map<String, ?> postData, Map<String, ?> postFiles) throws IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(System.currentTimeMillis());
		buffer.append(random(1000, 9999));
		if (postData != null) {
			buffer.append(Math.abs(postData.hashCode()));
		} else {
			buffer.append(0);
		}

		buffer.append(random(1000, 9999));

		if (postData != null) {
			buffer.append(Math.abs(postFiles.hashCode()));
		} else {
			buffer.append(0);
		}

		return buffer.toString();
	}

	/**
	 * random
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max) {
		return (int) ((max - min + 1) * Math.random() + min);
	}

	/**
	 * getConnection
	 *
	 * @param url
	 * @param method
	 * @param headers
	 * @param boundary
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private HttpURLConnection getConnection(String url, String method, String[] headers, String boundary) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		URL httpUrl = new URL(url);
		HttpURLConnection conn = null;

		if (cookieManager != null) {
			CookieHandler.setDefault(cookieManager);
		}

		if (url.substring(0, 8).equalsIgnoreCase("https://")) {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
			// HttpsURLConnection.setFollowRedirects(autoRedirect);
			conn = (HttpsURLConnection) httpUrl.openConnection();
		} else {
			// HttpURLConnection.setFollowRedirects(autoRedirect);
			conn = (HttpURLConnection) httpUrl.openConnection();
		}

		// connect
		conn.setRequestMethod(method);
		conn.setConnectTimeout(Integer.parseInt(ecConfig.getSsqConnectTimeout()));
		conn.setReadTimeout(Integer.parseInt(ecConfig.getSsqReadTimeout()));
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(autoRedirect);
		conn.setUseCaches(false);
		if (boundary != null && boundary.length() > 0) {
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		}

		setHeaders(conn, headers);

		conn.connect();

		return conn;
	}
	
	/**
	 * 设置headers
	 *
	 * @param conn
	 * @param headers
	 */
	private void setHeaders(HttpURLConnection conn, String[] headers) {
		Map<String, String> mapHeaders = buildHeaders(headers);
		if (mapHeaders == null || mapHeaders.size() == 0) {
			return;
		}
		for (String name : mapHeaders.keySet()) {
			String value = mapHeaders.get(name);
			conn.setRequestProperty(name, value);
		}
	}

	/**
	 * build headers to map
	 *
	 * @param headers
	 * @return
	 */
	private Map<String, String> buildHeaders(String[] headers) {
		
		if (defaultUserAgent == null || defaultUserAgent.length() == 0) {
			setDefaultUserAgent(ecConfig.getSsqAppName() + "/" + ecConfig.getSsqAppVersion());
		}
		
		if ((null == headers || headers.length == 0) && defaultUserAgent.length() == 0) {
			return null;
		}

		Map<String, String> result = new HashMap<String, String>();

		if (null != headers && headers.length > 0) {
			int i, a;
			String line, name, value;
			for (i = 0; i < headers.length; i++) {
				line = headers[i].trim();
				if (line.length() < 1) {
					continue;
				}
				a = line.indexOf(":");
				if (a < 0) {
					name = line;
					value = "";
				} else {
					name = line.substring(0, a).trim();
					value = line.substring(a + 1).trim();
				}
				result.put(name, value);
			}
		}

		if (defaultUserAgent.length() != 0 && !result.containsKey("UserAgent")) {
			result.put("User-Agent", defaultUserAgent);
		}

		return result;
	}

	/**
	 * 获取响应
	 *
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private String getResponse(HttpURLConnection conn) throws IOException {
		String line = null;
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		try {
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {

			}
		}

		return buffer.toString();
	}
	
	/**
	 * 获取响应
	 *
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private byte[] getResponseBytes(HttpURLConnection conn) throws IOException {
		final int bufferSize = 4096;
		
		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
		} catch (IOException e) {
			inputStream = conn.getErrorStream();
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[bufferSize];
		try {
			while (true) {
				int readBytes = inputStream.read(buffer);
				if (readBytes < 1) {
					break;
				}
				byte[] readData = new byte[readBytes];
				System.arraycopy(buffer, 0, readData, 0, readData.length);
				outputStream.write(readData);
			}
			outputStream.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				;
			}
			try {
				outputStream.close();
			} catch (Exception e) {
				;
			}
		}
		
		return outputStream.toByteArray();
	}

	private static TrustManager myX509TrustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
			
		}

		public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
			
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};	
	
	/**
	 * 发送请求
	 *
	 * @param conn
	 * @param sendData
	 * @throws IOException
	 */
	public static void send(HttpURLConnection conn, Object sendData) throws IOException {
		if (sendData == null) {
			return;
		}
		DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
		try {
			uploadData(dataOutputStream, sendData);
		} catch (IOException e) {
			throw e;
		} finally {
			dataOutputStream.close();
		}
	}

	/**
	 * 发送请求
	 *
	 * @param conn
	 * @param boundary
	 * @param sendData
	 * @param sendFiles
	 * @throws IOException
	 */
	public static void send(HttpURLConnection conn, String boundary, Map<String, Object> sendData, Map<String, Object> sendFiles) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());

		try {
			boolean isSend = false;

			if (sendData != null && sendData.size() > 0) {
				for (String sendName : sendData.keySet()) {
					Object sendValue = sendData.get(sendName);
					uploadData(dataOutputStream, boundary, sendName, sendValue);
					isSend = true;
				}
			}

			if (sendFiles != null && sendFiles.size() > 0) {
				for (String sendName : sendFiles.keySet()) {
					Object sendValue = sendFiles.get(sendName);
					uploadFile(dataOutputStream, boundary, sendName, sendValue);
					isSend = true;
				}
			}

			if (isSend) {
				dataOutputStream.writeBytes(boundary + "--\r\n");
			}

		} catch (IOException e) {
			throw e;
		} finally {
			dataOutputStream.close();
		}
	}

	/**
	 * uploadData
	 *
	 * @param dataOutputStream
	 * @param sendData
	 * @throws IOException
	 */
	private static void uploadData(DataOutputStream dataOutputStream, Object sendData) throws IOException {
		if (sendData instanceof byte[]) {
			dataOutputStream.write((byte[]) sendData);
		} else {
			dataOutputStream.writeBytes((String) sendData);
		}
	}

	/**
	 * uploadData
	 *
	 * @param dataOutputStream
	 * @param sendName
	 * @param sendData
	 * @throws IOException
	 */
	private static void uploadData(DataOutputStream dataOutputStream, String boundary, String sendName, Object sendData) throws IOException {
		if (sendData == null) {
			return;
		}
		String sendValue;
		if (boundary == null || boundary.length() == 0) {

			if (sendData instanceof byte[]) {
				sendValue = urlEncode(sendName) + "=" + urlEncode(new String((byte[]) sendData));
				dataOutputStream.writeBytes(sendValue);
			} else if (sendData instanceof String) {
				sendValue = urlEncode(sendName) + "=" + urlEncode((String) sendData);
				dataOutputStream.writeBytes(sendValue);
			} else {
				// TODO
			}
		} else {
			if (sendData instanceof byte[]) {
				sendValue = boundary + "\r\n";
				dataOutputStream.writeBytes(sendValue);

				sendValue = "Content-Disposition: form-data; name=\"" + urlEncode(sendName) + "\"\r\n\r\n";
				dataOutputStream.writeBytes(sendValue);

				dataOutputStream.write((byte[]) sendData);
				dataOutputStream.writeBytes("\r\n");
			} else if (sendData instanceof String) {
				sendValue = boundary + "\r\n";
				dataOutputStream.writeBytes(sendValue);

				sendValue = "Content-Disposition: form-data; name=\"" + urlEncode(sendName) + "\"\r\n\r\n";
				dataOutputStream.writeBytes(sendValue);

				dataOutputStream.writeBytes((String) sendData);

				dataOutputStream.writeBytes("\r\n");
			} else {
				// TODO
			}
		}
	}

	/**
	 * uploadFile
	 *
	 * @param dataOutputStream
	 * @param boundary
	 * @param sendName
	 * @param sendFile
	 * @throws IOException
	 */
	private static void uploadFile(DataOutputStream dataOutputStream, String boundary, String sendName, Object sendFile) throws IOException {
		if (sendFile == null) {
			return;
		}

		String fileName = null;
		String contentType = null;
		DataInputStream postStream = null;

		if (sendFile instanceof File) {
			fileName = ((File) sendFile).getName();
			Files.probeContentType(Paths.get(((File) sendFile).getPath()));
			postStream = new DataInputStream(new FileInputStream((File) sendFile));
		} else if (sendFile instanceof PostFile) {
			fileName = ((PostFile) sendFile).getName();
			contentType = ((PostFile) sendFile).getContentType();
			postStream = new DataInputStream(new ByteArrayInputStream(((PostFile) sendFile).getData()));
		} else if (sendFile instanceof byte[]) {
			postStream = new DataInputStream(new ByteArrayInputStream((byte[]) sendFile));
		} else {
			return;
		}

		// get a default file name
		if (null == fileName || fileName.length() < 1) {
			fileName = sendName;
		}

		// get a default content-type
		if (null == contentType || contentType.length() < 1) {
			String extName = "";
			int a = fileName.lastIndexOf(".");
			if (a > 0) {
				extName = fileName.substring(a + 1).trim().toLowerCase();
				// if (Constants.MIME_LIST.containsKey(extName)) {
				// contentType = Constants.MIME_LIST.get(extName);
				// }
				contentType = "application/octet-stream-" + extName;
			}
			if (null == contentType) {
				contentType = "application/octet-stream";
			}
		}

		String sendValue = null;

		sendValue = boundary + "\r\n";
		dataOutputStream.writeBytes(sendValue);

		sendValue = "Content-Disposition: form-data; name=\"" + urlEncode(sendName) + "\"; filename=\"" + urlEncode(fileName) + "\"\r\n";
		dataOutputStream.writeBytes(sendValue);

		sendValue = "Content-Type: " + contentType + "\r\n\r\n";
		dataOutputStream.writeBytes(sendValue);

		postStream.close();

		dataOutputStream.writeBytes("\r\n");
	}

	/**
	 * urlEncode
	 *
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static String urlEncode(String data) throws UnsupportedEncodingException {	
		return URLEncoder.encode(data, "UTF-8");		
	}
	
	/**
	 * 设置默认的UserAgent
	 *
	 * @param userAgent
	 */
	public void setDefaultUserAgent(String userAgent) {
		defaultUserAgent = userAgent;
	}
	
	/**
	 * 是否自动处理302
	 * 
	 * @param value
	 */
	public void setAutoRedirect(boolean value) {
		autoRedirect = value;
	}

	/**
	 * 是否启用cookie
	 *
	 * @param value
	 */
	public void setUseCookie(boolean value) {
		if (value) {
			if (cookieManager == null) {
				cookieManager = new CookieManager();
			}
		} else {
			if (cookieManager != null) {
				cookieManager = null;
			}
		}
	}
}
