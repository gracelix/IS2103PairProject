/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.AirportSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.FareSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.SeatInventorySessionBeanRemote;
import ejb.session.stateless.SeatSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author GraceLi
 */
public class Main {

    @EJB
    private static FareSessionBeanRemote fareSessionBeanRemote;

    @EJB
    private static SeatSessionBeanRemote seatSessionBeanRemote;

    @EJB
    private static SeatInventorySessionBeanRemote seatInventorySessionBeanRemote;

    @EJB
    private static FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;

    @EJB
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;

    @EJB
    private static FlightSessionBeanRemote flightSessionBean;

    @EJB
    private static AirportSessionBeanRemote airportSessionBean;

    @EJB
    private static FlightRouteSessionBeanRemote flightRouteSessionBean;

    @EJB
    private static AircraftTypeSessionBeanRemote aircraftTypeSessionBean;

    @EJB
    private static CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBean;

    @EJB
    private static AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBean;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBean;
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeSessionBean, aircraftConfigurationSessionBean, 
                cabinClassConfigurationSessionBean, aircraftTypeSessionBean, flightRouteSessionBean, 
                airportSessionBean, flightSessionBean, flightSchedulePlanSessionBeanRemote, flightScheduleSessionBeanRemote,
                seatInventorySessionBeanRemote, seatSessionBeanRemote, fareSessionBeanRemote);
        mainApp.runApp();
    }
    
}
