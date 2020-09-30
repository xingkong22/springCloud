package com.cloud.oauth2demo.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Oauth2Post {

    private static Log log = LogFactory.getLog(Oauth2Post.class);

    // 获取token
    public static String getOauthToken(){
        Map<String, String> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "123456");
        params.put("grant_type", "password");
        params.put("client_id", "client_id");
        params.put("client_secret", "client_secret");
        params.put("scope", "scope");

        String backTokenUrl = "http://172.11.1.154:10008/oauth/token";
        String returnCode = AuthHttpPost(backTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(returnCode);
        String access_token = jsonObject.get("access_token").toString();
        System.out.println("access_token:" + access_token);
        return access_token;
    }


    // 替换-获取新token
    public static String refreshToken(String refresh_token){
        Map<String, String> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "123456");
        params.put("grant_type", "refresh_token");
        params.put("client_id", "client_id");
        params.put("client_secret", "client_secret");
        params.put("scope", "scope");
        params.put("refresh_token", refresh_token);

        String backTokenUrl = "http://172.11.1.154:10008/oauth/token";
        String returnCode = AuthHttpPost(backTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(returnCode);
        String access_token = jsonObject.get("access_token").toString();
        System.out.println("access_token:" + access_token);
        return access_token;
    }

    /*
      * @Method AuthHttpPost
      * @Description TODO 登陆获取token
      * @Params  * @param url :
     * @param params :
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 4:58
      */
    public static String AuthHttpPost(String url,Map<String,String> params) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        String result = "";
        HttpPost httpPost = new HttpPost(url);       // 拼接参数
        //添加http头信息
//        httpPost.addHeader("Authorization", getHeader(params.get("client_id"),params.get("client_secret")));
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("client_id", params.get("client_id")));
        list.add(new BasicNameValuePair("client_secret", params.get("client_secret")));
        list.add(new BasicNameValuePair("grant_type", params.get("grant_type")));
        list.add(new BasicNameValuePair("username", params.get("username")));
        list.add(new BasicNameValuePair("password", params.get("password")));
        list.add(new BasicNameValuePair("scope", params.get("scope")));
        System.out.println("==== 提交参数 ======" + list);
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            System.out.println("==== 提交参数 ======" + list);
            System.out.println("========HttpResponseProxy：========" + statusCode);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
                System.out.println("========接口返回=======" + result);
            }
            EntityUtils.consume(entity);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /*
      * @Method getHeader
      * @Description TODO 构造Basic Auth认证头信息
      * @Params  * @param clientId :
     * @param clientSecret :
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 4:58
      */
    private static String getHeader(String clientId, String clientSecret) {
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    /*
      * @Method AuthHttpDelete
      * @Description TODO 注销登录功能专用 暂时没用到
      * @Params  * @param url :
     * @param params :
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 4:58
      */
    public static String AuthHttpDelete(String url,Map<String,String> params) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        String result = "";
        HttpDelete httpDelete = new HttpDelete(url+"?access_token="+params.get("access_token"));
        httpDelete.addHeader("Authorization", getHeader(params.get("username"),params.get("password")));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpDelete.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            System.out.println("========HttpResponseProxy：========" + statusCode);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
                System.out.println("========接口返回=======" + result);
            }
            EntityUtils.consume(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
