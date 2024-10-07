package br.com.dev.ap.services.resource;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.dev.ap.domain.User;
import br.com.dev.ap.domain.dto.UserDTO;
import br.com.dev.ap.resources.UserResource;
import br.com.dev.ap.service.impl.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	
	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;
	private UserDTO userDTO;
	
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnSuccess() {
		Mockito.when(service.findById(ArgumentMatchers.anyInt())).thenReturn(user);
		Mockito.when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(userDTO);
		
		
		ResponseEntity<UserDTO> response = resource.findById(1);
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
		
	}
	
	@Test
	void wheFindAllThenReturnAListOfUserDTO() {
		Mockito.when(service.findAll()).thenReturn(List.of(user));
		Mockito.when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = resource.findAll();	
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());	
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().get(0).getClass());
		
	}
	
	
	@Test
	void whenCreateThenReturnCreated() {
		Mockito.when(service.create(ArgumentMatchers.any())).thenReturn(user);
		ResponseEntity<UserDTO> response = resource.create(userDTO);
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	
	private void startUser() {
		user = new User(1, "silas", "silas@gmail.com", "123");
		userDTO = new UserDTO(1, "silas", "silas@gmail.com", "123");

	}
}
