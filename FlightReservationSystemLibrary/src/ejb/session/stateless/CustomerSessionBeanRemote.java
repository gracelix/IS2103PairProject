/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface CustomerSessionBeanRemote {
    
    public Long createNewCustomer(Customer newCustomer);

    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;
    
    public Customer login(String email, String password) throws InvalidLoginCredentialException;
}
