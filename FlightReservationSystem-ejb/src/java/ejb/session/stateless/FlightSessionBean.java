/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.Flight;
import entity.FlightRoute;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FlightSessionBean implements FlightSessionBeanRemote, FlightSessionBeanLocal {

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBeanLocal;
    
    
    
    

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewFlight(Flight newFlight, Long flightRouteId, Long aircraftConfigurationId) throws FlightNotFoundException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException {
        FlightRoute flightRoute = flightRouteSessionBeanLocal.retrieveFlightRouteById(flightRouteId);
        AircraftConfiguration aircraftConfiguration = aircraftConfigurationSessionBeanLocal.retrieveAircraftConfigurationById(aircraftConfigurationId);
        
        newFlight.setAircraftConfiguration(aircraftConfiguration);
        newFlight.setFlightRoute(flightRoute);
        newFlight.setEnableFlight(Boolean.TRUE);
        
        flightRoute.getFlights().add(newFlight);
        aircraftConfiguration.getFlights().add(newFlight);
        
        em.persist(newFlight);
        em.flush();
        
        return newFlight.getFlightId();
    }
    
    @Override
    public Flight retrieveFlightById(Long flightId) throws FlightNotFoundException {
        Flight flight = em.find(Flight.class, flightId);
        if (flight == null) {
            throw new FlightNotFoundException("Flight " + flightId + " does not exist!");
        }
        
        return flight;
    }
    
    @Override
    public Long createNewComplementaryReturnFlight(Flight newFlight, Long originalFlightId, Long returnFlightRouteId, Long aircraftConfigurationId) throws FlightRouteNotFoundException, AircraftConfigurationNotFoundException, FlightNotFoundException {
        FlightRoute returnFlightRoute = flightRouteSessionBeanLocal.retrieveFlightRouteById(returnFlightRouteId);
        Flight originalFlight = retrieveFlightById(originalFlightId);
        AircraftConfiguration aircraftConfiguration = aircraftConfigurationSessionBeanLocal.retrieveAircraftConfigurationById(aircraftConfigurationId);
        
        newFlight.setAircraftConfiguration(aircraftConfiguration);
        newFlight.setFlightRoute(returnFlightRoute);
        newFlight.setEnableFlight(Boolean.TRUE);
        
        
        aircraftConfiguration.getFlights().add(newFlight);
        returnFlightRoute.getFlights().add(newFlight);
        
        originalFlight.setComplementaryReturnFlight(newFlight);
        newFlight.setOriginalFlight(originalFlight);
        
        em.persist(newFlight);
        em.flush();
        
        return newFlight.getFlightId();
    
    }
}
