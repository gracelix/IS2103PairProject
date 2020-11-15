/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateful.CustomerFlightReservationSessionBeanRemote;
import ejb.session.stateless.CreditCardProcessinngSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.SeatSessionBeanLocal;
import entity.CreditCard;
import entity.Customer;
import entity.Fare;
import entity.FlightSchedule;
import entity.ItineraryItem;
import entity.Partner;
import entity.Seat;
import entity.SeatInventory;
import entity.Transaction;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;
import util.enumeration.SeatStatus;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.NoFlightsAvailableException;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;
import util.exception.SeatReservedException;
import util.exception.TransactionNotFoundException;

/**
 *
 * @author GraceLi
 */
@WebService(serviceName = "HolidayReservationSystemWebService")
@Stateless()
public class HolidayReservationSystemWebService {

    @EJB
    private CreditCardProcessinngSessionBeanLocal creditCardProcessinngSessionBean;

    @EJB
    private SeatSessionBeanLocal seatSessionBean;

    @EJB
    private CustomerFlightReservationSessionBeanRemote customerFlightReservationSessionBean;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

//    /**
//     * This is a sample web service operation
//     */
//        @WebMethod(operationName = "hello")
//        public String hello(@WebParam(name = "name") String txt) {
//            return "Hello " + txt + " !";
//        }
    
    @WebMethod(operationName = "login")
    public Partner login(@WebParam(name = "username") String username, 
                            @WebParam(name = "password") String password) 
                    throws InvalidLoginCredentialException {
        return partnerSessionBean.login(username, password);
    }
    
    @WebMethod(operationName = "searchSingleFlights") 
    public List<FlightSchedule> searchSingleFlights(@WebParam(name = "departureAirport") String departureAirport, 
                                                @WebParam(name = "destinationAirport") String destinationAirport, 
                                                @WebParam(name = "departureDateString") String departureDateString, 
                                                @WebParam(name = "numberOfTravellers") Integer numberOfTravellers) 
                                            throws NoFlightsAvailableException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date departureDate = dateFormat.parse(departureDateString);
        return customerFlightReservationSessionBean.searchSingleFlights(departureAirport, destinationAirport, departureDate, numberOfTravellers); 
    }
    
    @WebMethod(operationName = "searchOneConnectionFlights")
    public List<FlightSchedule> searchOneConnectionFlights(@WebParam(name = "departureAirport") String departureAirport, 
                                                        @WebParam(name = "destinationAirport") String destinationAirport, 
                                                        @WebParam(name = "departureDateString") String departureDateString, 
                                                        @WebParam(name = "numberOfTravellers") Integer numberOfTravellers) 
                                            throws NoFlightsAvailableException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date departureDate = dateFormat.parse(departureDateString);
        return customerFlightReservationSessionBean.searchOneConnectionFlights(departureAirport, destinationAirport, departureDate, numberOfTravellers);
    }
    
    @WebMethod(operationName = "searchTwoConnectionsFlight")
    public List<FlightSchedule> searchTwoConnectionsFlight(@WebParam(name = "departureAirport") String departureAirport, 
                                                        @WebParam(name = "destinationAirport") String destinationAirport, 
                                                        @WebParam(name = "departureDateString") String departureDateString, 
                                                        @WebParam(name = "numberOfTravellers")Integer numberOfTravellers) 
                                            throws NoFlightsAvailableException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date departureDate = dateFormat.parse(departureDateString);
        return customerFlightReservationSessionBean.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDate, numberOfTravellers); 
    }
    
    @WebMethod(operationName = "retrieveSeatById")
    public Seat retrieveSeatById(@WebParam(name = "seatId") Long seatId) throws SeatNotFoundException {
        return customerFlightReservationSessionBean.retrieveSeatById(seatId);
    }
    
    @WebMethod(operationName = "retrieveFlightScheduleById")
    public FlightSchedule retrieveFlightScheduleById(@WebParam(name = "flightScheduleId") Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        return customerFlightReservationSessionBean.retrieveFlightScheduleById(flightScheduleId);
    }
    
    @WebMethod(operationName = "reserveSeat")
    public Long reserveSeat(@WebParam(name = "flightSchedule") FlightSchedule flightSchedule, 
                        @WebParam(name = "cabinClassType") CabinClassType cabinClassType, 
                        @WebParam(name = "seatRow") Integer seatRow, 
                        @WebParam(name = "seatCol") String seatCol) throws SeatInventoryNotFoundException, SeatNotFoundException, SeatReservedException {
        return customerFlightReservationSessionBean.reserveSeat(flightSchedule, cabinClassType, seatRow, seatCol);
    }
    
    @WebMethod(operationName = "createNewCreditCardCustomer")
    public Long createNewCreditCardCustomer(@WebParam(name = "creditCard") CreditCard creditCard, @WebParam(name = "customer") Customer customer) throws CustomerNotFoundException {
        return customerFlightReservationSessionBean.createNewCreditCardCustomer(creditCard, customer);
    }
    
    @WebMethod(operationName = "retrieveCreditCardById")
    public CreditCard retrieveCreditCardById(@WebParam(name = "creditCardId") Long creditCardId) throws CreditCardNotFoundException {
        return customerFlightReservationSessionBean.retrieveCreditCardById(creditCardId);
    }
            
    @WebMethod(operationName = "retrieveAllCreditCardCustomer")
    public List<CreditCard> retrieveAllCreditCardCustomer(@WebParam(name = "customerId") Long customerId) throws CustomerNotFoundException {
        return customerFlightReservationSessionBean.retrieveAllCreditCardCustomer(customerId);
    }
    
    @WebMethod(operationName = "retrieveAllTransactionByCustomerId")
    public List<Transaction> retrieveAllTransactionByCustomerId(@WebParam(name = "customerId") Long customerId) {
        return customerFlightReservationSessionBean.retrieveAllTransactionByCustomerId(customerId);
    }
            
    @WebMethod(operationName = "retrieveAllItineraryItemByTransactionId")
    public List<ItineraryItem> retrieveAllItineraryItemByTransactionId(@WebParam(name = "customerId") Long transactionId) {
        return customerFlightReservationSessionBean.retrieveAllItineraryItemByTransactionId(transactionId);
    }
                    
    
    @WebMethod(operationName = "getFarePerPax")
    public Fare getFarePerPax(@WebParam(name = "flightSchedule") FlightSchedule flightSchedule, 
                            @WebParam(name = "seatInventory") SeatInventory seatInventory, 
                            @WebParam(name = "object") Object object) {
        return customerFlightReservationSessionBean.getFarePerPax(flightSchedule, seatInventory, object);
    }
    
    @WebMethod(operationName = "checkFlightScheduleExist")
    public Boolean checkFlightScheduleExist(@WebParam(name = "flightScheduleId") Long flightScheduleId, 
                                        @WebParam(name = "cabinClassType") CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException {
        return customerFlightReservationSessionBean.checkFlightScheduleExist(flightScheduleId, cabinClassType);
    }
    
    @WebMethod(operationName = "rollBackSeatsToAvailable")
    public void rollBackSeatsToAvailable(@WebParam(name = "seatId") Long seatId) throws SeatNotFoundException {
        Seat seat = seatSessionBean.retrieveSeatById(seatId);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
    }
    
    @WebMethod(operationName = "createNewTransaction")
    public Long createNewTransaction(@WebParam(name = "transaction") Transaction transaction, 
                                @WebParam(name = "customer") Customer customer) throws CustomerNotFoundException {
        return customerFlightReservationSessionBean.createNewTransaction(transaction, customer);
    }
    
    @WebMethod(operationName = "retrieveTransactionById")
    public Transaction retrieveTransactionById(@WebParam(name = "transactionId") Long transactionId) throws TransactionNotFoundException {
        return retrieveTransactionById(transactionId);
    }
    
    @WebMethod(operationName = "createNewItinerary")
    public Long createNewItinerary(@WebParam(name = "itineraryItem") ItineraryItem itineraryItem, 
            @WebParam(name = "transactionId") Long transactionId, 
            @WebParam(name = "flightScheduleId") Long flightScheduleId) throws TransactionNotFoundException, FlightSchedulePlanNotFoundException {
        return customerFlightReservationSessionBean.createNewItinerary(itineraryItem, transactionId, flightScheduleId);
    }
    
    @WebMethod(operationName = "makePayment")
    public void makePayment(@WebParam(name = "creditCard") CreditCard creditCard, @WebParam(name = "totalFare") BigDecimal totalFare) {
        creditCardProcessinngSessionBean.chargeCreditCard(creditCard.getCreditCardNumber(), totalFare);
    }
    
}
