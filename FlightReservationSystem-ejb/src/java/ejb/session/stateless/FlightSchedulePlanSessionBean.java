/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Flight;
import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {

    @EJB
    private FlightSessionBeanLocal flightSessionBeanLocal;

    
    
    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<FlightSchedulePlan> retrieveFlightSchedulePlansByFlightId(Long flightId) {
        Query query = em.createQuery("SELECT fsp FROM FlightSchedulePlan fsp WHERE fsp.flight.flightId = :inFlightId");
        query.setParameter("inFlightId", flightId);
        
        return query.getResultList();
    }
    
    @Override
    public FlightSchedulePlan retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException {
        FlightSchedulePlan flightSchedulePlan = em.find(FlightSchedulePlan.class, flightSchedulePlanId);
        if (flightSchedulePlan == null) {
            throw new FlightSchedulePlanNotFoundException("Flight schedule plan " + flightSchedulePlanId + " does not exist!");
        }
        
        return flightSchedulePlan;
    }
    
    @Override
    public Long createNewFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long flightId) throws FlightNotFoundException {
        Flight flight = flightSessionBeanLocal.retrieveFlightById(flightId);
        
        newFlightSchedulePlan.setFlight(flight);
        newFlightSchedulePlan.setEnableFlight(Boolean.TRUE);
        
        flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
        
        em.persist(newFlightSchedulePlan);
        em.flush();
        
        return newFlightSchedulePlan.getFlightSchedulePlanId();
    }
    
    @Override
    public Long createNewComplementaryReturnFlightSchedulePlan(FlightSchedulePlan newFlightSchedulePlan, Long originalFlightSchedulePlanId , Long flightId) throws FlightNotFoundException, FlightSchedulePlanNotFoundException {
        Flight flight = flightSessionBeanLocal.retrieveFlightById(flightId);
        FlightSchedulePlan originalFlightSchedulePlan = retrieveFlightSchedulePlanById(originalFlightSchedulePlanId);
        
        newFlightSchedulePlan.setFlight(flight);
        newFlightSchedulePlan.setEnableFlight(Boolean.TRUE);
        
        flight.getFlightSchedulePlans().add(newFlightSchedulePlan);
        
        originalFlightSchedulePlan.setComplementaryReturnFlightSchedulePlan(newFlightSchedulePlan);
        newFlightSchedulePlan.setOrginalFlightSchedulePlan(originalFlightSchedulePlan);
                
        em.persist(newFlightSchedulePlan);
        em.flush();
        
        return newFlightSchedulePlan.getFlightSchedulePlanId();
    }
    
    @Override
    public List<FlightSchedulePlan> retrieveAllFlightSchedulePlans() {
        //Query query = em.createQuery("SELECT DISTINCT fs.flightSchedulePlan FROM FlightSchedule fs LEFT JOIN FlightSchedulePlan fsp ON fs.flightSchedulePlan.flightSchedulePlanId = fsp.flightSchedulePlanId WHERE fsp.originalFlightSchedulePlan IS NULL ORDER BY fsp.flight.flightNumber ASC, fs.departureDateTime DESC");
    
        
        //Query query2 = em.createQuery("SELECT DISTINCT fsp FROM FlightSchedulePlan fsp WHERE fsp.orginalFlightSchedulePlan IS NULL ORDER BY fsp.flight.flightNumber ASC");
        Query query = em.createQuery("SELECT DISTINCT fs.flightSchedulePlan FROM FlightSchedule fs WHERE fs.flightSchedulePlan.orginalFlightSchedulePlan IS NULL ORDER BY fs.flightSchedulePlan.flight.flightNumber ASC, fs.departureDateTime DESC");
        List<FlightSchedulePlan> flightSchedulePlans = query.getResultList();
        
        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            flightSchedulePlan.getFlight().getFlightNumber();
            flightSchedulePlan.getComplementaryReturnFlightSchedulePlan();
            flightSchedulePlan.getFlightSchedules().size();
        }
        
        return flightSchedulePlans;
    
    }
}
