/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.Flight;
import entity.FlightRoute;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.UpdateFlightException;

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
    
//    @Override
//    public List<Flight> retrieveAllFlights() {
//        Query query = em.createQuery("SELECT f FROM Flight f WHERE f.originalFlight IS NULL ORDER BY f.flightNumber ASC");
//        List<Flight> flights = query.getResultList();
//        
//        for (Flight flight : flights) {
//            flight.getFlightSchedulePlans().size();
//            flight.getAircraftConfiguration();
//            flight.getFlightRoute();
//            flight.getComplementaryReturnFlight();
//            flight.getOriginalFlight();
//        }
//        
//        return flights;
//    }
    
    @Override
    public List<Flight> retrieveAllFlights() {
        Query query = em.createQuery("SELECT f FROM Flight f WHERE f.originalFlight IS NULL ORDER BY f.flightNumber ASC");
        List<Flight> flights = query.getResultList();
        Flight complementaryFlight = null;
        List<Flight> finalFlightList = new ArrayList<>();
        
        for (Flight flight : flights) {
            flight.getAircraftConfiguration();
            flight.getFlightRoute();
            flight.getComplementaryReturnFlight();
            flight.getOriginalFlight();
            flight.getFlightSchedulePlans().size();
        }
        
        for (Flight flight : flights) {
            if (flight.getComplementaryReturnFlight() != null) {
                
                Long complementaryFlightId = flight.getComplementaryReturnFlight().getFlightId();
                
                try {
                    complementaryFlight = retrieveFlightById(complementaryFlightId);
                } catch (FlightNotFoundException ex) {
                    
                }
                finalFlightList.add(flight);
                finalFlightList.add(complementaryFlight);
                
            } else {
                finalFlightList.add(flight);
            }
        }
        return finalFlightList;
    }
    
    @Override
    public void updateFlight(Flight updatedFlight) throws FlightNotFoundException, UpdateFlightException {
        if (updatedFlight != null && updatedFlight.getFlightId() != null) {
        
            Flight flightToUpdate = retrieveFlightById(updatedFlight.getFlightId());
            if (flightToUpdate.getFlightId().equals(updatedFlight.getFlightId())) {
                flightToUpdate.setFlightNumber(updatedFlight.getFlightNumber());
                flightToUpdate.setFlightRoute(updatedFlight.getFlightRoute());
                flightToUpdate.setAircraftConfiguration(updatedFlight.getAircraftConfiguration());
            } else {
                throw new UpdateFlightException("Flight ID of flight to be updated does not match existing record ID.");
            }
        
        } else {
            throw new FlightNotFoundException("Flight ID not provided or not found.");
        }
    }
}
