/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import entity.CabinClassConfiguration;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;

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
    
    @Override
    public CabinClassConfiguration retrieveCabinClassConfigurationById(Long cabinClassConfigurationId) throws CabinClassConfigurationNotFoundException {
        CabinClassConfiguration cabinClassConfiguration = em.find(CabinClassConfiguration.class, cabinClassConfigurationId);
        if (cabinClassConfiguration == null) {
            throw new CabinClassConfigurationNotFoundException("Cabin Class Configuration " + cabinClassConfigurationId + " does not exist!");
        }
        
        return cabinClassConfiguration;
    }
    
    @Override
    public List<CabinClassConfiguration> retrieveCabinClassConfigurationsByAircraftConfigurationId(Long aircraftConfigurationId) {
        Query query = em.createQuery("SELECT cc FROM CabinClassConfiguration cc WHERE cc.aircraftConfiguration.aircraftConfigurationId = :inAircraftConfigurationId");
        query.setParameter("inAircraftConfigurationId", aircraftConfigurationId);
        
        List<CabinClassConfiguration> cabinClassConfigurations = query.getResultList();
        
        for (CabinClassConfiguration cabinClassConfiguration : cabinClassConfigurations) {
            cabinClassConfiguration.getFares().size();
            cabinClassConfiguration.getCabinClassType();
        }
        
        return cabinClassConfigurations;
    }
}
