/*
 * Copyright (C) Proxime SpA - Todos los derechos reservados
 * Queda expresamente prohibida la copia o reproducción total o parcial de este archivo
 * sin el permiso expreso y por escrito de Proxime SpA.
 * La detección de un uso no autorizado puede acarrear el inicio de acciones legales.
 */
package appp.proxime.maven.plugins.config.structure;

import java.util.LinkedHashMap;

/**
 *
 * @author Michel M. <michel@febos.cl>
 */
public class ApiGateway {
    private String api;
    private String metodo;
    private String resource;
    private LinkedHashMap<String,String> mapping;

    /**
     * Retora el valor de la propiedad metodo.
     * @return metodo
     */
    public String metodo() {
        return this.metodo;
    }

    /**
     * Asigna el valor a la propiedad metodo.
     * @param metodo valor a asignar
     */
    public void metodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * Retora el valor de la propiedad url.
     * @return url
     */
    public String resource() {
        return this.resource;
    }

    /**
     * Asigna el valor a la propiedad url.
     * @param resource valor a asignar
     */
    public void resource(String resource) {
        this.resource = resource;
    }

    /**
     * Retora el valor de la propiedad mapping.
     * @return mapping
     */
    public LinkedHashMap<String,String> mapping() {
        return this.mapping;
    }

    /**
     * Asigna el valor a la propiedad mapping.
     * @param mapping valor a asignar
     */
    public void mapping(LinkedHashMap<String,String> mapping) {
        this.mapping = mapping;
    }    
   
    /**
     * Retora el valor de la propiedad api.
     * @return api
     */
    public String api() {
        return this.api;
    }

    /**
     * Asigna el valor a la propiedad api.
     * @param api valor a asignar
     */
    public void api(String api) {
        this.api = api;
    }
    
    
}