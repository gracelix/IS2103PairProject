/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Seat;
import entity.SeatInventory;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.SeatStatus;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;

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
    
    @Override
    public Seat retrieveSeatByRowCol(Long seatInventoryId, Integer seatRow, String seatColumn) throws SeatInventoryNotFoundException, SeatNotFoundException {
        SeatInventory seatInventory = seatInventorySessionBeanLocal.retrieveSeatInventoryById(seatInventoryId);
        List<Seat> seats = seatInventory.getSeats();
        for (Seat seat : seats) {
            if (seat.getRowAlphabet().equals(seatColumn) && seat.getSeatNumber() == seatRow) {
                return seat;
            }
        }
        throw new SeatNotFoundException("Seat with row: " + seatRow + " column: " + seatColumn + " does not exist!");
    }
    
    @Override
    public Seat retrieveSeatById(Long seatId) throws SeatNotFoundException {
        Seat seat = em.find(Seat.class, seatId);
        if (seat == null) {
            throw new SeatNotFoundException("Seat " + seatId + " does not exist!");
        }
    
        return seat;
    }
}
