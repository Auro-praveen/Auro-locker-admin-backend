package com.auto.user.logdetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.auro.beans.UserLogs;
import com.auro.hibernateUtilities.HibernateUtils;

/**
 * Servlet implementation class HandleUserLogsDetails
 */
@WebServlet("/handle-logs")
public class HandleUserLogsDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleUserLogsDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		java.util.Date date = new java.util.Date();
		date.getTime();

		// TODO Auto-generated method stub
		response.getWriter().append(
				"Served at: " + new Date(new java.util.Date().getTime()) + " " + new Time(System.currentTimeMillis()))
				.append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "*");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

		String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
				.collect(Collectors.joining("\n"));
		// System.out.println(jsonBody);
		JSONObject requestedJsonObject = new JSONObject(jsonBody);

		PrintWriter writer = response.getWriter();

		JSONObject logsObj = new JSONObject();

		JSONArray slno = new JSONArray();
		JSONArray username = new JSONArray();
		JSONArray eventType = new JSONArray();
		JSONArray eventDate = new JSONArray();
		JSONArray eventTime = new JSONArray();
		JSONArray remarks = new JSONArray();


		if (requestedJsonObject.getString("packetType").equals("STORE_LOGS")) {

			Session session = HibernateUtils.getSession();

			session.beginTransaction();

			try {
				UserLogs logs = new UserLogs();
				logs.setEventDate(new Date(new java.util.Date().getTime()));
				logs.setEventTime(new Time(new java.util.Date().getTime()));
				logs.setEventType(requestedJsonObject.getString("eventType"));
				logs.setRemarks(requestedJsonObject.getString("remarks"));
				logs.setUserName(requestedJsonObject.getString("username"));
				logs.setIpAddress(request.getRemoteAddr());

				int n = (int) session.save(logs);
				session.getTransaction().commit();

				if (n > 1) {
					logsObj.put("status", "success");
				} else {
					logsObj.put("status", "failed");
				}
			} catch (Exception e) {
				// TODO: handle exception
				logsObj.put("status", "failed");

			} finally {
				if (session != null) {
					session.close();

				}

				writer.append(logsObj.toString());
			}

//			logs.s

		} else if (requestedJsonObject.getString("packetType").equals("LOGS_BY_DATE")) {

			Session session = HibernateUtils.getSession();

			session.beginTransaction();

			try {

				Date selectedDate = Date.valueOf(requestedJsonObject.getString("date"));
				String user = requestedJsonObject.getString("username");

				String hql;
				ArrayList<UserLogs> userLogs;
				if (user.equals("ALL_USERS")) {
					hql = "FROM UserLogs WHERE eventDate=:date";
					userLogs = (ArrayList<UserLogs>) session.createQuery(hql).setParameter("date", selectedDate)
							.getResultList();
				} else {
					hql = "FROM UserLogs WHERE eventDate=:date AND userName=:uName";
					userLogs = (ArrayList<UserLogs>) session.createQuery(hql).setParameter("date", selectedDate)
							.setParameter("uName", user).getResultList();
				}

				if (userLogs.size() > 0) {
					logsObj.put("response", HttpServletResponse.SC_OK);

					for (UserLogs uLogs : userLogs) {
						slno.put(uLogs.getSlno());
						eventDate.put(uLogs.getEventDate());
						eventTime.put(uLogs.getEventTime());
						eventType.put(uLogs.getEventType());
						remarks.put(uLogs.getRemarks());
						username.put(uLogs.getUserName());
					}

					logsObj.put("slno", slno);
					logsObj.put("eventTime", eventTime);
					logsObj.put("eventDate", eventDate);
					logsObj.put("username", username);
					logsObj.put("eventType", eventType);
					logsObj.put("remarks", remarks);

				} else {
					logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);
				}

			} catch (Exception e) {
				// TODO: handle exception
				logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);

			} finally {
				if (session != null) {
					session.close();

				}

				writer.append(logsObj.toString());
			}

		} else if (requestedJsonObject.getString("packetType").equals("LOGS_BY_USER")) {

			Session session = HibernateUtils.getSession();

			session.beginTransaction();

			try {

				String user = requestedJsonObject.getString("username");

				String hql = "FROM UserLogs WHERE userName=:user";

				ArrayList<UserLogs> userLogs = (ArrayList<UserLogs>) session.createQuery(hql).setParameter("user", user)
						.getResultList();

				if (userLogs.size() > 0) {
					logsObj.put("response", HttpServletResponse.SC_OK);

					for (UserLogs uLogs : userLogs) {
						slno.put(uLogs.getSlno());
						eventDate.put(uLogs.getEventDate());
						eventTime.put(uLogs.getEventTime());
						eventType.put(uLogs.getEventType());
						remarks.put(uLogs.getRemarks());
						username.put(uLogs.getUserName());
					}

					logsObj.put("slno", slno);
					logsObj.put("eventTime", eventTime);
					logsObj.put("eventDate", eventDate);
					logsObj.put("username", username);
					logsObj.put("eventType", eventType);
					logsObj.put("remarks", remarks);

				} else {
					logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);
				}

			} catch (Exception e) {
				// TODO: handle exception
				logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);

			} finally {
				if (session != null) {
					session.close();

				}

				writer.append(logsObj.toString());
			}

		} else if (requestedJsonObject.getString("packetType").equals("GET_USERS")) {

			Session session = HibernateUtils.getSession();

			session.beginTransaction();

			try {

				String hql = "SELECT userName FROM User WHERE type NOT IN (:uType)";
				
				ArrayList<String> userLogs = (ArrayList<String>) session.createQuery(hql)
						.setParameter("uType", "Mall-Authority").getResultList();
				
//				System.out.println(userLogs);

				if (userLogs.size() > 0) {
					logsObj.put("response", HttpServletResponse.SC_OK);

					logsObj.put("users", userLogs);

				} else {
					logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);
				}

			} catch (Exception e) {
				// TODO: handle exception
				logsObj.put("response", HttpServletResponse.SC_NO_CONTENT);
				e.printStackTrace();

			} finally {
				if (session != null) {
					session.close();

				}

				writer.append(logsObj.toString());
			}

		}

		writer.flush();
		writer.close();

	}

}
