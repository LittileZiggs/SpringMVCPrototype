package xluo.github.spring.springcrud.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoluo on 17-7-18.
 */
public class ServletUtils {
    /**
     * 处理空格
     * @param request
     * @param parameterName
     * @return
     */
    public static String getParameter(HttpServletRequest request, String parameterName){
        String value=request.getParameter(parameterName);
        return value==null?null:value.trim();
    }
}
