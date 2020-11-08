/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.CabinClassConfiguration;
import entity.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRights;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
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
    private Employee currentEmployee;
    private Integer response;
    
    public MainApp() {
        
    }

    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote, 
            AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, 
            CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, 
            AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote) {
        
        this.currentEmployee = null;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        response = 0;

        while (true) {
            System.out.println("*** Welcome to Flight Reservation System :: Management ***\n");

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
                    }
                }
            }

            if (currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.FLEET_MANAGER)) {
                doFleetManagerMenu();
                if (response == 4) {
                    break;
                }
            } else if (currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.ROUTE_PLANNER)) {
                
            } else if (currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.SCHEDULE_MANAGER)) {
                
            } else if (currentEmployee.getEmployeeAccessRights().equals(EmployeeAccessRights.SALES_MANAGER)) {
                
            }
        }
    }

    public void doFleetManagerMenu() {
        Scanner sc = new Scanner(System.in);
        response = 0;
        
        System.out.println("*** Flight Reservation System Management :: Fleet Manager ***\n");
        System.out.println("1: Create aircraft configuration");
        System.out.println("2: View all aircraft configuration");
        System.out.println("3: View aircraft configuration details");
        System.out.println("4: Logout\n");

        response = 0;
        while (response < 1 || response > 4) {
            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                try {
                    doCreateAircraftConfiguration();
                } catch (AircraftConfigurationNotFoundException | AircraftTypeNotFoundException ex) {
                    System.out.println(ex.getMessage() + "\n");
                }
            } else if (response == 2) {
                System.out.println("viewing all aircraft config");
            } else if (response == 3) {
                System.out.println("viewing aircraft config details");
            } else if (response == 4) {
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
    
    public void doCreateAircraftConfiguration() throws AircraftConfigurationNotFoundException, AircraftTypeNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer classTypeInt;
        CabinClassType cabinClassType;
        Integer numberOfAisles;
        Integer numberOfRows;
        Integer numberOfSeatsAbreast;
        String seatingConfiguration;
        Integer cabinMaximumSeatCapacity;
        Integer totalMaximumSeatCapacity = 0;
        
        System.out.println("*** Flight Reservation System Management :: Create Aircraft Configuration ***\n");
        
        System.out.println("Choose aircraft type");
        System.out.println("1: Boeing 737-800");
        System.out.println("2: Boeing 747-8\n");
        
        System.out.print("> ");
        Integer aircraftTypeInt = sc.nextInt();
        
        String aircraftConfigurationName = "";
        Long aircraftTypeId = 0l;
        if (aircraftTypeInt == 1) {
            aircraftTypeId = 1l;
        } else if (aircraftTypeInt == 2) {
            aircraftTypeId = 2l;
        }
        aircraftConfigurationName = aircraftTypeSessionBeanRemote.retrieveAircraftTypeById(aircraftTypeId).getAircraftTypeName();
        
        System.out.print("Enter number of cabin classes> ");
        Integer numberOfCabinClasses = sc.nextInt();
        
        AircraftConfiguration aircraftConfiguration = new AircraftConfiguration(aircraftConfigurationName, numberOfCabinClasses);
        List<CabinClassConfiguration> cabinClassConfigurations = new ArrayList<>();
        
        OUTER:
        for (int i = 1; i <= numberOfCabinClasses; i++) {
            System.out.println("Details of cabin classes: Class " + i + "\n");
            System.out.println("Choose cabin class type");
            System.out.println("1: First class");
            System.out.println("2: Business class");
            System.out.println("3: Premium Economy class");
            System.out.println("4: Economy class\n");
            System.out.print("> ");
            classTypeInt = sc.nextInt();
            
            switch (classTypeInt) {
                case 1:
                    cabinClassType = CabinClassType.FIRST_CLASS;
                    break;
                case 2:
                    cabinClassType = CabinClassType.BUSINESS_CLASS;
                    break;
                case 3:
                    cabinClassType = CabinClassType.PREMIUM_ECONOMY;
                    break;
                case 4:
                    cabinClassType = CabinClassType.ECONOMY;
                    break;
                default:
                    System.out.println("Cabin class does not exist!\n");
                    break OUTER;
            }
            sc.nextLine();
            System.out.print("Enter number of aisles> ");
            numberOfAisles = sc.nextInt();
            System.out.print("Enter number of rows> ");
            numberOfRows = sc.nextInt();
            System.out.print("Enter number of seats abreast> ");
            numberOfSeatsAbreast = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter seating configuration > ");
            seatingConfiguration = sc.nextLine().trim();
            
            cabinMaximumSeatCapacity = numberOfRows * numberOfSeatsAbreast;
            totalMaximumSeatCapacity += cabinMaximumSeatCapacity;
            
            CabinClassConfiguration cabinClassConfiguration = new CabinClassConfiguration(cabinClassType, numberOfAisles, numberOfRows, numberOfSeatsAbreast, seatingConfiguration, cabinMaximumSeatCapacity);
            cabinClassConfigurations.add(cabinClassConfiguration);
        }
        
        aircraftConfiguration.setTotalMaximumSeatCapacity(totalMaximumSeatCapacity);
        Long aircraftConfigurationId = aircraftConfigurationSessionBeanRemote.createNewAircraftConfiguration(aircraftConfiguration, aircraftTypeId);
        
        for (CabinClassConfiguration cabinClass : cabinClassConfigurations) {
            cabinClassConfigurationSessionBeanRemote.createNewCabinClassConfiguration(cabinClass, aircraftConfigurationId);
        }
        
        System.out.println("Aircraft Configuration " + aircraftConfigurationId + " created successfully!\n");
         
    }
}
