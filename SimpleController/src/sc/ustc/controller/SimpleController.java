package sc.ustc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import sc.ustc.analyzeXML.analyzeXML;
import sc.ustc.createHTML.CreateHtml;
import sc.ustc.interceptor.InterceptorCheck;
import sc.ustc.tool.CglibProxyX;

public class SimpleController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//b判断action中存在interceptor
    	Boolean b = false;
    	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//Date date = new Date();
    	
    	response.setContentType("text/html;charset=utf-8");
    	response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        //获取url
        String getUrl = request.getServletPath().toString();
        String[] splitUrl = getUrl.split("/");
        //获取actionname
        String actionName = splitUrl[splitUrl.length - 1].substring(0,splitUrl[splitUrl.length - 1].indexOf("."));
        
        //controller的路径
        String path = this.getServletContext().getRealPath("WEB-INF/classes/controller.xml");
        
        String path2 = this.getServletContext().getRealPath("");
        //log.xml路径
        String logPath = "F:\\myeclipse-workspace\\UseSC\\src\\log.xml";
        //解析xml
        analyzeXML hzx = new analyzeXML();
        Map<String, String> xmlMap = hzx.readXML(actionName,path);
        
        //声明一个InterceptorCheck类
        InterceptorCheck check = new InterceptorCheck();
        
        //声明InterceptorAction类
        //InterceptorAction returnAction = new InterceptorAction();  
        if (!xmlMap.isEmpty()) {
        	xmlMap.put("logPath",logPath);
        	b = check.interExist(actionName,xmlMap);
        	
            String className = xmlMap.get("class");
            String methodName = xmlMap.get("method");
            try {
            	
                Class cl = Class.forName(className);
                //Method m = cl.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class,Map.class,String.class);
                Method m;
                String result;
                if(b == true){
                	CglibProxyX proxy = new CglibProxyX(actionName, xmlMap, request, response);
            	    Enhancer enhancer = new Enhancer();
            	    enhancer.setSuperclass(cl);
            	    enhancer.setCallback(proxy);
            	    HttpServlet obj = (HttpServlet) enhancer.create();
                	m = obj.getClass().getDeclaredMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
                	// result = (String) m.invoke(clc, request, response, xmlMap,actionName);

                	//result = (String) m.invoke(cl.newInstance(), request, response);
                	result = (String) m.invoke(obj, request, response);
                }else{
                	m = cl.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                	result = (String) m.invoke(cl.newInstance(), request, response);
                }
                System.out.println(cl.getName());
                System.out.println(m.getName());
                
                String resName = xmlMap.get("result" + result);
                String resType = resName.substring(0, resName.indexOf("+"));
               	String resValue = resName.substring(resName.indexOf("+") + 1);
               	
               	CreateHtml ch = new CreateHtml();

               	String viewValue = resValue.substring(resValue.indexOf("."));
               	if(viewValue.equals("xml")){
               		ch.ctreathtml(path2);
               	}
               	
                if (resType.equals("foward")) {
               		request.getRequestDispatcher(resValue).forward(request, response);
                } else if (resType.equals("redirect")) {
                	response.sendRedirect(resValue);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
        } else {
            response.sendRedirect("/UseSC/Login.jsp");
        }
    }
    
}
