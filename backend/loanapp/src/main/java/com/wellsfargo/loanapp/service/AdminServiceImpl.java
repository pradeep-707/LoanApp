package com.wellsfargo.loanapp.service;

import java.util.List;
import java.util.Optional;

import com.wellsfargo.loanapp.utils.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wellsfargo.loanapp.dao.AdminRepository;
import com.wellsfargo.loanapp.dto.AdminDTO;
import com.wellsfargo.loanapp.model.Admin;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ResponseEntity<AdminDTO> validateAdmin(AdminDTO adminDto) {
		Optional<Admin> optionalAdmin = adminRepository.findById(adminDto.getUsername());
		if (optionalAdmin.isPresent()) {
			Admin admin = optionalAdmin.get();
			if (admin.getPassword().equals(adminDto.getPassword())) {
				adminDto = modelMapper.map(admin, AdminDTO.class);
				return ResponseGenerator.generateResponse(HttpStatus.OK, "Successfully Logged In", adminDto);
			} else {
				return ResponseGenerator.generateResponse(HttpStatus.UNAUTHORIZED, "Login Failed : Invalid password", null);
			}
		}
		return ResponseGenerator.generateResponse(HttpStatus.UNAUTHORIZED, "Login Failed : Invalid Admin Username", null);
	}
	
//	public boolean verfiyAdminUsername(String userName)
//	{
//		return adminRepository.findById(userName).isPresent();
//    }

	public boolean verifyAdmin(UserDetails userDetails)
	{
		String employeeId = userDetails.getUsername();
    	List<? extends GrantedAuthority> role = userDetails.getAuthorities().stream().toList();
		return role.get(0).toString().equals(Role.ADMIN.name());
	}

}
