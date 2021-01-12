package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repo.EmployeeRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeRepository employeeRepo;
	
    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

        Optional<Employee> employee = employeeRepo.findById(Integer.valueOf(id));

        return ResponseEntity.of(employee);
    }

	@Override
	public ResponseEntity<Employee> addEmployee(Employee employee) {
		return new ResponseEntity<>(employeeRepo.save(employee),HttpStatus.CREATED);
	}

}
