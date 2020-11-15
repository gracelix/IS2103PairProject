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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.enumeration.CabinClassType;
import util.enumeration.FlightSchedulePlanType;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.DeleteFlightException;
import util.exception.DeleteFlightSchedulePlanException;
import util.exception.FareNotFoundException;
import util.exception.FlightAlreadyExistsException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleOverlapException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidDateFormatException;
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

    public ScheduleManagerModule(FlightSessionBeanRemote flightSessionBeanRemote, 
            FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, 
            AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, 
            CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, 
            FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, 
            FlightScheduleSessionBeanRemote flightScheduleSessionBeanRemote, 
            SeatInventorySessionBeanRemote seatInventorySessionBeanRemote,
            SeatSessionBeanRemote seatSessionBeanRemote, 
            FareSessionBeanRemote fareSessionBeanRemote,
            Employee employee) {
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
        
            System.out.println("\n*** Flight Reservation System Management :: Schedule Manager ***\n");
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
                    } catch (FlightNotFoundException | AircraftConfigurationNotFoundException | FlightRouteNotFoundException | FlightAlreadyExistsException ex) {
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
    
    public void doCreateFlight() throws FlightNotFoundException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException, FlightAlreadyExistsException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Flight Reservation System Management :: Create Flight ***\n");
        
        System.out.print("Enter flight number (eg. ML123)> ");
        String flightNumber = sc.nextLine().trim();
        System.out.print("Enter flight route ID> ");
        Long flightRouteId = sc.nextLong();
        System.out.print("Enter aircraft configuration ID> ");
        Long aircraftConfigurationID = sc.nextLong();
        
        Flight flight = new Flight(flightNumber);
        Long flightId;
        
        try {
            flightId = flightSessionBeanRemote.createNewFlight(flight, flightRouteId, aircraftConfigurationID);
        } catch (FlightAlreadyExistsException ex) {
            throw new FlightAlreadyExistsException(ex.getMessage());
        }
        
        System.out.println("Flight " + flightId + " of flight number " + flightNumber + " created successfully!\n");
        
        if (flightRouteSessionBeanRemote.retrieveFlightRouteById(flightRouteId).getComplementaryFlightRoute() != null) {
            sc.nextLine();
            System.out.print("Create complementary flight? (Enter Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                System.out.print("Enter return flight number> ");
                String returnFlightNumber = sc.nextLine().trim();
                Flight complementaryFlight = new Flight(returnFlightNumber);
                Long returnFlightRouteId = flightRouteSessionBeanRemote.retrieveFlightRouteById(flightRouteId).getComplementaryFlightRoute().getFlightRouteId();
                Long returnFlightId = flightSessionBeanRemote.createNewComplementaryReturnFlight(complementaryFlight, flightId, returnFlightRouteId, aircraftConfigurationID);
                System.out.println("Return Flight " + returnFlightId + " of flight number " + returnFlightNumber + " created successfully!\n");
            }
        }
    }
    
    
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
        
        // display a list of flights
        List<Flight> flights = flightSessionBeanRemote.retrieveAllFlights();
        System.out.printf("%20s%20s%20s%28s\n", "Flight Number", "Flight ID", "Flight Route ID", "Aircraft Configuration ID");
        for (Flight flight : flights) {
            System.out.printf("%20s%20s%20s%28s\n", flight.getFlightNumber(), flight.getFlightId(), flight.getFlightRoute().getFlightRouteId(), flight.getAircraftConfiguration().getAircraftConfigurationId());
        }
        
        System.out.print("Enter Flight ID to view> ");
        Long flightId = sc.nextLong();
        
        try {
            Flight flight = flightSessionBeanRemote.retrieveFlightById(flightId);
            
            System.out.printf("%15s%18s%25s%15s%15s\n", "Flight Number", "Flight Route ID", "Origin-Destination", "Cabin Classes", "Total Seats");
            String odPair = flight.getFlightRoute().getOriginAirport().getIataCode() + "-" + flight.getFlightRoute().getDestinationAirport().getIataCode();
            
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

            if(response == 1) {
                doUpdateFlight(flight);
            } else if(response == 2) {
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
                System.out.println("An error has occurred while deleting the flight: " + ex.getMessage() + "\n");
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
        
        System.out.println("\n1: Create plan with a single schedule");
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
                     CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException | InvalidDateFormatException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else if (response == 2) {
            try {
                doCreateMultipleSchedules(flight);
            } catch (FlightNotFoundException | FlightSchedulePlanNotFoundException |
                CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException | InvalidDateFormatException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else if (response == 3) {
            try {
                doCreateRecurrentSchedule(flight);
            } catch (FlightNotFoundException | FlightSchedulePlanNotFoundException |
                CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException | InvalidDateFormatException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else if (response == 4) {
            try {
                doCreateWeeklyRecurrentSchedule(flight);
            } catch (FlightNotFoundException | FlightSchedulePlanNotFoundException |
                CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException | InvalidDateFormatException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        }
    }
    
    public void doCreateSingleSchedule(Flight flight) throws FlightNotFoundException, FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException, InvalidDateFormatException {
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
        
        System.out.print("Enter departure time in DD-MM-YYYY HH:MM format in departure country timezone (eg. 13-11-2020 17:00)> ");
        try { 
            dateInput = sc.nextLine().trim();
            departureDate = dateFormat.parse(dateInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
        try {
            timeInput = sc.nextLine().trim();
            flightTime = timeFormat.parse(timeInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
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
        
        System.out.println("Please enter fare details for the following cabin classes");
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        System.out.println("------------------------");
        System.out.println("\n");
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare = null;
        enterFareDetails(cabinClassConfigurations, fare, fares);
          
        // persist everything
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        
        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }
        System.out.println("\n");

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");
        
        for (FlightSchedule flightScheduleToCreate : flightSchedules) {

            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightScheduleToCreate, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);
            
            for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId).getSeatInventories()) {
                System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + flightScheduleId);
            }
            System.out.println("\n");  
        }

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
                
                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }
                System.out.println("\n");
                
                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");
                System.out.println("\n");
                
                for (FlightSchedule returnFlightSchedule : returnFlightSchedules) {

                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);
                    
                    for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(returnFlightScheduleId).getSeatInventories()) {
                        System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + returnFlightScheduleId);
                    }
                    System.out.println("\n");
                }
            }
        }        
    }
    
    public void doCreateMultipleSchedules(Flight flight) throws FlightSchedulePlanNotFoundException, SeatInventoryNotFoundException, CabinClassConfigurationNotFoundException, FlightNotFoundException, InvalidDateFormatException {
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
        
            System.out.print("Enter departure time in DD-MM-YYYY HH:MM format in departure country timezone (eg. 13-11-2020 17:00)> ");
            try { 
                dateInput = sc.nextLine().trim();
                departureDate = dateFormat.parse(dateInput);
            } catch (ParseException ex) {
                throw new InvalidDateFormatException("Invalid date/time format!");
            }

            System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
            try {
                timeInput = sc.nextLine().trim();
                flightTime = timeFormat.parse(timeInput);
            } catch (ParseException ex) {
                throw new InvalidDateFormatException("Invalid date/time format!");
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
            System.out.println("------------------------\n");
            if (responseString.equals("Y")) {
                continue;
            } else {
                break;
            }
        }
        
        System.out.println("Please enter fare details for the following cabin classes");
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
        System.out.println("\n");
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare = null;
        enterFareDetails(cabinClassConfigurations, fare, fares);
        
        // PERSIST FLIGHTSCHEDULEPLAN 
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        
        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }
        System.out.println("\n");

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");
        
        for (FlightSchedule flightScheduleToCreate : flightSchedules) {

            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightScheduleToCreate, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);

            for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId).getSeatInventories()) {
                System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + flightScheduleId);
            }
            System.out.println("\n");
        }

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
                
                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }
                System.out.println("\n");

                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");
                
                for (FlightSchedule returnFlightSchedule : returnFlightSchedules) {

                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);

                    for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(returnFlightScheduleId).getSeatInventories()) {
                        System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + returnFlightScheduleId);
                    }
                    System.out.println("\n");
                }
                
            }
        }        
    }
    
    public void doCreateRecurrentSchedule(Flight flight) throws FlightSchedulePlanNotFoundException, SeatInventoryNotFoundException, CabinClassConfigurationNotFoundException, FlightNotFoundException, InvalidDateFormatException {
        Scanner sc = new Scanner(System.in);
        String dateInput = "";
        String timeInput = "";
        Date startDate = null;
        Date flightTime = null;
        Integer n = 0;
        Date endDate = null;
        
        Date departureDate = null;
        Date arrivalDate = null;
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Schedule Plan :: Recurrent Schedule For N Days ***\n");
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        System.out.print("Enter start date of recurrent flight schedule in DD-MM-YYYY HH:MM format (eg. 13-11-2020 17:00)> ");
        try { 
            dateInput = sc.nextLine().trim();
            startDate = dateTimeFormat.parse(dateInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
        try {
            timeInput = sc.nextLine().trim();
            flightTime = timeFormat.parse(timeInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        System.out.print("Recurrent flight schedule repeats every _n_ days? Enter an integer for n> ");
        n = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter end date of recurrent flight schedule in DD-MM-YYYY HH:MM format (eg. 13-11-2020 17:00)> ");
        try {
            dateInput = sc.nextLine().trim();
            endDate = dateFormat.parse(dateInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        FlightSchedulePlan flightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_DAY, endDate, n);
        List<Fare> fares = new ArrayList<>();
        List<Long> fareIds = new ArrayList<>();
                
        System.out.println("\nPlease enter fare details for the following cabin classes\n");
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare = null;
        enterFareDetails(cabinClassConfigurations, fare, fares);
          
        // PERSIST FLIGHTSCHEDULEPLAN
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        
        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");
        
        List<Date> arrivalDates = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);
        departureDate = calendar1.getTime();
        
        while (true) {
            if (departureDate.after(endDate)) {
                break;
            }
            
            calendar1.add(Calendar.DAY_OF_MONTH, n);
            departureDate = calendar1.getTime();
            
            // CALCULATE ARRIVAL DAY
            double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();

            Integer timeZoneHours = (int) timeZoneDiff;
            Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(flightTime);
            Integer flightHours = calendar2.get(Calendar.HOUR_OF_DAY);
            Integer flightMinutes = calendar2.get(Calendar.MINUTE);
            
            calendar2.setTime(departureDate);
            calendar2.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
            calendar2.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
            arrivalDate = calendar2.getTime();
            
            arrivalDates.add(arrivalDate);
            
            try {
                flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
            } catch (FlightScheduleOverlapException ex) {
                System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                return;
            }
            
            FlightSchedule flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightSchedule, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);

            for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId).getSeatInventories()) {
                System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + flightScheduleId);
            }
            System.out.println("\n");
            
        }
        
        // COMPLEMENTARY FSP
        if (flight.getComplementaryReturnFlight() != null) {
            System.out.print("Create complementary flight schedule? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                FlightSchedulePlan returnFlightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_DAY, endDate, n);
                
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
                

                Long returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewComplementaryReturnFlightSchedulePlan(returnFlightSchedulePlan, flightSchedulePlanId, returnFlight.getFlightId());
                
                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }

                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");
                
                for (Date date : arrivalDates) {
                    calendar1.setTime(date);
                    calendar1.add(Calendar.HOUR_OF_DAY, layoverHours);
                    calendar1.add(Calendar.MINUTE, layoverMinutes);
                    departureDate = calendar1.getTime();

                    double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();

                    Integer timeZoneHours = (int) timeZoneDiff;
                    Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(flightTime);
                    Integer flightHours = calendar2.get(Calendar.HOUR_OF_DAY);
                    Integer flightMinutes = calendar2.get(Calendar.MINUTE);

                    calendar2.setTime(departureDate);
                    calendar2.add(Calendar.HOUR_OF_DAY, -timeZoneHours + flightHours);
                    calendar2.add(Calendar.MINUTE, -timeZoneMinutes + flightMinutes);
                    arrivalDate = calendar2.getTime();

                    try {
                        flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
                    } catch (FlightScheduleOverlapException ex) {
                        System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                        return;
                    }

                    FlightSchedule returnFlightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);

                    for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(returnFlightScheduleId).getSeatInventories()) {
                        System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + returnFlightScheduleId);
                    }
                    System.out.println("\n");

                } 
            }
        }
    }
    
    public void doCreateWeeklyRecurrentSchedule(Flight flight) throws InvalidDateFormatException, FlightSchedulePlanNotFoundException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException, FlightNotFoundException {
        Scanner sc = new Scanner(System.in);
        String dateInput = "";
        String timeInput = "";
        Date startDate = null;
        Date flightTime = null;
        Date endDate = null;
        
        Date departureDate = null;
        Date arrivalDate = null;
        
        System.out.println("*** Flight Reservation System Management :: Create Flight Schedule Plan :: Recurrent WEEKLY Schedule ***\n");
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        System.out.print("Enter start date of WEEKLY recurrent flight schedule in DD-MM-YYYY HH:MM format (eg. 13-11-2020 17:00)> ");
        try { 
            dateInput = sc.nextLine().trim();
            startDate = dateTimeFormat.parse(dateInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        System.out.print("Enter flight duration in HH:MM format (eg. 05:30)> ");
        try {
            timeInput = sc.nextLine().trim();
            flightTime = timeFormat.parse(timeInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        System.out.print("Enter the day the flight schedule begins on (eg. Monday)> ");
        String day = sc.nextLine().trim();
        
        System.out.print("Enter end date of recurrent flight schedule in DD-MM-YYYY HH:MM format (eg. 13-11-2020 17:00)> ");
        try {
            dateInput = sc.nextLine().trim();
            endDate = dateFormat.parse(dateInput);
        } catch (ParseException ex) {
            throw new InvalidDateFormatException("Invalid date/time format!");
        }
        
        FlightSchedulePlan flightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, endDate);
        List<Fare> fares = new ArrayList<>();
        List<Long> fareIds = new ArrayList<>();
                
        System.out.println("\nPlease enter fare details for the following cabin classes\n");
        
        List<CabinClassConfiguration> cabinClassConfigurations = cabinClassConfigurationSessionBeanRemote.retrieveCabinClassConfigurationsByAircraftConfigurationId(flight.getAircraftConfiguration().getAircraftConfigurationId());
        Fare fare = null;
        enterFareDetails(cabinClassConfigurations, fare, fares);
        
        // PERSIST FLIGHTSCHEDULEPLAN
        Long flightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewFlightSchedulePlan(flightSchedulePlan, flight.getFlightId());
        
        for (Fare fareToCreate : fares) {
            Long fareId = fareSessionBeanRemote.createNewFare(fareToCreate, flightSchedulePlanId);
            fareIds.add(fareId);
            System.out.println("Fare " + fareId + " created under Flight Schedule Plan " + flightSchedulePlanId);
        }

        System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " created successfully!" + "\n");
        
        List<Date> arrivalDates = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);
        while (true) {
            if (day.toLowerCase().equals("monday") && calendar1.get(Calendar.DAY_OF_WEEK) == 2) {
                break;
            } else if (day.toLowerCase().equals("tuesday") && calendar1.get(Calendar.DAY_OF_WEEK) == 3) {
                break;
            } else if (day.toLowerCase().equals("wednesday") && calendar1.get(Calendar.DAY_OF_WEEK) == 4) {
                break;
            } else if (day.toLowerCase().equals("thursday") && calendar1.get(Calendar.DAY_OF_WEEK) == 5) {
                break;
            } else if (day.toLowerCase().equals("friday") && calendar1.get(Calendar.DAY_OF_WEEK) == 6) {
                break;
            } else if (day.toLowerCase().equals("saturday") && calendar1.get(Calendar.DAY_OF_WEEK) == 7) {
                break;
            } else if (day.toLowerCase().equals("sunday") && calendar1.get(Calendar.DAY_OF_WEEK) == 1) {
                break;
            } else {
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        
        departureDate = calendar1.getTime();
        
        while (true) {
            
            departureDate = calendar1.getTime();
            calendar1.add(Calendar.DAY_OF_MONTH, 7);
            
            if (departureDate.after(endDate)) {
                break;
            }
            
            // CALCULATE ARRIVAL DAY
            double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();

            Integer timeZoneHours = (int) timeZoneDiff;
            Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(flightTime);
            Integer flightHours = calendar2.get(Calendar.HOUR_OF_DAY);
            Integer flightMinutes = calendar2.get(Calendar.MINUTE);
            
            calendar2.setTime(departureDate);
            calendar2.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
            calendar2.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
            arrivalDate = calendar2.getTime();
            
            arrivalDates.add(arrivalDate);
            
            try {
                flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
            } catch (FlightScheduleOverlapException ex) {
                System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                return;
            }
            
            FlightSchedule flightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
            Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightSchedule, flightSchedulePlanId);
            System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanId);

            for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId).getSeatInventories()) {
                System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + flightScheduleId);
            }
            System.out.println("\n");
            
        }
        
        // COMPLEMENTARY FSP
        if (flight.getComplementaryReturnFlight() != null) {
            System.out.print("Create complementary flight schedule? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                FlightSchedulePlan returnFlightSchedulePlan = new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, endDate);
                
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
                

                Long returnFlightSchedulePlanId = flightSchedulePlanSessionBeanRemote.createNewComplementaryReturnFlightSchedulePlan(returnFlightSchedulePlan, flightSchedulePlanId, returnFlight.getFlightId());
                
                for (Long returnFareId : fareIds) {
                    try {
                        fareSessionBeanRemote.associateFareWithReturnFlightSchedulePlan(returnFareId, returnFlightSchedulePlanId);
                    } catch (FareNotFoundException ex) {
                        System.out.println("Fare " + returnFareId + " does not exist " + ex.getMessage() + "\n");
                    }
                    System.out.println("Fare " + returnFareId + " created under return Flight Schedule Plan " + returnFlightSchedulePlanId);
                }

                System.out.println("Return Flight Schedule Plan " + returnFlightSchedulePlanId + " created successfully!" + "\n");
                
                for (Date date : arrivalDates) {
                    calendar1.setTime(date);
                    calendar1.add(Calendar.HOUR_OF_DAY, layoverHours);
                    calendar1.add(Calendar.MINUTE, layoverMinutes);
                    departureDate = calendar1.getTime();

                    double timeZoneDiff = flight.getFlightRoute().getDestinationAirport().getTimeZone() - flight.getFlightRoute().getOriginAirport().getTimeZone();

                    Integer timeZoneHours = (int) timeZoneDiff;
                    Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(flightTime);
                    Integer flightHours = calendar2.get(Calendar.HOUR_OF_DAY);
                    Integer flightMinutes = calendar2.get(Calendar.MINUTE);

                    calendar2.setTime(departureDate);
                    calendar2.add(Calendar.HOUR_OF_DAY, -timeZoneHours + flightHours);
                    calendar2.add(Calendar.MINUTE, -timeZoneMinutes + flightMinutes);
                    arrivalDate = calendar2.getTime();

                    try {
                        flightScheduleSessionBeanRemote.checkForScheduleOverlap(flight.getFlightId(), departureDate, arrivalDate);
                    } catch (FlightScheduleOverlapException ex) {
                        System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                        return;
                    }

                    FlightSchedule returnFlightSchedule = new FlightSchedule(departureDate, flightTime, arrivalDate);
                    Long returnFlightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(returnFlightSchedule, returnFlightSchedulePlanId);
                    System.out.println("Return Flight Schedule " + returnFlightScheduleId + " created under return flight schedule plan " + returnFlightSchedulePlanId);

                    for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(returnFlightScheduleId).getSeatInventories()) {
                        System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + returnFlightScheduleId);
                    }
                    System.out.println("\n");

                } 
            }
        }
        
    }
    
    public void enterFareDetails(List<CabinClassConfiguration> cabinClassConfigurations, Fare fare, List<Fare> fares) {
        Scanner sc = new Scanner(System.in);
        for (CabinClassConfiguration cabinClassConfiguration : cabinClassConfigurations) {
            if (cabinClassConfiguration.getCabinClassType() == CabinClassType.FIRST_CLASS) {
                
                while (true) {
                    
                    System.out.println("Enter Fare details for FIRST Class(F)");
                    
                    System.out.print("Enter fare basis code (eg. F001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another FIRST(F) fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    System.out.println("------------------------");
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.BUSINESS_CLASS) {
                
                while (true) {
                    System.out.println("Enter Fare details for BUSINESS Class(J)");
                    
                    System.out.print("Enter fare basis code (eg. J001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another BUSINESS(J) fare? (Press Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    System.out.println("------------------------");
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.PREMIUM_ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for PREMIUM ECONOMY Class(W)");
                    
                    System.out.print("Enter fare basis code (eg. W001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another PREMIUM ECONOMY(W) fare? (Enter Y to create, N otherwise)> ");
                    sc.nextLine();
                    String responseString = sc.nextLine().trim();
                    System.out.println("------------------------");
                    if (responseString.equals("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            } else if (cabinClassConfiguration.getCabinClassType() == CabinClassType.ECONOMY) {
                
                while (true) {
                    System.out.println("Enter Fare details for ECONOMY Class(Y)");
                    System.out.println("------------------------");
                    System.out.print("Enter fare basis code (eg. Y001)> ");
                    String fareBasisCode = sc.nextLine().trim();
                    System.out.print("Enter fare amount (eg. 999.99)> ");
                    BigDecimal fareAmount = sc.nextBigDecimal();
                    fare = new Fare(fareBasisCode, fareAmount, cabinClassConfiguration);
                    fares.add(fare);
                    //session bean create Fare, associate, if returnFlightSchedulePlan true, add to the return also
                    System.out.print("Add another ECONOMY(Y) fare? (Enter Y to create, N otherwise)> ");
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
    }
    
    
    public void doViewAllFlightSchedulePlans() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Flight Reservation System Management :: View All Flight Schedule Plans ***\n");
        
        List<FlightSchedulePlan> flightSchedulePlans =  flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
        System.out.printf("%20s%25s%28s%28s%20s\n", "Flight Number", "Flight Schedule Plan ID", "Flight Schedule Plan Type", "End Date(Recurrent)", "n Days(Recurrent)");

        for (FlightSchedulePlan flightSchedulePlan : flightSchedulePlans) {
            
            if (flightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_WEEK)) {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), dateFormat.format(flightSchedulePlan.getEndDate()), 7);
            } else if (flightSchedulePlan.getFlightSchedulePlanType().equals(FlightSchedulePlanType.RECURRENT_DAY)) {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), dateFormat.format(flightSchedulePlan.getEndDate()), flightSchedulePlan.getnDays());
            } else {
                System.out.printf("%20s%25s%28s%28s%20s\n", flightSchedulePlan.getFlight().getFlightNumber(), flightSchedulePlan.getFlightSchedulePlanId(), flightSchedulePlan.getFlightSchedulePlanType(), "N/A", "N/A");       
            }
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }
    
    public void doViewFlightSchedulePlanDetails() throws FareNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** Flight Reservation System Management :: View Flight Schedule Plan Details ***\n");
        
        System.out.print("Enter Flight Schedule Plan ID> ");
        Long flightSchedulePlanId = sc.nextLong();
        
        try {
            FlightSchedulePlan flightSchedulePlan = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(flightSchedulePlanId);
            
            System.out.println("Flight Number: " + flightSchedulePlan.getFlight().getFlightNumber());
            System.out.println("Flight Schedule Plan Type: " + flightSchedulePlan.getFlightSchedulePlanType());
            if (flightSchedulePlan.getEndDate() != null) {
                System.out.println("End Date: " + dateFormat.format(flightSchedulePlan.getEndDate()));
            } else {
                System.out.println("End date: N/A" );
            }
            
            if (flightSchedulePlan.getnDays() != null) {
                System.out.println("Recurs every n days: " + flightSchedulePlan.getnDays());
            } else {
                System.out.println("Recurs every n days: N/A" );
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
            
            for (FlightSchedule flightSchedule : flightSchedulePlan.getFlightSchedules()) {
                Long flightScheduleId = flightSchedule.getFlightScheduleId();
                FlightSchedule flightScheduleToPrint = flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId);
                System.out.printf("%20s%32s%32s%20s\n", flightScheduleId, dateFormat.format(flightScheduleToPrint.getDepartureDateTime()), dateFormat.format(flightScheduleToPrint.getArrivalDateTime()), format.format(flightScheduleToPrint.getEstimatedFlightDuration()));
            }
                        
            System.out.println("------------------------");
            System.out.println("1: Update Flight Schedule Plan");
            System.out.println("2: Delete Flight Schedule Plan");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = sc.nextInt();

            if(response == 1)
            {
                try {
                    doUpdateFlightSchedulePlan(flightSchedulePlan);
                } catch (InvalidDateFormatException | CabinClassConfigurationNotFoundException | FlightSchedulePlanNotFoundException | SeatInventoryNotFoundException | ParseException ex) {
                    System.out.println(ex.getMessage() + "\n");
                }
            }
            else if(response == 2)
            {
                doDeleteFlightSchedulePlan(flightSchedulePlan);
            }
        } catch (FlightSchedulePlanNotFoundException ex) {
            System.out.println("Flight Schedule Plan " + flightSchedulePlanId + " not found!" + "\n");
        }
    }
    
    public void doUpdateFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) throws FlightSchedulePlanNotFoundException, InvalidDateFormatException, CabinClassConfigurationNotFoundException, SeatInventoryNotFoundException, ParseException {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        FlightSchedulePlan flightSchedulePlanToUpdate = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(flightSchedulePlan.getFlightSchedulePlanId());
        
        
        Integer integerInput;
        Long longInput;
        System.out.println("*** Flight Reservation System Management :: View Flight Schedule Plan Details :: Update Flight Schedule Plan ***\n");
        
        if (flightSchedulePlanToUpdate.getnDays() != null) {  
            System.out.print("Enter recurring n days (0 or negative number if no change)> ");
            integerInput = sc.nextInt();
            sc.nextLine();
            Calendar calendar = Calendar.getInstance();
            Date today = dateFormat.parse("13-12-2020");
            if(integerInput > 0) {
                List<FlightSchedule> flightSchedules = flightSchedulePlanToUpdate.getFlightSchedules();
                for (FlightSchedule flightSchedule : flightSchedules) {
                    FlightSchedule tempFlightSchedule = flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightSchedule.getFlightScheduleId());
                    
                    if (tempFlightSchedule.getDepartureDateTime().getTime() > today.getTime()) {
                         for (SeatInventory seatInventory : tempFlightSchedule.getSeatInventories()) {
                             if (seatInventory.getReservedSeats() > 0) {
                                 System.out.println("Cannot be updated, flight tickets have been sold for this schedule!");
                                 return;
                             }
                         }
                         flightScheduleSessionBeanRemote.deleteFlightSchedule(tempFlightSchedule.getFlightScheduleId());
                    }
                }
                
                flightSchedulePlanToUpdate.setnDays(integerInput);
                
                // get the last flight schedule from fsp before today
                FlightSchedule prevFlightSchedule = flightSchedulePlanToUpdate.getFlightSchedules().get(flightSchedulePlanToUpdate.getFlightSchedules().size()-1);
                
                
                if (prevFlightSchedule.getDepartureDateTime().before(today)) {
                    Date departureDate = null;
                    Date arrivalDate = null;
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(prevFlightSchedule.getDepartureDateTime());
                    departureDate = calendar1.getTime();
                    while (true) {
                        if (departureDate.after(flightSchedulePlanToUpdate.getEndDate())) {
                            break;
                        }

                        departureDate = calendar1.getTime();
                        calendar1.add(Calendar.DAY_OF_MONTH, integerInput);

                        // CALCULATE ARRIVAL DAY
                        double timeZoneDiff = flightSchedulePlanToUpdate.getFlight().getFlightRoute().getDestinationAirport().getTimeZone() - flightSchedulePlanToUpdate.getFlight().getFlightRoute().getOriginAirport().getTimeZone();

                        Integer timeZoneHours = (int) timeZoneDiff;
                        Integer timeZoneMinutes = (int) ((timeZoneDiff % 1) * 60);
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(prevFlightSchedule.getEstimatedFlightDuration());
                        Integer flightHours = calendar2.get(Calendar.HOUR_OF_DAY);
                        Integer flightMinutes = calendar2.get(Calendar.MINUTE);

                        calendar2.setTime(departureDate);
                        calendar2.add(Calendar.HOUR_OF_DAY, timeZoneHours + flightHours);
                        calendar2.add(Calendar.MINUTE, timeZoneMinutes + flightMinutes);
                        arrivalDate = calendar2.getTime();

                        try {
                            flightScheduleSessionBeanRemote.checkForScheduleOverlap(flightSchedulePlanToUpdate.getFlight().getFlightId(), departureDate, arrivalDate);
                        } catch (FlightScheduleOverlapException ex) {
                            System.out.println("Cannot add Flight Schedule Plan, " + ex.getMessage() + "\n");
                            return;
                        }

                        FlightSchedule flightSchedule = new FlightSchedule(departureDate, prevFlightSchedule.getEstimatedFlightDuration(), arrivalDate);
                        Long flightScheduleId = flightScheduleSessionBeanRemote.createNewFlightSchedule(flightSchedule, flightSchedulePlanToUpdate.getFlightSchedulePlanId());
                        flightSchedulePlanToUpdate.getFlightSchedules().add(flightSchedule);
                        System.out.println("Flight Schedule " + flightScheduleId + " created under flight schedule plan " + flightSchedulePlanToUpdate.getFlightSchedulePlanId());

                        for (SeatInventory seatInventory : flightScheduleSessionBeanRemote.retrieveFlightScheduleById(flightScheduleId).getSeatInventories()) {
                            System.out.println("Seat Inventory " + seatInventory.getSeatInventoryId() + " created for Flight Schedule " + flightScheduleId);
                        }
                        System.out.println("\n");

                    }
                }
           }
        }
        if (flightSchedulePlanToUpdate.getEndDate() != null) {   
            System.out.print("Edit recurrence end date? (Press Y to create, N otherwise)> ");
            String responseString = sc.nextLine().trim();
            if (responseString.equals("Y")) {
                System.out.print("Enter recurrence end date in DD-MM-YYYY (eg. 23-04-2020)> ");
                String dateInput = sc.nextLine().trim();
                
                try {
                    Date date = dateFormat.parse(dateInput);
                    flightSchedulePlanToUpdate.setEndDate(date);
                    for (FlightSchedule flightSchedule : flightSchedulePlanToUpdate.getFlightSchedules()) {
                        if (flightSchedule.getDepartureDateTime().getTime() > date.getTime()) {
                            for (SeatInventory seatInventory : flightSchedule.getSeatInventories()) {
                                if (seatInventory.getReservedSeats() > 0) {
                                    System.out.println("Cannot be updated, flight tickets have been sold for this schedule!");
                                    return;
                                }
                            }
                            flightScheduleSessionBeanRemote.deleteFlightSchedule(flightSchedule.getFlightScheduleId());
                        }
                    }
                } catch (ParseException ex) {
                    throw new InvalidDateFormatException("Invalid date/time format!");
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
                    flightSchedulePlanToUpdate.getFares().remove(fareSessionBeanRemote.retrieveFareByFareId(longInput));
                    flightSchedulePlanToUpdate.getFares().add(fareSessionBeanRemote.retrieveFareByFareId(fareId));
                } catch (FareNotFoundException ex) {
                    System.out.println("Fare " + longInput + "/" + fareId + " does not exist!");
                }
            } else {
                break;
            }
        }
        
        try {
            flightSchedulePlanSessionBeanRemote.updateFlightSchedulePlan(flightSchedulePlanToUpdate);
        } catch (FlightSchedulePlanNotFoundException | UpdateFlightSchedulePlanException ex) {
            System.out.println("Flight Schedule Plan could not be updated, " + ex.getMessage() + "\n");
        }
            
        
        System.out.println("Flight Schedule Plan " + flightSchedulePlanToUpdate.getFlightSchedulePlanId() + " updated successfully!");
    }
    
    public void doDeleteFlightSchedulePlan(FlightSchedulePlan flightSchedulePlan) {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Flight Reservation System Management :: View Flight Details :: Delete Flight Schedule Plan ***\n");
        System.out.printf("Confirm Delete Flight Schedule Plan (Flight Schedule Plan ID: %d) (Enter 'Y' to Delete)> ", flightSchedulePlan.getFlightSchedulePlanId());
        input = sc.nextLine().trim();
//        
        if(input.equals("Y")) { 
            try {
                flightSchedulePlanSessionBeanRemote.deleteFlightSchedulePlan(flightSchedulePlan.getFlightSchedulePlanId());
                System.out.println("Flight Schedule Plan deleted successfully!");
            } catch (FlightSchedulePlanNotFoundException | DeleteFlightSchedulePlanException ex) {
                System.out.println("Flight Not Deleted, " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Flight schedule plan NOT deleted!\n");
        }
    }
}
