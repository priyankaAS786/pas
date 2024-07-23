package com.apiexamples.controller;

import com.apiexamples.entity.Registration;
import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    //http://localhost:8080/api/v1/registration
    @PostMapping
    public ResponseEntity<?> addRegistration(  //  ? - returning multiple objects
            @Valid @RequestBody RegistrationDto registrationDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);
        }
        RegistrationDto regdto = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(regdto, HttpStatus.CREATED); //201 status
    }

    //http://localhost:8080/api/v1/registration?id=
    @DeleteMapping
    public ResponseEntity<String> deleteRegistrationById(@RequestParam Long id){
        registrationService.deleteRegById(id);
        return new ResponseEntity<>("Registration Deleted", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RegistrationDto> updateRegistrationById(
            @RequestParam Long id, @RequestBody RegistrationDto registrationDto){

        RegistrationDto dto = registrationService.updateRegistrationById(id, registrationDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/registration?pageNo=0&pageSize=5&sortBy=email&sortDir=asc
    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations(
            @RequestParam(name="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name="pageSize", defaultValue = "0", required = false) int pageSize,
            @RequestParam(name="soryBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(name="sortDir", defaultValue = "name", required = false) String sortDir)
    {

        List<RegistrationDto> allDtos = registrationService.getAllRegistrations(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allDtos,HttpStatus.OK);
    }


    @GetMapping("/byId")
    public ResponseEntity<RegistrationDto> getRegistrationById(@RequestParam Long id){
        RegistrationDto Dto = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(Dto, HttpStatus.OK);
    }
}

