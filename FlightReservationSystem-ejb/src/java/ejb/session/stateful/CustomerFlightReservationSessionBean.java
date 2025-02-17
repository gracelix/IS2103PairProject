/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.CreditCardProcessinngSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.FlightScheduleSessionBeanLocal;
import ejb.session.stateless.SeatSessionBeanLocal;
import entity.AircraftConfiguration;
import entity.CabinClassConfiguration;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;
import util.enumeration.SeatStatus;
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
@Stateful
public class CustomerFlightReservationSessionBean implements CustomerFlightReservationSessionBeanRemote {

    @EJB(name = "CreditCardProcessinngSessionBeanLocal")
    private CreditCardProcessinngSessionBeanLocal creditCardProcessinngSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "SeatSessionBeanLocal")
    private SeatSessionBeanLocal seatSessionBeanLocal;

    @EJB(name = "FlightScheduleSessionBeanLocal")
    private FlightScheduleSessionBeanLocal flightScheduleSessionBeanLocal;

    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private String departureAirport;
    private String destinationAirport;
    private Date departureDate;
    private Date returnDate;
    private Integer numberOfTravellers;
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    private List<FlightSchedule> flightSchedules;

    @Remove
    @Override
    public void remove()
    {
        // Do nothing
    }
    
    @PreDestroy
    public void preDestroy()
    {
        if(flightSchedules != null)
        {
            flightSchedules.clear();
            flightSchedules = null;
        }
    }
    
   
    @Override
    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
        this.numberOfTravellers = numberOfTravellers;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date startDate = calendar.getTime();
        
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        Date endDate = calendar.getTime();
        
        Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.departureDateTime >= :inStartDate AND fs.departureDateTime <= :inEndDate AND fs.flightSchedulePlan.flight.flightRoute.originAirport.iataCode = :inOriginAirport AND fs.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = :inDestinationAirport ORDER BY fs.departureDateTime ASC");
        query.setParameter("inStartDate", startDate);
        query.setParameter("inEndDate", endDate);
        query.setParameter("inOriginAirport", departureAirport);
        query.setParameter("inDestinationAirport", destinationAirport);
        
        List<FlightSchedule> tempList = query.getResultList();
        
        if (tempList.size() == 0) {
            throw new NoFlightsAvailableException("There are no flights available for " + departureAirport + " to " + destinationAirport + " between " + startDate + " and " + endDate + ".");
        }
        
        flightSchedules = new ArrayList<>();
        for (FlightSchedule flightSchedule : tempList) {
            flightSchedule.getFlightSchedulePlan();
            flightSchedule.getFlightSchedulePlan().getFares().size();
            
            Integer availableSeats = 0;
            for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
                availableSeats += seatInventory.getAvailableSeats();
            }
            
            if (availableSeats >= numberOfTravellers) {
                flightSchedules.add(flightSchedule);
            }
        }
        
        return flightSchedules;
    }
    
    @Override
    public List<FlightSchedule> searchOneConnectionFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
        this.numberOfTravellers = numberOfTravellers;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date startDate = calendar.getTime();
        
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        Date endDate = calendar.getTime();
                
        Query query = em.createQuery("SELECT fs1, fs2 FROM FlightSchedule fs1, FlightSchedule fs2 WHERE fs1.flightSchedulePlan.flight.flightRoute.originAirport.iataCode = :inDepartureAirport AND fs1.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = fs2.flightSchedulePlan.flight.flightRoute.originAirport.iataCode AND fs2.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = :inDestinationAirport AND fs1.departureDateTime >= :inStartDate AND fs1.arrivalDateTime <= fs2.departureDateTime AND fs2.departureDateTime < :inEndDate ORDER BY fs1.departureDateTime ASC, fs2.departureDateTime ASC");
        // fs1.seatInventories.availableSeats >= :inNumberOfTravellers AND fs2.seatInventories.availableSeats >= :inNumberOfTravellers 
        query.setParameter("inDepartureAirport", departureAirport);
        query.setParameter("inDestinationAirport", destinationAirport);
        query.setParameter("inStartDate", startDate);
        query.setParameter("inEndDate", endDate);
        //query.setParameter("inNumberOfTravellers", numberOfTravellers);
        
        List<Object[]> flightSchedulesObjects = query.getResultList();
        List<FlightSchedule> flightSchedules = new ArrayList<>();
        for (Object[] objectArr : flightSchedulesObjects) {
            //System.out.println(object + "  " + object.toString());
            FlightSchedule flightSchedule = null;
            //System.out.println("correct?");
            for (Object object : objectArr) {
                //System.out.println("here1");
                if (object instanceof FlightSchedule) {
                    //System.out.println("here2");
                    flightSchedule = (FlightSchedule) object;
                    flightSchedule.getFlightSchedulePlan().getFares().size();
                    flightSchedule.getSeatInventories().size();
                    flightSchedules.add(flightSchedule);
                }
            }
        }
        
//        List<FlightSchedule> flightSchedules = (List<FlightSchedule>) query.getResultList();
//        for (FlightSchedule flightSchedule : flightSchedules) {
//            flightSchedule.getFlightSchedulePlan().getFares().size();
//            flightSchedule.getSeatInventories().size();
//        }
        
        if (flightSchedules.isEmpty()) {
            throw new NoFlightsAvailableException("There are no single connection flights available for " + departureAirport + " to " + destinationAirport + " between " + startDate + " and " + endDate + ".");
        }
        
        return flightSchedules;        
    }
    
    @Override
    public List<FlightSchedule> searchTwoConnectionsFlight(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) throws NoFlightsAvailableException {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
        this.numberOfTravellers = numberOfTravellers;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date startDate = calendar.getTime();
        
        calendar.setTime(departureDate);
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        Date endDate = calendar.getTime();
                
        Query query = em.createQuery("SELECT fs1, fs2, fs3 FROM FlightSchedule fs1, FlightSchedule fs2, FlightSchedule fs3 WHERE fs1.flightSchedulePlan.flight.flightRoute.originAirport.iataCode = :inDepartureAirport AND fs1.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = fs2.flightSchedulePlan.flight.flightRoute.originAirport.iataCode AND fs2.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = fs3.flightSchedulePlan.flight.flightRoute.originAirport.iataCode AND fs3.flightSchedulePlan.flight.flightRoute.destinationAirport.iataCode = :inDestinationAirport AND fs1.departureDateTime >= :inStartDate AND fs1.arrivalDateTime <= fs2.departureDateTime AND fs2.arrivalDateTime <= fs3.departureDateTime AND fs3.departureDateTime < :inEndDate ORDER BY fs1.departureDateTime ASC, fs2.departureDateTime ASC, fs3.departureDateTime ASC");
        // fs1.seatInventories.availableSeats >= :inNumberOfTravellers AND fs2.seatInventories.availableSeats >= :inNumberOfTravellers 
        query.setParameter("inDepartureAirport", departureAirport);
        query.setParameter("inDestinationAirport", destinationAirport);
        query.setParameter("inStartDate", startDate);
        query.setParameter("inEndDate", endDate);
        //query.setParameter("inNumberOfTravellers", numberOfTravellers);
        
        List<Object[]> flightSchedulesObjects = query.getResultList();
        List<FlightSchedule> flightSchedules = new ArrayList<>();
//        List<List<FlightSchedule>> flightSchedules = new ArrayList<>();
        for (Object[] objectArr : flightSchedulesObjects) {
            //System.out.println(object + "  " + object.toString());
            FlightSchedule flightSchedule = null;
//            FlightSchedule flightSchedule = null;
//            List<FlightSchedule> flightScheduleList = new ArrayList<>();
//            FlightSchedule flightSchedule = null;
            //System.out.println("correct?");
            for (Object object : objectArr) {
                //System.out.println("here1");
                if (object instanceof FlightSchedule) {
                    //System.out.println("here2");
                    flightSchedule = (FlightSchedule) object;
                    flightSchedule.getFlightSchedulePlan().getFares().size();
                    flightSchedule.getSeatInventories().size();
                    flightSchedules.add(flightSchedule);
//                    flightScheduleList.add(flightSchedule);
                }
            }
            
//            flightSchedules.add(flightScheduleList);
        }
        
//        List<FlightSchedule> flightSchedules = (List<FlightSchedule>) query.getResultList();
//        for (FlightSchedule flightSchedule : flightSchedules) {
//            flightSchedule.getFlightSchedulePlan().getFares().size();
//            flightSchedule.getSeatInventories().size();
//        }
        
        if (flightSchedules.isEmpty()) {
            throw new NoFlightsAvailableException("There are no double connection flights available for " + departureAirport + " to " + destinationAirport + " between " + startDate + " and " + endDate + ".");
        }
        
        return flightSchedules;     
    }
    
    @Override
    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        FlightSchedule flightSchedule = null;
        
        try {
            flightSchedule = flightScheduleSessionBeanLocal.retrieveFlightScheduleById(flightScheduleId);    
        } catch (FlightSchedulePlanNotFoundException ex) {
            throw new FlightSchedulePlanNotFoundException("Flight Schedule Not Found");
        }
        return flightSchedule;
    }
    
    @Override
    public Seat retrieveSeatById(Long seatId) throws SeatNotFoundException {
        Seat seat = null;
        
        try {
            seat = seatSessionBeanLocal.retrieveSeatById(seatId);
        } catch (SeatNotFoundException ex) {
            throw new SeatNotFoundException("Seat Not Found");
        } 
        
        return seat;
    }
    
    @Override
    public Long reserveSeat(FlightSchedule flightSchedule, CabinClassType cabinClassType, Integer seatRow, String seatCol) throws SeatInventoryNotFoundException, SeatNotFoundException, SeatReservedException {
        SeatInventory seatInventory = null;
        for (SeatInventory seatInventoryTemp : flightSchedule.getSeatInventories()) {
            if (seatInventoryTemp.getCabinClass().getCabinClassType().equals(cabinClassType)) {
                seatInventory = seatInventoryTemp;
                break;
            }
        }
        
        if (seatInventory == null) {
            throw new SeatInventoryNotFoundException("Seat Inventory not found?");
        }
        
        
        Seat seat = seatSessionBeanLocal.retrieveSeatByRowCol(seatInventory.getSeatInventoryId(), seatRow, seatCol);
        
        if (!seat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
            throw new SeatReservedException("Oops! Seat is reserved");
        }
        
        seat.setSeatStatus(SeatStatus.RESERVED);      
        
        return seat.getSeatId();
    }
    
    @Override
    public Long createNewCreditCardCustomer(CreditCard creditCard, Customer customer) throws CustomerNotFoundException {
//        Customer customerToSet = customerSessionBeanLocal.retrieveCustomerById(customer.getCustomerId());
        Customer customerToSet = em.find(Customer.class, customer.getCustomerId());
        customerToSet.getCreditCards().add(creditCard);
        customerToSet.getCreditCards().size();
        
        em.persist(creditCard);
        em.flush();
        
        return creditCard.getCreditCardId();
    }
    
    @Override
    public CreditCard retrieveCreditCardById(Long creditCardId) throws CreditCardNotFoundException {
        CreditCard creditCard = em.find(CreditCard.class, creditCardId);
        if (creditCard == null) {
            throw new CreditCardNotFoundException("Credit Card " + creditCardId + " does not exist!");
        }
        
        return creditCard;
    }
    
    @Override
    public List<CreditCard> retrieveAllCreditCardCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
        customer.getCreditCards().size();
        
        return customer.getCreditCards();
    }
    
    @Override
    public List<Transaction> retrieveAllTransactionByCustomerId(Long customerId) {
        //Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.customerId = :inCustomerId");
        query.setParameter(":inCustomerId", customerId);
        
        List<Transaction> transactions = query.getResultList();
        
        for (Transaction transaction : transactions) {
            transaction.getItineraryItems().size();
        }
        
        return transactions;
    }
    
    @Override
    public List<ItineraryItem> retrieveAllItineraryItemByTransactionId(Long transactionId) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.transactionId = :inTransactionId");
        query.setParameter(":inTransactionId", transactionId);
        Transaction transaction = (Transaction) query.getSingleResult();
        transaction.getItineraryItems().size();
        
        return transaction.getItineraryItems();
    }
      
    @Override
    public Fare getFarePerPax(FlightSchedule flightSchedule, SeatInventory seatInventory, Object object) {
        List<Fare> fares = flightSchedule.getFlightSchedulePlan().getFares();
        
        Fare fareToReturn = null;
        
        BigDecimal farePax = BigDecimal.ZERO;
        if (object instanceof Customer) {
            for (Fare fare : fares) {
                if(fare.getCabinClassConfiguration().equals(seatInventory.getCabinClass())) {
                    if (farePax.equals(BigDecimal.ZERO) || farePax.compareTo(fare.getFareAmount()) > 0) {
                        farePax = fare.getFareAmount();
                        fareToReturn = fare;
                    }
                }
            }
        } else if (object instanceof Partner) {
            for (Fare fare : fares) {
                if(fare.getCabinClassConfiguration().equals(seatInventory.getCabinClass())) {
                    if (farePax.equals(BigDecimal.ZERO) || farePax.compareTo(fare.getFareAmount()) < 0) {
                        farePax = fare.getFareAmount();
                        fareToReturn = fare;
                    }
                }
            }
        } else {
            throw new NullPointerException("Unable to retrieve Fare as entity neither a customer or partner!");
        }
        
        return fareToReturn;
    }
    
    @Override
    public Boolean checkFlightScheduleExist(Long flightScheduleId, CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException {
        try {
            FlightSchedule flightSchedule = flightScheduleSessionBeanLocal.retrieveFlightScheduleById(flightScheduleId);
            Boolean cabinClass = false;
            for (CabinClassConfiguration cabinClassConfiguration : flightSchedule.getFlightSchedulePlan().getFlight().getAircraftConfiguration().getCabinClassConfigurations()) {
                if(cabinClassConfiguration.getCabinClassType().equals(cabinClassType)) {
                    cabinClass = true;
                }
            }
            if (cabinClass == false) {
                throw new CabinClassConfigurationNotFoundException("Cabin class entered does not exist!");
            }
        } catch (FlightSchedulePlanNotFoundException ex) {
            throw new FlightSchedulePlanNotFoundException("Invalid Flight Schedule Id entered!");
        }
        
        return true;
    }
    
    @Override
    public void rollBackSeatsToAvailable(Long seatId) throws SeatNotFoundException {
        Seat seat = seatSessionBeanLocal.retrieveSeatById(seatId);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
    }
    
    
    @Override
    public Long createNewTransaction(Transaction transaction, Customer customer) throws CustomerNotFoundException {
        
//        Customer customerToSet = customerSessionBeanLocal.retrieveCustomerById(customer.getCustomerId());
        Customer customerToSet = em.find(Customer.class, customer.getCustomerId());
        transaction.setCustomer(customerToSet);
        customerToSet.getTransactions().add(transaction);
        
        em.persist(transaction);
        em.flush();
        
        return transaction.getTransactionId();
    }
    
    @Override
    public Transaction retrieveTransactionById(Long transactionId) throws TransactionNotFoundException {
    
        Transaction transaction = em.find(Transaction.class, transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction Doesnt Exist!");
        }
        
        return transaction;
    
    }
    
    @Override
    public Long createNewItinerary(ItineraryItem itineraryItem, Long transactionId, Long flightScheduleId) throws TransactionNotFoundException, FlightSchedulePlanNotFoundException {
        Transaction transaction = retrieveTransactionById(transactionId);
        FlightSchedule flightSchedule = retrieveFlightScheduleById(flightScheduleId);
        
        itineraryItem.setFlightSchedule(flightSchedule);
        flightSchedule.getItineraryItems().add(itineraryItem);
        transaction.getItineraryItems().add(itineraryItem);
        
        em.persist(itineraryItem);
        em.flush();
        
        return itineraryItem.getItineraryItemId();
        
    }
    
    @Override
    public void makePayment(CreditCard creditCard, BigDecimal totalFare) {
        creditCardProcessinngSessionBeanLocal.chargeCreditCard(creditCard.getCreditCardNumber(), totalFare);
    }
}
