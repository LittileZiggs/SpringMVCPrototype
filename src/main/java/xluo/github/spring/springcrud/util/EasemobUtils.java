package xluo.github.spring.springcrud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xluo.github.spring.springcrud.model.ImUser;
import xluo.github.spring.springcrud.service.im.impl.ImServiceImpl;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by xiaoluo on 17-7-25.
 */
public class EasemobUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EasemobUtils.class);

    public static String baseUrl = "";
    public static String appKey = "";
    public static String clientId = "";
    public static String clientSecret = "";

    static {
        Properties properties = new Properties();
        URL url=ImServiceImpl.class.getClassLoader().getResource("easemob.properties");
        try {
            InputStream is = url.openStream();
            properties.load(is);
            baseUrl = properties.getProperty("easemob.api.baseurl");
            appKey = properties.getProperty("easemob.appkey");
            clientId = properties.getProperty("client.id");
            clientSecret = properties.getProperty("client.secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String register(ImUser imUser) {
        try {
            HttpPost httpPost = new HttpPost(getRegisterUrl(baseUrl, appKey));
            ByteArrayEntity entity = new ByteArrayEntity(JSON.toJSONString(imUser).getBytes(Charsets.UTF_8));
            httpPost.setEntity(entity);
            httpPost.addHeader(new BasicHeader("Authorization", "Bearer " + getToken()));
            CloseableHttpResponse response = HttpUtils.getInstance().execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("easemob register response code != 200", response.getStatusLine().getStatusCode());
                return "fail";
            } else {
                JSONObject responseObject = JSON.parseObject(new String(EntityUtils.toByteArray(response.getEntity())));
                if (responseObject == null) {
                    return "fail";
                }
                if (responseObject.containsKey("err_status")) {
                    // 参见 http://www.easemob.com/docs/rest/userapi/#im
                    if (responseObject.getIntValue("err_status") == 400) {
                        return "success";
                    }
                }

                // 重复注册返回成功
                String error = responseObject.getString("error");
                if (error != null && error.contains("duplicate_unique_property_exists")) {
                    return "success";
                }

                JSONArray array = responseObject.getJSONArray("entities");
                if (array != null && array.size() > 0) {
                    return "success";
                } else {
                    return "fail";
                }
            }

        } catch (Exception e) {
            LOG.warn("Failed to create user to Easemob.", e);
            return "fail";
        }
    }

    private static String getRegisterUrl(String baseUrl, String appKey) {
        return baseUrl + "/" + appKey.replace("#", "/") + "users";
    }


    public static String getToken() {
        HttpPost httpPost = new HttpPost(getTokenUrl(baseUrl, appKey));
        JSONObject json = new JSONObject();
        json.put("grant_type", "client_credentials");
        json.put("client_id", clientId);
        json.put("client_secret", clientSecret);
        ByteArrayEntity entity = new ByteArrayEntity(json.toJSONString().getBytes(Charsets.UTF_8));
        try {
            CloseableHttpResponse response = HttpUtils.getInstance().execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("easemob getToken response code != 200", response.getStatusLine().getStatusCode());
                return null;
            } else {
                return JSON.parseObject(new String(EntityUtils.toByteArray(response.getEntity()))).getString("access_token");
            }
        } catch (IOException e) {
            LOG.error("easemob getToken IOException", e);
            return null;
        }
    }

    private static String getTokenUrl(String baseUrl, String appKey) {
        return baseUrl + "/" + appKey.replace("#", "/") + "token";
    }
}
