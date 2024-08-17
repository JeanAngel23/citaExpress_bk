package com.citaexpressbk.demo.service;

import com.citaexpressbk.demo.direccion.DatosDireccion;
import com.citaexpressbk.demo.domain.entity.Supplier;
import com.citaexpressbk.demo.service.interfaces.ISupplierService;
import com.citaexpressbk.demo.supplier.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public DataResponseSupplier registrarSupplier(DataRegisterSupplier dataRegisterSupplier) {
        Supplier supplier = supplierRepository.save(new Supplier(dataRegisterSupplier));
        return new DataResponseSupplier(supplier.getId(), supplier.getNombre(),supplier.getEmail(),supplier.getTelefono(),supplier.getDocumento(),
                supplier.getService().toString(),
                new DatosDireccion(supplier.getDireccion().getCalle(), supplier.getDireccion().getCiudad(), supplier.getDireccion().getDistrito()));
    }

    @Override
    public Page<DataListSupplier> listadoSupplier(Pageable pageable) {
        return supplierRepository.findByStatusTrue(pageable).map(DataListSupplier::new);
    }

    @Override
    public DataResponseSupplier retornarSupplier(Long id) {
        Supplier supplier = supplierRepository.getReferenceById(id);
        return new DataResponseSupplier(supplier.getId(), supplier.getNombre(),supplier.getEmail(),supplier.getTelefono(),supplier.getDocumento(),
                supplier.getService().toString(),
                new DatosDireccion(supplier.getDireccion().getCalle(), supplier.getDireccion().getCiudad(), supplier.getDireccion().getDistrito()));
    }

    @Override
    @Transactional
    public DataResponseSupplier actualizarSupplier(@Valid DataUpdateSupplier dataUpdateSupplier) {
        Supplier supplier = supplierRepository.getReferenceById(dataUpdateSupplier.id());
        supplier.actualizarDatos(dataUpdateSupplier);
        return new DataResponseSupplier(supplier.getId(), supplier.getNombre(),supplier.getEmail(),supplier.getTelefono(),supplier.getDocumento(),
                supplier.getService().toString(),
                new DatosDireccion(supplier.getDireccion().getCalle(), supplier.getDireccion().getCiudad(),supplier.getDireccion().getDistrito()));
    }

    @Override
    public void desactivarSupplier(Long id) {
        Supplier supplier = supplierRepository.getReferenceById(id);
        supplier.desactivarSupplier();

    }
}


