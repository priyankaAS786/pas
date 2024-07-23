package com.apiexamples.service;
import com.apiexamples.entity.Registration;
import com.apiexamples.exception.ResourceNotFound;
import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {


    private RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
        Registration registration = maptoEntity(registrationDto);
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDto dto = maptoDto(savedEntity);
        return dto;
    }

    Registration maptoEntity(RegistrationDto dto) {
        Registration reg = new Registration();
        reg.setName(dto.getName());
        reg.setEmail(dto.getEmail());
        reg.setMobile(dto.getMobile());
        return reg;

    }

    RegistrationDto maptoDto(Registration entity) {
        RegistrationDto dto = new RegistrationDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMobile(entity.getMobile());
        return dto;
    }

    @Override
    public void deleteRegById(Long id) {

        registrationRepository.deleteById(id);
    }

    @Override
    public RegistrationDto updateRegistrationById(Long id, RegistrationDto registrationDto) {
        Optional<Registration> opReg = registrationRepository.findById(id);
        Registration registration = opReg.get();

        registration.setName(registrationDto.getName());
        registration.setEmail(registrationDto.getEmail());
        registration.setMobile(registrationDto.getMobile());
        Registration saveEntity = registrationRepository.save(registration);
        RegistrationDto dto = maptoDto(saveEntity);
        return dto;
    }

    @Override
    public List<RegistrationDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Ternary Operator

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Registration> all = registrationRepository.findAll(pageable);
        List<Registration> registrations = all.getContent();
        List<RegistrationDto> dtos = registrations.stream().map(r -> maptoDto(r)).collect(Collectors.toList());

        System.out.println(all.getTotalPages());
        System.out.println(all.isLast());
        System.out.println(all.isFirst());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        return dtos;
    }

    @Override
    public RegistrationDto getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Registration not found with id:" + id)
        );
        RegistrationDto dto = maptoDto(registration);
        return dto;
    }
}
