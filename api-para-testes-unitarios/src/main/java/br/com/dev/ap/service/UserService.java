package br.com.dev.ap.service;

import java.util.List;

import br.com.dev.ap.domain.User;
import br.com.dev.ap.domain.dto.UserDTO;

public interface UserService {
	User findById(Integer id);
	List<User> findAll();
	User create(UserDTO obj);
	User update(UserDTO obj);
	void delete(Integer id);
}
