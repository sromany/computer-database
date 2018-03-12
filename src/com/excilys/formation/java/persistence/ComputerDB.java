package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.Computer;
import com.excilys.formation.java.mapper.ComputerMP;

public class ComputerDB {
	private int numComputers;
	
	
	public int getNumComputers(Connection conn) throws SQLException {
		synchronized(conn) {
			if (numComputers == -1)
			{
				Statement s = conn.createStatement();
				ResultSet res = s
						.executeQuery("SELECT COUNT(*) AS NUM FROM MOVIE");
				res.next();
				numComputers = res.getInt("NUM");
			}
		}
		return numComputers;
	}
	
	public ArrayList<ComputerMP> getComputerList(Connection conn) throws SQLException {
		
		ArrayList<ComputerMP> computers = new ArrayList<ComputerMP>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		Statement s = conn.createStatement();
		ResultSet res = s.executeQuery("SELECT * FROM COMPUTERS ORDER BY TITLE");
		
		while (res.next())
			computers.add(ComputerMP.map(res));
	
		return computers;
	}
}
