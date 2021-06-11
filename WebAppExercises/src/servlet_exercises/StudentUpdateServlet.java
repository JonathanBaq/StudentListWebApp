package servlet_exercises;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data_access.StudentDAO;
import model.Student;

/**
 * Servlet implementation class StudentUpdateServlet
 */
@WebServlet("/updateStudent")
public class StudentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(request.getParameter("id"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String streetaddress = request.getParameter("streetaddress");
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String postoffice = request.getParameter("postoffice");

		Student modifiedStudent = new Student(id, firstname, lastname, streetaddress, postcode, postoffice);
		StudentDAO studentDAO = new StudentDAO();
		int errorCode = studentDAO.updateStudent(modifiedStudent);

		Gson gson = new Gson();
		String json = gson.toJson(new Status(errorCode));
		out.print(json);
	}

}
