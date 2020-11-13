/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Partner;
import javax.ejb.Remote;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface PartnerSessionBeanRemote {
    public Long createNewPartner(Partner newPartner);
    
    public Partner retrievePartnerById(Long partnerId) throws PartnerNotFoundException;
    
    public Partner login(String username, String password) throws InvalidLoginCredentialException;
}
