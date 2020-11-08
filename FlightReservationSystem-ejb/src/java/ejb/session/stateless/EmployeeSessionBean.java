/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewEmployee(Employee newEmployee) {
        em.persist(newEmployee);
        em.flush();
        return newEmployee.getEmployeeId();
    }
    
    @Override
    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee " + employeeId + " does not exist!");
        }
        
        return employee;
        
    }
    
    @Override
    public Employee login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.userName = :inUserName");
            query.setParameter("inUserName", username);
            Employee employee = (Employee)query.getSingleResult();

            if (employee.getPassword().equals(password)) {
                return employee;
            } else {
                throw new InvalidLoginCredentialException("Entered password is incorrect.");
            }
        } catch (NoResultException ex) {
            throw new InvalidLoginCredentialException("One or more entered credentials are incorrect.");
        }
    }
}
