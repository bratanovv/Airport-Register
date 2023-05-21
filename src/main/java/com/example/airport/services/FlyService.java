package com.example.airport.services;

import com.example.airport.models.Flight;
import com.example.airport.models.Ticket;
import com.example.airport.models.User;
import com.example.airport.repositories.FlightRepository;
import com.example.airport.repositories.TicketRepository;
import com.example.airport.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlyService {
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TicketRepository ticketRepository;
    private final MailSenderService mailSenderService;


    public List<Flight> getFlightList(String dest, String dep, boolean fl) {
        if (dest != null && dep != null) {
            if (!dest.equals("") && !dep.equals("")) {
                if (fl) return getFlightsAfterNow(flightRepository.findByCityDestAndCityFrom(dest, dep));
                return flightRepository.findByCityDestAndCityFrom(dest, dep);
            } else if (!dest.equals("")) {
                if(fl) return getFlightsAfterNow(flightRepository.findByCityDest(dest));
                return flightRepository.findByCityDest(dest);
            } else if (!dep.equals("")){
                if(fl) return getFlightsAfterNow(flightRepository.findByCityFrom(dep));
                return flightRepository.findByCityFrom(dep);
            }
        }
        return getFlightsAfterNow(flightRepository.findAll());
    }

    public List<Flight> getFlightsAfterNow(List<Flight> list) {

        List<Flight> result = new ArrayList<>();
        for (Flight f : list) {
            if (f.getDeparture().isAfter(LocalDateTime.now())) result.add(f);
        }
        return result;

    }

    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
        for (int i = 0; i < flight.getAmount(); i++) {
            Ticket t = new Ticket();
            t.setPlace(i + 1);
            t.setFlight(flight);
            ticketRepository.save(t);
            flight.getTicketList().add(t);
        }

        log.info("Saving new {}", flight);
        flightRepository.save(flight);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteFlight(Long id) {
        System.out.println(flightRepository.findById(id).orElse(null));
        List<Ticket> t = ticketRepository.findAllByFlight(flightRepository.findById(id).orElse(null));
        List<User> u = userRepository.findAll();
        for (User ui : u) {
            for (Ticket ti : t) {
                ui.getTicketList().remove(ti);
            }
        }
        for (Ticket ti : t) {
            ticketRepository.delete(ti);
            ti.setUser(null);
        }

        flightRepository.deleteById(id);

        log.info("Deleting {}", flightRepository.findById(id).orElse(null));
    }

    public Flight getFlightById(Long id) {

        return flightRepository.findById(id).orElse(null);
    }

    public void deleteTicket(Long id) {
        Ticket t = ticketRepository.findById(id).orElse(null);
        User u = t.getUser();
        u.getTicketList().remove(t);
        userRepository.save(u);
        t.setUser(null);
        ticketRepository.save(t);
        log.info("Ticket RETURN {}", t);

        // mailSenderService.send(u.getEmail(),"ОТМЕНА БИЛЕТА","УВЕДОМЛЯЕМ ВАС ОБ ОТМЕНЕ ПЕРЕЛЕТА\n"+t.getFlight().getCityFrom()+"->"+t.getFlight().getCityDest()+" Время: "+t.getFlight().getDeparture()+" Место: "+t.getPlace());

    }

    public List<Integer> statistics() {
        List<Flight> flights = flightRepository.findAll();
        List<Ticket> tickets = ticketRepository.findAll();
        List<Integer> res = new ArrayList<>();
        res.add(flights.size());//всего
        //--------------------------------------------------------------------------------
        int num = 0;
        for (Flight f : flights) {
            if (f.getDeparture().isBefore(LocalDateTime.now())) num++;

        }
        res.add(num);//выполненные рейсы

        res.add(tickets.size()); //всего билетов

        //--------------------------------------------------------------------------------
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        num = 0;
        int money = 0;
        for (Ticket t : tickets) {
            if (t.getUser() != null) {
                if (t.getFlight().getDescription().equals("1")) {
                    a++;
                }
                if (t.getFlight().getDescription().equals("2")) {
                    b++;
                }
                if (t.getFlight().getDescription().equals("3")) {
                    c++;
                }
                if (t.getFlight().getDescription().equals("4")) {
                    d++;
                }
                num++;
                money += t.getFlight().getPrice();
            }

        }
        res.add(num); //ВСЕГО брони

        //--------------------------------------------------------------------------------
        num = 0;
        for (Ticket t : tickets) {
            if (t.getUser() != null && t.getFlight().getDeparture().isAfter(LocalDateTime.now())) num++;
        }
        res.add(num);//активные брони

        res.add(money);//общий кэш

        res.add(a);//business
        res.add(b);//komfort
        res.add(c);//econom
        res.add(d);//1kla$$

        return res;
    }

}
