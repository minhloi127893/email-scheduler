package com.javatest.email_scheduler.common;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	 private final ModelMapper modelMapper = new ModelMapper();

	    public <D, E> E mapToEntity(D dto, Class<E> entityClass) {
	        return modelMapper.map(dto, entityClass);
	    }

	    public <E, D> D mapToDto(E entity, Class<D> dtoClass) {
	        return modelMapper.map(entity, dtoClass);
	    }
}
