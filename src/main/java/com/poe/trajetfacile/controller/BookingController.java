package com.poe.trajetfacile.controller;

import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.form.BookARideForm;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import com.poe.trajetfacile.service.BookingService;
import com.poe.trajetfacile.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookingService bookingService;

    @PostMapping
    public String bookARide(@Valid BookARideForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/ride/list";
        }

        Booking booking = null;
        try {
            booking = bookingService.bookARide(userRepository.findByLogin(principal.getName()).getId(), form.getRideId());
        } catch (RideIsFullBusinessException e) {
            redirectAttributes.addFlashAttribute("message", "Ce trajet est déjà complet.");
            return "redirect:/ride/list";
        }
        model.addAttribute("book", booking);
        return "ride/booked";
    }

	public RideService getRideService() {
		return rideService;
	}

	public void setRideService(RideService rideService) {
		this.rideService = rideService;
	}

	public RideRepository getRideRepository() {
		return rideRepository;
	}

	public void setRideRepository(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
    
    
    
}
