/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightreservationsystemclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import ejb.session.stateless.FareSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightScheduleSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.SeatInventorySessionBeanRemote;
import ejb.session.stateless.SeatSessionBeanRemote;
import entity.CabinClassConfiguration;
import entity.Employee;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.Seat;
import entity.SeatInventory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.CabinClassType;
import util.enumeration.FlightSchedulePlanType;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.FareNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.SeatInventoryNotFoundException;
import util.exception.UpdateFlightException;
import util.exception.UpdateFlightSchedulePlanException;

/**
 *
 * @author Ziyue
 */
public class ScheduleManagerModule {
    private FlightSessionBeanRemote flightSessionBeanRemote;
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    private CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    private FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote;
    private SeatInventorySessionBeanRemote seatInventorySessionBeanRemote;
    private SeatSessionBeanRemote seatSessionBeanRemote;
    private FareSessionBeanRemote fareSessionBeanRemote;
    private Employee employee;

    public ScheduleManagerModule() {
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.employee = employee;
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.employee = employee;
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.flightScheduleSessionBeanRemote = flightScheduleSessionBeanRemote;
        this.employee = employee;
    }

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote, SeatInventorySessionBeanRemote seatInventorySessionBeanRemote, SeatSessionBeanRemote seatSessionBeanRemote, FareSessionBeanRemote fareSessionBeanRemote, Employee employee) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightRouteSessionBeanRemote = flightRouteSessionBeanRemote;
        this.aircraftConfigurationSessionBeanRemote = aircraftConfigurationSessionBeanRemote;
        this.cabinClassConfigurationSessionBeanRemote = cabinClassConfigurationSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.flightScheduleSessionBeanRemote = flightScheduleSessionBeanRemote;
        this.seatInventorySessionBeanRemote = seatInventorySessionBeanRemote;
        this.seatSessionBeanRemote = seatSessionBeanRemote;
        this.fareSessionBeanRemote = fareSessionBeanRemote;
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
                    try {
                    doViewFlightSchedulePlanDetails();
                    } catch (FareNotFoundException ex) {
                        System.out.println(ex.getMessage() + "\n");
                    }
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
        
        System.out.print("Enter flight number (eg. 123)> ");
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
    
    public void doViewFlightDetails() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** Flight Reservation System Management :: View Flight Details ***\n");
        
        System.out.print("Enter Flight ID> ");
        Long flightId = sc.nextLong();
        
        try {
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
        
            System.out.println("------------------------");
            System.out.println("1: Update Flight");
            System.out.println("2: Delete Flight");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = sc.nextInt();

            if(response == 1)
            {
                doUpdateFlight(flight);
            }
            else if(response == 2)
            {
                doDeleteFlight(flight);
            }
        } catch (FlightNotFoundException ex) {
            System.out.println("Flight " + flightId + " not found!" + "\n");
        }
    }
    public void doUpdateFlight(Flight flight) {
        Scanner sc = new Scanner(System.in);
        Integer integerInput;
        Long longInput;
        System.out.println("*** Flight Reservation System Management :: View Flight Details :: Update Flight ***\n");
        
        System.out.print("Enter flight number (eg. 123, negative number if no change)> ");
        integerInput = sc.nextInt();
        if(integerInput >= 0) {
            flight.setFlightNumber("ML" + integerInput);
        }
        System.out.print("Enter flight route ID (negative number if no change)> ");
        longInput = sc.nextLong();
        if (longInput >= 0) {
            try {
                flight.setFlightRoute(flightRouteSessionBeanRemote.retrieveFlightRouteById(longInput));
            } catch (FlightRouteNotFoundException ex) {
                System.out.println("Flight route " + longInput + " does not exist!");
            }
        }
        System.out.print("Enter aircraft configuration ID (negative number if no change)> ");
        longInput = sc.nextLong();
        if (longInput >= 0) {
            try {
                flight.setAircraftConfiguration(aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationById(longInput));
            } catch (AircraftConfigurationNotFoundException ex) {
                System.out.println("Aircraft configuration " + longInput + " does not exist!");
            }
        }
        
        try {
            flightSessionBeanRemote.updateFlight(flight);
        } catch (FlightNotFoundException | UpdateFlightException ex) {
            System.out.println("An error has occured: " + ex.getMessage() + "\n");
        }
    }
    public void doDeleteFlight(Flight flight) {
        Scanner sc = new Scanner(System.in);
        //sc.nextLine();
        String input;
        System.out.println("*** Flight Reservation System Management :: View Flight Details :: Delete Flight ***\n");
        System.out.printf("Confirm Delete Flight %s (Flight ID: %d) (Enter 'Y' to Delete)> ", flight.getFlightNumber(), flight.getFlightId());
        input = sc.nextLine().trim();
        
        if(input.equals("Y"))
        {
            try
            {
                flightSessionBeanRemote.deleteFlight(flight.getFlightId());
                System.out.println("Flight deleted successfully!\n");
            }
            catch (FlightNotFoundException | DeleteFlightException ex) 
            {
                System.out.println("An error has occurred while deleting the staff: " + ex.getMessage() + "\n");
            }
        }
        else
        {
            System.out.println("Flight NOT deleted!\n");
        }
    }
    
    public void doCreateFlightSchedulePlan() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Schedule Plan ***\n");

        System.out.print("Enter flight number (eg. ML123)> ");
        String flightNumber = sc.nextLine().trim();
        Flight flight = null;
        try {
            flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNumber);
        } catch (FlightNotFoundException ex) {
            System.out.println("Flight " + flightNumber + ", encountered " + ex.getMessage() + "\n");
            return;
        }
        
        if (flight.getEnableFlight() == Boolean.FALSE)  {
            System.out.println("Flight is disabled and no more flights can be added.");
            return;
        }
        
        System.out.println("1: Create plan with a single schedule");
        System.out.println("2: Create plan with multiple schedules");
        System.out.println("3: Create plan with recurrent schedules");
        System.out.println("4: Create plan with weekly recurrent schedules");
        System.out.println("5: Back\n");

        response = 0;
        System.out.print("> ");
        response = sc.nextInt();

        if (response == 1) {
            try {
                doCreateSingleSchedule(flight);
            } catch (FlightNotFoundException | FlightSchedulePlanNotFoundException |
                     CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else if (response == 2) {
            try {
            doCreateMultipleSchedules(flight);
            } catch (FlightNotFoundException | FlightSchedulePlanNotFoundException |
                     CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else if (response == 3) {
            doCreateRecurrentSchedule(flight);
        } else if (response == 4) {
            doCreateWeeklyRecurrentSchedule(flight);
        }
    }
    
    public void doCreateSingleSchedule(Flight flight) throws FlightNotFoundException, FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        String dateInput = "";
        String timeInput = "";
        Date departureDate = null;
        Date flightTime = null;
        Date arrivalDate = null;
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Schedule Plan :: Single Schedule ***\n");
        
        FlightSchedulePlan flightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.SINGLE);
        List<FlightSchedule> flightSchedules = new ArrayList<>();
        List<Fare> fares = new ArrayList<>();
        List<Long> fareIds = new ArrayList<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        System.out.print("Enter departure time in DD-MM-YYYY HH:MM formt (eg. 13-11-2020 17:00)> ");
        try { 
            dateInput = sc.nextLine().trim();
            departureDate = dateFormat.parse(dateInput);
        } catch (ParseException ex) {
            System.out.println("Date entered not of correct format " + ex.getMessage() + "\n");
        }
        
        System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
        try {
            timeInput = sc.nextLine().trim();
            flightTime = timeFormat.parse(timeInput);
        } catch (ParseException ex) {
            System.out.println("Time entered not of correct format " + ex.getMessage() + "\n");
        }
        
        double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();
        
        Integer timeZoneHours = (int) timeZoneDiff;
        Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(flightTime);
        Integer flightHours = calendar.get(Calendar.HOUR_OF_DAY);
        Integer flightMinutes = calendar.get(Calendar.MINUTE);
        
        
        calendar.setTime(departureDate);
        calendar.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
        calendar.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
        arrivalDate = calendar.getTime();
        
        try {
            flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
        } catch (FlightScheduleOverlapException ex) {
            System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
            return;
        }
        
        FlightSchedule flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
        flightSchedules.add(flightSchedule);
        //sessionbean to create schedule. pass in flight, the 3 dates, rmb check overlap
        
        
        
        System.out.print("Please enter fare details for the following cabin classes ");
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare;
        for (CabinClassConfiguration cabinClassConfiguration : cabinClassConfigurations) {
            if (cabinClassConfiguration.getCabinClassType() == CabinClassType.FIRST_CLASS) {
                
                while (true) {
                    System.out.println("Enter Fare details for First Class(F)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. F001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.BUSINESS_CLASS) {
                
                while (true) {
                    System.out.println("Enter Fare details for Business Class(J)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. J001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.PREMIUM_ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for Premium Economy Class(W)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. W001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for Economy Class(Y)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. Y001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }
            
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        //System.out.println("FlightSchedulePlan " + flightSchedulePlanId + "created successfully!");
        for (FlightSchedule flightScheduleToCreate : flightSchedules) {

            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightScheduleToCreate, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);

            for (CabinClassConfiguration cabinClassConfigurationForSeatInventory : cabinClassConfigurations) {
                SeatInventory seatInventory = new SeatInventory(cabinClassConfigurationForSeatInventory.getCabinMaximumSeatCapacity());
                Long seatInventoryId = seatInventorySessionBeanRemote.createNewSeatInventory(seatInventory, cabinClassConfigurationForSeatInventory.getCabinClassConfigurationId(), flightScheduleId);

                Integer rows = cabinClassConfigurationForSeatInventory.getNumberOfRows();
                Integer columns = cabinClassConfigurationForSeatInventory.getNumberOfSeatsAbreast();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        char alphabet = (char) ('A' + j);
                        String rowAlphabet = Character.toString(alphabet);
                        Seat seat = new Seat(i + 1, rowAlphabet);
                        seatSessionBeanRemote.createNewSeat(seat, seatInventoryId);
                    }
                }

                System.out.println("Seat Inventory " + seatInventoryId + " created for Flight Schedule " + flightScheduleId + " under flight schedule plan " + flightSchedulePlanId);

            }                
        }

        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");

        if (flight.getComplementaryReturnFlight() != null) {
            System.out.print("Create complementary flight schedule? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                FlightSchedulePlan returnFlightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.SINGLE);
                System.out.print("Enter return flight number (eg. ML123)> ");
                String returnFlightNumber = sc.nextLine().trim();
                Flight returnFlight = null;
                try {
                    returnFlight = flightSessionBeanRemote.retrieveFlightByFlightNumber(returnFlightNumber);
                } catch (FlightNotFoundException ex) {
                    System.out.println("Flight " + returnFlightNumber + ", encountered " + ex.getMessage() + "\n");
                } 
                
                if (returnFlight.getEnableFlight() == Boolean.FALSE)  {
                    System.out.println("Flight is disabled and no more flights can be added.");
                    return;
                }
                
                System.out.print("Enter layover duration in HH MM (eg. 02 00)> ");
                Integer layoverHours = sc.nextInt();
                Integer layoverMinutes = sc.nextInt();
                sc.nextLine();
                calendar.setTime(arrivalDate);
                calendar.add(Calendar.HOUR_OF_DAY, layoverHours);
                calendar.add(Calendar.MINUTE, layoverMinutes);
                departureDate = calendar.getTime();

                calendar.setTime(departureDate);
                calendar.add(Calendar.HOUR_OF_DAY, -timeZoneHours + flightHours);
                calendar.add(Calendar.MINUTE, -timeZoneMinutes + flightMinutes);
                arrivalDate = calendar.getTime();


                List<FlightSchedule> returnFlightSchedules = new ArrayList<>();
                flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
                returnFlightSchedules.add(flightSchedule);

                //sessionbean to create RETURN schedule. pass in flight, the 3 dates, rmb check overlap

                Long returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewComplementaryReturnFlightSchedulePlan(returnFlightSchedulePlan, flightSchedulePlanId, returnFlight.getFlightId());
                //System.out.println("FlightSchedulePlan " + flightSchedulePlanId + "created successfully!");
                for (FlightSchedule returnFlightSchedule : returnFlightSchedules) {

                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);

                    for (CabinClassConfiguration cabinClassConfigurationForSeatInventory : cabinClassConfigurations) {
                        SeatInventory seatInventory = new SeatInventory(cabinClassConfigurationForSeatInventory.getCabinMaximumSeatCapacity());
                        Long seatInventoryId = seatInventorySessionBeanRemote.createNewSeatInventory(seatInventory, cabinClassConfigurationForSeatInventory.getCabinClassConfigurationId(), returnFlightScheduleId);

                        Integer rows = cabinClassConfigurationForSeatInventory.getNumberOfRows();
                        Integer columns = cabinClassConfigurationForSeatInventory.getNumberOfSeatsAbreast();

                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < columns; j++) {
                                char alphabet = (char) ('A' + j);
                                String rowAlphabet = Character.toString(alphabet);
                                Seat seat = new Seat(i + 1, rowAlphabet);
                                seatSessionBeanRemote.createNewSeat(seat, seatInventoryId);
                            }
                        }

                        System.out.println("Seat Inventory " + seatInventoryId + " created for Return Flight Schedule " + returnFlightScheduleId + " under flight schedule plan " + returnFlightSchedulePlanId);

                    }                
                }

                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }

                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");

            }
        }        
    }
    
    public void doCreateMultipleSchedules(Flight flight) throws FlightSchedulePlanNotFoundException, SeatInventoryNotFoundException, CabinClassConfigurationNotFoundException, FlightNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        String dateInput = "";
        String timeInput = "";
        Date departureDate = null;
        Date flightTime = null;
        Date arrivalDate = null;
        
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Schedule Plan :: Multiple Schedule ***\n");
        
        FlightSchedulePlan flightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE);
        List<FlightSchedule> flightSchedules = new ArrayList<>();
        List<Fare> fares = new ArrayList<>();
        List<Long> fareIds = new ArrayList<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        
        Integer timeZoneHours;
        Integer timeZoneMinutes;
        Integer flightHours;
        Integer flightMinutes;
        
        while(true) {
        
            System.out.print("Enter departure time in DD-MM-YYYY HH:MM formt (eg. 13-11-2020 17:00)> ");
            try { 
                dateInput = sc.nextLine().trim();
                departureDate = dateFormat.parse(dateInput);
            } catch (ParseException ex) {
                System.out.println("Date entered not of correct format " + ex.getMessage() + "\n");
            }

            System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
            try {
                timeInput = sc.nextLine().trim();
                flightTime = timeFormat.parse(timeInput);
            } catch (ParseException ex) {
                System.out.println("Time entered not of correct format " + ex.getMessage() + "\n");
            }

            double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();

            timeZoneHours = (int) timeZoneDiff;
            timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
            calendar.setTime(flightTime);
            flightHours = calendar.get(Calendar.HOUR_OF_DAY);
            flightMinutes = calendar.get(Calendar.MINUTE);


            calendar.setTime(departureDate);
            calendar.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
            calendar.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
            arrivalDate = calendar.getTime();

            try {
                flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
            } catch (FlightScheduleOverlapException ex) {
                System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                return;
            }

            FlightSchedule flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
            flightSchedules.add(flightSchedule);
            //sessionbean to create schedule. pass in flight, the 3 dates, rmb check overlap
            System.out.print("Add another flight schedule? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                continue;
            } else {
                break;
            }
        }
        
        System.out.print("Please enter fare details for the following cabin classes ");
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare;
        for (CabinClassConfiguration cabinClassConfiguration : cabinClassConfigurations) {
            if (cabinClassConfiguration.getCabinClassType() == CabinClassType.FIRST_CLASS) {
                
                while (true) {
                    System.out.println("Enter Fare details for First Class(F)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. F001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.BUSINESS_CLASS) {
                
                while (true) {
                    System.out.println("Enter Fare details for Business Class(J)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. J001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.PREMIUM_ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for Premium Economy Class(W)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. W001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for Economy Class(Y)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. Y001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }
        
            
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        //System.out.println("FlightSchedulePlan " + flightSchedulePlanId + "created successfully!");
        for (FlightSchedule flightScheduleToCreate : flightSchedules) {

            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightScheduleToCreate, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);

            for (CabinClassConfiguration cabinClassConfigurationForSeatInventory : cabinClassConfigurations) {
                SeatInventory seatInventory = new SeatInventory(cabinClassConfigurationForSeatInventory.getCabinMaximumSeatCapacity());
                Long seatInventoryId = seatInventorySessionBeanRemote.createNewSeatInventory(seatInventory, cabinClassConfigurationForSeatInventory.getCabinClassConfigurationId(), flightScheduleId);

                Integer rows = cabinClassConfigurationForSeatInventory.getNumberOfRows();
                Integer columns = cabinClassConfigurationForSeatInventory.getNumberOfSeatsAbreast();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        char alphabet = (char) ('A' + j);
                        String rowAlphabet = Character.toString(alphabet);
                        Seat seat = new Seat(i + 1, rowAlphabet);
                        seatSessionBeanRemote.createNewSeat(seat, seatInventoryId);
                    }
                }

                System.out.println("Seat Inventory " + seatInventoryId + " created for Flight Schedule " + flightScheduleId + " under flight schedule plan " + flightSchedulePlanId);

            }                
        }

        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");

        if (flight.getComplementaryReturnFlight() != null) {
            System.out.print("Create complementary flight schedule? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                FlightSchedulePlan returnFlightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE);
                System.out.print("Enter return flight number (eg. ML123)> ");
                String returnFlightNumber = sc.nextLine().trim();
                Flight returnFlight = null;
                try {
                    returnFlight = flightSessionBeanRemote.retrieveFlightByFlightNumber(returnFlightNumber);
                } catch (FlightNotFoundException ex) {
                    System.out.println("Flight " + returnFlightNumber + ", encountered " + ex.getMessage() + "\n");
                } 
                
                if (returnFlight.getEnableFlight() == Boolean.FALSE)  {
                    System.out.println("Flight is disabled and no more flights can be added.");
                    return;
                }
                
                System.out.print("Enter layover duration in HH MM (eg. 02 00)> ");
                Integer layoverHours = sc.nextInt();
                Integer layoverMinutes = sc.nextInt();
                sc.nextLine();
                
                List<FlightSchedule> returnFlightSchedules = new ArrayList<>();
                
                for (FlightSchedule originalFlightSchedule : flightSchedules) {
                    arrivalDate = originalFlightSchedule.getArrivalDateTime();
                    calendar.setTime(arrivalDate);
                    calendar.add(Calendar.HOUR_OF_DAY, layoverHours);
                    calendar.add(Calendar.MINUTE, layoverMinutes);
                    departureDate = calendar.getTime();

                    flightTime = originalFlightSchedule.getEstimatedFlightDuration();
                    calendar.setTime(flightTime);
                    flightHours = calendar.get(Calendar.HOUR_OF_DAY);
                    flightMinutes = calendar.get(Calendar.MINUTE);
                    
                    Date originalDepartureDate = originalFlightSchedule.getDepartureDateTime();
                    calendar.setTime(originalDepartureDate);
                    calendar.add(Calendar.HOUR_OF_DAY, -timeZoneHours + flightHours);
                    calendar.add(Calendar.MINUTE, -timeZoneMinutes + flightMinutes);
                    arrivalDate = calendar.getTime();                   
                    
                    FlightSchedule flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
                    returnFlightSchedules.add(flightSchedule);
                }

                    //sessionbean to create RETURN schedule. pass in flight, the 3 dates, rmb check overlap

                Long returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewComplementaryReturnFlightSchedulePlan(returnFlightSchedulePlan, flightSchedulePlanId, returnFlight.getFlightId());
                //System.out.println("FlightSchedulePlan " + flightSchedulePlanId + "created successfully!");
                for (FlightSchedule returnFlightSchedule : returnFlightSchedules) {

                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);

                    for (CabinClassConfiguration cabinClassConfigurationForSeatInventory : cabinClassConfigurations) {
                        SeatInventory seatInventory = new SeatInventory(cabinClassConfigurationForSeatInventory.getCabinMaximumSeatCapacity());
                        Long seatInventoryId = seatInventorySessionBeanRemote.createNewSeatInventory(seatInventory, cabinClassConfigurationForSeatInventory.getCabinClassConfigurationId(), returnFlightScheduleId);

                        Integer rows = cabinClassConfigurationForSeatInventory.getNumberOfRows();
                        Integer columns = cabinClassConfigurationForSeatInventory.getNumberOfSeatsAbreast();

                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < columns; j++) {
                                char alphabet = (char) ('A' + j);
                                String rowAlphabet = Character.toString(alphabet);
                                Seat seat = new Seat(i + 1, rowAlphabet);
                                seatSessionBeanRemote.createNewSeat(seat, seatInventoryId);
                            }
                        }

                        System.out.println("Seat Inventory " + seatInventoryId + " created for Return Flight Schedule " + returnFlightScheduleId + " under flight schedule plan " + returnFlightSchedulePlanId);

                    }                
                }
                

                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }

                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");

            }
        }        
    }
    
    public void doCreateRecurrentSchedule(Flight flight) {
        System.out.println("hehe");
    }
    
    public void doCreateWeeklyRecurrentSchedule(Flight flight) {
        System.out.println("hehe");
    }
    
    
    public void doViewAllFlightSchedulePlans() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View All Flight Schedule Plans ***\n");
        
        List<FlightSchedulePlan> flightSchedulePlans =  flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
        System.out.printf("%20s%25s%28s%28s%20s\n", "Flight Number", "Flight Schedule Plan ID", "Flight Schedule Plan Type", "End Date(Recurrent)", "n Days(Recurrent)");

//        System.out.printf("%20s%20s%20s%28s\n", "Flight Number", "Flight ID", "Flight Route ID", "Aircraft Configuration ID");
        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            
            if (flightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_WEEK)) {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), flightSchedulePlan.getEndDate(), 7);
            } else if (flightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_DAY)) {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), flightSchedulePlan.getEndDate(), flightSchedulePlan.getnDays());
            } else {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), "N/A", "N/A");       
            }
            
            if (flightSchedulePlan.getComplementaryReturnFlightSchedulePlan() != null) {
                FlightSchedulePlan returnFlightSchedulePlan = flightSchedulePlan.getComplementaryReturnFlightSchedulePlan();
                if (returnFlightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_WEEK)) {
                    System.out.printf("%20s%25s%28s%28s%20s\n", returnFlightSchedulePlan.getFlight().getFlightNumber(), returnFlightSchedulePlan.getFlightSchedulePlanId(), returnFlightSchedulePlan.getFlightSchedulePlanType(), returnFlightSchedulePlan.getEndDate(), 7);
                } else if (returnFlightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_DAY)) {
                    System.out.printf("%20s%25s%28s%28s%20s\n", returnFlightSchedulePlan.getFlight().getFlightNumber(), returnFlightSchedulePlan.getFlightSchedulePlanId(), returnFlightSchedulePlan.getFlightSchedulePlanType(), returnFlightSchedulePlan.getEndDate(), returnFlightSchedulePlan.getnDays());
                } else {
                    System.out.printf("%20s%25s%28s%28s%20s\n", returnFlightSchedulePlan.getFlight().getFlightNumber(), returnFlightSchedulePlan.getFlightSchedulePlanId(), returnFlightSchedulePlan.getFlightSchedulePlanType(), "N/A", "N/A");       
                }
            }
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doViewFlightSchedulePlanDetails() throws FareNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** Flight Reservation System Management :: View Flight Schedule Plan Details ***\n");
        
        System.out.print("Enter Flight Schedule Plan ID> ");
        Long flightSchedulePlanId = sc.nextLong();
        
        try {
            FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(flightSchedulePlanId);
            
            System.out.println("Flight Number: " + flightSchedulePlan.getFlight().getFlightNumber());
            System.out.println("Flight Schedule Plan Type :" + flightSchedulePlan.getFlightSchedulePlanType());
            if (flightSchedulePlan.getEndDate() != null) {
                System.out.println("End Date: " + flightSchedulePlan.getEndDate());
            } else {
                System.out.println("End date : N/A" );
            }
            
            if (flightSchedulePlan.getnDays() != null) {
                System.out.println("Recurs every n days: " + flightSchedulePlan.getnDays());
            } else {
                System.out.println("Recurs every n days : N/A" );
            }
            
            String origin = flightSchedulePlan.getFlight().getFlightRoute().getOriginAirport().getIataCode();
            String destination = flightSchedulePlan.getFlight().getFlightRoute().getDestinationAirport().getIataCode();
            
            System.out.println("Origin-Destination: " + origin + "-" + destination);
            System.out.println("");
            System.out.printf("%15s%20s%15s\n", "Fare Id", "Fare Basis Code", "Fare Amount");
            
            for (Fare fare : flightSchedulePlan.getFares()) {
                Long fareId = fare.getFareId();
                Fare fareToPrint = fareSessionBeanRemote.retrieveFareByFareId(fareId);
                System.out.printf("%15s%20s%15s\n", fareId, fareToPrint.getFareBasisCode(), "$" + fareToPrint.getFareAmount());
            }
            System.out.println("");
            System.out.printf("%20s%32s%32s%20s\n", "Flight Schedule Id", "Departure Date", "Arrival Date", "Flight Duration");
            
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            //System.out.println(sdf.format(date));  
            
            for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
                Long flightScheduleId = flightSchedule.getFlightScheduleId();
                FlightSchedule flightScheduleToPrint = flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId);
                System.out.printf("%20s%32s%32s%20s\n", flightScheduleId, flightScheduleToPrint.getDepartureDateTime(), flightScheduleToPrint.getArrivalDateTime(), format.format(flightScheduleToPrint.getEstimatedFlightDuration()));
            }
                        
            System.out.println("------------------------");
            System.out.println("1: Update Flight Schedule Plan");
            System.out.println("2: Delete Flight Schedule Plan");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = sc.nextInt();

            if(response == 1)
            {
                doUpdateFlightSchedulePlan(flightSchedulePlan);
            }
            else if(response == 2)
            {
                doDeleteFlightSchedulePlan(flightSchedulePlan);
            }
        } catch (FlightSchedulePlanNotFoundException ex) {
            System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " not found!" + "\n");
        }
    }
    
    public void doUpdateFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) throws FlightSchedulePlanNotFoundException {
        Scanner sc = new Scanner(System.in);
        Integer integerInput;
        Long longInput;
        System.out.println("*** Flight Reservation System Management :: View Flight Schedule Plan Details :: Update Flight Schedule Plan ***\n");
        
        if (flightSchedulePlan.getnDays() != null) {  
           System.out.print("Enter recurring n days (0 or negative number if no change)> ");
           integerInput = sc.nextInt();
           sc.nextLine();
           if(integerInput > 0) {
               flightSchedulePlan.setnDays(integerInput);
           }
        }
        if (flightSchedulePlan.getEndDate() != null) {   
            System.out.print("Edit recurrence end date? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                System.out.print("Enter recurrence end date in DD-MM-YYYY (eg. 23-04-2020)> ");
                String dateInput = sc.nextLine().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = dateFormat.parse(dateInput);
                    flightSchedulePlan.setEndDate(date);
                    for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
                        if (flightSchedule.getDepartureDateTime().getTime() > date.getTime()) {
                            for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
                                if (seatInventory.getReservedSeats() > 0) {
                                    System.out.println("Cannot be update, flight tickets have been sold for this schedule!");
                                    return;
                                }
                            }
                            flightScheduleSessionBeanRemote.deleteFlightSchedule(flightSchedule.getFlightScheduleId());
                        }
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage() + "\n");
                }
            }
        }
        
        while(true) {
            System.out.print("Enter Fare ID to Change (negative number if no change)> ");
            longInput = sc.nextLong();
            if (longInput >= 0) {
                System.out.print("Enter new Fare ID)> ");
                Long fareId = sc.nextLong();
                sc.nextLine();
                try {
                    flightSchedulePlan.getFares().remove(fareSessionBeanRemote.retrieveFareByFareId(longInput));
                    flightSchedulePlan.getFares().add(fareSessionBeanRemote.retrieveFareByFareId(fareId));
                } catch (FareNotFoundException ex) {
                    System.out.println("Fare " + longInput + "/" + fareId + " does not exist!");
                }
            } else {
                break;
            }
        }
        try {
            flightSchedulePlanSessionBeanRemote.updateFlightSchedulePlan(flightSchedulePlan);
        } catch (FlightSchedulePlanNotFoundException | UpdateFlightSchedulePlanException ex) {
            System.out.println("Flight Schedule Plan could not be updated, " + ex.getMessage() + "\n");
        }
        
        System.out.println("Flight Schedule Plan " + flightSchedulePlan.getFlightSchedulePlanId() + " updated successfully!");
    }
    
    public void doDeleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) {
        System.out.println("hehe");
    }
}
