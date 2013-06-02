package ar.edu.itba.olap.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtro que captura excepciones y muestra una página de error.
 */
public class ErrorFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void destroy() {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		try {
			chain.doFilter(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error_message", "Ha ocurrido un error");
			

			req.setAttribute("errorStackTrace", e.toString());
			
			
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
		}
	}
}
