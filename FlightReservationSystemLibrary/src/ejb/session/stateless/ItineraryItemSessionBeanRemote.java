/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItineraryItem;
import javax.ejb.Remote;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface ItineraryItemSessionBeanRemote {
    public Long createItineraryItem(ItineraryItem itineraryItem, Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

}
