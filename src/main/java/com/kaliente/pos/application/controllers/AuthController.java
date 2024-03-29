package com.kaliente.pos.application.controllers;

import java.util.List;

import com.kaliente.pos.application.configs.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaliente.pos.application.models.base.BaseResponse;
import com.kaliente.pos.application.requests.auth.AuthenticationRequestDto;
import com.kaliente.pos.application.responses.auth.AuthenticationResponseDto;
import com.kaliente.pos.application.responses.auth.GetPersonnelListResponseDto;
import com.kaliente.pos.application.models.auth.PersonnelDetailsDto;
import com.kaliente.pos.application.requests.auth.RegisterAdminRequestDto;
import com.kaliente.pos.application.responses.auth.RegisterAdminResponseDto;
import com.kaliente.pos.application.requests.auth.RegisterPersonnelRequestDto;
import com.kaliente.pos.application.responses.auth.RegisterPersonnelResponseDto;
import com.kaliente.pos.application.requests.auth.RegisterRequestDto;
import com.kaliente.pos.application.services.AuthService;
import com.kaliente.pos.sharedkernel.Constants;
import com.kaliente.pos.application.utilities.JwtUtility;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authManager;

	private final JwtUtility jwtUtility;

	private final AuthService authService;


	@Autowired
	public AuthController(AuthenticationManager authManager, JwtUtility jwtUtility, AuthService authService) {
		this.authManager = authManager;
		this.jwtUtility = jwtUtility;
		this.authService = authService;
	}
	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/getPersonnelList")
	public ResponseEntity<BaseResponse<GetPersonnelListResponseDto>> getPersonnelList() {
		List<PersonnelDetailsDto> personnelList = authService.getPersonnelList();
		var response = new GetPersonnelListResponseDto(personnelList);
		return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
	
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<BaseResponse<AuthenticationResponseDto>> authenticate(@RequestBody AuthenticationRequestDto requestDto) throws Exception {
		final Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jToken = jwtUtility.generateToken(authentication);

		var response = new AuthenticationResponseDto(jToken);

		return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
	
	}
	
	@GetMapping(value="health_check")
	public ResponseEntity<String> health_check() throws Exception {
		return ResponseEntity.ok("Things are looking good!");
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
	@PostMapping("/registerAdmin")
	public ResponseEntity<BaseResponse<RegisterAdminResponseDto>> registerAdmin(@RequestBody RegisterAdminRequestDto requestDto) {
		var response = authService.registerAdmin(requestDto);
		return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
	}
	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/registerPersonnel")
	public ResponseEntity<BaseResponse<RegisterPersonnelResponseDto>> registerPersonnel(@RequestBody RegisterPersonnelRequestDto requestDto) {
		var registeredPersonnel = authService.registerPersonnel(requestDto);
		return new ResponseEntity<>(new BaseResponse<>(registeredPersonnel, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterRequestDto requestDto) {
		String token = authService.register(requestDto);
		return new ResponseEntity<>(
			new BaseResponse<>(token, Constants.OPERATION_SUCCESS_MESSAGE),
			HttpStatus.CREATED
		);
	}
	
}
