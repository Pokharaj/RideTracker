package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideRepository {

	Ride createRide(Ride ride);
	
	List<Ride> getRides();

	Ride getRide(int id);

	Ride updateRide(Ride ride);

	void batchUpdate(List<Object[]> pairs);

	void delete(Integer id);
}