/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Fare;
import javax.ejb.Remote;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FareNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface FareSessionBeanRemote {
    
    public Long createNewFare(Fare newFare, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException;
    
    public Fare retrieveFareByFareId(Long fareId) throws FareNotFoundException;
    
    public void associateFareWithReturnFlightSchedulePlan(Long fareId, Long flightSchedulePlanId) throws FareNotFoundException, FlightSchedulePlanNotFoundException;

}
