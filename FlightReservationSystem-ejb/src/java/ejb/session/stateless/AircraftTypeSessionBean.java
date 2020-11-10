/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class AircraftTypeSessionBean implements AircraftTypeSessionBeanRemote, AircraftTypeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewAircraftType(AircraftType newAircraftType) {
        em.persist(newAircraftType);
        em.flush();
        return newAircraftType.getAircraftTypeId();
    }
    
    @Override
    public AircraftType retrieveAircraftTypeById(Long aircraftTypeId) throws AircraftTypeNotFoundException {
        AircraftType aircraftType = em.find(AircraftType.class, aircraftTypeId);
        if (aircraftType == null) {
            throw new AircraftTypeNotFoundException("Aircraft Type " + aircraftTypeId + " does not exist!");
        }
        
        return aircraftType;
    }
}
