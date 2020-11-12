/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Seat;
import entity.SeatInventory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.SeatStatus;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Stateless
public class SeatSessionBean implements SeatSessionBeanRemote, SeatSessionBeanLocal {

    @EJB
    private SeatInventorySessionBeanLocal seatInventorySessionBeanLocal;

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewSeat(Seat newSeat, Long seatInventoryId) throws SeatInventoryNotFoundException {
        SeatInventory seatInventory = seatInventorySessionBeanLocal.retrieveSeatInventoryById(seatInventoryId);
        
        newSeat.setSeatStatus(SeatStatus.AVAILABLE);
        seatInventory.getSeats().add(newSeat);
        
        em.persist(newSeat);
        em.flush();
        
        return newSeat.getSeatId();
    }
}
