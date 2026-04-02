package com.viniciusstorch.bookwhere_api.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.library.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
