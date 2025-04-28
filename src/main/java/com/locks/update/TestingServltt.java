package com.locks.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< Updated upstream
import javax.servlet.http.Part;

=======

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
>>>>>>> Stashed changes
import org.json.JSONObject;

/**
 * Servlet implementation class TestingServltt
 */

@WebServlet("/TestingServltt")
public class TestingServltt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestingServltt() {
		super();
		// TODO Auto-generated constructor stub

		System.out.println("inside constructor");
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		System.out.println("inside init");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.init();
		resp.getWriter().append("Served at: ").append(request.getContextPath());

		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "*");

		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setCharacterEncoding("UTF-8");

		System.out.println(" Got Request From the npm server");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);

		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE");

		resp.setCharacterEncoding("UTF-8");

		String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
				.collect(Collectors.joining("/"));
		JSONObject requestedObject = new JSONObject(jsonBody);

		System.out.println(requestedObject);

//		System.out.println(imageBytePart);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();

		System.out.println("destroy");
	}

}
