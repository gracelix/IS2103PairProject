/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SeatInventory;
import javax.ejb.Local;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Local
public interface SeatInventorySessionBeanLocal {

    public Long createNewSeatInventory(SeatInventory newSeatInventory, Long cabinClassConfigurationId, Long flightScheduleId) throws CabinClassConfigurationNotFoundException, FlightSchedulePlanNotFoundException;
    public SeatInventory retrieveSeatInventoryById(Long seatInventoryId) throws SeatInventoryNotFoundException;    
}
