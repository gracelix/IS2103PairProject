/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AirportNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class AirportSessionBean implements AirportSessionBeanRemote, AirportSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewAirport(Airport newAirport) {
        em.persist(newAirport);
        em.flush();
        return newAirport.getAirportId();
    }
    
    @Override
    public Airport retrieveAirportById(Long airportId) throws AirportNotFoundException {
        Airport airport = em.find(Airport.class, airportId);
        if (airport == null) {
            throw new AirportNotFoundException("Partner " + airportId + " does not exist!");
        }
        
        return airport;
    }
}
