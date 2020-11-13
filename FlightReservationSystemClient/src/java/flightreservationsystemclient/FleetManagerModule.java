/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import entity.AircraftConfiguration;
import entity.CabinClassConfiguration;
import entity.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author GraceLi
 */
public class FleetManagerModule {
    private AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    private CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;
    private Employee currentEmployee;

    public FleetManagerModule() {
    }

    public FleetManagerModule(AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, Employee currentEmployee) {
        this.aircraftTypeSessionBeanRemote = aircraftTypeSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }
    
    public void doFleetManagerMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
        
            System.out.println("\n*** Flight Reservation System Management :: Fleet Manager ***\n");
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
                    doViewAllAircraftConfigurations();
                } else if (response == 3) {
                    try {
                    doViewAircraftConfigurationDetails();
                    } catch (AircraftConfigurationNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else if (response == 4) {
                    break;
                }
            }
            if (response == 4) {
                break;
            }
        }
    }
    
    public void doCreateAircraftConfiguration() throws AircraftConfigurationNotFoundException, AircraftTypeNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer classTypeInt;
        CabinClassType cabinClassType = null;
        Integer numberOfAisles;
        Integer numberOfRows;
        Integer numberOfSeatsAbreast;
        String seatingConfiguration;
        Integer cabinMaximumSeatCapacity;
        Integer totalMaximumSeatCapacity = 0;
        
        System.out.println("*** Flight Reservation System Management :: Create Aircraft Configuration ***\n");
        
        System.out.print("Enter aircraft type ID> ");
        Long aircraftTypeId = sc.nextLong();
        sc.nextLine();
        System.out.print("Enter aircraft configuration name> ");
        String aircraftConfigurationName = sc.nextLine().trim();
        
        System.out.print("Enter number of cabin classes> ");
        Integer numberOfCabinClasses = sc.nextInt();
        
        AircraftConfiguration aircraftConfiguration = new AircraftConfiguration(aircraftConfigurationName, numberOfCabinClasses);
        List<CabinClassConfiguration> cabinClassConfigurations = new ArrayList<>();
        
        for (int i = 1; i <= numberOfCabinClasses; i++) {
            System.out.println("Details of cabin classes: Class " + i + "\n");
            System.out.println("Choose cabin class type");
            System.out.println("1: First class");
            System.out.println("2: Business class");
            System.out.println("3: Premium Economy class");
            System.out.println("4: Economy class\n");
            System.out.print("> ");
            classTypeInt = sc.nextInt();
            
            if (classTypeInt == 1) {
                cabinClassType = CabinClassType.FIRST_CLASS;
            } else if (classTypeInt == 2) {
                cabinClassType = CabinClassType.BUSINESS_CLASS;
            } else if (classTypeInt == 3) {
                cabinClassType = CabinClassType.PREMIUM_ECONOMY;
            } else if (classTypeInt == 4) {
                cabinClassType = CabinClassType.ECONOMY;
            
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
    
    public void doViewAllAircraftConfigurations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View All Aircraft Configurations ***\n");
        
        List<AircraftConfiguration> aircraftConfigurations = aircraftConfigurationSessionBeanRemote.retrieveAllAircraftConfigurations();
        
        System.out.printf("%20s%20s%30s%20s\n", "AircraftConfig ID", "Aircraft Type", "Name", "Max Capacity");
        
        for (AircraftConfiguration aircraftConfiguration : aircraftConfigurations) {
            System.out.printf("%20s%20s%30s%20s\n", aircraftConfiguration.getAircraftConfigurationId(),aircraftConfiguration.getAircraftType().getAircraftTypeName(), aircraftConfiguration.getAircraftConfigurationName(), aircraftConfiguration.getTotalMaximumSeatCapacity());
        }
        
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doViewAircraftConfigurationDetails() throws AircraftConfigurationNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View Aircraft Configuration Details ***\n");
        
        // display list of aircraft configurations 
        List<AircraftConfiguration> aircraftConfigurations = aircraftConfigurationSessionBeanRemote.retrieveAllAircraftConfigurations();
        System.out.printf("%20s%20s%30s%20s\n", "AircraftConfig ID", "Aircraft Type", "Name", "Max Capacity");
        for (AircraftConfiguration aircraftConfiguration : aircraftConfigurations) {
            System.out.printf("%20s%20s%30s%20s\n", aircraftConfiguration.getAircraftConfigurationId(),aircraftConfiguration.getAircraftType().getAircraftTypeName(), aircraftConfiguration.getAircraftConfigurationName(), aircraftConfiguration.getTotalMaximumSeatCapacity());
        }
        
        System.out.print("Enter Aircraft Configuration ID> ");
        Long aircraftConfigurationId = sc.nextLong();
        
        AircraftConfiguration aircraftConfiguration = aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationById(aircraftConfigurationId);
        
        System.out.printf("%10s%20s%20s%20s\n", "Aircraft Type", "Name", "No. of Cabin Class", "Max Capacity");
        System.out.printf("%10s%20s%20s%20s\n", aircraftConfiguration.getAircraftType().getAircraftTypeName(), aircraftConfiguration.getAircraftConfigurationName(), aircraftConfiguration.getNumberOfCabinClasses(), aircraftConfiguration.getTotalMaximumSeatCapacity());
    }
    
}
