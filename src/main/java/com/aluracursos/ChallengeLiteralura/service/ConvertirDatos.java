package com.aluracursos.ChallengeLiteralura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvertirDatos {

    @Override
    public <T> T convertirDatos(String json, Class<T> clase) {
        ObjectMapper mapper = new ObjectMapper();

        try{
            return mapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
