/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Flight;
import javax.ejb.Remote;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface FlightSessionBeanRemote {
    public Long createNewFlight(Flight newFlight, Long flightRouteId, Long aircraftConfigurationId) throws FlightNotFoundException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException;
    
    public Flight retrieveFlightById(Long flightId) throws FlightNotFoundException;
    
    public Long createNewComplementaryReturnFlight(Flight newFlight, Long originalFlightId, Long flightRouteId, Long aircraftConfigurationId) throws FlightRouteNotFoundException, AircraftConfigurationNotFoundException, FlightNotFoundException;
}
