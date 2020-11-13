/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SeatInventory;
import javax.ejb.Remote;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author Ziyue
 */
@Remote
public interface SeatInventorySessionBeanRemote {
    
    public Long createNewSeatInventory(SeatInventory newSeatInventory, Long cabinClassConfigurationId, Long flightScheduleId) throws CabinClassConfigurationNotFoundException, FlightSchedulePlanNotFoundException;
    public SeatInventory retrieveSeatInventoryById(Long seatInventoryId) throws SeatInventoryNotFoundException;
}
