/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightRoute;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightRouteNotFoundException;
import util.exception.InvalidIataCodeException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface FlightRouteSessionBeanRemote {
    
    public Long createNewFlightRoute(FlightRoute newFlightRoute, String originAirportIata, String destinationAirportIata) throws InvalidIataCodeException;
    
    public FlightRoute retrieveFlightRouteById(Long flightRouteId) throws FlightRouteNotFoundException;

    public Long createNewComplementaryFlightRoute(FlightRoute complementaryFlightRoute, Long originalFlightRouteId, String originAirportIata, String destinationAirportIata) throws InvalidIataCodeException, FlightRouteNotFoundException;
    
    public List<FlightRoute> retrieveAllFlightRoutes();
    
}
