package com.apiexamples.service;

import com.apiexamples.payload.RegistrationDto;

import java.util.List;

public interface RegistrationService {

    public RegistrationDto createRegistration(RegistrationDto registrationDto);

    void deleteRegById(Long id);

   public RegistrationDto updateRegistrationById(Long id,RegistrationDto registrationDto);


   public List<RegistrationDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir);

    public RegistrationDto getRegistrationById(Long id);
}



