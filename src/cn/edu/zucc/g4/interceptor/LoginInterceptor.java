package cn.edu.zucc.g4.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(333);
		String url = request.getRequestURI();
		
		if(true){
			
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
