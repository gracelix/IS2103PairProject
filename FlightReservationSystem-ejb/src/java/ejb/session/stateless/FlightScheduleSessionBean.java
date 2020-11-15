/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Seat;
import entity.SeatInventory;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FlightScheduleSessionBean implements FlightScheduleSessionBeanRemote, FlightScheduleSessionBeanLocal {

    @EJB
    private SeatSessionBeanLocal seatSessionBean;

    @EJB
    private SeatInventorySessionBeanLocal seatInventorySessionBean;

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBeanLocal;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewFlightSchedule(FlightSchedule newFlightSchedule, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException {
        FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(flightSchedulePlanId);
    
        flightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
        newFlightSchedule.setFlightSchedulePlan(flightSchedulePlan);
        
        em.persist(newFlightSchedule);
        em.flush();
        
        List<CabinClassConfiguration> cabinClassConfigurations = flightSchedulePlan.getFlight().getAircraftConfiguration().getCabinClassConfigurations();
        
        for (CabinClassConfiguration cabinClassConfigurationForSeatInventory : cabinClassConfigurations) {
                SeatInventory seatInventory = new SeatInventory(cabinClassConfigurationForSeatInventory.getCabinMaximumSeatCapacity());
                Long seatInventoryId = seatInventorySessionBean.createNewSeatInventory(seatInventory, cabinClassConfigurationForSeatInventory.getCabinClassConfigurationId(), newFlightSchedule.getFlightScheduleId());

                Integer rows = cabinClassConfigurationForSeatInventory.getNumberOfRows();
                Integer columns = cabinClassConfigurationForSeatInventory.getNumberOfSeatsAbreast();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        char alphabet = (char) ('A' + j);
                        String rowAlphabet = Character.toString(alphabet);
                        Seat seat = new Seat(i + 1, rowAlphabet);
                        seatSessionBean.createNewSeat(seat, seatInventoryId);
                    }
                }
                //System.out.println("Seat Inventory " + seatInventoryId + " created for Flight Schedule " + newFlightSchedule.getFlightScheduleId() + " under flight schedule plan " + flightSchedulePlanId);
            }
        
        return newFlightSchedule.getFlightScheduleId();
    
    }
    
    @Override
    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        FlightSchedule flightSchedule = em.find(FlightSchedule.class, flightScheduleId);
        if (flightSchedule == null) {
            throw new FlightSchedulePlanNotFoundException("Flight Schedule "+ flightScheduleId + " does not exist!");
        }
        
        flightSchedule.getSeatInventories().size();
        
        return flightSchedule;
        
    }
    
    @Override
    public void checkForScheduleOverlap(Long flightId, Date departureDate, Date arrivalDate) throws FlightScheduleOverlapException {
        Query query = em.createQuery("SELECT fs FROM FlightSchedule fs WHERE fs.flightSchedulePlan.flight.flightId = :inFlightId");
        query.setParameter("inFlightId", flightId);
        
        List<FlightSchedule> flightSchedules = query.getResultList();
        
        for (FlightSchedule flightSchedule : flightSchedules) {
            if ((departureDate.getTime() < flightSchedule.getArrivalDateTime().getTime()
                    && departureDate.getTime() > flightSchedule.getDepartureDateTime().getTime())
                    || (arrivalDate.getTime() < flightSchedule.getArrivalDateTime().getTime()
                    && arrivalDate.getTime() > flightSchedule.getDepartureDateTime().getTime())) {
                throw new FlightScheduleOverlapException("Time clashes with existing flight schedules!");
            }
        }
        
    }
    
    @Override
    public void deleteFlightSchedule(Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        FlightSchedule flightScheduleToRemove = retrieveFlightScheduleById(flightScheduleId);
        flightScheduleToRemove.getFlightSchedulePlan().getFlightSchedules().remove(flightScheduleToRemove);
        em.remove(flightScheduleToRemove);
    }
    
   
    
}
