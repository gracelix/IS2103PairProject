/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystem;

import ejb.session.ws.InvalidLoginCredentialException_Exception;
import ejb.session.ws.Partner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                doReserveFlight();
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

    public void doViewPartnerFlightReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservations ***\n");
        System.out.println("viewing flight reservations");
    }

    public void doViewPartnerFlightReservationDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System :: View My Flight Reservation Details ***\n");
        System.out.println("viewing flight reservation details");
    }

    private static Partner login(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ejb.session.ws.HolidayReservationSystemWebService_Service service = new ejb.session.ws.HolidayReservationSystemWebService_Service();
        ejb.session.ws.HolidayReservationSystemWebService port = service.getHolidayReservationSystemWebServicePort();
        return port.login(username, password);
    }

//    public static Partner login2(java.lang.String username, java.lang.String password) {
//
//        try {
//            java.lang.String username1 = "";
//            java.lang.String password2 = "";
//            ejb.session.ws.HolidayReservationSystemWebService_Service service1 = new ejb.session.ws.HolidayReservationSystemWebService_Service();
//            ejb.session.ws.HolidayReservationSystemWebService port1 = service1.getHolidayReservationSystemWebServicePort();
//            // TODO process result here
//            ejb.session.ws.Partner result = port1.login(username, password);
//            System.out.println("Result = " + result);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
}
