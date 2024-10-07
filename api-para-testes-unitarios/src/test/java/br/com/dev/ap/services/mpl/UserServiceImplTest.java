package br.com.dev.ap.services.mpl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

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

import br.com.dev.ap.domain.User;
import br.com.dev.ap.domain.dto.UserDTO;
import br.com.dev.ap.repositories.UserRepository;
import br.com.dev.ap.service.exceptions.DataIntegrityViolationException;
import br.com.dev.ap.service.exceptions.ObjectNotFoundException;
import br.com.dev.ap.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	@InjectMocks // precisa de um instancia real pois vamos testar exatamente ela e por isso não
					// pode ser um mock
	private UserServiceImpl service;

	@Mock
	private UserRepository repository;

	@Mock
	private ModelMapper mapper;

	private User user;
	private UserDTO userDTO;
	private Optional<User> optionalUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {

		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

		User response = service.findById(1);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(1, user.getId());
		Assertions.assertEquals("silas", user.getName());
		Assertions.assertEquals("silas@gmail.com", user.getEmail());

	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		Mockito.when(repository.findById(ArgumentMatchers.anyInt()))
				.thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

		try {
			User response = service.findById(1);
		} catch (Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
			Assertions.assertEquals("Objeto não encontrado", ex.getMessage());

		}

	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		Mockito.when(repository.findAll()).thenReturn(List.of(user));

		List<User> response = service.findAll();

		Assertions.assertNotNull(response);
		Assertions.assertEquals(response.get(0).getClass(), User.class);
		Assertions.assertEquals(response.get(0).getId(), 1);
	}

	@Test
	void whenCreateReturnSuccess() {
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(user);

		User response = service.create(userDTO);

		Assertions.assertNotNull(user);
		Assertions.assertEquals(response, User.class);
		Assertions.assertEquals(1, response.getId());

	}

	@Test
	void whenCreateReturnADataIntegrityViolationException() {
		Mockito.when(repository.findByEmail(ArgumentMatchers.any())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception ex) {
			Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
			Assertions.assertEquals("E-mail ja cadastrado no sistema", ex.getMessage());
		}

	}
	
	@Test
	void whenUpdateReturnSuccess() {
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(user);

		User response = service.update(userDTO);

		Assertions.assertNotNull(user);
		Assertions.assertEquals(response, User.class);
		Assertions.assertEquals(1, response.getId());

	}
	
	@Test
	void whenUpdateReturnADataIntegrityViolationException() {
		Mockito.when(repository.findByEmail(ArgumentMatchers.any())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.update(userDTO);
		} catch (Exception ex) {
			Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
			Assertions.assertEquals("E-mail ja cadastrado no sistema", ex.getMessage());
		}

	}
	
	@Test
	void deleteWithSuccess() {
		Mockito.when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(optionalUser);
		Mockito.doNothing().when(repository).deleteById(ArgumentMatchers.anyInt());
		service.delete(1);
		Mockito.verify(repository, times(1)).deleteById(ArgumentMatchers.anyInt());
		
	}
	
	@Test
	void deleteWithObjectNotFoundException() {
		Mockito.when(repository.findById(ArgumentMatchers.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		
		try {
			service.delete(1);
		}catch(Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
			Assertions.assertEquals("Objeto não encontrado", ex.getMessage());
		}
		
	}

	private void startUser() {
		user = new User(1, "silas", "silas@gmail.com", "123");
		userDTO = new UserDTO(1, "silas", "silas@gmail.com", "123");
		optionalUser = Optional.of(new User(1, "silas", "silas@gmail.com", "123"));

	}
}
