package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Ride createRide(Ride ride) {

//		jdbcTemplate.update("INSERT INTO Ride (name, duration) values (?, ?);", ride.getName(), ride.getDuration());

		/*
		 * SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		 * 
		 * List<String> columnsNames = new ArrayList<>(); columnsNames.add("name");
		 * columnsNames.add("duration");
		 * 
		 * insert.setTableName("Ride"); insert.setColumnNames(columnsNames);
		 * 
		 * Map<String, Object> data = new HashMap<>(); data.put("name", ride.getName());
		 * data.put("duration", ride.getDuration());
		 * 
		 * insert.setGeneratedKeyName("id");
		 * 
		 * Number key = insert.executeAndReturnKey(data);
		 * 
		 * System.out.println("Returned key: " + key);
		 */
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("INSERT INTO Ride (name, duration) values (?, ?);",
						new String[] { "id" });
				ps.setString(1, ride.getName());
				ps.setInt(2, ride.getDuration());
				return ps;
			}
		}, keyHolder);
		Number key = keyHolder.getKey();

		return getRide(key.intValue());
	}

	@Override
	public Ride getRide(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM Ride WHERE id = ?", new RideRowMapper(), id);
	}
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("SELECT * FROM Ride", new RideRowMapper());
		return rides;
	}

	@Override
	public Ride updateRide(Ride ride) {

		jdbcTemplate.update("UPDATE Ride SET name = ?, duration = ? WHERE id = ?",
				ride.getName(), ride.getDuration(), ride.getId());

		return ride;
	}
	
	@Override
	public void batchUpdate(List<Object[]> pairs) {
		jdbcTemplate.batchUpdate("UPDATE Ride SET ride_date = ? WHERE id = ?", pairs);
	}
	
	@Override
	public void delete(Integer id) {
//		jdbcTemplate.update("DELETE FROM Ride WHERE id = ?", id);
		
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		namedJdbcTemplate.update("DELETE FROM Ride WHERE id = :id", paramMap);
	}

//	public void dropRideTable() {
//		jdbcTemplate.execute("DROP TABLE Ride");
//	}
//
//	public void createRideTable() {
//		jdbcTemplate.execute(
//				"CREATE TABLE Ride(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100) NOT NULL, duration INT NOT NULL)");
//	}
//	
//	public void addDateTimeColumnInRideTable() {
//		jdbcTemplate.execute(
//				"ALTER TABLE Ride ADD ride_date DATETIME AFTER duration");
//	}
}
