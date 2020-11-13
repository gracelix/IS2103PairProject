/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import java.util.Date;
import javax.ejb.Remote;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface FlightScheduleSessionBeanRemote {
    public Long createNewFlightSchedule(FlightSchedule newFlightSchedule, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;
    
    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

    public void checkForScheduleOverlap(Long flightId, Date departureDate, Date arrivalDate) throws FlightScheduleOverlapException;

    public void deleteFlightSchedule(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

}
