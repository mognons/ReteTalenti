package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.model.CalendarDTO;
import org.joda.time.DateTime;
import com.jdbc.DataAccessObject;


public class CalendarsDao {

	private Connection dbConnection;
	private PreparedStatement pStmt, pModules, pDocuments;
	private Statement stmt;
		
	public CalendarsDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public int createEvent(CalendarDTO myEvent) throws Exception {
		int autoIncKeyFromFunc = -1;

		String insertQuery = "INSERT INTO CALENDAR "
				+ "(START,START_TIME,END, END_TIME, TITLE,DESCRIPTION,COLOR,ALLDAY,COURSEID,MODULEID) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			stmt = dbConnection.createStatement();
			pStmt.setString(1, myEvent.getStart().substring(0, 10));
			pStmt.setString(2, myEvent.getStart().substring(11));
			pStmt.setString(3, myEvent.getEnd().substring(0, 10));
			pStmt.setString(4, myEvent.getEnd().substring(11));
			pStmt.setString(5, myEvent.getTitle());
			pStmt.setString(6, myEvent.getDescription());
			pStmt.setString(7, myEvent.getColor());
			pStmt.setBoolean(8, myEvent.getAllDay());
			pStmt.setInt(9, myEvent.getCourseId());
			pStmt.setInt(10, myEvent.getModuleId());
			pStmt.executeUpdate();
/*
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			} else {
				throw new Exception("Focca la bindella");
			}

			System.out.println("Key returned from "
					+ "'SELECT LAST_INSERT_ID()': " + autoIncKeyFromFunc);
*/
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateEvent(CalendarDTO myEvent) {
		String updateQuery = "UPDATE CALENDAR SET  "
				+ "START=?, START_TIME=?, END=?, END_TIME=?"
				+ "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, myEvent.getStart().substring(0, 10));
			pStmt.setString(2, myEvent.getStart().substring(11));
			pStmt.setString(3, myEvent.getEnd().substring(0, 10));
			pStmt.setString(4, myEvent.getEnd().substring(11));
			pStmt.setInt(5, myEvent.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public int deleteEvent(int eventId) {

		String query = "DELETE FROM CALENDAR WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, eventId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

	public int deleteAllEvents(int courseId) {

		String query = "DELETE FROM CALENDAR WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

	public List<CalendarDTO> retrieveEvents(int courseId, String start, String end) {
		List<CalendarDTO> events = new ArrayList<CalendarDTO>();

		String query = "SELECT * FROM CALENDAR WHERE START>=? AND END<=?";
		if (courseId != -1)
			query = query + " AND (COURSEID=-1 OR COURSEID=?)";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, start.substring(0,10));
			pStmt.setString(2, end.substring(0,10));
			if (courseId != -1)
				pStmt.setInt(3, courseId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				CalendarDTO myEvent = new CalendarDTO();
				myEvent.setId(rs.getInt("ID"));
				myEvent.setTitle(rs.getString("TITLE"));
				myEvent.setDescription(rs.getString("DESCRIPTION"));
				myEvent.setStart(rs.getString("START")+"T"+rs.getString("START_TIME"));
				myEvent.setEnd(rs.getString("END")+"T"+rs.getString("END_TIME"));
				myEvent.setColor(rs.getString("COLOR"));
				myEvent.setAllDay(rs.getBoolean("ALLDAY"));
				myEvent.setCourseId(rs.getInt("COURSEID"));
				myEvent.setModuleId(rs.getInt("MODULEID"));
				events.add(myEvent);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return events;
	}
	
	public Boolean isDroppable(String myDate) {
		String query = "SELECT * FROM CALENDAR WHERE START=? AND COURSEID=-1";
		Boolean result = true;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, myDate);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				result = false;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}

	public int retrieveLastCalendarLoaded() {
		String query = "SELECT * FROM CONFIGURATION C WHERE C.KEY='lastYearHolidayLoaded'";
		int value = 1997;
		try {
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				value = rs.getInt("VALUE");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return value;
	}

	public void updateLastCalendarLoaded(int yearValue) {
		String updateQuery = "UPDATE CONFIGURATION C SET VALUE=? WHERE C.KEY='lastYearHolidayLoaded'";
		try {
			pStmt = dbConnection.prepareCall(updateQuery);
			pStmt.setInt(1, yearValue);
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}