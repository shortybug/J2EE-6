package water.ustc.interceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import water.ustc.writeXML.writeXml;

public class LogInterceptor extends HttpServlet {
	private static String startTime;
    private static String endTime;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String name;
    private static String path;
    private static String result;
	private static final long serialVersionUID = 1L;
	public void preAction(String actionname,String logpath,HttpServletRequest request, HttpServletResponse response) throws IOException{
		path = logpath;
		name = actionname;
		startTime = sdf.format(new Date());
	}
	public void afterAction(String actionresult,HttpServletRequest request, HttpServletResponse response) throws IOException{
		//System.out.println(path);
		//System.out.println(5+5);
		result = actionresult;
		endTime = sdf.format(new Date());
		writeXml writetolog = new writeXml();
		writetolog.writeToLog(name, path, startTime,result,endTime);
	}
}
