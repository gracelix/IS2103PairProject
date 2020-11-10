/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftType;
import javax.ejb.Local;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author GraceLi
 */
@Local
public interface AircraftTypeSessionBeanLocal {

    public Long createNewAircraftType(AircraftType newAircraftType);

    public AircraftType retrieveAircraftTypeById(Long aircraftTypeId) throws AircraftTypeNotFoundException;
    
}
