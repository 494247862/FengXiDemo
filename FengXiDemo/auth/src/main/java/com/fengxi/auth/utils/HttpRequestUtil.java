package com.fengxi.auth.utils;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpHeaders;

/**
 * http 请求工具类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: HttpRequestUtil
 * @projectName FengXiDemo
 * @date 2023/3/3 14:16:06
 */
@Slf4j
public class HttpRequestUtil {
    //设置字符编码
    private static final String CHARSET = "UTF-8";
    private static RequestConfig defaultRequestConfig = RequestConfig
            .custom()
            //设置等待数据超时时间
            .setSocketTimeout(30000)
            //设置连接超时时间
            .setConnectTimeout(30000)
            //设置从连接池获取连接的等待超时时间
            .setConnectionRequestTimeout(30000)
            //.setStaleConnectionCheckEnabled(true)
            .build();
    //释放资源，httpResponse为响应流，httpClient为请求客户端
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
    //get请求带参数、带请求头
    public static String get(String urlWithParams, Map<String, String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(urlWithParams);
        if (!MapUtils.isEmpty(header)) {
            header.forEach(httpget::addHeader);
        }
        CloseableHttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, CHARSET);
        httpget.releaseConnection();
        release(response, httpClient);
        return result;
    }
    public static String get(String urlWithParams) throws IOException {
        return get(urlWithParams, null);
    }
    //发送post请求，带json请求体和请求头
    public static String post(String url, String json, Map<String, String> headersMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.add(entry.getKey(),entry.getValue());
        }
        org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
