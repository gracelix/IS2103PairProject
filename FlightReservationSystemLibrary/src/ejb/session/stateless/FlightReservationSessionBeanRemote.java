/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItineraryItem;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ziyue
 */
@Remote
public interface FlightReservationSessionBeanRemote {
    public List<ItineraryItem> retrieveAllItineraryItems();
    public List<ItineraryItem> retrieveAllItineraryItemsByFlightScheduleId(Long flightScheduleId);

}
