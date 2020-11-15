/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemreservationclient;

import ejb.session.stateful.CustomerFlightReservationSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.Airport;
import entity.CreditCard;
import entity.Customer;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.ItineraryItem;
import entity.Seat;
import entity.SeatInventory;
import entity.Transaction;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.enumeration.SeatStatus;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.NoFlightsAvailableException;
import util.exception.SeatInventoryNotFoundException;
import util.exception.SeatNotFoundException;
import util.exception.SeatReservedException;
import util.exception.TransactionException;
import util.exception.TransactionNotFoundException;

/**
 *
 * @author GraceLi
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private CustomerFlightReservationSessionBeanRemote customerFlightReservationSessionBeanRemote;
    private Customer currentCustomer;
    private Customer placeHolderCustomer;
    
    public MainApp() {
        
    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, CustomerFlightReservationSessionBeanRemote customerFlightReservationSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.customerFlightReservationSessionBeanRemote = customerFlightReservationSessionBeanRemote;
        this.currentCustomer = null;
        this.placeHolderCustomer = new Customer("firstName", "lastName", "email", "mobilePhoneNumber", "address", "userName", "password");
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
                        try {
                        doSearchFlight();
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                        
                    } else if (response == 2) {
                        doViewMyFlightReservations();
                        System.out.println("Do you want to view flight reservation details for a reservation? (Enter Y if yes, N for no)");
                        String responseString = sc.nextLine().trim();
                        if (responseString.equals("Y")) {
                            doViewMyFlightReservationDetails();
                        }
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
                        try {
                            doRegisterNewCustomer();
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } else if (response == 2) {
                        try {
                            doLogin();
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    } else if (response == 3) {
                        try {
                        doSearchFlight();
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                        
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
    
    public void doRegisterNewCustomer() throws InvalidLoginCredentialException {
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
        
        if (lastName.length() > 0 && email.length() > 0 && username.length() > 0 && password.length() > 0) {
            Customer customer = new Customer(firstName, lastName, email, phoneNumber, address, username, password);
            customerSessionBeanRemote.createNewCustomer(customer);
            System.out.println("New account created with username " + customer.getUserName());
            currentCustomer = customer;
        } else {
            throw new InvalidLoginCredentialException("One or more credentials are missing!");
        }
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
    
    public void doSearchFlight() throws InvalidLoginCredentialException {
        try {
            Scanner sc = new Scanner(System.in);
            Integer response = 0;
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

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
            Date departureDate = dateFormat.parse(sc.nextLine().trim());
            
            Date returnDate = null;
            if (tripType == 2) {
                System.out.print("Enter return date (dd-mm-yyyy)> ");
                returnDate = dateFormat.parse(sc.nextLine().trim());
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
            
            String responseString = "";

            if (tripType == 1) {
                if (flightType == 1) {
                    try {
                        System.out.println("** Available Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                        doGetFlightScheduleAvailability(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        System.out.println("-----------------------------------");
                    } catch (NoFlightsAvailableException ex) {
                        System.out.println(ex.getMessage() + "\n");
                        System.out.println("Do you want to look for connecting flights instead?");
                    }
                    
                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                } else if (flightType == 2) {
                    System.out.println("** Available Connecting Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                    try {
                        List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        doGetSingleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                    } catch (NoFlightsAvailableException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                    
                    System.out.print("Do you still want to view more connecting(2-connections) flights? (Enter Y to show connecting flights)> ");
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }                        
                    }
                                        
                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                    
                } else if (flightType == 3) {
                    try {
                        System.out.println("** Available Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                        doGetFlightScheduleAvailability(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        System.out.println("-----------------------------------");
                    } catch (NoFlightsAvailableException ex) {
                        System.out.println(ex.getMessage() + "\n");
                        
                        System.out.println("No Single flights available");
                    }
                    
                    System.out.print("Do you still want to view more connecting flights? (Enter Y to show connecting flights)> ");
                    
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        System.out.println("** Available Connecting Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetSingleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    }
                    
                    System.out.print("Do you still want to view more connecting(2-connections) flights? (Enter Y to show connecting flights)> ");
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }  
                    }
                    
                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                }
            } else if (tripType == 2) {
                if (flightType == 1) {
                    try {
                        System.out.println("** Available Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                        doGetFlightScheduleAvailability(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        
                        System.out.println("-----------------------------------");
                        
                        System.out.println("** Available Return Flights from " + destinationAirport + " to " + departureAirport + " **\n");
                        doGetFlightScheduleAvailability(destinationAirport, departureAirport, returnDate, numberOfPassengers);
                    } catch (NoFlightsAvailableException ex) {
                        System.out.println(ex.getMessage() + "\n");
                        System.out.println("Do you want to look for connecting flights instead?");
                    }                                     

                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                } else if (flightType == 2) {
                    System.out.println("** Available Connecting Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                    try {
                        List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        doGetSingleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                        
                        System.out.println("-----------------------------------");
                        
                        System.out.println("** Available Connecting Flights from " + destinationAirport + " to " + departureAirport + " **\n");
                        List<FlightSchedule> returnFlightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(destinationAirport, departureAirport, returnDate, numberOfPassengers);
                        doGetSingleConnectionFlights(returnFlightSchedules, destinationAirport, departureAirport, returnDate, numberOfPassengers);
                        
                    } catch (NoFlightsAvailableException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                    
                    System.out.print("Do you still want to view more connecting(2-connections) flights? (Enter Y to show connecting flights)> ");
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            
                            System.out.println("-----------------------------------");
                            
                            System.out.println("** Available Connecting Flights from " + destinationAirport + " to " + departureAirport + " **\n");
                            List<FlightSchedule> returnFlightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(destinationAirport, departureAirport, returnDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(returnFlightSchedules, destinationAirport, departureAirport, returnDate, numberOfPassengers);
                        
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }  
                    }
                                        
                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                    
                } else if (flightType == 3) {
                    System.out.print("Do you still want to view more connecting flights? (Enter Y to show connecting flights)> ");
                    
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        System.out.println("** Available Connecting Flights from " + departureAirport + " to " + destinationAirport + " **\n");
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetSingleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);

                            System.out.println("-----------------------------------");

                            System.out.println("** Available Connecting Flights from " + destinationAirport + " to " + departureAirport + " **\n");
                            List<FlightSchedule> returnFlightSchedules = customerFlightReservationSessionBeanRemote.searchOneConnectionFlights(destinationAirport, departureAirport, returnDate, numberOfPassengers);
                            doGetSingleConnectionFlights(returnFlightSchedules, destinationAirport, departureAirport, returnDate, numberOfPassengers);
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }
                    }
                    
                    System.out.print("Do you still want to view more connecting(2-connections) flights? (Enter Y to show connecting flights)> ");
                    responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        try {
                            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(flightSchedules, departureAirport, destinationAirport, departureDate, numberOfPassengers);
                            
                            System.out.println("-----------------------------------");
                            
                            System.out.println("** Available Connecting Flights from " + destinationAirport + " to " + departureAirport + " **\n");
                            List<FlightSchedule> returnFlightSchedules = customerFlightReservationSessionBeanRemote.searchTwoConnectionsFlight(destinationAirport, departureAirport, returnDate, numberOfPassengers);
                            doGetDoubleConnectionFlights(returnFlightSchedules, destinationAirport, departureAirport, returnDate, numberOfPassengers);
                        
                        } catch (NoFlightsAvailableException ex) {
                            System.out.println(ex.getMessage() + "\n");
                        }  
                    }
                    
                    System.out.print("Press any key to continue...> ");
                    sc.nextLine();
                    
                }
            }
            
            System.out.print("Do you want to make a reservation? (Y: Yes, N: No)> ");
            responseString = sc.nextLine().trim();

            if (responseString.equals("Y")) {

                if (currentCustomer == null) {

                    System.out.println("1: Register");
                    System.out.println("2: Login");

                    while (response < 1 || response > 2) {
                        System.out.print("> ");
                        response = sc.nextInt();

                        if (response == 1) {
                            try {
                                doRegisterNewCustomer();
                            } catch (InvalidLoginCredentialException ex) {
                                System.out.println(ex.getMessage() + "\n");
                            }
                            break;
                        } else if (response == 2) {
                            try {
                                doLogin();
                            } catch (InvalidLoginCredentialException ex) {
                                throw new InvalidLoginCredentialException(ex.getMessage() + "\n");
                            }
                            break;
                        } else {
                            System.out.println("You must login before reserving a flight!\n");
                        }
                    }
                    try {
                        doReserveFlight();
                    } catch (SeatNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                } else {
                    try {
                        doReserveFlight();
                    } catch (SeatNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
                }
            } else {
                System.out.println("Returning to main menu...\n");
            }
            
        } catch (ParseException ex) {
            System.out.println("Invalid date input!\n");
        }
    }
    
    public void doReserveFlight() throws SeatNotFoundException { //take in choices whether single return
        
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: Reserve Flight ***\n");
        Long flightScheduleId = -1l;
        String cabinClass = "";
        
        List<List<Long>> seatIdsOfEachPassenger = new ArrayList<>();
        List<List<Long>> flightScheduleIdsOfEachPassenger = new ArrayList<>();
        List<List<CabinClassType>> cabinClassTypeOfEachPassenger = new ArrayList<>();
        List<Transaction> transactions =  new ArrayList<>();
        while (true) {
            List<Long> seatIds = new ArrayList<>();
            List<Long> flightScheduleIds = new ArrayList<>();
            List<CabinClassType> cabinClasses = new ArrayList<>();
                    
            Transaction transaction = null;
            String passengerFirstName = "";
            String passengerLastName = "";
            String passportNumber = "";
            String responseString = "";
            while (true) {
                try { //single direct
                    CabinClassType cabinClassType = null;
                    System.out.print("Enter Flight Schedule ID to reserve> ");
                    flightScheduleId = sc.nextLong();
                    sc.nextLine();
                    System.out.print("Enter Cabin Class Alphabet (F: First Class, J: Business, W: Premium Economy, Y: Economy)> ");
                    cabinClass = sc.nextLine().trim();
                    if (cabinClass.equals("F")) {
                        cabinClassType = CabinClassType.FIRST_CLASS;
                    } else if (cabinClass.equals("J")) {
                        cabinClassType = CabinClassType.BUSINESS_CLASS;
                    } else if (cabinClass.equals("W")) {
                        cabinClassType = CabinClassType.PREMIUM_ECONOMY;
                    } else if (cabinClass.equals("Y")) {
                        cabinClassType = CabinClassType.ECONOMY;
                    } else {
                        throw new InputMismatchException("Please only enter F, J, W or Y!");
                    }
                    
                    
                    
                    Boolean flightScheduleExists = customerFlightReservationSessionBeanRemote.checkFlightScheduleExist(flightScheduleId, cabinClassType);

                    
                    
                    FlightSchedule flightSchedule = customerFlightReservationSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId);
                    doPrintAvailableSeats(flightSchedule, cabinClassType);
                    System.out.println("-----------------------------------");
                    System.out.print("Enter Seat Column To Reserve(eg. A)> ");
                    String col = sc.nextLine().trim();
                    System.out.print("Enter Seat Row to Reserve(eg. 1)> ");        
                    Integer row = sc.nextInt();
                    sc.nextLine();

                    if (passportNumber.equals("")) {
                        System.out.print("Enter Passenger First Name (eg. Samuel)> ");
                        passengerFirstName = sc.nextLine().trim();
                        System.out.print("Enter Passenger Last Name (eg. Wang)> ");
                        passengerLastName = sc.nextLine().trim();
                        System.out.print("Enter " + passengerFirstName + " " + passengerLastName  + "'s Passport Number (E12345678)> ");
                        passportNumber = sc.nextLine().trim();
                        transaction = new Transaction(passengerFirstName, passengerLastName, flightScheduleId);
                    }


                    Long seatId = customerFlightReservationSessionBeanRemote.reserveSeat(flightSchedule, cabinClassType, row, col);
                    flightScheduleIds.add(flightScheduleId);
                    cabinClasses.add(cabinClassType);
                    seatIds.add(seatId);
                    System.out.println("Seat " + col + row + " is reserved!");



                    System.out.println("Would you like to book another ticket for the same passenger? (Enter Y for yes)> ");
                    responseString = sc.nextLine().trim();
                    if(!responseString.equals("Y")) {
                        break;
                    }

                } catch (FlightSchedulePlanNotFoundException | InputMismatchException | CabinClassConfigurationNotFoundException | SeatNotFoundException | SeatInventoryNotFoundException | SeatReservedException ex) {
                    System.out.println(ex.getMessage() + "\n");
                    for (List<Long> seatIdsRollBacks : seatIdsOfEachPassenger) {
                        for (Long seatIdRollBack : seatIdsRollBacks) {
                            customerFlightReservationSessionBeanRemote.rollBackSeatsToAvailable(seatIdRollBack);
                        }
                    }
                }
            }
            
            seatIdsOfEachPassenger.add(seatIds);
            flightScheduleIdsOfEachPassenger.add(flightScheduleIds);
            cabinClassTypeOfEachPassenger.add(cabinClasses);
            transactions.add(transaction);
            
            System.out.print("Reserve another flight? (Enter Y to create, N otherwise)>");
            responseString = sc.nextLine().trim();

            if (!responseString.equals("Y")) {
                break;
            }
        }
        
        try {
            System.out.println("Credit Card Payment(VISA), press any key to continue..>");
            sc.nextLine();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            CreditCard currCreditCard = null;
            
            while (true) {
                try {
                    List<CreditCard> creditCardsCurr = customerFlightReservationSessionBeanRemote.retrieveAllCreditCardCustomer(currentCustomer.getCustomerId());
                    if (creditCardsCurr.isEmpty()) {
                        System.out.print("Enter Credit Card Number: >");
                        String creditCardNumber = sc.nextLine().trim();
                        System.out.print("Enter Credit Card Name: >");
                        String creditCardName = sc.nextLine().trim();
                        System.out.print("Enter Credit Card Expiry Date (eg. 12-12-2020): >");
                        String creditCardExpiryDateToParse = sc.nextLine().trim();
                        Date creditCardExpiryDate = simpleDateFormat.parse(creditCardExpiryDateToParse);
                        System.out.print("Enter Credit Card Cvv(Look at the back of your card): >");
                        Integer cvv = sc.nextInt();
                        sc.nextLine();
                        CreditCard creditCard = new CreditCard(creditCardNumber, creditCardName, creditCardExpiryDate, cvv);
                        customerFlightReservationSessionBeanRemote.createNewCreditCardCustomer(creditCard, currentCustomer);
                        currCreditCard = creditCard;
                    } else {
                        System.out.println("1: Choose Existing Credit Card");
                        System.out.println("2: Enter New Credit Card");
                        Integer responseInt = sc.nextInt();
                        sc.nextLine();
                        if (responseInt == 1) {
                            List<CreditCard> creditCards = customerFlightReservationSessionBeanRemote.retrieveAllCreditCardCustomer(currentCustomer.getCustomerId());
                            for (CreditCard cCard : creditCards) {
                                System.out.println("Credit Card ID: " + cCard.getCreditCardId() + "; Credit Card Number: " + cCard.getCreditCardNumber());
                            }
                            System.out.print("Select a credit card ID> ");
                            Long responseLong = sc.nextLong();
                            sc.nextLine();
                            try {
                                currCreditCard = customerFlightReservationSessionBeanRemote.retrieveCreditCardById(responseLong);
                            } catch (CreditCardNotFoundException ex) {
                                System.out.println(ex.getMessage() + "\n");
                            }
                        } else {
                            System.out.print("Enter Credit Card Number: >");
                            String creditCardNumber = sc.nextLine().trim();
                            System.out.print("Enter Credit Card Name: >");
                            String creditCardName = sc.nextLine().trim();
                            System.out.print("Enter Credit Card Expiry Date (eg. 12-12-2020): >");
                            String creditCardExpiryDateToParse = sc.nextLine().trim();
                            Date creditCardExpiryDate = simpleDateFormat.parse(creditCardExpiryDateToParse);
                            System.out.print("Enter Credit Card Cvv(Look at the back of your card): >");
                            Integer cvv = sc.nextInt();
                            sc.nextLine();
                            CreditCard creditCard = new CreditCard(creditCardNumber, creditCardName, creditCardExpiryDate, cvv);
                            customerFlightReservationSessionBeanRemote.createNewCreditCardCustomer(creditCard, currentCustomer);
                        }
                    }

                    if (currCreditCard instanceof CreditCard) {
                        break;
                    }

                    System.out.print("Try again? (Enter Y to create, N otherwise)>");
                    String responseString = sc.nextLine().trim();

                    if (!responseString.equals("Y")) {
                        throw new TransactionException("Cancelled Payment!");
                    }
                } catch(ParseException | TransactionException ex) {
                    System.out.println(ex.getMessage() + "\n");
                    for (List<Long> seatIds : seatIdsOfEachPassenger) {
                        for (Long seatIdRollBack : seatIds) {
                            customerFlightReservationSessionBeanRemote.rollBackSeatsToAvailable(seatIdRollBack);
                        }
                    }
                }
            }
            
            BigDecimal totalFare = BigDecimal.ZERO;
            if (transactions.size() != flightScheduleIdsOfEachPassenger.size() && flightScheduleIdsOfEachPassenger.size() != seatIdsOfEachPassenger.size() && seatIdsOfEachPassenger.size() != cabinClassTypeOfEachPassenger.size()) {
                
                throw new TransactionException("A transaction error occured!");
            }
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transactionToCreate = transactions.get(i);
                List<Long> transactionFlightScheduleIds = flightScheduleIdsOfEachPassenger.get(i);
                List<Long> transactionSeatIds = seatIdsOfEachPassenger.get(i);
                List<CabinClassType> transactionCabinClasses = cabinClassTypeOfEachPassenger.get(i);
                System.out.println("FS: " + transactionFlightScheduleIds.size() + " S: "+ transactionSeatIds.size() + "CC: " + transactionCabinClasses.size());//debug
                if (transactionFlightScheduleIds.size() != transactionSeatIds.size() && transactionSeatIds.size() != transactionCabinClasses.size()) {
                    throw new TransactionException("An internal transaction error occured!");
                }
                Long transactionId = customerFlightReservationSessionBeanRemote.createNewTransaction(transactionToCreate, this.currentCustomer);
                for (int j = 0; j < transactionFlightScheduleIds.size(); j++) {
                    Long flightScheduleIdToSet = transactionFlightScheduleIds.get(j);
                    FlightSchedule flightSchedule = customerFlightReservationSessionBeanRemote.retrieveFlightScheduleById(flightScheduleIdToSet);
                    FlightRoute flightRoute = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute();
                    CabinClassType cabinClassType = transactionCabinClasses.get(j);
                    Seat seat = customerFlightReservationSessionBeanRemote.retrieveSeatById(transactionSeatIds.get(j));
                    SeatInventory seatInventoryToSet = null;
                    for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
                        if (seatInventory.getCabinClass().getCabinClassType().equals(cabinClassType)) {
                            seatInventoryToSet = seatInventory;
                            break;
                        }
                    }
                    Fare fare = customerFlightReservationSessionBeanRemote.getFarePerPax(flightSchedule, seatInventoryToSet, seat);
                    totalFare = totalFare.add(fare.getFareAmount());
                    String odDateTime = flightSchedule.getDepartureDateTime() + "; " + flightSchedule.getArrivalDateTime();
                    String odCode = flightRoute.getOriginAirport().getIataCode() + "-" + flightRoute.getDestinationAirport().getIataCode();
                    String cabinClassString = cabinClassType + "";
                    String seatNumber = seat.getRowAlphabet() + "" + seat.getSeatNumber();
                    ItineraryItem itineraryItem = new ItineraryItem(odDateTime, odCode, cabinClassString, seatNumber, fare.getFareAmount(), transactionToCreate.getPassengerFirstName() + " " + transactionToCreate.getPassengerLastName(), fare.getFareBasisCode());
                    customerFlightReservationSessionBeanRemote.createNewItinerary(itineraryItem, transactionId, flightScheduleIdToSet);
                }
                customerFlightReservationSessionBeanRemote.makePayment(currCreditCard, totalFare);
                System.out.println("Transaction Completed!");
            }
        } catch (TransactionException | TransactionNotFoundException | CustomerNotFoundException | FlightSchedulePlanNotFoundException ex) {
            System.out.println("Transaction cannot be processed! " + ex.getMessage() + "\n");
            for (List<Long> seatIds : seatIdsOfEachPassenger) {
                for (Long seatIdRollBack : seatIds) {
                    customerFlightReservationSessionBeanRemote.rollBackSeatsToAvailable(seatIdRollBack);
                }
            }
        }
        
        
    }
    
    public void doViewMyFlightReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservations ***\n");
        System.out.println("viewing flight reservations");
        
        List<Transaction> transactions = customerFlightReservationSessionBeanRemote.retrieveAllTransactionByCustomerId(currentCustomer.getCustomerId());
        System.out.printf("%20s%32s%32s%20s\n", "Transaction Id", "Passenger Name" + "Passport Number" + "Total Price");
        for (Transaction transaction : transactions) {
           System.out.printf("%20s%32s%32s%20s\n",transaction.getTransactionId(), transaction.getPassengerFirstName() + "" + transaction.getPassengerLastName(), transaction.getPassportNumber(), transaction.getTotalPrice());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        
        System.out.println(" ");
    }
    
    public void doViewMyFlightReservationDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservation Details ***\n");
        System.out.println("viewing flight reservation details");
        
        System.out.print("Enter Transaction Id> ");
        Long transactionId = sc.nextLong();
        sc.nextLine();
        
        List<ItineraryItem> itineraryItems = customerFlightReservationSessionBeanRemote.retrieveAllItineraryItemByTransactionId(transactionId);
        
        System.out.printf("%22s%25s%32s%20s%20s%20s\n", "Itinerary Item Id", "Origin-Destination", "Passenger Name", "Cabin Class", "Seat Number", "Fare Paid");
        for (ItineraryItem itineraryItem : itineraryItems) {
            System.out.printf("%22s%25s%32s%20s%20s%20s\n",itineraryItem.getItineraryItemId(), itineraryItem.getOdCode(), itineraryItem.getPassengerName(), itineraryItem.getCabinClass(), itineraryItem.getSeatNumber(), itineraryItem.getFareAmount());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doGetFlightScheduleAvailability(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            List<FlightSchedule> flightSchedules = customerFlightReservationSessionBeanRemote.searchSingleFlights(departureAirport, destinationAirport, departureDate, numberOfPassengers);

            Date beforeDate;
            Date afterDate;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateFormat.format(departureDate)));
            calendar.add(Calendar.DAY_OF_MONTH, -4);
            beforeDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            afterDate = calendar.getTime();

            for (int i = 0; i < 7; i++) {
                beforeDate = afterDate;
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                afterDate = calendar.getTime();
                System.out.println("Flights for Date: " + dateFormat.format(beforeDate));
                System.out.printf("%20s%20s%20s%25s%25s%20s%20s\n", "Flight Schedule ID","Flight Number", "Departure Time", "Seats Available", "Cabin Classes Available", "Fare/pax", "Total Fare Amount");

                for (FlightSchedule flightSchedule : flightSchedules) {
                    if (flightSchedule.getDepartureDateTime().after(beforeDate) && flightSchedule.getDepartureDateTime().before(afterDate)) {
                        doGetCabinClassAvailability(flightSchedule, numberOfPassengers);
                    } else {
                        //System.out.printf("%20s%20s%20s%25s%25s%20s%20s\n", "-","-", "-", "-", "-", "-", "-");
                    }
                }
                System.out.println("\n");
            } 
        } catch (ParseException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void doGetCabinClassAvailability(FlightSchedule flightSchedule, Integer numberOfPassengers) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        FlightSchedulePlan flightSchedulePlan = flightSchedule.getFlightSchedulePlan();
        List<Fare> fares = flightSchedulePlan.getFares();
        
        List<SeatInventory> seatInventories = flightSchedule.getSeatInventories();
        
        String originGMT = "";
        Double timeZoneOrigin = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOriginAirport().getTimeZone();
        if (timeZoneOrigin >= 0) {
            originGMT = "+" + timeZoneOrigin;
        } else {
            originGMT = "" + timeZoneOrigin;
        }
        
        for (SeatInventory seatInventory : seatInventories) {
            if (seatInventory.getAvailableSeats() > 0) {
                Fare farePaxfare = customerFlightReservationSessionBeanRemote.getFarePerPax(flightSchedule, seatInventory, placeHolderCustomer);
                BigDecimal farePax = farePaxfare.getFareAmount();
                BigDecimal totalFare = farePax.multiply(new BigDecimal(numberOfPassengers));
                
                System.out.printf("%20s%20s%20s%25s%25s%20s%20s\n", 
                        flightSchedule.getFlightScheduleId(),
                        flightSchedule.getFlightSchedulePlan().getFlight().getFlightNumber(), 
                        timeFormat.format(flightSchedule.getDepartureDateTime()) + " (GMT " + originGMT + ")",
                        seatInventory.getAvailableSeats(),
                        seatInventory.getCabinClass().getCabinClassType(), 
                        farePax, 
                        totalFare);
            }
        }
    }
    
    public void doGetSingleConnectionFlights(List<FlightSchedule> flightSchedules, String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException {
        Scanner sc = new Scanner(System.in);
        List<String> connectingAirports = new ArrayList<>();
        for (FlightSchedule flightSchedule : flightSchedules) {
            String iata = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestinationAirport().getIataCode();

            if (!iata.equals(departureAirport) && !iata.equals(destinationAirport) && !connectingAirports.contains(iata)) {
                //System.out.println(iata);
                connectingAirports.add(iata);
            }
        }
        for (String connectingIata : connectingAirports) {
            System.out.println("** Connecting Flight : " + departureAirport + "-" + connectingIata + "-" + destinationAirport + " **\n");
            System.out.println("** Available Flights from " + departureAirport + " to " + connectingIata + " **\n");
            doGetFlightScheduleAvailability(departureAirport, connectingIata, departureDate, numberOfPassengers);
            System.out.println("-----------------------------------");
            System.out.println("** Available Flights from " + connectingIata + " to " + destinationAirport + " **\n");
            doGetFlightScheduleAvailability(connectingIata, destinationAirport, departureDate, numberOfPassengers);
            System.out.print("Press any key to continue...> ");
            sc.nextLine();
        }
    }
    
    public void doGetDoubleConnectionFlights(List<FlightSchedule> flightSchedules, String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException {
        Scanner sc = new Scanner(System.in);
        List<String> connectingAirports1 = new ArrayList<>();
        List<String> connectingAirports2 = new ArrayList<>();
        for (FlightSchedule flightSchedule : flightSchedules) {
            String iata1 = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestinationAirport().getIataCode();
            String iata2 = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOriginAirport().getIataCode();
            if (!iata1.equals(departureAirport) && !iata1.equals(destinationAirport) && !connectingAirports1.contains(iata1)) {
                System.out.println(iata1); // debug
                connectingAirports1.add(iata1);
            }
            
            if (!iata2.equals(departureAirport) && !iata2.equals(destinationAirport) && !connectingAirports2.contains(iata2)) {
                System.out.println(iata2); // debug
                connectingAirports1.add(iata2);
            }
        }
        
        for (String connectingIata1 : connectingAirports1) {
            for (String connectingIata2 : connectingAirports2) {
                if (connectingIata1.equals(connectingIata2)) {
                    System.out.println("always?");
                    continue;
                }
                System.out.println("** Connecting Flight : " + departureAirport + "-" + connectingIata1 + "-" + connectingIata2 + "-" + destinationAirport + " **\n");
                System.out.println("** Available Flights from " + departureAirport + " to " + connectingIata1 + " **\n");
                doGetFlightScheduleAvailability(departureAirport, connectingIata1, departureDate, numberOfPassengers);
                System.out.println("-----------------------------------");
                System.out.println("** Available Flights from " + connectingIata1 + " to " + connectingIata2 + " **\n");
                doGetFlightScheduleAvailability(connectingIata1, connectingIata2, departureDate, numberOfPassengers);
                System.out.println("-----------------------------------");
                System.out.println("** Available Flights from " + connectingIata2 + " to " + destinationAirport + " **\n");
                doGetFlightScheduleAvailability(connectingIata2, destinationAirport, departureDate, numberOfPassengers);
                System.out.print("Press any key to continue...> ");
                sc.nextLine();
            }
        }
    }
    
    public void doPrintAvailableSeats(FlightSchedule flightSchedule, CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException {
        Scanner sc = new Scanner(System.in);
        Flight flight = flightSchedule.getFlightSchedulePlan().getFlight();
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Flight Route: " + flight.getFlightRoute().getOriginAirport().getIataCode() + "-" + flight.getFlightRoute().getDestinationAirport().getIataCode());
        System.out.println("Cabin Class: " + cabinClassType);
        System.out.println("Departure Date: " + flightSchedule.getDepartureDateTime());
        System.out.println("Arrival Date: " + flightSchedule.getArrivalDateTime());
        System.out.println();
        SeatInventory seatInventoryToPrint = null;
        for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
            if (seatInventory.getCabinClass().getCabinClassType().equals(cabinClassType)) {
                seatInventoryToPrint = seatInventory;
            }
        }
        
        System.out.println("Seats Available(O : Available, X: Reserved): ");
        System.out.println("-----------------------------------");
        for (int i = 0; i < seatInventoryToPrint.getCabinClass().getNumberOfRows(); i++) {
            if (i == 0) {
                System.out.print("Column: ");
                for (int k = 0; k < seatInventoryToPrint.getCabinClass().getNumberOfSeatsAbreast(); k++) {
                    System.out.print(seatInventoryToPrint.getSeats().get(k).getRowAlphabet() + " ");
                }
                System.out.println();
            }
            for (int j = 0; j < seatInventoryToPrint.getCabinClass().getNumberOfSeatsAbreast(); j++) {
                Seat seat = seatInventoryToPrint.getSeats().get((i * seatInventoryToPrint.getCabinClass().getNumberOfSeatsAbreast()) + j);
                if (j == 0) {
                    System.out.print("Row" + (i+1) + ":  ");
                    if (i < 9) {
                        System.out.print(" ");
                    }
                }
                if(seat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                    //System.out.print(seat.getRowAlphabet() + seat.getSeatNumber() + " ");
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        
    }
}
