/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Partner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class PartnerSessionBean implements PartnerSessionBeanRemote, PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewPartner(Partner newPartner) {
        em.persist(newPartner);
        em.flush();
        return newPartner.getPartnerId();
    }
    
    @Override
    public Partner retrievePartnerById(Long partnerId) throws PartnerNotFoundException {
        Partner partner = em.find(Partner.class, partnerId);
        if (partner == null) {
            throw new PartnerNotFoundException("Partner " + partnerId + " does not exist!");
        }
        
        return partner;
    }
    
    @Override
    public Partner login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Query query = em.createQuery("SELECT p FROM Partner p WHERE p.userName = :inUserName");
            query.setParameter("inUserName", username);
            Partner partner = (Partner)query.getSingleResult();

            if (partner.getPassword().equals(password)) {
                return partner;
            } else {
                throw new InvalidLoginCredentialException("Entered password is incorrect.");
            }
        } catch (NoResultException ex) {
            throw new InvalidLoginCredentialException("One or more entered credentials are incorrect.");
        }
    }
}
