/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItineraryItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FlightReservationSessionBean implements FlightReservationSessionBeanRemote, FlightReservationSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<ItineraryItem> retrieveAllItineraryItems() {
        Query query = em.createQuery("SELECT it FROM ItineraryItem it ORDER BY it.seatNumber ASC");
        
        List<ItineraryItem> itineraryItems = query.getResultList();
        
        //if remove association this got to go
        for (ItineraryItem itineraryItem : itineraryItems) {
            itineraryItem.getFlightSchedule();
        }
        
        return itineraryItems;
    }
    
    @Override
    public List<ItineraryItem> retrieveAllItineraryItemsByFlightScheduleId(Long flightScheduleId) {
        Query query = em.createQuery("SELECT it FROM ItineraryItem it WHERE it.flightSchedule.flightScheduleId = :inFlightScheduleId ORDER BY it.seatNumber ASC");
        query.setParameter("inFlightScheduleId", flightScheduleId);
        List<ItineraryItem> itineraryItems = query.getResultList();
        
        //if remove association this got to go
        for (ItineraryItem itineraryItem : itineraryItems) {
            itineraryItem.getFlightSchedule();
        }
        
        return itineraryItems;
    }
}
