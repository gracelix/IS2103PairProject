/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import entity.FlightSchedule;
import entity.SeatInventory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class SeatInventorySessionBean implements SeatInventorySessionBeanRemote, SeatInventorySessionBeanLocal {

    @EJB
    private FlightScheduleSessionBeanLocal flightScheduleSessionBeanLocal;

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewSeatInventory(SeatInventory newSeatInventory, Long cabinClassConfigurationId, Long flightScheduleId) throws CabinClassConfigurationNotFoundException, FlightSchedulePlanNotFoundException {
        CabinClassConfiguration cabinClassConfiguration = cabinClassConfigurationSessionBeanLocal.retrieveCabinClassConfigurationById(cabinClassConfigurationId);
        FlightSchedule flightSchedule = flightScheduleSessionBeanLocal.retrieveFlightScheduleById(flightScheduleId);

        flightSchedule.getSeatInventories().add(newSeatInventory);
        cabinClassConfiguration.getSeatInventories().add(newSeatInventory);
        newSeatInventory.setCabinClass(cabinClassConfiguration);
        
        em.persist(newSeatInventory);
        em.flush();
        
        return newSeatInventory.getSeatInventoryId();
    }
    
    @Override
    public SeatInventory retrieveSeatInventoryById(Long seatInventoryId) throws SeatInventoryNotFoundException {
        SeatInventory seatInventory = em.find(SeatInventory.class, seatInventoryId);
        if (seatInventory == null) {
            throw new SeatInventoryNotFoundException("Seat Inventory " + seatInventoryId + " does not exist!");
        }
    
        return seatInventory;
    }
}
