/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Remote;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author GraceLi
 */
@Remote
public interface EmployeeSessionBeanRemote {

    public Long createNewEmployee(Employee newEmployee);

    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;
    
}
