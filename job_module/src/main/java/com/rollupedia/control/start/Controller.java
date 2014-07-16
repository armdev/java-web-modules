package com.rollupedia.control.start;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rollupmedia.control.jobs.JobsManager;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action") ;
		String param1 = request.getParameter("param1") ;
		String param2 = request.getParameter("param2") ;
		
		if ("startJob".equals(action)) {
			JobsManager.getInstance().startJob(param1, param2) ;
		}
		
		if ("startGroup".equals(action)) {
			JobsManager.getInstance().startGroup(param1) ;
		}
		
		response.sendRedirect(request.getHeader("Referer")) ;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response) ;
	}

}
