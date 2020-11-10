/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Local;
import util.exception.AirportNotFoundException;
import util.exception.InvalidIataCodeException;

/**
 *
 * @author GraceLi
 */
@Local
public interface AirportSessionBeanLocal {

    public Long createNewAirport(Airport newAirport);

    public Airport retrieveAirportById(Long airportId) throws AirportNotFoundException;

    public Airport retrieveAirportByIataCode(String iataCode) throws InvalidIataCodeException;
    
}
