package ctrl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import bean.BookReviewBean;
import bean.OrderHistoryBean;
import bean.POBean;
import model.BookDAO;
import model.BookReviewDAO;
import model.DatabaseConnector;
import model.POContainer;
import model.PODAO;
import model.POItemDAO;

/**
 * Servlet implementation class Book
 */
@WebServlet("/Analytics")

public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BookDAO b;
	BookReviewDAO br;
	PODAO po;
	POItemDAO pd;

	public void init() {
		b = new BookDAO();
		br = new BookReviewDAO();
		po = new PODAO();
		pd = new POItemDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private void serveJSP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/analytics.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			populatePopular(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serveJSP(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("fajdfalk");
		if (request.getParameter("submitted") != null) {
		populateByMonth(request, response);
		}
		// UC A3: Set all PO records as attribute.
		/*
		 * request.setAttribute("anonymizedpo", po.retrieveAllPO());
		 * List<OrderHistoryBean> anonPO =
		 * po.retrieveOrderHistory(yearMonthFormat.format(date)+"01");
		 * Iterator<OrderHistoryBean> iteAnonPO= anonPo.iterator();
		 * while(iteReport.hasNext())
		 * System.out.println(iteAnonPO.next().getTitle()); //Forward to page
		 */

		doGet(request, response);
	}

	private void populatePopular(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<BookBean> mostPop = pd.mostPopular();
		Iterator<BookBean> iterBook = mostPop.iterator();
		int i = 0;
		while (iterBook.hasNext()) {
			request.setAttribute("popular" + i, iterBook.next().getBid());
			i++;
		}
	}

	private void populateByMonth(HttpServletRequest request, HttpServletResponse response) {
		int month = 1;
		if (request.getSession().getAttribute("month") != null)
			month = Integer.parseInt(request.getSession().getAttribute("month").toString());

		try {
			// Get the product orders
			request.setAttribute("report", po.retrievePOByMonth(month));
				// Get all product orders
				System.out.println("This gets here");
				List<POBean> report = po.retrieveAllPO();
				Iterator<POBean> iteReport = report.iterator();
				while (iteReport.hasNext())
					System.out.println(iteReport.next().getDate());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
