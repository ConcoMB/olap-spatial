package olap.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ErrorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
		try{
			filterChain.doFilter(request, response);
		}catch(Exception e){
			//request.setAttribute("error_message", "Ha ocurrido un error");
			//request.setAttribute("errorStackTrace", e.toString());
			response.sendRedirect("/olap-spatial/bin/error/view?error_message=Ha ocurrido un error&errorStackTrace="+e.toString());
		}
	}

}