/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {
    public List<FlightSchedulePlan> retrieveFlightSchedulePlansByFlightId(Long flightId);
       
    public Long createNewFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId) throws FlightNotFoundException;
    
    public FlightSchedulePlan retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;
    
    public Long createNewComplementaryReturnFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long originalFlightSchedulePlanId, Long flightId) throws FlightNotFoundException, FlightSchedulePlanNotFoundException;

}
