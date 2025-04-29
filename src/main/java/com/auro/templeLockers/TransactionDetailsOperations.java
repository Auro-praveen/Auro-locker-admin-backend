package com.auro.templeLockers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.auro.hibernateUtilities.HibernateUtils;

/**
 * Servlet implementation class TransactionDetailsOperations
 */

@WebServlet("/TransactionDetailsOperations")
public class TransactionDetailsOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionDetailsOperations() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE, OPTIONS, DELETE");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		String reqType = request.getParameter("type");
		JSONObject respObject = new JSONObject();
		JSONArray locArr = new JSONArray();

		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		if (reqType.equalsIgnoreCase("locId")) {

			String hql = "SELECT DISTINCT(siteID) FROM SiteRegistration";

			try {

				ArrayList<String> siteIds = (ArrayList<String>) session.createQuery(hql).getResultList();
				transaction.commit();

				if (!siteIds.isEmpty()) {
					respObject.put("lockId", siteIds);
					respObject.put("responsecode", "status-200");
				} else {
					respObject.put("responsecode", "status-500");
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				respObject.put("responsecode", "status-404");
			} finally {
				if (session != null) {
					session.close();
				}
			}

		} else if (reqType.equalsIgnoreCase("termId")) {
			String locId = request.getParameter("locationID");
			String hql;
			
//			System.out.println("requested locID : "+locId);

			if (locId.equalsIgnoreCase("ALL")) {
				hql = "SELECT DISTINCT(terminalid) FROM SiteRegistration"; // WHERE terminalid NOT IN ('G21')
			} else {
				hql = "SELECT DISTINCT(terminalid) FROM SiteRegistration WHERE siteId='" + locId + "'"; // AND																			// ('G21')
			}

			try {

				ArrayList<String> terminalid = (ArrayList<String>) session.createQuery(hql).getResultList();
				transaction.commit();
				
//				System.out.println("requested locID : "+terminalid);

				if (!terminalid.isEmpty()) {
					respObject.put("lockId", terminalid);
					respObject.put("responsecode", "status-200");
				} else {
					respObject.put("responsecode", "status-500");
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				respObject.put("responsecode", "status-404");
			} finally {
				if (session != null) {
					session.close();
				}

			}

		}

		writer.append(respObject.toString()).flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE, OPTIONS, DELETE");
		response.setCharacterEncoding("UTF-8");

	}

}
