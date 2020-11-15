/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.CabinClassConfigurationSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FareSessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSchedulePlanSessionBeanLocal;
import ejb.session.stateless.FlightScheduleSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.ItineraryItemSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.SeatInventorySessionBean;
import ejb.session.stateless.SeatInventorySessionBeanLocal;
import entity.AircraftConfiguration;
import entity.AircraftType;
import entity.Airport;
import entity.CabinClassConfiguration;
import entity.Employee;
import entity.Fare;
import entity.Flight;
import entity.FlightRoute;
import entity.FlightSchedule;
import entity.FlightSchedulePlan;
import entity.ItineraryItem;
import entity.Partner;
import entity.SeatInventory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRights;
import util.enumeration.FlightSchedulePlanType;
import util.enumeration.PartnerAccessRights;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.AirportNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;
import util.exception.EmployeeNotFoundException;
import util.exception.FlightAlreadyExistsException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidIataCodeException;
import util.exception.PartnerNotFoundException;
import util.exception.SeatInventoryNotFoundException;

/**
 *
 * @author GraceLi
 */
@Singleton
@LocalBean
@Startup
public class TestDataInitSessionBean {

    @EJB
    private ItineraryItemSessionBeanLocal itineraryItemSessionBeanLocal;

    @EJB
    private SeatInventorySessionBeanLocal seatInventorySessionBeanLocal;

    @EJB
    private FareSessionBeanLocal fareSessionBean;

    @EJB
    private FlightScheduleSessionBeanLocal flightScheduleSessionBean;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBean;

    @EJB
    private FlightSessionBeanLocal flightSessionBean;

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBean;

    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBean;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBean;

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBean;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void postConstruct() {
        try {
            employeeSessionBean.retrieveEmployeeById(1l);
        } catch (EmployeeNotFoundException ex) {
            loadEmployeeData();
        }
        
        try {
            partnerSessionBean.retrievePartnerById(1l);
        } catch (PartnerNotFoundException ex) {
            loadPartnerData();
        }
        
        try {
            airportSessionBean.retrieveAirportById(1l);
        } catch (AirportNotFoundException ex) {
            loadAirportData();
        }
        
        try {
            aircraftTypeSessionBean.retrieveAircraftTypeById(1l);
        } catch (AircraftTypeNotFoundException ex) {
            loadAircraftType();
        }
        
        try {
            aircraftConfigurationSessionBean.retrieveAircraftConfigurationById(1l);
        } catch (AircraftConfigurationNotFoundException ex) {
            loadAircraftConfiguration();
        }
        
        try {
            flightRouteSessionBean.retrieveFlightRouteById(1l);
        } catch (FlightRouteNotFoundException ex) {
            loadFlightRoute();
        }
        
        try {
            flightSessionBean.retrieveFlightById(1l);
        } catch (FlightNotFoundException ex) {
            loadFlightData();
        }
        
        try {
            flightSchedulePlanSessionBean.retrieveFlightSchedulePlanById(1l);
        } catch (FlightSchedulePlanNotFoundException ex) {
            loadFlightSchedulePlan();
        }
        
//        try {
//            flightScheduleSessionBean.retrieveFlightScheduleById(1l);
//        } catch (FlightSchedulePlanNotFoundException ex) {
//            loadTest();
//        }
        
    }
    
    public void loadEmployeeData() {
        employeeSessionBean.createNewEmployee(new Employee("Fleet Manager", "fleetmanager", "password", EmployeeAccessRights.FLEET_MANAGER));
        
        employeeSessionBean.createNewEmployee(new Employee("Route Planner", "routeplanner", "password", EmployeeAccessRights.ROUTE_PLANNER));
        
        employeeSessionBean.createNewEmployee(new Employee("Schedule Manager", "schedulemanager", "password", EmployeeAccessRights.SCHEDULE_MANAGER));
        
        employeeSessionBean.createNewEmployee(new Employee("Sales Manager", "salesmanager", "password", EmployeeAccessRights.SALES_MANAGER));
    }
    
    public void loadPartnerData() {
        partnerSessionBean.createNewPartner(new Partner("Holiday.com", "holidaydotcom", "password", PartnerAccessRights.MANAGER));
    }
    
    public void loadAirportData() {
        
        airportSessionBean.createNewAirport(new Airport("Changi", "SIN", "Singapore", "Singapore", "Singapore", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Hong Kong", "HKG", "Chek Lap Kok", "Hong Kong", "China", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Taoyuan", "TPE", "Taoyuan", "Taipei", "Taiwan R.O.C.", 8.0));
        
        airportSessionBean.createNewAirport(new Airport("Narita", "NRT", "Narita", "Chiba", "Japan", 9.0));
        
        airportSessionBean.createNewAirport(new Airport("Sydney", "SYD", "Sydney", "New South Wales", "Australia", 11.0));
    }
    
    public void loadAircraftType() {
        aircraftTypeSessionBean.createNewAircraftType(new AircraftType("Boeing 737", 200));
        
        aircraftTypeSessionBean.createNewAircraftType(new AircraftType("Boeing 747", 400));
    }
    
    public void loadAircraftConfiguration() {
        try {
            Long aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 737 All Economy", 1, 180), 1l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 1, 30, 6, "3-3", 180), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 737 Three Classes", 3, 180), 1l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.FIRST_CLASS, 1, 5, 2, "1-1", 10), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.BUSINESS_CLASS, 1, 5, 4, "2-2", 20), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 1, 25, 6, "3-3", 150), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 747 All Economy", 1, 380), 2l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 2, 38, 10, "3-4-3", 380), aircraftConfigurationId);
            
            aircraftConfigurationId = aircraftConfigurationSessionBean.createNewAircraftConfiguration(new AircraftConfiguration("Boeing 747 Three Classes", 3, 360), 2l);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.FIRST_CLASS, 1, 5, 2, "1-1", 10), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.BUSINESS_CLASS, 2, 5, 6, "2-2-2", 30), aircraftConfigurationId);
            cabinClassConfigurationSessionBean.createNewCabinClassConfiguration(new CabinClassConfiguration(CabinClassType.ECONOMY, 2, 32, 10, "3-4-3", 320), aircraftConfigurationId);
            
        } catch (AircraftTypeNotFoundException | AircraftConfigurationNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightRoute() {
        try {
            Long flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "HKG");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "HKG", "SIN");
            //1,2
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "TPE");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "TPE", "SIN");
            //3,4
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SIN");
            //5,6
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "HKG", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "HKG");
            //7,8
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "TPE", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "TPE");
            //9,10
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "SYD");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "SYD", "SIN");
            //11,12
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SYD", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SYD");
            //13,14
            //For own testing purpose
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "TPE", "SYD");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "SYD", "TPE");
            //15,16
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "TPE", "HKG");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "HKG", "TPE");  
            //17,18
            //
        } catch (InvalidIataCodeException | FlightRouteNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightData() {
        try {
            Long flightId = flightSessionBean.createNewFlight(new Flight("ML111"), 1l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML112"), flightId, 2l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML211"), 3l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML212"), flightId, 4l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML311"), 5l, 4l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML312"), flightId, 6l, 4l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML411"), 7l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML412"), flightId, 8l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML511"), 9l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML512"), flightId, 10l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML611"), 11l, 2l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML612"), flightId, 12l, 2l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML621"), 11l, 1l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML622"), flightId, 12l, 1l);
            
            flightId = flightSessionBean.createNewFlight(new Flight("ML711"), 13l, 4l);
            flightSessionBean.createNewComplementaryReturnFlight(new Flight("ML712"), flightId, 14l, 4l);
                    
            //for own testing purposes
            flightId = flightSessionBean.createNewFlight(new Flight("ML1"), 5l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML2"), 10l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML3"), 15l,1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML4"), 3l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML5"), 9l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML6"), 14l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML7"), 16l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML8"), 17l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML9"), 2l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML10"), 15l, 1l);
            flightId = flightSessionBean.createNewFlight(new Flight("ML11"), 12l, 1l);
            //
            
            
        } catch (AircraftConfigurationNotFoundException | FlightNotFoundException | FlightRouteNotFoundException | FlightAlreadyExistsException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightSchedulePlan() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            // ML711, Recurrent Weekly
            Long flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 09:00")), 15l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(6500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(6000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(3500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(3000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(1500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(1000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), flightSchedulePlanId);
            
            Long complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 09:00")), flightSchedulePlanId, 16l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(6500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(6000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(3500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(3000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(1500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(1000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), complementaryFlightSchedulePlanId);
            
            // ML711, 7 Dec 20, 9:00 AM, 14 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 09:00"), timeFormat.parse("14:00"), dateFormat.parse("07-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 23:00"), timeFormat.parse("14:00"), dateFormat.parse("08-12-2020 15:00")), complementaryFlightSchedulePlanId);
            
            // ML711, 14 Dec 20, 9:00 AM, 14 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("14-12-2020 09:00"), timeFormat.parse("14:00"), dateFormat.parse("14-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("14-12-2020 23:00"), timeFormat.parse("14:00"), dateFormat.parse("15-12-2020 15:00")), complementaryFlightSchedulePlanId);
            
            // ML711, 21 Dec 20, 9:00 AM, 14 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 09:00"), timeFormat.parse("14:00"), dateFormat.parse("21-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 23:00"), timeFormat.parse("14:00"), dateFormat.parse("22-12-2020 15:00")), complementaryFlightSchedulePlanId);
            
            // ML711, 28 Dec 20, 9:00 AM, 14 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("28-12-2020 09:00"), timeFormat.parse("14:00"), dateFormat.parse("28-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("28-12-2020 23:00"), timeFormat.parse("14:00"), dateFormat.parse("29-12-2020 15:00")), complementaryFlightSchedulePlanId);
            
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // ML611, Recurrent Weekly
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 12:00")), 11l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3250), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(3000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1750), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(750), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            
            complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 12:00")), flightSchedulePlanId, 12l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3250), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(3000), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1750), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(750), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(500), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            
            // ML611, 6 Dec 2020, 12:00 PM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("06-12-2020 12:00"), timeFormat.parse("08:00"), dateFormat.parse("06-12-2020 23:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 01:00"), timeFormat.parse("08:00"), dateFormat.parse("07-12-2020 06:00")), complementaryFlightSchedulePlanId);
            
            // ML611, 13 Dec 2020, 12:00 PM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("13-12-2020 12:00"), timeFormat.parse("08:00"), dateFormat.parse("13-12-2020 23:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("14-12-2020 01:00"), timeFormat.parse("08:00"), dateFormat.parse("14-12-2020 06:00")), complementaryFlightSchedulePlanId);
            
            // ML611, 20 Dec 2020, 12:00 PM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("20-12-2020 12:00"), timeFormat.parse("08:00"), dateFormat.parse("20-12-2020 23:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 01:00"), timeFormat.parse("08:00"), dateFormat.parse("21-12-2020 06:00")), complementaryFlightSchedulePlanId);
            
            // ML611, 27 Dec 2020, 12:00 PM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("27-12-2020 12:00"), timeFormat.parse("08:00"), dateFormat.parse("27-12-2020 23:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("28-12-2020 01:00"), timeFormat.parse("08:00"), dateFormat.parse("28-12-2020 06:00")), complementaryFlightSchedulePlanId);
            
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // ML621, Recurrent Weekly
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 10:00")), 13l);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(700), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            
            complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 10:00")), flightSchedulePlanId,14l);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(700), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), complementaryFlightSchedulePlanId);
            
            // ML621, 1 Dec 20, 10:00 AM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("01-12-2020 10:00"), timeFormat.parse("08:00"), dateFormat.parse("01-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("01-12-2020 23:00"), timeFormat.parse("08:00"), dateFormat.parse("02-12-2020 04:00")), complementaryFlightSchedulePlanId);
            
            // ML621, 8 Dec 20, 10:00 AM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 10:00"), timeFormat.parse("08:00"), dateFormat.parse("08-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 23:00"), timeFormat.parse("08:00"), dateFormat.parse("09-12-2020 04:00")), complementaryFlightSchedulePlanId);
            
            // ML621, 15 Dec 20, 10:00 AM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("15-12-2020 10:00"), timeFormat.parse("08:00"), dateFormat.parse("15-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("15-12-2020 23:00"), timeFormat.parse("08:00"), dateFormat.parse("16-12-2020 04:00")), complementaryFlightSchedulePlanId);
            
            // ML621, 22 Dec 20, 10:00 AM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("22-12-2020 10:00"), timeFormat.parse("08:00"), dateFormat.parse("22-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("22-12-2020 23:00"), timeFormat.parse("08:00"), dateFormat.parse("23-12-2020 04:00")), complementaryFlightSchedulePlanId);
            
            // ML621, 29 Dec 20, 10:00 AM, 8 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("29-12-2020 10:00"), timeFormat.parse("08:00"), dateFormat.parse("29-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("29-12-2020 23:00"), timeFormat.parse("08:00"), dateFormat.parse("30-12-2020 04:00")), complementaryFlightSchedulePlanId);
            
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // ML311, Recurrent Weekly
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 10:00")), 5l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), flightSchedulePlanId);
            
            complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_WEEK, dateFormat.parse("31-12-2020 10:00")), flightSchedulePlanId, 6l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(6l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(7l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(8l)), complementaryFlightSchedulePlanId);
            
            // ML311, 7 Dec 20, 10:00 AM, 6 Hours 30 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 10:00"), timeFormat.parse("06:30"), dateFormat.parse("07-12-2020 17:30")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 20:30"), timeFormat.parse("06:30"), dateFormat.parse("08-12-2020 02:00")), complementaryFlightSchedulePlanId);
            
            // ML311, 14 Dec 20, 10:00 AM, 6 Hours 30 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("14-12-2020 10:00"), timeFormat.parse("06:30"), dateFormat.parse("14-12-2020 17:30")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("14-12-2020 20:30"), timeFormat.parse("06:30"), dateFormat.parse("15-12-2020 02:00")), complementaryFlightSchedulePlanId);
            
            // ML311, 21 Dec 20, 10:00 AM, 6 Hours 30 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 10:00"), timeFormat.parse("06:30"), dateFormat.parse("21-12-2020 17:30")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 20:30"), timeFormat.parse("06:30"), dateFormat.parse("22-12-2020 02:00")), complementaryFlightSchedulePlanId);
            
            // ML311, 28 Dec 20, 10:00 AM, 6 Hours 30 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("28-12-2020 10:00"), timeFormat.parse("06:30"), dateFormat.parse("28-12-2020 17:30")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("28-12-2020 20:30"), timeFormat.parse("06:30"), dateFormat.parse("29-12-2020 02:00")), complementaryFlightSchedulePlanId);
            
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // ML411, Recurrent NDay
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_DAY, dateFormat.parse("31-12-2020 13:00"), 2), 7l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3150), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2900), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1650), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(650), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            
            complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.RECURRENT_DAY, dateFormat.parse("31-12-2020 13:00"), 2), flightSchedulePlanId, 8l);
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3150), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2900), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1650), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(650), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(400), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            
            // ML411,  1 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("01-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("01-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("01-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("02-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  3 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("03-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("03-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("03-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("04-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  5 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("05-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("05-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("05-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("06-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  7 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("07-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("08-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  9 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("09-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("10-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  11 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("11-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("11-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("11-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("12-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  13 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("13-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("13-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("13-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("14-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  15 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("15-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("15-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("15-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("16-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  17 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("17-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("17-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("17-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("18-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  19 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("19-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("19-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("19-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("20-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  21 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("21-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("21-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("22-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  23 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("23-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("23-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("23-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("24-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  25 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("25-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("25-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("25-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("26-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  27 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("27-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("27-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("27-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("28-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  29 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("29-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("29-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("29-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("30-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML411,  31 Dec 20, 1:00 PM, 4 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("31-12-2020 13:00"), timeFormat.parse("04:00"), dateFormat.parse("31-12-2020 18:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("31-12-2020 22:00"), timeFormat.parse("04:00"), dateFormat.parse("01-01-2021 01:00")), complementaryFlightSchedulePlanId);
            
            // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // ML511, Manual Multiple
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE), 9l);
            
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            
            complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE), flightSchedulePlanId, 10l);
            
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            
            
            // ML511, 7 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("07-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("08-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML511, 8 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("08-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("09-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            // ML511, 9 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("09-12-2020 21:00")), flightSchedulePlanId);
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("10-12-2020 01:00")), complementaryFlightSchedulePlanId);
            
            
            
            //For own testing purposes
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 17l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            Long flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 08:00"), timeFormat.parse("01:00"), dateFormat.parse("07-12-2020 10:00")), flightSchedulePlanId);
            //seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 18l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 15:00"), timeFormat.parse("01:00"), dateFormat.parse("07-12-2020 15:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 19l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 23:00"), timeFormat.parse("01:00"), dateFormat.parse("08-12-2020 00:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 20l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 08:00"), timeFormat.parse("01:00"), dateFormat.parse("08-12-2020 9:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 21l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 15:00"), timeFormat.parse("01:00"), dateFormat.parse("08-12-2020 17:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 22l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 23:00"), timeFormat.parse("01:00"), dateFormat.parse("08-12-2020 23:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 23l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("10-12-2020 08:00"), timeFormat.parse("01:00"), dateFormat.parse("10-12-2020 09:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 24l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("10-12-2020 15:00"), timeFormat.parse("01:00"), dateFormat.parse("10-12-2020 16:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
           
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 25l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("10-12-2020 232:00"), timeFormat.parse("01:00"), dateFormat.parse("11-12-2020 00:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 26l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("10-12-2020 10:00"), timeFormat.parse("01:00"), dateFormat.parse("10-12-2020 11:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.SINGLE), 27l);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(1l)), flightSchedulePlanId);
            flightScheduleId = flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("10-12-2020 23:00"), timeFormat.parse("01:00"), dateFormat.parse("11-12-2020 00:00")), flightSchedulePlanId);
//            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(180), 1l, flightScheduleId);
            
            
            
            //
            
        } catch (FlightNotFoundException | ParseException | FlightSchedulePlanNotFoundException | CabinClassConfigurationNotFoundException | SeatInventoryNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
//    public void loadTest() {
//        try {
//            itineraryItemSessionBeanLocal.createItineraryItem(new ItineraryItem("11-11-2222 12-11-2222", "SIN-KUL", "FIRST_CLASS", "B2", BigDecimal.valueOf(12.00), "Wang Ziyue", "F001"), 1l);
//            itineraryItemSessionBeanLocal.createItineraryItem(new ItineraryItem("11-11-2222 12-11-2222", "SIN-KUL", "FIRST_CLASS", "A2", BigDecimal.valueOf(13.00), "Grace Li", "F002"), 1l);
//        } catch (FlightSchedulePlanNotFoundException ex) {
//            System.out.println(ex.getMessage() + "\n");
//        } 
//    }    
}
