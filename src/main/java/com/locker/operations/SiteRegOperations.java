package com.locker.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.auro.beans.SiteRegistration;
import com.auro.hibernateUtilities.HibernateUtils;

/**
 * 
 * Servlet implementation class SiteRegOperations
 * 
 */

@WebServlet("/SiteRegOperations")
public class SiteRegOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "POST, GET, UPDATE, OPTIONS, DELETE");
		response.setCharacterEncoding("UTF-8");

		String terminalid = request.getParameter("terminalid");
		Session session = HibernateUtils.getSession();
		JSONObject siteRegisterObject = new JSONObject();
		Writer writer = response.getWriter();

		try {

			session.beginTransaction();
			String hql = "from SiteRegistration where terminalid=:terminalID";

			SiteRegistration siteRegistration = (SiteRegistration) session.createQuery(hql)
					.setParameter("terminalID", terminalid).getSingleResult();

			if (siteRegistration != null) {

//				System.out.println(siteRegistration.getArea());
//				System.out.println(siteRegistration.getAreaCode());

				siteRegisterObject.put("slno", siteRegistration.getSlno());
				siteRegisterObject.put("terminalId", siteRegistration.getTerminalid());
				siteRegisterObject.put("siteId", siteRegistration.getSiteID());
				siteRegisterObject.put("siteName", siteRegistration.getSiteName());
				siteRegisterObject.put("noOfLockers", siteRegistration.getNo_of_locks());
				siteRegisterObject.put("areaCode", siteRegistration.getAreaCode());
				siteRegisterObject.put("areaName", siteRegistration.getArea());
				siteRegisterObject.put("cityName", siteRegistration.getCity());
				siteRegisterObject.put("state", siteRegistration.getState());
				siteRegisterObject.put("imeiNumber", siteRegistration.getImeiNo());
				siteRegisterObject.put("mobileNumber", siteRegistration.getMobileNo());
				siteRegisterObject.put("userName", siteRegistration.getUserName());
				siteRegisterObject.put("lattitude", siteRegistration.getLattitude());
				siteRegisterObject.put("longitude", siteRegistration.getLongitude());
				siteRegisterObject.put("siteStatus", siteRegistration.getStatus());

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
		}

//		System.out.println(siteRegisterObject);

		writer.append(siteRegisterObject.toString());
		writer.flush();
		writer.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "*");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
//		response.setStatus(HttpServletResponse.SC_OK);

		String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
				.collect(Collectors.joining("\n"));
		// System.out.println(jsonBody);
		JSONObject requestedJsonObject = new JSONObject(jsonBody);

		PrintWriter writer = response.getWriter();

//		System.out.println(requestedJsonObject);

		JSONArray slno = new JSONArray();
		JSONArray area = new JSONArray();
		JSONArray areaCode = new JSONArray();
		JSONArray city = new JSONArray();
		JSONArray imei = new JSONArray();
		JSONArray lattitude = new JSONArray();
		JSONArray longitude = new JSONArray();
		JSONArray mobileNo = new JSONArray();
		JSONArray no_of_locks = new JSONArray();
		JSONArray siteId = new JSONArray();
		JSONArray siteName = new JSONArray();
		JSONArray state = new JSONArray();
		JSONArray terminalId = new JSONArray();
		JSONArray user = new JSONArray();
		JSONArray status = new JSONArray();
		JSONArray terminalType = new JSONArray();

		JSONObject respObject = new JSONObject();

		if (requestedJsonObject.getString("packetType").equals("UPDATE_SITE")) {

			Session session = HibernateUtils.getSession();
			try {
				SiteRegistration siteRegistration = new SiteRegistration();
				siteRegistration.setSlno(requestedJsonObject.getInt("slno"));
				siteRegistration.setLattitude(requestedJsonObject.getDouble("lattitude"));
				siteRegistration.setLongitude(requestedJsonObject.getDouble("longitude"));
				siteRegistration.setSiteID(requestedJsonObject.getString("siteId"));
				siteRegistration.setSiteName(requestedJsonObject.getString("siteName"));
				siteRegistration.setArea(requestedJsonObject.getString("areaName"));
				siteRegistration.setAreaCode(requestedJsonObject.getString("areaCode"));
				siteRegistration.setCity(requestedJsonObject.getString("cityName"));
				siteRegistration.setState(requestedJsonObject.getString("state"));
				siteRegistration.setUserName(requestedJsonObject.getString("userName"));
				siteRegistration.setImeiNo(requestedJsonObject.getString("imeiNumber"));
				siteRegistration.setMobileNo(requestedJsonObject.getString("mobileNumber"));
				siteRegistration.setTerminalid(requestedJsonObject.getString("terminalId"));
				siteRegistration.setNo_of_locks(requestedJsonObject.getString("noOfLockers"));
				siteRegistration.setNo_of_locks(requestedJsonObject.getString("status"));

				Transaction transaction = session.beginTransaction();

				session.update(siteRegistration);
				transaction.commit();
				writer.append("{\"status\":\"success\"}");

//			String hql = "UPDATE SiteRegistration SET siteID=:siteid, siteName=:sitename, no_of_locks=:noOfLocks,"
//					+ "area=:areaname, areaCode=:areacode,city=:cityname, state=:state, imeiNo=:imeino, mobileNo=:mobileno, userName=:username,"
//					+ "lattitude=:lattitute, longitude=longitude WHERE terminalid=:terminalid";

			} catch (Exception e) {
				// TODO: handle exception
				writer.append("{\"status\":\"fail\"}");
				e.printStackTrace();
			} finally {

				if (session != null) {
					session.close();
				}

			}

		} else if (requestedJsonObject.getString("packetType").equals("GET_SITES")) {

			Session session = HibernateUtils.getSession();

			String hql = "FROM SiteRegistration";

			try {

				ArrayList<SiteRegistration> siteRegList = (ArrayList<SiteRegistration>) session.createQuery(hql)
						.getResultList();

				if (siteRegList.size() > 0) {

					response.setStatus(HttpServletResponse.SC_OK);

					for (SiteRegistration siteReg : siteRegList) {
						slno.put(siteReg.getSlno());
						area.put(siteReg.getArea());
						areaCode.put(siteReg.getAreaCode());
						city.put(siteReg.getCity());
						imei.put(siteReg.getImeiNo());
						lattitude.put(siteReg.getLattitude());
						longitude.put(siteReg.getLongitude());
						mobileNo.put(siteReg.getMobileNo());
						no_of_locks.put(siteReg.getNo_of_locks());
						siteId.put(siteReg.getSiteID());
						siteName.put(siteReg.getSiteName());
						state.put(siteReg.getState());
						terminalId.put(siteReg.getTerminalid());
						user.put(siteReg.getUserName());
						status.put(siteReg.getStatus());
						terminalType.put(siteReg.getOutletType());

					}

					respObject.put("response", HttpServletResponse.SC_OK);
					respObject.put("slno", slno);
					respObject.put("area", area);
					respObject.put("areaCode", areaCode);
					respObject.put("city", city);
					respObject.put("imei", imei);
					respObject.put("lattitude", lattitude);
					respObject.put("longitude", longitude);
					respObject.put("mobileNo", mobileNo);
					respObject.put("no_of_locks", no_of_locks);
					respObject.put("siteId", siteId);
					respObject.put("siteName", siteName);
					respObject.put("state", state);
					respObject.put("terminalId", terminalId);
					respObject.put("user", user);
					respObject.put("status", status);
					respObject.put("outlet", terminalType);

				} else {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					respObject.put("response", HttpServletResponse.SC_NO_CONTENT);

				}

			} catch (Exception e) {
				// TODO: handle exception
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				respObject.put("response", HttpServletResponse.SC_NO_CONTENT);

			} finally {

				writer.append(respObject.toString());
				if (session != null) {
					session.close();
				}

			}

		} else if (requestedJsonObject.getString("packetType").equals("STATE_WISE_SITE")) {

			Session session = HibernateUtils.getSession();

			String hql = "FROM SiteRegistration FROM state=:STATE";

			try {

				ArrayList<SiteRegistration> siteRegList = (ArrayList<SiteRegistration>) session.createQuery(hql)
						.setParameter("STATE", requestedJsonObject.getString("statename")).getResultList();

				if (siteRegList.size() > 0) {

					response.setStatus(HttpServletResponse.SC_OK);

					for (SiteRegistration siteReg : siteRegList) {
						slno.put(siteReg.getSlno());
						area.put(siteReg.getArea());
						areaCode.put(siteReg.getAreaCode());
						city.put(siteReg.getCity());
						imei.put(siteReg.getImeiNo());
						lattitude.put(siteReg.getLattitude());
						longitude.put(siteReg.getLongitude());
						mobileNo.put(siteReg.getMobileNo());
						no_of_locks.put(siteReg.getNo_of_locks());
						siteId.put(siteReg.getSiteID());
						siteName.put(siteReg.getSiteName());
						state.put(siteReg.getState());
						terminalId.put(siteReg.getTerminalid());
						user.put(siteReg.getUserName());
						status.put(siteReg.getStatus());
						terminalType.put(siteReg.getOutletType());

					}

					respObject.put("response", HttpServletResponse.SC_OK);
					respObject.put("slno", slno);
					respObject.put("area", area);
					respObject.put("areaCode", areaCode);
					respObject.put("city", city);
					respObject.put("imei", imei);
					respObject.put("lattitude", lattitude);
					respObject.put("longitude", longitude);
					respObject.put("mobileNo", mobileNo);
					respObject.put("no_of_locks", no_of_locks);
					respObject.put("siteId", siteId);
					respObject.put("siteName", siteName);
					respObject.put("state", state);
					respObject.put("terminalId", terminalId);
					respObject.put("user", user);
					respObject.put("status", status);
					respObject.put("outlet", terminalType);

				} else {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					respObject.put("response", HttpServletResponse.SC_NO_CONTENT);

				}

			} catch (Exception e) {
				// TODO: handle exception	
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				respObject.put("response", HttpServletResponse.SC_NO_CONTENT);

			} finally {

				writer.append(respObject.toString());
				if (session != null) {
					session.close();
				}

			}

		}

//		System.out.println(respObject);

		writer.flush();
		writer.close();

	}

}
