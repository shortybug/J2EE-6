package sc.ustc.interceptor;

import java.util.Map;

public class InterceptorCheck {
	public Boolean interExist(String actionName,Map map){
		Boolean b = false;
		if((map.get("interceptro-ref"))!=null){
			b = true;
		}
		return b;
	}
}
