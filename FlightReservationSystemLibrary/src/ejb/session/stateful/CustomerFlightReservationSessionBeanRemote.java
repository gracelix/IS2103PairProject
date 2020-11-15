/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.CreditCard;
import entity.Customer;
import entity.Fare;
import entity.FlightSchedule;
import entity.ItineraryItem;
import entity.Seat;
import entity.SeatInventory;
import entity.Transaction;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.CabinClassType;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.NoFlightsAvailableException;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;
import util.exception.SeatReservedException;
import util.exception.TransactionNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface CustomerFlightReservationSessionBeanRemote {

    public void remove();

    //public List<FlightSchedule> searchFlights(String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Integer numberOfTravellers);

    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

    public Fare getFarePerPax(FlightSchedule flightSchedule, SeatInventory seatInventory, Object object);

    public List<FlightSchedule> searchOneConnectionFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

//    public List<List<FlightSchedule>> searchTwoConnectionsFlight(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;
    
    public List<FlightSchedule> searchTwoConnectionsFlight(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException;

    public Boolean checkFlightScheduleExist(Long flightScheduleId, CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException;

    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

    public Long reserveSeat(FlightSchedule flightSchedule, CabinClassType cabinClassType, Integer seatRow, String seatCol) throws SeatInventoryNotFoundException, SeatNotFoundException, SeatReservedException;

    public void rollBackSeatsToAvailable(Long seatId) throws SeatNotFoundException;

    public Long createNewTransaction(Transaction transaction, Long CustomerId) throws CustomerNotFoundException;

    public Seat retrieveSeatById(Long seatId) throws SeatNotFoundException;

    public Transaction retrieveTransactionById(Long transactionId) throws TransactionNotFoundException;

    public Long createNewItinerary(ItineraryItem itineraryItem, Long transactionId, Long flightScheduleId) throws TransactionNotFoundException, FlightSchedulePlanNotFoundException;

    public Long createNewCreditCardCustomer(CreditCard creditCard, Customer customer) throws CustomerNotFoundException;

    public CreditCard retrieveCreditCardById(Long creditCardId) throws CreditCardNotFoundException;

    public List<CreditCard> retrieveAllCreditCardCustomer(Long customerId) throws CustomerNotFoundException;

    public void makePayment(CreditCard creditCard, BigDecimal totalFare);
}
