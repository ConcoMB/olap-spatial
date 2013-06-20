package olap.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RestrictedFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getRequestURI().matches(".*/bin.*") || request.getRequestURI().matches(".*/css.*") || request.getRequestURI().matches(".*/img.*") || request.getRequestURI().matches(".*/js.*")) {
			filterChain.doFilter(request, response);
		} else {
			response.sendRedirect("/bin/index");
		}
	}

}