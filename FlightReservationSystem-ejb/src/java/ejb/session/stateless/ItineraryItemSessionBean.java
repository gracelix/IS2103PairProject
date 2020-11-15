/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import entity.ItineraryItem;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class ItineraryItemSessionBean implements ItineraryItemSessionBeanRemote, ItineraryItemSessionBeanLocal {

    @EJB
    private FlightScheduleSessionBeanLocal flightScheduleSessionBeanLocal;

    
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createItineraryItem(ItineraryItem itineraryItem, Long flightScheduleId) throws FlightSchedulePlanNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        
        FlightSchedule flightSchedule = flightScheduleSessionBeanLocal.retrieveFlightScheduleById(flightScheduleId);
        
        itineraryItem.setOdDateTime(dateFormat.format(flightSchedule.getDepartureDateTime()));
        itineraryItem.setOdCode(flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOriginAirport().getIataCode());
        
        itineraryItem.setFlightSchedule(flightSchedule);
        flightSchedule.getItineraryItems().add(itineraryItem);

        em.persist(itineraryItem);
        em.flush();
        
        return itineraryItem.getItineraryItemId();
    }
}
