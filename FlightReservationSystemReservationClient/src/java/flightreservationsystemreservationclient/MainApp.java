/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.Customer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private Customer currentCustomer;

    public MainApp() {
        
    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.currentCustomer = null;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Merlion Airlines! ***\n");
            response = 0;
            
            if (currentCustomer != null) {
                System.out.println("You are login as " + currentCustomer.getFirstName());
                System.out.println("1: Search for flights");
                System.out.println("2: View my flight reservations");
                System.out.println("3: Logout\n");
                
                response = 0;
                while (response < 1 || response > 3) {
                    System.out.print("> ");
                    response = sc.nextInt();
                    
                    if (response == 1) {
                        doSearchFlight();
                    } else if (response == 2) {
                        doViewMyFlightReservations();
                    } else if (response == 3) {
                        currentCustomer = null;
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
            } else {
                System.out.println("1: Register");
                System.out.println("2: Login");
                System.out.println("3: Search for flights");
                System.out.println("4: Exit\n");
                
                response = 0;
                while (response < 1 || response > 4) {
                    System.out.print("> ");
                    response = sc.nextInt();
                    
                    if (response == 1) {
                        doRegisterNewCustomer();
                    } else if (response == 2) {
                        try {
                            doLogin();
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } else if (response == 3) {
                        doSearchFlight();
                    } else if (response == 4) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
            }
            if (response == 4) {
                break;
            }
        }
    }
    
    public void doRegisterNewCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: Register New Account ***\n");
        
        System.out.print("Enter first name> ");
        String firstName = sc.nextLine().trim();
        System.out.print("Enter last name> ");
        String lastName = sc.nextLine().trim();
        System.out.print("Enter email> ");
        String email = sc.nextLine().trim();
        System.out.print("Enter phone number> ");
        String phoneNumber = sc.nextLine().trim();
        System.out.print("Enter address> ");
        String address = sc.nextLine().trim();
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();
        
        Customer customer = new Customer(firstName, lastName, email, phoneNumber, address, username, password);
        customerSessionBeanRemote.createNewCustomer(customer);
        System.out.println("New account created with username " + customer.getUserName());
        currentCustomer = customer;
    }
    
    public void doLogin() throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: Login ***\n");
        
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();
        
        if (username.length() > 0 && password.length() > 0) {
            currentCustomer = customerSessionBeanRemote.login(username, password);
            
        } else {
            throw new InvalidLoginCredentialException("One or more login credentials are missing.");
        }
    }
    
    public void doSearchFlight() {
        try {
            Scanner sc = new Scanner(System.in);
            Integer response = 0;
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("d/M/y");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

            System.out.println("*** Flight Reservation System :: Search For Flights ***\n");

            System.out.print("Enter trip type> ");
            String tripType = sc.nextLine().trim();
            System.out.print("Enter departure airport> ");
            String departureAirport = sc.nextLine().trim();
            System.out.print("Enter destination airport> ");
            String destinationAirport = sc.nextLine().trim();
            System.out.print("Enter departure date (dd/mm/yyyy)> ");
            Date departureDate = inputDateFormat.parse(sc.nextLine().trim());
            System.out.print("Enter return date (dd/mm/yyyy)> ");
            Date returnDate = inputDateFormat.parse(sc.nextLine().trim());
            System.out.print("Enter number of passengers> ");
            Integer numberOfPassengers = sc.nextInt();

            System.out.println("searching for flight....");
            System.out.println("flight found!");

            System.out.print("Do you want to make a reservation? (Y: Yes, N: No)> ");
            String responseString = sc.nextLine();

            if (responseString.equals("Y")) {

                if (currentCustomer == null) {

                    System.out.println("1: Register");
                    System.out.println("2: Login");

                    while (response < 1 || response > 2) {
                        System.out.print("> ");
                        response = sc.nextInt();

                        if (response == 1) {
                            doRegisterNewCustomer();
                            break;
                        } else if (response == 2) {
                            try {
                                doLogin();
                            } catch (InvalidLoginCredentialException ex) {
                                System.out.println(ex.getMessage() + "\n");
                            }
                            break;
                        } else {
                            System.out.println("You must login before reserving a flight!\n");
                        }
                    }

                    doReserveFlight();

                } else {
                    doReserveFlight();
                }
            } else {
                System.out.println("Returning to main menu...\n");
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date input!\n");
        }
    }
    
    public void doReserveFlight() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: Reserve Flight ***\n");
        System.out.println("reserving flight");
    }
    
    public void doViewMyFlightReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservations ***\n");
        System.out.println("viewing flight reservations");
    }
    
    public void doViewMyFlightReservationDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservation Details ***\n");
        System.out.println("viewing flight reservation details");
    }
}
