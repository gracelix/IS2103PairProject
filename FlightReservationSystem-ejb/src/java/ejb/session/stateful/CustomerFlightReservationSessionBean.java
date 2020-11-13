/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.FlightScheduleSessionBeanLocal;
import entity.FlightSchedule;
import entity.SeatInventory;
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

/**
 *
 * @author Ziyue
 */
@Stateful
public class CustomerFlightReservationSessionBean implements CustomerFlightReservationSessionBeanRemote {

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
    public List<FlightSchedule> searchSingleFlights(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfTravellers) {
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
        
        //lazy fetching for whatebe fare?
        
        List<FlightSchedule> tempList = query.getResultList();
        
        flightSchedules = new ArrayList<>();
        for (FlightSchedule flightSchedule : tempList) {
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
    public List<FlightSchedule> searchFlights(String departureAirport, String destinationAirport, Date departureDate, Date returnDate, Integer numberOfTravellers) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
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
        
        flightSchedules = new ArrayList<>();
        for (FlightSchedule flightSchedule : tempList) {
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
    
//    @Override
//    public List<ItineraryItem> searchHolidays(Date departureDate, Date returnDate, String departureCity, String destinationCity, Integer numberOfTravellers)
//    {
//        Random random = new Random((new Date()).getTime());
//        
//        this.departureDate = departureDate;
//        this.returnDate = returnDate;
//        this.departureCity = departureCity;
//        this.destinationCity = destinationCity;
//        this.numberOfTravellers = numberOfTravellers;       
//
//        List<ItineraryItem> flightTickets = flightTicketSessionBeanLocal.searchFlights(departureDate, returnDate, departureCity, destinationCity);
//        List<ItineraryItem> hotelRooms = hotelSessionBeanLocal.searchHotelRooms(flightTickets.get(1).getDateTime(), flightTickets.get(2).getDateTime(), departureCity, destinationCity);
//        List<ItineraryItem> rentalCars = carRentalSessionBeanLocal.searchRentalCars(flightTickets.get(1).getDateTime(), flightTickets.get(2).getDateTime(), departureCity, destinationCity);
//
//        itineraryItems = new ArrayList<>();
//        itineraryItems.add(flightTickets.get(0));
//        itineraryItems.add(flightTickets.get(1));
//        itineraryItems.add(rentalCars.get(0));
//        itineraryItems.add(hotelRooms.get(0));
//        itineraryItems.add(hotelRooms.get(1));
//        itineraryItems.add(rentalCars.get(1));
//        itineraryItems.add(flightTickets.get(2));
//        itineraryItems.add(flightTickets.get(3));
//
//        Integer sequenceNumber = 0;
//
//        for(ItineraryItem itineraryItem:itineraryItems)
//        {
//            itineraryItem.setSequenceNumber(++sequenceNumber);
//        }
//        
//        Integer totalAmountInt = (random.nextInt(100) + 1) * 100;        
//        totalAmount = BigDecimalHelper.createBigDecimal(totalAmountInt.toString());
//        
//        return itineraryItems;
//    }
//    
//    
//    
//    public Transaction reserveHoliday(Long customerId, PaymentModeEnum paymentMode, String creditCardNumber) throws CheckoutException
//    {
//        return checkoutSessionBeanLocal.checkout(customerId, itineraryItems, paymentMode, creditCardNumber, totalAmount);
//    }
//
//    
//    
//    @Override
//    public BigDecimal getTotalAmount() 
//    {
//        return totalAmount;
//    }
}
