/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.AircraftType;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class AircraftConfigurationSessionBean implements AircraftConfigurationSessionBeanRemote, AircraftConfigurationSessionBeanLocal {

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBean;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewAircraftConfiguration(AircraftConfiguration newAircraftConfiguration, Long aircraftTypeId) throws AircraftTypeNotFoundException {
        AircraftType aircraftType = aircraftTypeSessionBean.retrieveAircraftTypeById(aircraftTypeId);
        
        newAircraftConfiguration.setAircraftType(aircraftType);
        em.persist(newAircraftConfiguration);
        em.flush();
        return newAircraftConfiguration.getAircraftConfigurationId();
    }
    
    public AircraftConfiguration retrieveAircraftConfigurationById(Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException {
        AircraftConfiguration aircraftConfiguration = em.find(AircraftConfiguration.class, aircraftConfigurationId);
        if (aircraftConfiguration == null) {
            throw new AircraftConfigurationNotFoundException("Aircraft Configuration " + aircraftConfigurationId + " does not exist!");
        }
        
        return aircraftConfiguration;
    }
}
