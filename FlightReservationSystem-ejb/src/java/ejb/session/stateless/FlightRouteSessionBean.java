/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Airport;
import entity.FlightRoute;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.FlightRouteNotFoundException;
import util.exception.InvalidIataCodeException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class FlightRouteSessionBean implements FlightRouteSessionBeanRemote, FlightRouteSessionBeanLocal {

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewFlightRoute(FlightRoute newFlightRoute, String originAirportIata, String destinationAirportIata) throws InvalidIataCodeException {
        Airport originAirport = airportSessionBean.retrieveAirportByIataCode(originAirportIata);
        Airport destinationAirport = airportSessionBean.retrieveAirportByIataCode(destinationAirportIata);
        
        newFlightRoute.setOriginAirport(originAirport);
        newFlightRoute.setDestinationAirport(destinationAirport);
        newFlightRoute.setEnabledFlightRoute(Boolean.TRUE);
        
        originAirport.getOriginFlightRoutes().add(newFlightRoute);
        destinationAirport.getDestinationFlightRoutes().add(newFlightRoute);
        
        em.persist(newFlightRoute);
        em.flush();
        return newFlightRoute.getFlightRouteId();
    }
    
    @Override
    public FlightRoute retrieveFlightRouteById(Long flightRouteId) throws FlightRouteNotFoundException {
        FlightRoute flightRoute = em.find(FlightRoute.class, flightRouteId);
        if (flightRoute == null) {
            throw new FlightRouteNotFoundException("Flight Route " + flightRouteId + " does not exist!");
        }
        
        return flightRoute;
    }
    
    @Override
    public Long createNewComplementaryFlightRoute(FlightRoute complementaryFlightRoute, Long originalFlightRouteId, String originAirportIata, String destinationAirportIata) throws InvalidIataCodeException, FlightRouteNotFoundException {
        Airport originAirport = airportSessionBean.retrieveAirportByIataCode(originAirportIata);
        Airport destinationAirport = airportSessionBean.retrieveAirportByIataCode(destinationAirportIata);
        
        FlightRoute originalFlightRoute = retrieveFlightRouteById(originalFlightRouteId);
        
        complementaryFlightRoute.setOriginAirport(originAirport);
        complementaryFlightRoute.setDestinationAirport(destinationAirport);
        complementaryFlightRoute.setEnabledFlightRoute(Boolean.TRUE);
        
        originAirport.getOriginFlightRoutes().add(complementaryFlightRoute);
        destinationAirport.getDestinationFlightRoutes().add(complementaryFlightRoute);
        
        complementaryFlightRoute.setComplementaryFlightRoute(originalFlightRoute);
        originalFlightRoute.setComplementaryFlightRoute(complementaryFlightRoute);
        
        em.persist(complementaryFlightRoute);
        em.flush();
        return complementaryFlightRoute.getFlightRouteId();
    }
}
