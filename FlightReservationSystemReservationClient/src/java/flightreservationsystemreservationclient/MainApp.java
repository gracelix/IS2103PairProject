/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemreservationclient;

import ejb.session.stateful.CustomerFlightReservationSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.Customer;
import entity.Fare;
import entity.FlightSchedule;
import entity.ItineraryItem;
import entity.SeatInventory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private CustomerFlightReservationSessionBeanRemote customerFlightReservationSessionBeanRemote;
    private Customer currentCustomer;

    public MainApp() {
        
    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.currentCustomer = null;
    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, CustomerFlightReservationSessionBeanRemote customerFlightReservationSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.customerFlightReservationSessionBeanRemote = customerFlightReservationSessionBeanRemote;
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
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            System.out.println("*** Flight Reservation System :: Search For Flights ***\n");

            System.out.println("Select the trip type");
            System.out.println("1: Single trip");
            System.out.println("2: Round trip\n");
            System.out.print("> ");
            Integer tripType = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Enter departure airport (eg. SIN)> ");
            String departureAirport = sc.nextLine().trim();
            System.out.print("Enter destination airport (eg. KUL)> ");
            String destinationAirport = sc.nextLine().trim();
            System.out.print("Enter departure date (dd-mm-yyyy)> ");
            Date departureDate = inputDateFormat.parse(sc.nextLine().trim());
            Date returnDate;
            if (tripType == 2) {
                System.out.print("Enter return date (dd-mm-yyyy)> ");
                returnDate = inputDateFormat.parse(sc.nextLine().trim());
            }
            System.out.print("Enter number of passengers> ");
            Integer numberOfPassengers = sc.nextInt();

            System.out.println("Select flight type preference");
            System.out.println("1: Direct flight");
            System.out.println("2: Connecting flight");
            System.out.println("3: No Preference\n");
            System.out.print("> ");
            Integer flightType = sc.nextInt();
            sc.nextLine();
            
            if (flightType == 1 || flightType == 3) {
                //print directs
                List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchSingleFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                Date currentDate;
                Date nextDate;
                
                Date dateWithoutTime = dateFormat.parse(dateFormat.format(departureDate));
                Integer counter = -3;
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateWithoutTime);
                calendar.add(Calendar.DAY_OF_MONTH, -3);
                currentDate = calendar.getTime();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                nextDate = calendar.getTime();
                for (int i = 0; i < 6; i++) {
                    currentDate = nextDate;
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    nextDate = calendar.getTime();
                    System.out.println("Flights for Date: " + currentDate);
                    System.out.printf("%20s%25s%20s%25s%15s%20s\n",
                            "Flight Number", "Departure Time",
                            "Seats Available",
                            "Cabin Classes Available", 
                            "Fare/pax", "Total Fare Amount"); 

                    for (FlightSchedule flightSchedule : flightSchedules) {
                        if (flightSchedule.getDepartureDateTime().after(currentDate) && flightSchedule.getDepartureDateTime().before(nextDate)) {
                            Date dateTime = timeFormat.parse(timeFormat.format(flightSchedule.getDepartureDateTime()));
        //                        Integer seats = 0;
        //                        String cabinClass = "";
                            for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
        //                            seats += seatInventory.getAvailableSeats();
        //                            if (seatInventory.getAvailableSeats() > 0) {
        //                                if (seatInventory.getCabinClass().getCabinClassType().equals(CabinClassType.FIRST_CLASS)) {
        //                                    cabinClass += "F";
        //                                } else if (seatInventory.getCabinClass().getCabinClassType().equals(CabinClassType.BUSINESS_CLASS)) {
        //                                    cabinClass += "J";
        //                                } else if (seatInventory.getCabinClass().getCabinClassType().equals(CabinClassType.PREMIUM_ECONOMY)) {
        //                                    cabinClass += "W";
        //                                } else if (seatInventory.getCabinClass().getCabinClassType().equals(CabinClassType.ECONOMY)) {
        //                                    cabinClass += "Y";
        //                                }
        //                            }
                                if (seatInventory.getAvailableSeats() > 0) {
                                    BigDecimal farePax = BigDecimal.ZERO;
                                    for (Fare fare : flightSchedule.getFlightSchedulePlan().getFares()) {
                                        if (fare.getCabinClassConfiguration().equals(seatInventory.getCabinClass())) {
                                            if (farePax.equals(BigDecimal.ZERO) || farePax.compareTo(fare.getFareAmount()) > 0) {
                                                farePax = fare.getFareAmount();
                                            }
                                        }
                                    }
                                    BigDecimal totalFare = farePax.multiply(new BigDecimal(numberOfPassengers));

                                    System.out.printf("%20s%25s%20s%25s%15s%20s\n",
                                    flightSchedule.getFlightSchedulePlan().getFlight().getFlightNumber(), 
                                    dateTime,
                                    seatInventory.getAvailableSeats(),
                                    seatInventory.getCabinClass().getCabinClassType(), 
                                    farePax, totalFare);                            
                                }
                            }


                        }
                    }
                }
                
            } 
            
            System.out.print("Press any key to continue...> ");
            sc.nextLine();
            
            if (flightType == 2 || flightType == 3) {
                //print connecting
            }
            
            System.out.print("Press any key to continue...> ");
            sc.nextLine();
            
            
//            System.out.println("searching for flight....");
//            System.out.println("flight found!");

            System.out.print("Do you want to make a reservation? (Y: Yes, N: No)> ");
            String responseString = sc.nextLine().trim();

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
