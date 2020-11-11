/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.Employee;
import entity.Flight;
import entity.FlightRoute;
import java.util.Scanner;
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
    private Employee employee;

    public ScheduleManagerModule() {
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
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
                    doViewFlightDetails();
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
    
    public void doViewAllFlights() {
        System.out.println("hehe");
    }
    public void doViewFlightDetails() {
        System.out.println("hehe");
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
