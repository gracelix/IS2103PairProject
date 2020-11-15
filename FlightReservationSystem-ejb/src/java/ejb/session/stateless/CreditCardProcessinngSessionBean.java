/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import javax.ejb.Stateless;

/**
 *
 * @author Ziyue
 */
@Stateless
public class CreditCardProcessinngSessionBean implements CreditCardProcessinngSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public String chargeCreditCard(String creditCardNumber, BigDecimal amount) {
        Random random = new Random((new Date()).getTime());        
        String paymentReferenceNumber = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        return paymentReferenceNumber;
    }
}
