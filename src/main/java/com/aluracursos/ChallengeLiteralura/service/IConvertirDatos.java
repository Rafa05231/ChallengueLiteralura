package com.aluracursos.ChallengeLiteralura.service;

public interface IConvertirDatos {

    <T> T convertirDatos(String json, Class<T> clase);
}
