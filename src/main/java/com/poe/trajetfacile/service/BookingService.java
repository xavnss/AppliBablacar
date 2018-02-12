package com.poe.trajetfacile.service;

import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.repository.BookingRepository;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    RideRepository rideRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    public Booking bookARide(Long userId, Long rideId) throws RideIsFullBusinessException {
        Booking booking = null;
        User user = userRepository.findOne(userId);
        Ride ride = rideRepository.findOne(rideId);

        if (ride.getSeats() > 0) {
            ride.setSeats((short) (ride.getSeats() - 1));
            booking = new Booking();
            booking.setUser(user);
            booking.setRide(ride);
            bookingRepository.save(booking);
        } else {
            throw new RideIsFullBusinessException("plus de places");
        }
        return booking;
    }
}
