/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Remote;
import util.exception.AirportNotFoundException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface AirportSessionBeanRemote {
    public Long createNewAirport(Airport newAirport);
    
    public Airport retrieveAirportById(Long airportId) throws AirportNotFoundException;
}
