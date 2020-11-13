/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AirportSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import entity.Employee;
import entity.FlightRoute;
import java.util.List;
import java.util.Scanner;
import util.exception.DeleteFlightRouteException;
import util.exception.FlightRouteNotFoundException;
import util.exception.InvalidIataCodeException;

/**
 *
 * @author GraceLi
 */
public class RoutePlannerModule {
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    private AirportSessionBeanRemote airportSessionBeanRemote;
    private Employee employee;

    public RoutePlannerModule() {
    }

    public RoutePlannerModule(FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AirportSessionBeanRemote airportSessionBeanRemote, Employee employee) {
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.airportSessionBeanRemote = airportSessionBeanRemote;
        this.employee = employee;
    }
    
    public void doRoutePlannerMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
        
            System.out.println("\n*** Flight Reservation System Management :: Route Planner ***\n");
            System.out.println("1: Create Flight Route");
            System.out.println("2: View All Flight Routes");
            System.out.println("3: Delete Flight Route");
            System.out.println("4: Logout\n");

            response = 0;
            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = sc.nextInt();

                if (response == 1) {
                    try {
                        doCreateFlightRoute();
                    } catch (InvalidIataCodeException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    try {
                        doViewAllFlightRoutes();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    try {
                        try {
                            doDeleteFlightRoute();
                        } catch (DeleteFlightRouteException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } catch (FlightRouteNotFoundException ex) {
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
    
    public void doCreateFlightRoute() throws InvalidIataCodeException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Route ***\n");
        
        System.out.print("Enter origin airport IATA code> ");
        String originAirportIata = sc.nextLine().trim();
        System.out.print("Enter destination airport IATA code> ");
        String destinationAirportIata = sc.nextLine().trim();
        
        FlightRoute flightRoute = new FlightRoute();
        Long flightRouteId = flightRouteSessionBeanRemote.createNewFlightRoute(flightRoute, originAirportIata, destinationAirportIata);
        
        System.out.println("Flight route " + flightRouteId + " from " + originAirportIata + " to " + destinationAirportIata + " created successfully!\n");
        
        System.out.print("Create complementary flight route? (Press Y to create, N otherwise)> ");
        String responseString = sc.nextLine().trim();
        if (responseString.equals("Y")) {
            FlightRoute complementaryFlightRoute = new FlightRoute();
            // reverse origin and destination airports
            try {
                Long complementaryFlightRouteId = flightRouteSessionBeanRemote.createNewComplementaryFlightRoute(complementaryFlightRoute, flightRouteId, destinationAirportIata, originAirportIata);
                System.out.println("Flight route " + complementaryFlightRouteId + " from " + destinationAirportIata + " to " + originAirportIata + " created successfully!\n");
            } catch (FlightRouteNotFoundException | InvalidIataCodeException ex) {
                System.out.println("Complementary flight route cannot be created: " + ex.getMessage() + "\n");
            }
        }
    }
    
    public void doViewAllFlightRoutes() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View All Flight Routes ***\n");
        
        List<FlightRoute> flightRoutes = flightRouteSessionBeanRemote.retrieveAllFlightRoutes();
        
        System.out.printf("%20s%20s%20s\n", "Flight Route ID", "Origin Airport", "Destination Airport");
        for (FlightRoute flightRoute : flightRoutes) {
            System.out.printf("%20s%20s%20s\n", flightRoute.getFlightRouteId(), flightRoute.getOriginAirport().getIataCode(), flightRoute.getDestinationAirport().getIataCode());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doDeleteFlightRoute() throws FlightRouteNotFoundException, DeleteFlightRouteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: Delete Flight Route ***\n");
        
        // display list of flight routes
        List<FlightRoute> flightRoutes = flightRouteSessionBeanRemote.retrieveAllFlightRoutes();
        System.out.printf("%20s%20s%20s\n", "Flight Route ID", "Origin Airport", "Destination Airport");
        for (FlightRoute flightRoute : flightRoutes) {
            System.out.printf("%20s%20s%20s\n", flightRoute.getFlightRouteId(), flightRoute.getOriginAirport().getIataCode(), flightRoute.getDestinationAirport().getIataCode());
        }
        
        System.out.print("Enter Flight Route ID to be deleted> ");
        Long flightRouteId = sc.nextLong();
        
        try {
            flightRouteSessionBeanRemote.disableFlightRoute(flightRouteId);
            System.out.println("Flight Route " + flightRouteId + " successfully deleted.\n");
    
        } catch (FlightRouteNotFoundException | DeleteFlightRouteException ex) {
            flightRouteSessionBeanRemote.disableFlightRoute(flightRouteId);
            System.out.println("Error encountered: " + ex.getMessage() + "\n");
        }
    }
}
