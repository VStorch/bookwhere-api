package com.viniciusstorch.bookwhere_api.library.mapper;

import com.viniciusstorch.bookwhere_api.library.dto.request.AddressRequestDTO;
import com.viniciusstorch.bookwhere_api.library.model.Address;

public class AddressMapper {
    
    public static Address toEntity(AddressRequestDTO addressRequestDTO) {
        Address address = new Address();
        address.setState(addressRequestDTO.state());
        address.setCity(addressRequestDTO.city());
        address.setNeighborhood(addressRequestDTO.neighborhood());
        address.setStreet(addressRequestDTO.street());
        address.setNumber(addressRequestDTO.number());
        address.setAddressComplement(addressRequestDTO.addressComplement());
        return address;
    }
}
