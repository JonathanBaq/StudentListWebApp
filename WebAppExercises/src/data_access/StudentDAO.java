package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import java.util.ArrayList;

import model.Student;

public class StudentDAO {

	public StudentDAO() {
		try {
			Class.forName(ConnectionParameters.jdbcDriver);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
	}

	/**
	 * Open database connection
	 * 
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection(ConnectionParameters.connectionString, ConnectionParameters.username,
				ConnectionParameters.password);
	}
	
	/**
	 * Delete a student from the database
	 * 
	 * @param int studentID
	 * @return int deleteResult 0 - delete successful, 1 - not found, -1 - delete fail
	 * @throws SQLException
	 */
	
	public int deleteStudent(int studentId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int deleteResult = 0;
		
		try {
			connection = openConnection();

			String sqlText = "DELETE FROM STUDENT WHERE id = ?";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, studentId);

			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected == ConnectionParameters.ROWS_NOT_FOUND){
				deleteResult = 1;
				return deleteResult;
			} else {
				return deleteResult;
			}
			
		} catch (SQLException sqle) {
			deleteResult = -1;
			return deleteResult;

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
	}

	/**
	 * Retrieve all Students from the database
	 * 
	 * @return List<Student>
	 * @throws SQLException
	 */

	public List<Student> getAllStudents() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> studentList = new ArrayList<Student>();

		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student ORDER BY lastname ASC";

			preparedStatement = connection.prepareStatement(sqlText);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");

				studentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));
			}

		} catch (SQLException sqle) {
			studentList = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}

		return studentList;
	}

	/**
	 * Retrieve all Students from the database into a List the use GSON to convert to JSON string
	 * 
	 * @return String studentJson as single JSON string
	 * @throws SQLException
	 */

	public String getAllStudentsJSON() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> studentList = new ArrayList<Student>();
		String studentJson = "";
		
		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student ORDER BY lastname ASC";

			preparedStatement = connection.prepareStatement(sqlText);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");

				studentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));
			}
			
			Gson gson = new Gson();
			studentJson = gson.toJson(studentList);

		} catch (SQLException sqle) {
			studentJson = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}

		return studentJson;
	}

	/**
	 * Retrieve student information by student ID
	 * 
	 * @param studentID - student ID to be used as the filter in the query
	 * @return Student<List>
	 * @throw SQLException
	 */

	public String getStudentById(int studentId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> studentList = new ArrayList<Student>();
		String studentJson = "";
		
		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student WHERE id = ? ORDER BY lastname ASC";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, studentId);

			resultSet = preparedStatement.executeQuery();

			boolean rowFound = false;

			while (resultSet.next()) {
				rowFound = true;
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");

				studentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));
				
			}
			
			if (rowFound == false) {
				studentList = null;
			}
			
			Gson gson = new Gson();
			studentJson = gson.toJson(studentList);

		} catch (SQLException sqle) {
			studentJson = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}

		return studentJson;
	}
	
	/**
	 * Insert student information
	 * 
	 * @param Student - object
	 * @return int insertResult: 0 - insert successful, 1 - id already in use, -1 - insert fail
	 * @throw SQLException
	 */
	
	public int insertStudent(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int insertResult = 0;

		try {
			connection = openConnection();

			String sqlText = "INSERT INTO STUDENT (id, firstname, lastname, streetaddress, postcode, postoffice) VALUES (?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setString(2, student.getFirstname());
			preparedStatement.setString(3, student.getLastname());
			preparedStatement.setString(4, student.getStreetaddress());
			preparedStatement.setInt(5, student.getPostcode());
			preparedStatement.setString(6, student.getPostoffice());

			preparedStatement.executeUpdate();
			return insertResult;

		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				insertResult = 1;
				return insertResult;
			} else {
				insertResult = -1;
				return insertResult;
			}

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
	}
	
	/**
	 * Update student information
	 * 
	 * @param Student - object
	 * @return int insertResult: 0 - update successful, 1 - not found, -1 - updatet fail
	 * @throw SQLException
	 */
	
	public int updateStudent(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updateResult = 0;
		
		try {
			connection = openConnection();

			String sqlText = "UPDATE STUDENT SET id = ?, firstname = ?, lastname = ?, streetaddress = ?, postcode = ?, postoffice = ? WHERE id = ?";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setString(2, student.getFirstname());
			preparedStatement.setString(3, student.getLastname());
			preparedStatement.setString(4, student.getStreetaddress());
			preparedStatement.setInt(5, student.getPostcode());
			preparedStatement.setString(6, student.getPostoffice());
			preparedStatement.setInt(7,  student.getId());

			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected == ConnectionParameters.ROWS_NOT_FOUND){
				updateResult = 1;
				return updateResult;
			} else {
				return updateResult;
			}
			
		} catch (SQLException sqle) {
			updateResult = -1;
			return updateResult;

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
	}
}
