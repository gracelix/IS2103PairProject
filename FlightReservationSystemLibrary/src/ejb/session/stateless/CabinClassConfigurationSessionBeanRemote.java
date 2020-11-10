/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import javax.ejb.Remote;
import util.exception.AircraftConfigurationNotFoundException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface CabinClassConfigurationSessionBeanRemote {
    
    public Long createNewCabinClassConfiguration(CabinClassConfiguration cabinClassConfiguration, Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException;
    
}
