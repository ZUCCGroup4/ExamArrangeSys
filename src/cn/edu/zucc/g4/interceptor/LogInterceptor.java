package cn.edu.zucc.g4.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.LogBean;
import cn.edu.zucc.g4.dao.LogDAO;

public class LogInterceptor implements HandlerInterceptor {
	@Resource(name="logDAO")
	LogDAO logDAO;
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(444);
		String log = (String)request.getAttribute("log_msg");
		if(log!=null&&!log.equals("")){
			LogBean logBean=new LogBean();
			logBean.setLog_time(new Date());
			logBean.setMessage(log);
			logBean.setUser_id((String)request.getSession().getAttribute("userId"));
			logDAO.addLog(logBean);
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
