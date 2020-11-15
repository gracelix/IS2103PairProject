/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.FlightSchedule;
import entity.Seat;
import entity.SeatInventory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.CabinClassType;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.NoFlightsAvailableException;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;
import util.exception.SeatReservedException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface CustomerFlightReservationSessionBeanRemote {

    public void remove();

    //public List<FlightSchedule> searchFlights(String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Integer numberOfTravellers);

    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

    public BigDecimal getFarePerPax(FlightSchedule flightSchedule, SeatInventory seatInventory, Object object);

    public List<FlightSchedule> searchOneConnectionFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

//    public List<List<FlightSchedule>> searchTwoConnectionsFlight(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;
    
    public List<FlightSchedule> searchTwoConnectionsFlight(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

    public Boolean checkFlightScheduleExist(Long flightScheduleId, CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException;

    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

    public Long reserveSeat(FlightSchedule flightSchedule, CabinClassType cabinClassType, Integer seatRow, String seatCol) throws SeatInventoryNotFoundException, SeatNotFoundException, SeatReservedException;

    public void rollBackSeatsToAvailable(Long seatId) throws SeatNotFoundException;
}
