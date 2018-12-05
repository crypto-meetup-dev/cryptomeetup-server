package com.ly.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient调用工具类
 */
public class HttpClientUtil {
    private static final Log logger = LogFactory.getLog("httpClient");

    /**
     * 发送GET请求
     *
     * @param uri
     * @return
     */
    public static String sendGet(String uri) {
        String responseBody = null;
        HttpClient httpClient = HttpClients.createDefault();
        // 设置超时时间
        try {

            URIBuilder builder = new URIBuilder(uri);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setConfig(requestConfig);
            logger.info("executing request " + httpGet.getURI());
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpGet, responseHandler);
            logger.info(responseBody);
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return responseBody;
    }


    /**
     * 发送POST请求
     *
     * @param uri
     * @param paramMap 请求参数
     * @return
     */
    public static String sendPost(String uri, Map<String, String> paramMap) {
        return sendPost(uri, paramMap, null);
    }

    /**
     * 发送json
     *
     * @param uri
     * @param paramMap
     * @return
     */
    public static String sendPostJson(String uri, Map<String, String> paramMap) {
        String responseBody = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder(uri);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(JSON.toJSONString(paramMap)));
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpPost, responseHandler);
            logger.info("----------------------------------------");
            logger.info(responseBody);
            logger.info("----------------------------------------");
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    /**
     * 发送POST请求
     *
     * @param uri
     * @param paramMap 请求参数
     * @param charset  参数编码
     * @return
     */
    public static String sendPost(String uri, Map<String, String> paramMap,
                                  String charset) {
        String responseBody = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder(uri);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setConfig(requestConfig);
            logger.info("executing request " + httpPost.getURI());
            if (paramMap != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>(paramMap.size());
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    nvps.add(nvp);
                }
                if (charset != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
                } else {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                }
            }
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpPost, responseHandler);
            logger.info("----------------------------------------");
            logger.info(responseBody);
            logger.info("----------------------------------------");
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return responseBody;

    }

    /**
     * Extends the post data
     */
    public static String sendPostRequest(String _url, String data) {

        try {
            // Send the request
            URL url = new URL(_url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            // write parameters
            if (data != null) {
                writer.write(data);
            }
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            // Output the response
            return answer.toString();

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 可以处理中文乱码，
     */
    public static String postData(String url, String data) {
        StringBuilder sb = new StringBuilder();
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = null;

        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        try {

            HttpClient client = HttpClients.createDefault();
            StringEntity payload = new StringEntity(data, "UTF-8");
            httpPost.setEntity(payload);
            HttpResponse response = client.execute(httpPost);
            entity = response.getEntity();
            String text;
            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text);
                }

            }
        } catch (Exception e) {
            logger.error("与[" + url + "]通信过程中发生异常,堆栈信息如下", e.getCause());
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                ex.printStackTrace();
                logger.error("net io exception");
            }
        }
        return sb.toString();
    }

}
