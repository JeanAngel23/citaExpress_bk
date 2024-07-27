package com.citaexpressbk.demo.supplier;

import com.citaexpressbk.demo.direccion.DatosDireccion;

public record DatosRespuestaSupplier(Long id, String nombre, String email, String telefono, String documento,
                                     String service, DatosDireccion direccion) {
}