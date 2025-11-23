package com.andres.springcloud.msvc.users.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseSunat {
    private String razon_social;
    private String numero_documento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String via_tipo;
    private String via_nombre;
    private String zona_codigo;
    private String zona_tipo;
    private String numero;
    private String interior;
    private String lote;
    private String dpto;
    private String manzana;
    private String kilometro;
    private String distrito;
    private String provincia;
    private String departamento;
    private boolean es_agente_retencion;
}

