package olap.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ConnectedFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(!(request.getRequestURI().matches(".*/bin/index") || request.getRequestURI().matches(".*/error/view")) && request.getSession().getAttribute("dbuser") == null ){
			response.sendRedirect("/bin/error/view?error_message=No connection&errorStackTrace=You must be connected to continue!");
		}else{
			filterChain.doFilter(request, response);
		}
	}
}
