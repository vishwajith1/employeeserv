package com.paypal.bfs.test.employeeserv.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repo.EmployeeRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EmployeeResourceImplTest {

	@Autowired
	private WebApplicationContext context;

	@InjectMocks
	private EmployeeResourceImpl employeeResourceImpl;

//	private MockMvc mockMvc;

	@MockBean
	private EmployeeRepository employeeRepo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .build();
	
	}

	@Test
	public void testaddEmployee2xx() throws Exception {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("John");
		employee.setLastName("Carter");
		employee.setDateOfBirth("1990-01-01");
		employee.setAddress(setAddressInfo());

		Mockito.when(employeeRepo.save(employee)).thenReturn(employee);
		ResponseEntity<Employee> empResult = employeeResourceImpl.addEmployee(employee);
		assertNotNull(empResult);
		assertEquals(HttpStatus.CREATED, empResult.getStatusCode());
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/addemployee").contentType(MediaType.APPLICATION_JSON)
//				.content(asJsonString(employee))).andExpect(status().isCreated());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testemployeeGetById4xx() throws Exception {
		Mockito.when(employeeRepo.findById(Integer.valueOf("1"))).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, employeeResourceImpl.employeeGetById("2").getStatusCode());

	}

	@Test
	public void testemployeeGetById2xx() throws Exception {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("John");
		employee.setLastName("Carter");
		employee.setDateOfBirth("1990-01-01");
		employee.setAddress(setAddressInfo());
		Mockito.when(employeeRepo.findById(Integer.valueOf("1"))).thenReturn(Optional.of(employee));
		assertEquals(HttpStatus.OK, employeeResourceImpl.employeeGetById("1").getStatusCode());
	}

	private Address setAddressInfo() {
		Address address = new Address();
		address.setLine1("New line");
		address.setCity("test city");
		address.setState("State");
		address.setCountry("US");
		address.setZipCode("12345");
		return address;
	}
}
