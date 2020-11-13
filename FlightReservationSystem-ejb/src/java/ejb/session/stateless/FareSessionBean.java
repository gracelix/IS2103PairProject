/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import entity.Fare;
import entity.FlightSchedulePlan;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FareNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class FareSessionBean implements FareSessionBeanRemote, FareSessionBeanLocal {

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBeanLocal;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewFare(Fare newFare, Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException {
        FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(flightSchedulePlanId);
        
        flightSchedulePlan.getFares().add(newFare);
        Long cabinClassConfigurationId = newFare.getCabinClassConfiguration().getCabinClassConfigurationId();
        
        CabinClassConfiguration cabinClassConfiguration = cabinClassConfigurationSessionBeanLocal.retrieveCabinClassConfigurationById(cabinClassConfigurationId);
        cabinClassConfiguration.getFares().add(newFare);
        
        em.persist(newFare);
        em.flush();
        
        return newFare.getFareId();
        
    }
    
    @Override
    public Fare retrieveFareByFareId(Long fareId) throws FareNotFoundException {
        Fare fare = em.find(Fare.class, fareId);
        if (fare == null) {
            throw new FareNotFoundException("Flight " + fareId + " does not exist!");
        }
        
        return fare;
    }
    
    @Override
    public void associateFareWithReturnFlightSchedulePlan(Long fareId, Long flightSchedulePlanId) throws FareNotFoundException, FlightSchedulePlanNotFoundException {
        Fare fare = retrieveFareByFareId(fareId);
        FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanLocal.retrieveFlightSchedulePlanById(flightSchedulePlanId);
        flightSchedulePlan.getFares().add(fare);
    }
    
//    public List<Fare> retrieveFareByFlightSchedulePlanId(Long flightSchedulePlanId) {
//        Query query = em.createQuery("SELECT f FROM Fare f WHERE F");
//    }
}
