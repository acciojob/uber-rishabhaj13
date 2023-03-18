package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		Customer customer = customerRepository2.findById(customerId).get();
		List<TripBooking> bookedTrips = customer.getTripBookingList();

		for(TripBooking trip : bookedTrips){
			Driver driver = trip.getDriver();
			Cab cab = driver.getCab();
			cab.setAvailable(true);
			driverRepository2.save(driver);
			trip.setStatus(TripStatus.CANCELED);
		}

		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		List<Driver> drivers = driverRepository2.findAll();
		Driver driver = null;
		for(Driver d : drivers){
			if(d.getCab().getAvailable()){
				if((driver == null) || (d.getDriverId()  < driver.getDriverId())){
					driver = d;
				}
			}
		}
		if(driver==null){
			throw new Exception("No cab available!");
		}

		TripBooking newTripBooked = new TripBooking(fromLocation,toLocation,distanceInKm);
		newTripBooked.setCustomer(customerRepository2.findById(customerId).get());
		newTripBooked.setStatus(TripStatus.CONFIRMED);
		newTripBooked.setDriver(driver);
		int rate = driver.getCab().getPerKmRate();
		newTripBooked.setBill(distanceInKm*rate);
		driver.getCab().setAvailable(false);
		driverRepository2.save(driver);
		Customer customer = customerRepository2.findById(customerId).get();
		customer.getTripBookingList().add(newTripBooked);
		customerRepository2.save(customer);
		tripBookingRepository2.save(newTripBooked);


		return newTripBooked;
	}

	@Override
	public void cancelTrip(Integer tripId){
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setStatus(TripStatus.CANCELED);
		bookedTrip.setBill(0); //Bill amount set to zero after cancel
		bookedTrip.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(bookedTrip);
	}

	@Override
	public void completeTrip(Integer tripId){
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setStatus(TripStatus.COMPLETED);
		bookedTrip.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(bookedTrip);
	}
}
