/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystem;

import ejb.session.ws.CabinClassConfigurationNotFoundException;
import ejb.session.ws.CabinClassConfigurationNotFoundException_Exception;
import ejb.session.ws.CabinClassType;
import ejb.session.ws.CreditCard;
import ejb.session.ws.CreditCardNotFoundException;
import ejb.session.ws.CreditCardNotFoundException_Exception;
import ejb.session.ws.CustomerNotFoundException;
import ejb.session.ws.CustomerNotFoundException_Exception;
import ejb.session.ws.Fare;
import ejb.session.ws.Flight;
import ejb.session.ws.FlightRoute;
import ejb.session.ws.FlightSchedule;
import ejb.session.ws.FlightSchedulePlan;
import ejb.session.ws.FlightSchedulePlanNotFoundException;
import ejb.session.ws.FlightSchedulePlanNotFoundException_Exception;
import ejb.session.ws.InvalidLoginCredentialException_Exception;
import ejb.session.ws.ItineraryItem;
import ejb.session.ws.NoFlightsAvailableException;
import ejb.session.ws.NoFlightsAvailableException_Exception;
import ejb.session.ws.ParseException_Exception;
import ejb.session.ws.Partner;
import ejb.session.ws.Seat;
import ejb.session.ws.SeatInventory;
import ejb.session.ws.SeatInventoryNotFoundException;
import ejb.session.ws.SeatInventoryNotFoundException_Exception;
import ejb.session.ws.SeatNotFoundException;
import ejb.session.ws.SeatNotFoundException_Exception;
import ejb.session.ws.SeatReservedException;
import ejb.session.ws.SeatReservedException_Exception;
import ejb.session.ws.SeatStatus;
import ejb.session.ws.Transaction;
import ejb.session.ws.TransactionNotFoundException;
import ejb.session.ws.TransactionNotFoundException_Exception;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GraceLi
 */
public class MainApp {

    private Partner currentPartner;

    public MainApp() {
    }

    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Welcome to Holiday Reservation System ***\n");
            if (currentPartner == null) {
                System.out.println("1: Partner Login");
            } else {
                System.out.println("You are login as " + currentPartner.getName() + "\n");
            }
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 1) {
                System.out.print("> ");
                response = sc.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        doMainMenu();
                    } catch (InvalidLoginCredentialException_Exception ex) {
                        System.out.println(ex.getFaultInfo());
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again! ");
                }
            }
            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginCredentialException_Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("*** Holiday Reservtation System :: Partner Login ***\n");
        System.out.print("Enter username> ");
        String email = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();

        if (email.length() > 0 && password.length() > 0) {
            currentPartner = login(email, password);
        }
    }

    public void doMainMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Holiday Reservtation System :: Main Menu ***\n");
            System.out.println("1: Search for flights");
            System.out.println("2: View partner flight reservations");
            System.out.println("3: View partner flight reservation details");
            System.out.println("4: Logout\n");

            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                doSearchFlight();
            } else if (response == 2) {
                doViewPartnerFlightReservations();
            } else if (response == 3) {
                doViewPartnerFlightReservationDetails();
            } else if (response == 4) {
                currentPartner = null; 
                break;
            } else {
                System.out.println("Invalid option, try again!\n");
            }
        }
    }

    public void doSearchFlight() {
        try {
            Scanner sc = new Scanner(System.in);
            Integer response = 0;
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("d/M/y");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

            System.out.println("*** Flight Reservation System :: Search For Flights ***\n");

            System.out.println("Select the trip type");
            System.out.println("1: Single trip");
            System.out.println("2: Round trip\n");
            System.out.print("> ");
            Integer tripType = sc.nextInt();
            sc.nextLine();
            
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

            System.out.println("Select flight type preference");
            System.out.println("1: Direct flight");
            System.out.println("2: Connecting flight\n");
            System.out.print("> ");
            Integer flightType = sc.nextInt();

            if (flightType == 1) {

            }

            System.out.println("searching for flight....");
            System.out.println("flight found!");

            System.out.print("Do you want to make a reservation? (Y: Yes, N: No)> ");
            String responseString = sc.nextLine();

            if (responseString.equals("Y")) {
//                try {
//                    doReserveFlight();
//                } catch (SeatNotFoundException_Exception ex) {
//                    System.out.println(ex.getMessage());
//                }
            } else {
                System.out.println("Returning to main menu...\n");
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date input!\n");
        }
    }
//commented to allow compilation
//    public void doReserveFlight() throws SeatNotFoundException_Exception { //take in choices whether single return
//        
//        //reserve flight is like 
//        //add to itinerary, 
//        //create a new transaction to contain list of itinerary, 
//        //link itinerary to flightschedule then 
//        //simulate a fake transaction right
//        
//        Scanner sc = new Scanner(System.in);
//        System.out.println("*** Flight Reservation System :: Reserve Flight ***\n");
//        //another while here?
//        Long flightScheduleId = -1l;
//        String cabinClass = "";
//        
//        List<List<Long>> seatIdsOfEachPassenger = new ArrayList<>();
//        List<List<Long>> flightScheduleIdsOfEachPassenger = new ArrayList<>();
//        List<List<CabinClassType>> cabinClassTypeOfEachPassenger = new ArrayList<>();
//        List<Transaction> transactions =  new ArrayList<>();
//        while (true) {
//            List<Long> seatIds = new ArrayList<>();
//            List<Long> flightScheduleIds = new ArrayList<>();
//            List<CabinClassType> cabinClasses = new ArrayList<>();
//                    
//            Transaction transaction = null;
//            String passengerFirstName = "";
//            String passengerLastName = "";
//            String passportNumber = "";
//            String responseString = "";
//            while (true) {
//                try { //single direct
//                    CabinClassType cabinClassType = null;
//                    System.out.print("Enter Flight Schedule ID to reserve> ");
//                    flightScheduleId = sc.nextLong();
//                    sc.nextLine();
//                    System.out.print("Enter Cabin Class Alphabet (F: First Class, J: Business, W: Premium Economy, Y: Economy)> ");
//                    cabinClass = sc.nextLine().trim();
//                    if (cabinClass.equals("F")) {
//                        cabinClassType = CabinClassType.FIRST_CLASS;
//                    } else if (cabinClass.equals("J")) {
//                        cabinClassType = CabinClassType.BUSINESS_CLASS;
//                    } else if (cabinClass.equals("W")) {
//                        cabinClassType = CabinClassType.PREMIUM_ECONOMY;
//                    } else if (cabinClass.equals("Y")) {
//                        cabinClassType = CabinClassType.ECONOMY;
//                    } else {
//                        throw new InputMismatchException("Please only enter F, J, W or Y!");
//                    }
//                    
//                    
//                    
//                    Boolean flightScheduleExists = checkFlightScheduleExist(flightScheduleId, cabinClassType);
//
//                    
//                    
//                    FlightSchedule flightSchedule = retrieveFlightScheduleById(flightScheduleId);
//                    doPrintAvailableSeats(flightSchedule, cabinClassType);
//                    System.out.println("-----------------------------------");
//                    System.out.print("Enter Seat Column To Reserve(eg. A)> ");
//                    String col = sc.nextLine().trim();
//                    System.out.print("Enter Seat Row to Reserve(eg. 1)> ");        
//                    Integer row = sc.nextInt();
//                    sc.nextLine();
//
//                    if (passportNumber.equals("")) {
//                        System.out.print("Enter Passenger First Name (eg. Samuel)> ");
//                        passengerFirstName = sc.nextLine().trim();
//                        System.out.print("Enter Passenger Last Name (eg. Wang)> ");
//                        passengerLastName = sc.nextLine().trim();
//                        System.out.print("Enter " + passengerFirstName + " " + passengerLastName  + "'s Passport Number (E12345678)> ");
//                        passportNumber = sc.nextLine().trim();
//                        transaction = new Transaction();
//                        transaction.setPassengerFirstName(passengerFirstName);
//                        transaction.setPassengerLastName(passengerLastName);
//                        transaction.setPassportNumber(passportNumber);
//                    }
//
//
//                    Long seatId = reserveSeat(flightSchedule, cabinClassType, row, col);
//                    flightScheduleIds.add(flightScheduleId);
//                    cabinClasses.add(cabinClassType);
//                    seatIds.add(seatId);
//                    System.out.println("Seat " + col + row + " is reserved!");
//
//
//
//                    System.out.println("Would you like to book another ticket for the same passenger? (Enter Y for yes)> ");
//                    responseString = sc.nextLine().trim();
//                    if(!responseString.equals("Y")) {
//                        break;
//                    }
//
//                } catch (CabinClassConfigurationNotFoundException_Exception | FlightSchedulePlanNotFoundException_Exception | SeatInventoryNotFoundException_Exception | SeatNotFoundException_Exception | SeatReservedException_Exception ex) {
//                    System.out.println(ex.getMessage() + "\n");
//                    for (List<Long> seatIdsRollBacks : seatIdsOfEachPassenger) {
//                        for (Long seatIdRollBack : seatIdsRollBacks) {
//                            rollBackSeatsToAvailable(seatIdRollBack);
//                        }
//                    }
//                }
//                //customerFlightReservationSessionBeanRemote.reserveFlights();
//            }
//            
//            seatIdsOfEachPassenger.add(seatIds);
//            flightScheduleIdsOfEachPassenger.add(flightScheduleIds);
//            cabinClassTypeOfEachPassenger.add(cabinClasses);
//            transactions.add(transaction);
//            
//            System.out.print("Reserve another flight? (Enter Y to create, N otherwise)>");
//            responseString = sc.nextLine().trim();
//
//            if (!responseString.equals("Y")) {
//                break;
//            }
//        }
//        
//        try {
//            System.out.println("Credit Card Payment(VISA), press any key to continue..>");
//            sc.nextLine();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            CreditCard currCreditCard = null;
//            
//            while (true) {
//                try {
//                    List<CreditCard> creditCardsCurr = retrieveAllCreditCardCustomer(currentCustomer.getCustomerId());
//                    if (creditCardsCurr.isEmpty()) {
//                        System.out.print("Enter Credit Card Number: >");
//                        String creditCardNumber = sc.nextLine().trim();
//                        System.out.print("Enter Credit Card Name: >");
//                        String creditCardName = sc.nextLine().trim();
//                        System.out.print("Enter Credit Card Expiry Date (eg. 12-12-2020): >");
//                        String creditCardExpiryDateToParse = sc.nextLine().trim();
//                        Date creditCardExpiryDate = simpleDateFormat.parse(creditCardExpiryDateToParse);
//                        System.out.print("Enter Credit Card Cvv(Look at the back of your card): >");
//                        Integer cvv = sc.nextInt();
//                        sc.nextLine();
//                        CreditCard creditCard = new CreditCard();
//                        creditCard.setCreditCardNumber(creditCardNumber);
//                        creditCard.setCreditCardName(creditCardName);
//                        String ccExDateString =
//                        creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
//                        creditCard.setCvv(cvv);
//                        createNewCreditCardCustomer(creditCard, currentPartner);
//                        currCreditCard = creditCard;
//                    } else {
//                        System.out.println("1: Choose Existing Credit Card");
//                        System.out.println("2: Enter New Credit Card");
//                        Integer responseInt = sc.nextInt();
//                        sc.nextLine();
//                        if (responseInt == 1) {
//                            List<CreditCard> creditCards = retrieveAllCreditCardCustomer(currentPartner.getPartnerId());
//                            for (CreditCard cCard : creditCards) {
//                                System.out.println("Credit Card ID: " + cCard.getCreditCardId() + "; Credit Card Number: " + cCard.getCreditCardNumber());
//                            }
//                            System.out.print("Select a credit card ID> ");
//                            Long responseLong = sc.nextLong();
//                            sc.nextLine();
//                            try {
//                                currCreditCard = retrieveCreditCardById(responseLong);
//                            } catch (CreditCardNotFoundException_Exception ex) {
//                                System.out.println(ex.getMessage() + "\n");
//                            }
//                        } else {
//                            System.out.print("Enter Credit Card Number: >");
//                            String creditCardNumber = sc.nextLine().trim();
//                            System.out.print("Enter Credit Card Name: >");
//                            String creditCardName = sc.nextLine().trim();
//                            System.out.print("Enter Credit Card Expiry Date (eg. 12-12-2020): >");
//                            String creditCardExpiryDateToParse = sc.nextLine().trim();
//                            Date creditCardExpiryDate = simpleDateFormat.parse(creditCardExpiryDateToParse);
//                            System.out.print("Enter Credit Card Cvv(Look at the back of your card): >");
//                            Integer cvv = sc.nextInt();
//                            sc.nextLine();
//                            CreditCard creditCard = new CreditCard();
//                            creditCard.setCreditCardNumber(creditCardNumber);
//                            creditCard.setCreditCardName(creditCardName);
//                            
//                            creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
//                            creditCard.setCvv(cvv);
//                            createNewCreditCardCustomer(creditCard, currentPartner);
//                        }
//                    }
//
//                    if (currCreditCard instanceof CreditCard) {
//                        break;
//                    }
//
//                    System.out.print("Try again? (Enter Y to create, N otherwise)>");
//                    String responseString = sc.nextLine().trim();
//
//                    if (!responseString.equals("Y")) {
//                        throw new TransactionException("Cancelled Payment!");
//                    }
//                } catch(ParseException | TransactionException ex) {
//                    System.out.println(ex.getMessage() + "\n");
//                    for (List<Long> seatIds : seatIdsOfEachPassenger) {
//                        for (Long seatIdRollBack : seatIds) {
//                            rollBackSeatsToAvailable(seatIdRollBack);
//                        }
//                    }
//                }
//            }
//            
//            BigDecimal totalFare = BigDecimal.ZERO;
//            if (transactions.size() != flightScheduleIdsOfEachPassenger.size() && flightScheduleIdsOfEachPassenger.size() != seatIdsOfEachPassenger.size() && seatIdsOfEachPassenger.size() != cabinClassTypeOfEachPassenger.size()) {
//                
//                throw new TransactionException("A transaction error occured!");
//            }
//            for (int i = 0; i < transactions.size(); i++) {
//                Transaction transactionToCreate = transactions.get(i);
//                List<Long> transactionFlightScheduleIds = flightScheduleIdsOfEachPassenger.get(i);
//                List<Long> transactionSeatIds = seatIdsOfEachPassenger.get(i);
//                List<CabinClassType> transactionCabinClasses = cabinClassTypeOfEachPassenger.get(i);
//                System.out.println("FS: " + transactionFlightScheduleIds.size() + " S: "+ transactionSeatIds.size() + "CC: " + transactionCabinClasses.size());//debug
//                if (transactionFlightScheduleIds.size() != transactionSeatIds.size() && transactionSeatIds.size() != transactionCabinClasses.size()) {
//                    throw new TransactionException("An internal transaction error occured!");
//                }
//                Long transactionId = createNewTransaction(transactionToCreate, this.currentPartner);
//                for (int j = 0; j < transactionFlightScheduleIds.size(); j++) {
//                    Long flightScheduleIdToSet = transactionFlightScheduleIds.get(j);
//                    FlightSchedule flightSchedule = retrieveFlightScheduleById(flightScheduleIdToSet);
//                    FlightRoute flightRoute = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute();
//                    CabinClassType cabinClassType = transactionCabinClasses.get(j);
//                    Seat seat = retrieveSeatById(transactionSeatIds.get(j));
//                    SeatInventory seatInventoryToSet = null;
//                    for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
//                        if (seatInventory.getCabinClass().getCabinClassType().equals(cabinClassType)) {
//                            seatInventoryToSet = seatInventory;
//                            break;
//                        }
//                    }
//                    Fare fare = getFarePerPax(flightSchedule, seatInventoryToSet, seat);
//                    totalFare = totalFare.add(fare.getFareAmount());
//                    String odDateTime = flightSchedule.getDepartureDateTime() + "; " + flightSchedule.getArrivalDateTime();
//                    String odCode = flightRoute.getOriginAirport().getIataCode() + "-" + flightRoute.getDestinationAirport().getIataCode();
//                    String cabinClassString = cabinClassType + "";
//                    String seatNumber = seat.getRowAlphabet() + "" + seat.getSeatNumber();
//                    ItineraryItem itineraryItem = new ItineraryItem();
//                    itineraryItem.setOdDateTime(odDateTime);
//                    itineraryItem.setOdCode(odCode);
//                    itineraryItem.setCabinClass(cabinClassString);
//                    itineraryItem.setSeatNumber(seatNumber);
//                    itineraryItem.setFareAmount(fare.getFareAmount());
//                    itineraryItem.setPassengerName(transactionToCreate.getPassengerFirstName() + " " + transactionToCreate.getPassengerLastName());
//                    itineraryItem.setFareBasisCode(fare.getFareBasisCode());
//                    createNewItinerary(itineraryItem, transactionId, flightScheduleIdToSet);
//                }
//                makePayment(currCreditCard, totalFare);
//                System.out.println("Transaction Completed!");
//            }
//        } catch (CustomerNotFoundException_Exception | FlightSchedulePlanNotFoundException_Exception | SeatNotFoundException_Exception | TransactionNotFoundException_Exception ex) {
//            System.out.println("Transaction cannot be processed! " + ex.getMessage() + "\n");
//            for (List<Long> seatIds : seatIdsOfEachPassenger) {
//                for (Long seatIdRollBack : seatIds) {
//                    rollBackSeatsToAvailable(seatIdRollBack);
//                }
//            }
//        }
//    }

    public void doViewPartnerFlightReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservations ***\n");
        System.out.println("viewing flight reservations");
        
        List<Transaction> transactions = retrieveAllTransactionByCustomerId(currentPartner.getPartnerId());
        System.out.printf("%20s%32s%32s%20s\n", "Transaction Id", "Passenger Name" + "Passport Number" + "Total Price");
        for (Transaction transaction : transactions) {
           System.out.printf("%20s%32s%32s%20s\n",transaction.getTransactionId(), transaction.getPassengerFirstName() + "" + transaction.getPassengerLastName(), transaction.getPassportNumber(), transaction.getTotalPrice());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        
        System.out.println(" ");
    }

    public void doViewPartnerFlightReservationDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservation Details ***\n");
        System.out.println("viewing flight reservation details");
        
        System.out.print("Enter Transaction Id> ");
        Long transactionId = sc.nextLong();
        sc.nextLine();
        
        List<ItineraryItem> itineraryItems = retrieveAllItineraryItemByTransactionId(transactionId);
        
        System.out.printf("%22s%25s%32s%20s%20s%20s\n", "Itinerary Item Id", "Origin-Destination", "Passenger Name", "Cabin Class", "Seat Number", "Fare Paid");
        for (ItineraryItem itineraryItem : itineraryItems) {
            System.out.printf("%22s%25s%32s%20s%20s%20s\n",itineraryItem.getItineraryItemId(), itineraryItem.getOdCode(), itineraryItem.getPassengerName(), itineraryItem.getCabinClass(), itineraryItem.getSeatNumber(), itineraryItem.getFareAmount());
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doGetFlightScheduleAvailability(String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException_Exception, ParseException_Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            List<FlightSchedule> flightSchedules = searchSingleFlights(departureAirport, destinationAirport, dateTimeFormat.format(departureDate), numberOfPassengers);

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
                    String flightScheduleDateString = dateTimeFormat.format(flightSchedule.getDepartureDateTime());
                    Date flightScheduleDate = dateTimeFormat.parse(flightScheduleDateString);
                    if (flightScheduleDate.after(beforeDate) && flightScheduleDate.before(afterDate)) {
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
                Fare farePaxfare = getFarePerPax(flightSchedule, seatInventory, new Partner());
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
    
    public void doGetSingleConnectionFlights(List<FlightSchedule> flightSchedules, String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException_Exception, ParseException_Exception {
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
    
    public void doGetDoubleConnectionFlights(List<FlightSchedule> flightSchedules, String departureAirport, String destinationAirport, Date departureDate, Integer numberOfPassengers) throws NoFlightsAvailableException_Exception, ParseException_Exception {
        Scanner sc = new Scanner(System.in);
        List<String> connectingAirports1 = new ArrayList<>();
        List<String> connectingAirports2 = new ArrayList<>();
        for (FlightSchedule flightSchedule : flightSchedules) {
            String iata1 = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getDestinationAirport().getIataCode();
            String iata2 = flightSchedule.getFlightSchedulePlan().getFlight().getFlightRoute().getOriginAirport().getIataCode();
            if (!iata1.equals(departureAirport) && !iata1.equals(destinationAirport) && !connectingAirports1.contains(iata1)) {

                connectingAirports1.add(iata1);
            }
            
            if (!iata2.equals(departureAirport) && !iata2.equals(destinationAirport) && !connectingAirports2.contains(iata2)) {

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
    
    public void doPrintAvailableSeats(FlightSchedule flightSchedule, CabinClassType cabinClassType) {
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
//        System.out.printf("%20s%20s%20s%25s%25s%20s%20s\n", );
        //for (Seat seat : seatInventoryToPrint.getSeats()) {
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


    private static Partner login(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.login(username, password);
    }

    private static Boolean checkFlightScheduleExist(java.lang.Long flightScheduleId, ejb.session.ws.CabinClassType cabinClassType) throws FlightSchedulePlanNotFoundException_Exception, CabinClassConfigurationNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.checkFlightScheduleExist(flightScheduleId, cabinClassType);
    }

    private static Long createNewCreditCardCustomer(ejb.session.ws.CreditCard creditCard, ejb.session.ws.Customer customer) throws CustomerNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.createNewCreditCardCustomer(creditCard, customer);
    }

    private static Long createNewItinerary(ejb.session.ws.ItineraryItem itineraryItem, java.lang.Long transactionId, java.lang.Long flightScheduleId) throws FlightSchedulePlanNotFoundException_Exception, TransactionNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.createNewItinerary(itineraryItem, transactionId, flightScheduleId);
    }

    private static Long createNewTransaction(ejb.session.ws.Transaction transaction, ejb.session.ws.Customer customer) throws CustomerNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.createNewTransaction(transaction, customer);
    }

    private static Fare getFarePerPax(ejb.session.ws.FlightSchedule flightSchedule, ejb.session.ws.SeatInventory seatInventory, java.lang.Object object) {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.getFarePerPax(flightSchedule, seatInventory, object);
    }

    private static void makePayment(ejb.session.ws.CreditCard creditCard, java.math.BigDecimal totalFare) {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        port.makePayment(creditCard, totalFare);
    }

    private static Long reserveSeat(ejb.session.ws.FlightSchedule flightSchedule, ejb.session.ws.CabinClassType cabinClassType, java.lang.Integer seatRow, java.lang.String seatCol) throws SeatInventoryNotFoundException_Exception, SeatReservedException_Exception, SeatNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.reserveSeat(flightSchedule, cabinClassType, seatRow, seatCol);
    }

    private static java.util.List<ejb.session.ws.CreditCard> retrieveAllCreditCardCustomer(java.lang.Long customerId) throws CustomerNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveAllCreditCardCustomer(customerId);
    }

    private static java.util.List<ejb.session.ws.ItineraryItem> retrieveAllItineraryItemByTransactionId(java.lang.Long customerId) {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveAllItineraryItemByTransactionId(customerId);
    }

    private static java.util.List<ejb.session.ws.Transaction> retrieveAllTransactionByCustomerId(java.lang.Long customerId) {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveAllTransactionByCustomerId(customerId);
    }

    private static CreditCard retrieveCreditCardById(java.lang.Long creditCardId) throws CreditCardNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveCreditCardById(creditCardId);
    }

    private static FlightSchedule retrieveFlightScheduleById(java.lang.Long flightScheduleId) throws FlightSchedulePlanNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveFlightScheduleById(flightScheduleId);
    }

    private static Seat retrieveSeatById(java.lang.Long seatId) throws SeatNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveSeatById(seatId);
    }

    private static Transaction retrieveTransactionById(java.lang.Long transactionId) throws TransactionNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.retrieveTransactionById(transactionId);
    }

    private static void rollBackSeatsToAvailable(java.lang.Long seatId) throws SeatNotFoundException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        port.rollBackSeatsToAvailable(seatId);
    }

    private static java.util.List<ejb.session.ws.FlightSchedule> searchOneConnectionFlights(java.lang.String departureAirport, java.lang.String destinationAirport, java.lang.String departureDateString, java.lang.Integer numberOfTravellers) throws NoFlightsAvailableException_Exception, ParseException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.searchOneConnectionFlights(departureAirport, destinationAirport, departureDateString, numberOfTravellers);
    }

    private static java.util.List<ejb.session.ws.FlightSchedule> searchSingleFlights(java.lang.String departureAirport, java.lang.String destinationAirport, java.lang.String departureDateString, java.lang.Integer numberOfTravellers) throws ParseException_Exception, NoFlightsAvailableException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.searchSingleFlights(departureAirport, destinationAirport, departureDateString, numberOfTravellers);
    }

    private static java.util.List<ejb.session.ws.FlightSchedule> searchTwoConnectionsFlight(java.lang.String departureAirport, java.lang.String destinationAirport, java.lang.String departureDateString, java.lang.Integer numberOfTravellers) throws ParseException_Exception, NoFlightsAvailableException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.searchTwoConnectionsFlight(departureAirport, destinationAirport, departureDateString, numberOfTravellers);
    }

    

}
