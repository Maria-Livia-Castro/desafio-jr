package com.desafio.demo.mapper;

import com.desafio.demo.dto.UsuarioRequest;
import com.desafio.demo.dto.UsuarioResponse;
import com.desafio.demo.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // Converte de Request (entrada da API) para Entity (banco)
    Usuario toEntity(UsuarioRequest request);
    // Converte de Entity (banco) para Response (saída da API)
    UsuarioResponse toResponse(Usuario entity);

}
