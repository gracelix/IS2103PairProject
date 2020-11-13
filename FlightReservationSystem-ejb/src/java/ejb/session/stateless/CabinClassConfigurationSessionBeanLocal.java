/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfiguration;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;

/**
 *
 * @author GraceLi
 */
@Local
public interface CabinClassConfigurationSessionBeanLocal {

    public Long createNewCabinClassConfiguration(CabinClassConfiguration cabinClassConfiguration, Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException;

    public CabinClassConfiguration retrieveCabinClassConfigurationById(Long cabinClassConfigurationId) throws CabinClassConfigurationNotFoundException;

    public List<CabinClassConfiguration> retrieveCabinClassConfigurationsByAircraftConfigurationId(Long aircraftConfigurationId);
    
}
