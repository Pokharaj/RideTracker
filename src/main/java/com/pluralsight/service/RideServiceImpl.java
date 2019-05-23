package com.pluralsight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;

	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride createRide(Ride ride) {
		return rideRepository.createRide(ride);
	}

	@Override
	public Ride getRide(Integer id) {
		return rideRepository.getRide(id);
	}

	@Override
	public Ride updateRide(Ride ride) {
		return rideRepository.updateRide(ride);
	}

	@Override
	public void batchUpdate() {
		List<Ride> rides = getRides();
		List<Object[]> pairs = new ArrayList<>();
		for (Ride ride : rides) {
			Object[] temp = { new Date(), ride.getId() };
			pairs.add(temp);
		}
		rideRepository.batchUpdate(pairs);
	}
	
	@Override
	public void delete(Integer id) {
		rideRepository.delete(id);
	}
}
