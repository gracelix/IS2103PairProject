/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.SeatInventory;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FlightScheduleSessionBean implements FlightScheduleSessionBeanRemote, FlightScheduleSessionBeanLocal {

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBeanLocal;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    
     
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewFlightSchedule(FlightSchedule newFlightSchedule, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException {
        FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(flightSchedulePlanId);
    
        flightSchedulePlan.getFlightSchedules().add(newFlightSchedule);
        newFlightSchedule.setFlightSchedulePlan(flightSchedulePlan);
        
        em.persist(newFlightSchedule);
        em.flush();
        
        return newFlightSchedule.getFlightScheduleId();
    
    }
    
    @Override
    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        FlightSchedule flightSchedule = em.find(FlightSchedule.class, flightScheduleId);
        if (flightSchedule == null) {
            throw new FlightSchedulePlanNotFoundException("Flight Schedule "+ flightScheduleId + " does not exist!");
        }
        
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
