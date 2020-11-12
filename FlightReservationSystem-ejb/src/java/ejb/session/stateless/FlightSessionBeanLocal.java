/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Flight;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.UpdateFlightException;

/**
 *
 * @author Ziyue
 */
@Local
public interface FlightSessionBeanLocal {

    public Long createNewFlight(Flight newFlight, Long flightRouteId, Long aircraftConfigurationId) throws FlightNotFoundException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException;

    public Flight retrieveFlightById(Long flightId) throws FlightNotFoundException;

    public Long createNewComplementaryReturnFlight(Flight newFlight, Long originalFlightId, Long flightRouteId, Long aircraftConfigurationId) throws FlightRouteNotFoundException, AircraftConfigurationNotFoundException, FlightNotFoundException;

    public List<Flight> retrieveAllFlights();

    public void updateFlight(Flight updatedFlight) throws FlightNotFoundException, UpdateFlightException;

    public void deleteFlight(Long flightId) throws FlightNotFoundException, DeleteFlightException;
    
}
