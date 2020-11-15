/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewCustomer(Customer newCustomer) {
        
        newCustomer.getCreditCards().size();
        
        em.persist(newCustomer);
        em.flush();
        return newCustomer.getCustomerId();
    }
    
    @Override
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Employee " + customerId + " does not exist!");
        }
        
        return customer;
    }
    
    @Override
    public Customer login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.userName = :inUserName");
            query.setParameter("inUserName", username);
            Customer customer = (Customer)query.getSingleResult();

            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Entered password is incorrect.");
            }
        } catch (NoResultException ex) {
            throw new InvalidLoginCredentialException("One or more entered credentials are incorrect.");
        }
    }
}
