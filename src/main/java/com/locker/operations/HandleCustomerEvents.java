package com.locker.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.json.JSONObject;

import com.auro.beans.CustomerDetails;
import com.auro.beans.CustomerEvents;
import com.auro.hibernateUtilities.HibernateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opDTO.beans.FollowUpTransactionDetails;
import com.opDTO.beans.SQLDateAdaptor;
import com.opDTO.beans.SQLTimeAdaptor;

//import com.opDTO.beans.FollowUpTransactionDetails;

/**
 * Servlet implementation class HandleCustomerEvents
 */
@WebServlet("/customer-events")
public class HandleCustomerEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleCustomerEvents() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		response.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE, OPTIONS, DELETE");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();

		String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
				.collect(Collectors.joining("/"));
		JSONObject reqObj = new JSONObject(jsonBody);

//		System.out.println(reqObj);
		JSONObject respObj = new JSONObject();

		try {
			if (reqObj.getString("PacketType").equals("GET-TD")) {

				Session session = HibernateUtils.getSession();
				session.beginTransaction();

				try {
//					FollowUpTransactionDetails

					String hql = "SELECT new com.opDTO.beans.FollowUpTransactionDetails("
							+ "t.slno, t.customerName, t.mobileNo, t.date_of_open, t.time_of_open, "
							+ "t.terminalid, t.no_of_hours, t.amount, t.status, " + "t.excess_hours, t.excess_amount, "
							+ "t.lockNo, t.passcode,t.balance, t.itemsStored, "
							+ "t.browtype, t.partretamount) FROM TransactionDetails t WHERE "
							+ "t.excess_amount > 0 OR partretamount > 0";

					List<FollowUpTransactionDetails> followUpDetails = session.createQuery(hql).getResultList();

					if (followUpDetails.size() > 0) {
						respObj.put("response", HttpServletResponse.SC_OK);
						Gson gson = new Gson();
						String details = gson.toJson(followUpDetails);

						respObj.put("tdDetails", details);
					} else {
						respObj.put("response", HttpServletResponse.SC_NO_CONTENT);
					}
				} catch (Exception e) {
					// TODO: handle exception
					respObj.put("response", HttpServletResponse.SC_NO_CONTENT);
					e.printStackTrace();
				} finally {
					if (session != null) {
						session.close();
					}

					writer.append(respObj.toString());
				}

			} else if (reqObj.getString("PacketType").equals("GET-FOLLOW-DATA")) {

			} else if (reqObj.getString("PacketType").equals("GET-FOLLOW-BY-DATE")) {

				Session session = HibernateUtils.getSession();
				session.beginTransaction();
//				System.out.println(reqObj);

				Date selDate = Date.valueOf(reqObj.get("selectedDate").toString());
				
//				System.out.println(selDate);

				String hql = "FROM CustomerEvents WHERE eventDate=:date";

				try {
					List<CustomerEvents> customerEvents = session.createQuery(hql).setParameter("date", selDate)
							.getResultList();

					if (customerEvents.size() > 0) {
						respObj.put("response", HttpServletResponse.SC_OK);
						Gson gson = new Gson();
						String events = gson.toJson(customerEvents);
						respObj.put("customers", events);
					} else {
						respObj.put("response", HttpServletResponse.SC_NOT_FOUND);
					}
				} catch (Exception e) {
					// TODO: handle exception
					respObj.put("response", HttpServletResponse.SC_NOT_FOUND);
				} finally {
					writer.append(respObj.toString());

					if (session != null) {
						session.close();
					}
				}

			} else if (reqObj.getString("PacketType").equals("STORE-FOLLOW-DATA")) {

//				Gson gson = new Gson();

//				Gson gson = new GsonBuilder()
//						.setDateFormat("HH:mm:ss").create();

				Gson gson = new GsonBuilder().registerTypeAdapter(Time.class, new SQLTimeAdaptor())
						.registerTypeAdapter(Date.class, new SQLDateAdaptor()).create();

				Session session = HibernateUtils.getSession();
				session.beginTransaction();

//				System.out.println(jsonBody);
//				System.out.println(reqObj);

//				JSONObject obj = new JSONObject(reqObj.get("customer").toString());
//				
//				System.out.println(obj);
//				
//				Time conTime = Time.valueOf(obj.get("eventTime").toString());
//				
//				System.out.println(conTime);
//				
				try {

//					String obb = "{\"eventTriggeredUser\":\"satish\",\"city\":\"\",\"eventTime\":\"13:32:39\",\"eventType\":\"partial-retrieve\",\"terminalId\":\"ORN\",\"mobileNo\":\"2222222222\",\"state\":\"\",\"lockerNo\":\"S1\",\"customerName\":\"bharat\",\"remarks\":\"ddddd\",\"slno\":0,\"eventDate\":\"2025-01-25\"}";

//					CustomerEvents customerEvents = gson.fromJson(obb, CustomerEvents.class);
					CustomerEvents customerEvents = gson.fromJson(reqObj.get("customer").toString(),
							CustomerEvents.class);

					String hql = "SELECT city, state FROM SiteRegistration where terminalid=:terminalID";

					List<Object[]> terminalIdDetails = session.createQuery(hql)
							.setParameter("terminalID", customerEvents.getTerminalId()).getResultList();

//					System.out.println(reqObj.get("customer").toString());

//					System.out.println(customerEvents.getEventDate());
//					System.out.println(customerEvents.getEventTime());
//					System.out.println(customerEvents.getSlno());
//					System.out.println(customerEvents.getCustomerName());
//					System.out.println(customerEvents.getEventTriggeredUser());
//					System.out.println(customerEvents.getLockerNo());
//					System.out.println(customerEvents.getEventType());
//					System.out.println(customerEvents.getCity());
//					System.out.println(customerEvents.getMobileNo());
//					System.out.println(customerEvents.getRemarks());
//					System.out.println(customerEvents.getState());
//					System.out.println(customerEvents.getTerminalId());

					if (terminalIdDetails.size() == 1) {
						for (Object[] objects : terminalIdDetails) {
							customerEvents.setCity((String) objects[0]);
							customerEvents.setState((String) objects[1]);
						}

						int op = (int) session.save(customerEvents);
						session.getTransaction().commit();
						if (op > -1) {
							respObj.put("response", HttpServletResponse.SC_OK);
						} else {
							respObj.put("response", HttpServletResponse.SC_BAD_REQUEST);
						}

					}

					else {
						respObj.put("response", HttpServletResponse.SC_BAD_REQUEST);
					}

				} catch (Exception e) {
					// TODO: handle exception
					respObj.put("response", HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				} finally {
					writer.append(respObj.toString());

					if (session != null) {
						session.close();
					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			respObj.put("response", HttpServletResponse.SC_NOT_FOUND);
			writer.append(respObj.toString());
			e.printStackTrace();
		} finally {

//			System.out.println(respObj);

			writer.flush();

			writer.close();
		}

	}

}
