package xluo.github.spring.springcrud.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequest.class);

    /**
     *
     * sendGet(HTTP get请求)
     * @Title: sendPost
     * @Description: TODO
     * @param @param Url
     * @param @param params
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String sendGet(String apiUrl, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = apiUrl + "?" + params;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOG.error("invoke get throw exception, details: ", e);
            return "998";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                LOG.error("invoke get throw exception, details: ", e2);
            }
        }
        return result;
    }

    /**
     *
     * sendPost(HTTP post请求)
     * @Title: sendPost
     * @Description: TODO
     * @param @param apiUrl
     * @param @param params
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String sendPost(String apiUrl, Map<String, Object> params) {
        HttpURLConnection conn;
        StringBuilder result = new StringBuilder("");
        BufferedReader bufferedReader=null;
        try {
            URL url = new URL(apiUrl);
            // 组织请求参数
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null)
                    continue;
                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), "utf-8")).append("&");
            }

            if (!params.isEmpty()) {
                postBody.deleteCharAt(postBody.length() - 1);
            }

            conn = (HttpURLConnection) url.openConnection();
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(5000);
            // 设置读取超时
            conn.setReadTimeout(5000);
            // 提交参数
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(postBody.toString().getBytes());
            conn.getOutputStream().flush();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                LOG.error("invoke failed,request url:{}, response status:{}", url, responseCode);
                return  ServletUtils.trimNull(responseCode);
            }
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            LOG.error("invoke post throw exception, details: ", e);
            return "999";
        }// 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                LOG.error("invoke post throw exception, details: ", e2);
            }
        }
        return result.toString();
    }


    /**
     *
     * sendPost(HTTP post请求)
     * @Title: sendPost
     * @Description: TODO
     * @param @param apiUrl
     * @param @param params
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String sendPostUseJson(String apiUrl,String content) {
        HttpURLConnection conn;
        StringBuilder result = new StringBuilder("");
        BufferedReader bufferedReader =null;
        int retry=3;
        while (retry > 0) {
            try {
                URL url = new URL(apiUrl);
                // 组织请求参数

                conn = (HttpURLConnection) url.openConnection();
                // 设置长链接
                conn.setRequestProperty("Connection", "Keep-Alive");
                // 设置格式
                conn.setRequestProperty("Content-Type", "application/json");
                // 设置连接超时
                conn.setConnectTimeout(60000);
                // 设置读取超时
                conn.setReadTimeout(60000);
                // 提交参数
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(ServletUtils.trimNull(content).getBytes());
                conn.getOutputStream().flush();
                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    LOG.error("sendPostUseJson failed,request url:{}, response status:{}", url, responseCode);
                    return ServletUtils.trimNull(responseCode);
                }
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } catch (SocketTimeoutException e) {
                // TODO: handle exception
                LOG.error("sendPostUseJson throw SocketTimeoutException,try {} times, details: ",4-retry, e);
                retry--;
                continue;
            } catch (Exception e) {
                LOG.error("sendPostUseJson throw exception, details: ", e);
                return "999";
            }
            // 使用finally块来关闭输出流、输入流
            finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception e2) {
                    LOG.error("invoke sendPostUseJson throw exception, details: ", e2);
                }
            }
            break;
        }
        return result.toString();
    }


}