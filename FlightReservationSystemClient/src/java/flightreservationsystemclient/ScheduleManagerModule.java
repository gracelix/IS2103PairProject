/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClassConfiguration;
import entity.Employee;
import entity.Flight;
import entity.FlightRoute;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author Ziyue
 */
public class ScheduleManagerModule {
    private FlightSessionBeanRemote flightSessionBeanRemote;
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    private CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;
    private Employee employee;

    public ScheduleManagerModule() {
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.employee = employee;
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.employee = employee;
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.employee = employee;
    }
    
    
    
    public void doScheduleManagerMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
        
            System.out.println("*** Flight Reservation System Management :: Schedule Manager ***\n");
            System.out.println("1: Create flight");
            System.out.println("2: View all flights");
            System.out.println("3: View flight details");
            System.out.println("4: Create flight schedule plan");
            System.out.println("5: View all flight schedule plans");
            System.out.println("6: View flight schedule plan details");
            System.out.println("7: Logout\n");

            response = 0;
            while (response < 1 || response > 7) {
                System.out.print("> ");
                response = sc.nextInt();

                if (response == 1) {
                    try {
                        doCreateFlight();
                    } catch (FlightNotFoundException | AircraftConfigurationNotFoundException | FlightRouteNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    doViewAllFlights();
                } else if (response == 3) {
                    try {
                        doViewFlightDetails();
                    } catch (FlightNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else if (response == 4) {
                    doCreateFlightSchedulePlan();                
                } else if (response == 5) {
                    doViewAllFlightSchedulePlans();
                } else if (response == 6) {
                    doViewFlightSchedulePlanDetails();
                } else if (response == 7) {
                    break;
                }
            }
            if (response == 7) {
                break;
            }
        }
    }
    
    public void doCreateFlight() throws FlightNotFoundException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Flight Reservation System Management :: Create Flight ***\n");
        
        System.out.print("Enter flight number> ");
        Integer flightInt = sc.nextInt();
        System.out.print("Enter flight route ID> ");
        //perhaps a retrieve all flight route here?
        Long flightRouteId = sc.nextLong();
        System.out.print("Enter aircraft configuration ID> ");
        Long aircraftConfigurationID = sc.nextLong();
        
        String flightNumber = "ML" + flightInt;
        
        Flight flight = new Flight(flightNumber);
        
        Long flightId = flightSessionBeanRemote.createNewFlight(flight, flightRouteId, aircraftConfigurationID);
        
        System.out.println("Flight " + flightId + " of flight number " + flightNumber + " created successfully!\n");
        
        if (flightRouteSessionBeanRemote.retrieveFlightRouteById(flightRouteId).getComplementaryFlightRoute() != null) {
            sc.nextLine();
            System.out.print("Create complementary flight? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                System.out.print("Enter return flight number> ");
                Integer returnFlightInt = sc.nextInt();
                String returnFlightNumber = "ML" + returnFlightInt;
                Flight complementaryFlight = new Flight(returnFlightNumber);
                Long returnFlightRouteId = flightRouteSessionBeanRemote.retrieveFlightRouteById(flightRouteId).getComplementaryFlightRoute().getFlightRouteId();
                Long returnFlightId = flightSessionBeanRemote.createNewComplementaryReturnFlight(complementaryFlight, flightId, returnFlightRouteId, aircraftConfigurationID);
                System.out.println("Return Flight " + returnFlightId + " of flight number " + returnFlightNumber + " created successfully!\n");
            }
        }
    }
    
//    public void doViewAllFlights() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("*** Flight Reservation System Management :: View All Flight ***\n");
//        
//        List<Flight> flights = flightSessionBeanRemote.retrieveAllFlights();
//        
//        Flight complementaryFlight = null;
//        
//        System.out.printf("%20s%20s%20s%20s\n", "Flight ID", "Flight Number", "Flight Route ID", "Aircraft Configuration ID");
//        for (Flight flight : flights) {
//            System.out.printf("%20s%20s%20s%20\n", flight.getFlightId(), flight.getFlightNumber(), flight.getFlightRoute().getFlightRouteId(), flight.getAircraftConfiguration().getAircraftConfigurationId());
//            if (flight.getComplementaryReturnFlight() != null) {
//                complementaryFlight = flight.getComplementaryReturnFlight();
//                System.out.printf("%20s%20s%20s%20\n", complementaryFlight.getFlightId(), complementaryFlight.getFlightNumber(), complementaryFlight.getFlightRoute().getFlightRouteId(), complementaryFlight.getAircraftConfiguration().getAircraftConfigurationId());
//            }
//        }
//        System.out.print("Press any key to continue...> ");
//        sc.nextLine();
//    }
    
    public void doViewAllFlights() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View All Flight ***\n");
        
        List<Flight> flights = flightSessionBeanRemote.retrieveAllFlights();
        
        System.out.printf("%20s%20s%20s%28s\n", "Flight Number", "Flight ID", "Flight Route ID", "Aircraft Configuration ID");
        for (Flight flight : flights) {
            System.out.printf("%20s%20s%20s%28s\n", flight.getFlightNumber(), flight.getFlightId(), flight.getFlightRoute().getFlightRouteId(), flight.getAircraftConfiguration().getAircraftConfigurationId());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doViewFlightDetails() throws FlightNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View Flight Details ***\n");
        
        System.out.print("Enter Flight ID> ");
        Long flightId = sc.nextLong();
        
        Flight flight = flightSessionBeanRemote.retrieveFlightById(flightId);
        
        System.out.printf("%15s%18s%25s%15s%15s\n", "Flight Number", "Flight Route ID", "Origin-Destination", "Cabin Classes", "Total Seats");
        String odPair = flight.getFlightRoute().getOriginAirport().getIataCode() + "-" + flight.getFlightRoute().getDestinationAirport().getIataCode();
        //List<CabinClassConfiguration> cabinClassConfigurations = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        String cabinClasses = "";
        for (CabinClassConfiguration cabinClassConfiguration : cabinClassConfigurations) {
            if (cabinClassConfiguration.getCabinClassType() == CabinClassType.FIRST_CLASS) {
                cabinClasses += "F";
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.BUSINESS_CLASS) {
                cabinClasses += "J";
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.PREMIUM_ECONOMY) {
                cabinClasses += "W";
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.ECONOMY) {
                cabinClasses += "Y";
            }
        }
        System.out.printf("%15s%18s%25s%15s%15s\n", flight.getFlightNumber(), flight.getFlightRoute().getFlightRouteId(), odPair, cabinClasses, flight.getAircraftConfiguration().getTotalMaximumSeatCapacity());
    }
    public void doCreateFlightSchedulePlan() {
        System.out.println("hehe");
    }
    public void doViewAllFlightSchedulePlans() {
        System.out.println("hehe");
    }
    public void doViewFlightSchedulePlanDetails() {
        System.out.println("hehe");
    }
}
