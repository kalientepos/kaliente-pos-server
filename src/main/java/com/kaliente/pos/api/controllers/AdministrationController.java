package com.kaliente.pos.api.controllers;

import java.util.ArrayList;
import java.util.UUID;

import com.kaliente.pos.application.models.base.BaseResponse;
import com.kaliente.pos.application.models.dtos.administration.GetAllRolesResponse;
import com.kaliente.pos.application.models.dtos.auth.PersonnelDetailsDto;
import com.kaliente.pos.application.responses.administration.GetPersonnelByIdResponse;
import com.kaliente.pos.application.responses.administration.RemovePersonnelResponse;
import com.kaliente.pos.application.services.AdministrationService;
import com.kaliente.pos.domain.useraggregate.Role;
import com.kaliente.pos.domain.useraggregate.User;
import com.kaliente.pos.sharedkernel.Constants;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;

@RestController
@RequestMapping("/administration")
public class AdministrationController {
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AdministrationService adminService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/getPersonnelById")
    public ResponseEntity<BaseResponse<GetPersonnelByIdResponse>> getById(@RequestParam UUID personnelId) throws Exception {
        User foundUser = adminService.getPersonnelById(personnelId);
        
        PersonnelDetailsDto personnelDto = this.mapper.map(foundUser, PersonnelDetailsDto.class);
        var response = new GetPersonnelByIdResponse(personnelDto);
        
        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @GetMapping("/getRoles")
    public ResponseEntity<BaseResponse<ArrayList<GetAllRolesResponse>>> getSystemRoles() throws Exception {
        ArrayList<Role> sysRoles = adminService.getSystemRoles();

        var response = new ArrayList<GetAllRolesResponse>();
        sysRoles.stream().forEach(x -> response.add(new GetAllRolesResponse(x.getTitle())));


        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/removePersonnel")
    public ResponseEntity<BaseResponse<RemovePersonnelResponse>> removePersonnel(@RequestParam String personnelEmail) throws Exception {
        User serviceResult = adminService.removePersonnel(personnelEmail);
        var response = new RemovePersonnelResponse(serviceResult.getId(), serviceResult.getEmail());
        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);

    }

}
