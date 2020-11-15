/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author Ziyue
 */
@Local
public interface CreditCardProcessinngSessionBeanLocal {

    public String chargeCreditCard(String creditCardNumber, BigDecimal amount);
    
}
