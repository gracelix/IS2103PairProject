/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Seat;
import javax.ejb.Remote;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface SeatSessionBeanRemote {
    public Long createNewSeat(Seat newSeat, Long seatInventoryId) throws SeatInventoryNotFoundException;
    public Seat retrieveSeatByRowCol(Long seatInventoryId, Integer seatRow, String seatColumn) throws SeatInventoryNotFoundException, SeatNotFoundException;
    public Seat retrieveSeatById(Long seatId) throws SeatNotFoundException;
}
