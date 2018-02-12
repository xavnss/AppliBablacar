package com.poe.trajetfacile.controller;

import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.form.BookARideForm;
import com.poe.trajetfacile.form.OfferARideForm;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import com.poe.trajetfacile.service.RideService;
import com.poe.trajetfacile.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/ride")
public class RideManagerController {

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showForm(OfferARideForm form, @RequestParam(name = "ride", required = false) String rideId, Model model) {
        if (rideId != null && !rideId.isEmpty()) {
            Ride ride = rideRepository.findOne(Long.valueOf(rideId));
            model.addAttribute("ride", ride);
        }

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "ride/create";
    }

    @PostMapping
    public String offerARide(@Valid OfferARideForm form, BindingResult bindingResult, Principal principal, Model model, RedirectAttributes redirectAttributes) {

        System.out.println("LoggedIn user : " + principal.getName());
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "ride/create";
        }
        
        System.out.println("Start date: " + form.getStartDate().toString());
        
        
        Date convertedDateMinutePrecision = DateUtils.convert(form.getStartDate(), form.getStartHours(), form.getStartMinutes());
        Ride ride = rideService.offerARide(convertedDateMinutePrecision, form.getFromCity(), form.getToCity(), form.getCost(), form.getSeats(),
        		userRepository.findByLogin(principal.getName()).getId());
        redirectAttributes.addAttribute("ride", ride.getId());
        
        return "redirect:/ride";
    }

    @GetMapping("list")
    public String list(BookARideForm form, @ModelAttribute(value = "message") String message, Model model) {

        Iterable<Ride> rides;
        rides = rideRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        model.addAttribute("rides", rides);
        model.addAttribute("users", users);
        model.addAttribute("message", message);
        return "ride/list";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "search", required = true) String search, BookARideForm form) {

        Iterable<Ride> rides;
        System.out.println("searching " + search);
        if (search != null && !search.isEmpty()) {
            System.out.println("searching town for " + search);
            rides = rideRepository.findAllByToCityLikeIgnoreCaseOrFromCityLikeIgnoreCase("%" + search + "%", "%" + search + "%");
        } else {
            rides = rideRepository.findAll();
        }

        Iterable<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("rides", rides);
        model.addAttribute("search", search);
        return "ride/list";
    }


    @MessageMapping("aNewRide")
    @SendTo("/topic/rideList")
    public Ride newRideNotification(Ride ride) {
        System.out.println("new ride on air ! " + ride.getId() + "\nDe: " + ride.getFromCity() + " à " + ride.getToCity());
        return ride;
    }
    
    
    @GetMapping("appendRide")
    public String appendRide(@RequestParam(name = "id", required = true) String rideId,
    		@RequestParam(name = "startDate", required = true) String startDate,
    		@RequestParam(name = "fromCity", required = true) String fromCity,
    		@RequestParam(name = "toCity", required = true) String toCity,
    		@RequestParam(name = "cost", required = true) String cost,
    		@RequestParam(name = "seats", required = true) String seats,
    		@RequestParam(name = "bookings", required = true) String bookings,
    		Model model){
    	
    	System.out.println("appendRide");
    	Ride ride = rideRepository.findOne(Long.parseLong(rideId));
    	System.out.println("Ride id: " + ride.getId());
    	System.out.println("User id: " + ride.getUserWhoProposed().getId());
    	System.out.println("start date " + ride.getStartDate());
    	System.out.println("to " + ride.getToCity());
    	System.out.println("from " + ride.getFromCity());
    	System.out.println("cost " + ride.getCost());
    	System.out.println("seats " + ride.getSeats());
    	BookARideForm rideForm = new BookARideForm(ride.getUserWhoProposed().getId(), ride.getId());
    	
    	
    	model.addAttribute("ride", ride);
    	model.addAttribute("bookARideForm", rideForm);
    	return "ride/oneRide";
    }

}
