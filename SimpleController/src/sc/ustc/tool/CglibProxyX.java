package sc.ustc.tool;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
public class CglibProxyX implements MethodInterceptor {
	private String actionname;
	private String logpath;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map map;
	private static String startTime;
    private static String endTime;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String name;
    private static String path;
    
	public CglibProxyX(String actionName, Map map, HttpServletRequest request, HttpServletResponse response) {
		this.actionname = actionName;
		this.map = map;
		this.logpath = (String) map.get("logPath");
		this.request = request;
		this.response = response;
	}
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		String preClassName = (String) map.get("interclass");
        String preMethodName = (String) map.get("predo");
        Class c1 = Class.forName(preClassName);
        Method m1 = c1.getDeclaredMethod(preMethodName, String.class,String.class,HttpServletRequest.class, HttpServletResponse.class);
        m1.invoke(c1.newInstance(), actionname,logpath,request, response);
        
		String result = (String) proxy.invokeSuper(object, args);
		
		String afterClassName = (String) map.get("interclass");
        String afterMethodName = (String) map.get("afterdo");
        Class c2 = Class.forName(afterClassName);
        Method m2 = c2.getDeclaredMethod(afterMethodName, String.class,HttpServletRequest.class, HttpServletResponse.class);
        m2.invoke(c2.newInstance(),result,request, response);
        
		return result;
	    }
}
