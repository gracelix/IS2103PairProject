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
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidIataCodeException;
import util.exception.PartnerNotFoundException;

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
        
        airportSessionBean.createNewAirport(new Airport("Sydney", "SYD", "Sydney", "New South Wales", "Australia", 8.0));
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
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "TPE");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "TPE", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "HKG", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "HKG");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "TPE", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "TPE");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SIN", "SYD");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "SYD", "SIN");
            
            flightRouteId = flightRouteSessionBean.createNewFlightRoute(new FlightRoute(), "SYD", "NRT");
            flightRouteSessionBean.createNewComplementaryFlightRoute(new FlightRoute(), flightRouteId, "NRT", "SYD");
            
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
                    
        } catch (AircraftConfigurationNotFoundException | FlightNotFoundException | FlightRouteNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
    
    public void loadFlightSchedulePlan() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            Long flightSchedulePlanId = flightSchedulePlanSessionBean.createNewFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE), 9l);
            
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), flightSchedulePlanId);
            
            Long complementaryFlightSchedulePlanId = flightSchedulePlanSessionBean.createNewComplementaryReturnFlightSchedulePlan(new FlightSchedulePlan(FlightSchedulePlanType.MULTIPLE), flightSchedulePlanId, 10l);
            
            fareSessionBean.createNewFare(new Fare("F001", BigDecimal.valueOf(3100), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("F002", BigDecimal.valueOf(2850), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(2l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J001", BigDecimal.valueOf(1600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("J002", BigDecimal.valueOf(1350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(3l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y001", BigDecimal.valueOf(600), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            fareSessionBean.createNewFare(new Fare("Y002", BigDecimal.valueOf(350), cabinClassConfigurationSessionBean.retrieveCabinClassConfigurationById(4l)), complementaryFlightSchedulePlanId);
            
            
            // ML511, 7 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("07-12-2020 21:00")), flightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 1l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 1l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 1l);
            
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("07-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("08-12-2020 01:00")), complementaryFlightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 2l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 2l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 2l);
            
            // ML511, 8 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("08-12-2020 21:00")), flightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 3l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 3l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 3l);
            
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("08-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("09-12-2020 01:00")), complementaryFlightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 4l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 4l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 4l);
            
            // ML511, 9 Dec 20, 5:00 PM, 3 Hours 0 Minute
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 17:00"), timeFormat.parse("03:00"), dateFormat.parse("09-12-2020 21:00")), flightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 5l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 5l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 5l);
            
            flightScheduleSessionBean.createNewFlightSchedule(new FlightSchedule(dateFormat.parse("09-12-2020 23:00"), timeFormat.parse("03:00"), dateFormat.parse("10-12-2020 01:00")), complementaryFlightSchedulePlanId);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(10), 2l, 6l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(20), 3l, 6l);
            seatInventorySessionBeanLocal.createNewSeatInventory(new SeatInventory(150), 4l, 6l);
            
        } catch (FlightNotFoundException | ParseException | FlightSchedulePlanNotFoundException | CabinClassConfigurationNotFoundException ex) {
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
