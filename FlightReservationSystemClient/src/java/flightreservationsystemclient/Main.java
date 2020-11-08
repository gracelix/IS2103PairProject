/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author GraceLi
 */
public class Main {

    @EJB
    private static AircraftTypeSessionBeanRemote aircraftTypeSessionBean;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(aircraftTypeSessionBean, employeeSessionBean);
        mainApp.runApp();
    }
    
}
