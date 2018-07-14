package cn.edu.zucc.g4.interceptor;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.LogBean;
import cn.edu.zucc.g4.service.LogService;

public class LogInterceptor implements HandlerInterceptor {
	@Resource(name="logService")
	LogService logService;
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String log = (String)request.getAttribute("log_msg");
		if(log!=null&&!log.equals("")){
			LogBean logBean=new LogBean();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			logBean.setLog_time(time);
			logBean.setMessage(log);
			logBean.setUser_id((String)request.getSession().getAttribute("userId"));
			logService.addLog(logBean);
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		
		if(url.indexOf("/login")>=0){
			
			return true;
		}
		HttpSession  session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		if(userId==null||userId.equals("")){
			request.getRequestDispatcher("login.html").forward(request, response);;
			return false;
		}
		else{
			return true;
		}
	}
	
}
