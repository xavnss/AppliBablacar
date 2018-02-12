package com.poe.trajetfacile;

import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import com.poe.trajetfacile.service.UserService;

import java.util.Calendar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RideRepository rideRepository;
    
    @Autowired
    private UserService userService;

    @Bean
    public InitializingBean init() {
        return () -> {
            System.out.println("init");
            
            User user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            userRepository.save(user);
            userService.signup(user);
            
            Ride ride = new Ride();
            ride.setCost((double) 10);
            ride.setFromCity("Nantes");
            ride.setToCity("Orlean");
            ride.setSeats((short)3);
            ride.setStartDate(Calendar.getInstance().getTime());
            ride.setUserWhoProposed(user);
            rideRepository.save(ride);

            ride = new Ride();
            ride.setCost((double) 5.15);
            ride.setFromCity("Strasbourg");
            ride.setToCity("Vienne");
            ride.setSeats((short)3);
            ride.setStartDate(Calendar.getInstance().getTime());
            ride.setUserWhoProposed(user);
            rideRepository.save(ride);

            
            
            user = new User();
            user.setLogin("xav");
            user.setPassword("pass");
            userRepository.save(user);
            userService.signup(user);
            
            ride = new Ride();
            ride.setCost((double) 1005.65);
            ride.setFromCity("Nice");
            ride.setToCity("Brest");
            ride.setSeats((short)2);
            ride.setStartDate(Calendar.getInstance().getTime());
            ride.setUserWhoProposed(user);
            rideRepository.save(ride);


        };
    }
}
