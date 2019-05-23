package com.pluralsight.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		Ride ride = new Ride();
		ride.setName("Corner Canyon");
		ride.setDuration(120);
		List <Ride> rides = new ArrayList<>();
		rides.add(ride);
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
		
		jdbcTemplate.update("INSERT INTO Ride (name, duration) values (?, ?);", ride.getName(), ride.getDuration());
/*		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		
		List<String> columnsNames = new ArrayList<>();
		columnsNames.add("name");
		columnsNames.add("duration");
		
		insert.setTableName("Ride");
		insert.setColumnNames(columnsNames);
		
		Map<String, Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());
		
		insert.setGeneratedKeyName("id");
		
		Number key = insert.executeAndReturnKey(data);
		
		System.out.println("Returned key: " + key);
		*/
		return null;
	}
}
