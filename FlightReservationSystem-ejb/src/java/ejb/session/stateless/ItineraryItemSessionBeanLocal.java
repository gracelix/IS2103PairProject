/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ItineraryItem;
import javax.ejb.Local;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Local
public interface ItineraryItemSessionBeanLocal {

    public Long createItineraryItem(ItineraryItem itineraryItem, Long flightScheduleId) throws FlightSchedulePlanNotFoundException;
    
}
