/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.CabinClassConfiguration;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftConfigurationNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class CabinClassConfigurationSessionBean implements CabinClassConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanLocal {

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewCabinClassConfiguration(CabinClassConfiguration cabinClassConfiguration, Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException {
        AircraftConfiguration aircraftConfiguration = aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(aircraftConfigurationId);
        
        aircraftConfiguration.getCabinClassConfigurations().add(cabinClassConfiguration);
        cabinClassConfiguration.setAircraftConfiguration(aircraftConfiguration);
        
        em.persist(cabinClassConfiguration);
        em.flush();
        
        return cabinClassConfiguration.getCabinClassConfigurationId();
    }
}
