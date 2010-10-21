package com.gaesample.main.server;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;

import com.gaesample.main.client.GreetingService;
import com.gaesample.main.model.Employee;
import com.gaesample.main.model.EmployeeEMF;
import com.gaesample.main.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
			        "Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		String s = savePMF();
		// String s2 = saveEMF();

		return "Hello, [" + s + "] [ " + input + "]!<br><br>I am running "
		        + serverInfo
		        + ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	private String saveEMF() {
		EntityManager em = EMF.get().createEntityManager();

		try {
			EmployeeEMF employee = new EmployeeEMF("Alfred", "Smith",
			        new Date());
			em.persist(employee);
			return "FOUND EMF " + employee.getKey().getId();
		} finally {
			em.close();
		}
	}

	private String savePMF() {
		Employee employee = new Employee("Alfred", "Smith", new Date());

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(employee);
		} finally {
			pm.close();
		}

		pm = PMF.get().getPersistenceManager();

		String query = "select from " + Employee.class.getName();
		// + " where lastName == 'Smith'";
		List<Employee> employees = (List<Employee>) pm.newQuery(query)
		        .execute();

		if (employees != null && employees.size() > 0) {
			int i = employees.size() - 1;
			return "FOUND " + employees.get(i).getKey().getId();
		}

		return "NOT FOUND";

	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		        .replaceAll(">", "&gt;");
	}
}
