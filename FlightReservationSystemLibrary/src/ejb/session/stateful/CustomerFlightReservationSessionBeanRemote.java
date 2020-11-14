/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.FlightSchedule;
import entity.SeatInventory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.NoFlightsAvailableException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface CustomerFlightReservationSessionBeanRemote {

    public void remove();

    public List<FlightSchedule> searchFlights(String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Integer numberOfTravellers);

    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

    public BigDecimal getFarePerPax(FlightSchedule flightSchedule, SeatInventory seatInventory);
    
}
