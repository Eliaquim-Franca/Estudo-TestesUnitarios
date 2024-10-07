package br.com.dev.ap.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dev.ap.domain.User;
import br.com.dev.ap.domain.dto.UserDTO;
import br.com.dev.ap.repositories.UserRepository;
import br.com.dev.ap.service.UserService;
import br.com.dev.ap.service.exceptions.DataIntegrityViolationException;
import br.com.dev.ap.service.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User create(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	
	@Override
	public User update(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	
	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	private void findByEmail(UserDTO dto) {
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if(user.isPresent() && !user.get().getId().equals(dto.getId())    ) {
			throw new DataIntegrityViolationException("E-mail ja cadastrado no sistema");
		}
	}



}
