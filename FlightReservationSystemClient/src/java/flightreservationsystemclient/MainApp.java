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
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRights;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
public class MainApp {
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    private CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;
    private AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    private AirportSessionBeanRemote airportSessionBeanRemote;
    private Employee currentEmployee;
    private Integer response;
    
    private FleetManagerModule fleetManagerModule;
    private RoutePlannerModule routePlannerModule;
    
    public MainApp() {
        
    }

    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote, 
            AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, 
            CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, 
            AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, 
            FlightRouteSessionBeanRemote flightRouteSessionBeanRemote,
            AirportSessionBeanRemote airportSessionBeanRemote) {
        
        this.currentEmployee = null;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.airportSessionBeanRemote = airportSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        response = 0;

        while (true) {
            System.out.println("*** Welcome to Flight Reservation System :: Management ***\n");
            response = 0;

            if(currentEmployee != null) {
                System.out.println("You are login as " + currentEmployee.getName() + ".\n");

            } else {
                System.out.println("1: Login");
                System.out.println("2: Exit\n");

                response = 0;
                while (response < 1 || response > 2) {
                    System.out.print("> ");
                    response = sc.nextInt();

                    if (response == 1) {
                        try {
                            doLogin();
                            System.out.println("You have login as " + currentEmployee.getName() + ".\n");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } else if (response == 2) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
            }

            if (currentEmployee != null && currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.FLEET_MANAGER)) {
                fleetManagerModule = new FleetManagerModule(aircraftTypeSessionBeanRemote, aircraftConfigurationSessionBeanRemote, cabinClassConfigurationSessionBeanRemote, currentEmployee);
                fleetManagerModule.doFleetManagerMenu();
                currentEmployee = null;
                
            } else if (currentEmployee != null && currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.ROUTE_PLANNER)) {
                routePlannerModule = new RoutePlannerModule(flightRouteSessionBeanRemote, airportSessionBeanRemote, currentEmployee);
                routePlannerModule.doRoutePlannerMenu();
                
            } else if (currentEmployee != null && currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.SCHEDULE_MANAGER)) {
                
            } else if (currentEmployee != null && currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.SALES_MANAGER)) {
                
            }
            
            if (response == 2) {
                break;
            }
        }
    }

    public void doLogin() throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);

        System.out.println("*** Flight Reservation System Management :: Login ***\n");
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentEmployee = employeeSessionBeanRemote.login(username, password);
        } else {
            throw new InvalidLoginCredentialException("One or more login credentials are missing.");
        }
    }
}
