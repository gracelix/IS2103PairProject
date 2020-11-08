/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRights;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author GraceLi
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

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
    }
    
     public void loadEmployeeData() {
        Employee fleetManager = new Employee("Fleet Manager", "fleetmanager", "password", EmployeeAccessRights.FLEET_MANAGER);
        employeeSessionBean.createNewEmployee(fleetManager);
        
        Employee routePlanner = new Employee("Route Planner", "routeplanner", "password", EmployeeAccessRights.ROUTE_PLANNER);
        employeeSessionBean.createNewEmployee(routePlanner);
        
        Employee scheduleManager = new Employee("Schedule Manager", "schedulemanager", "password", EmployeeAccessRights.SCHEDULE_MANAGER);
        employeeSessionBean.createNewEmployee(scheduleManager);
        
        Employee salesManager = new Employee("Sales Manager", "salesmanager", "password", EmployeeAccessRights.SALES_MANAGER);
        employeeSessionBean.createNewEmployee(salesManager);
        
    }
}
