/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightSchedule;
import java.util.Date;
import javax.ejb.Local;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Local
public interface FlightScheduleSessionBeanLocal {

    public Long createNewFlightSchedule(FlightSchedule newFlightSchedule, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException;

    public FlightSchedule retrieveFlightScheduleById(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;

    public void checkForScheduleOverlap(Long flightId, Date departureDate, Date arrivalDate) throws FlightScheduleOverlapException;

    public Long deleteFlightSchedule(Long flightScheduleId) throws FlightSchedulePlanNotFoundException;
    
}
