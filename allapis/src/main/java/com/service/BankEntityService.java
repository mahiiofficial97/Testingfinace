package com.service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.model.Admin;
import com.model.BankEntity;
import com.model.JsonResponse;
import com.repository.BankEntityRepo;

import jakarta.websocket.Session;

@Service
public class BankEntityService {

	@Autowired
	BankEntityRepo bankEntityRepo;
	
	@Value("${admin.email}")
	  private String fromEmail;
	
	@Value("${admin.password}")
	  private String fromPassword;
	

	public BankEntity findbyemail(String email) {
		
		return bankEntityRepo.findbyemail(email);
	}
	
	public void saved(BankEntity bankEntity) {
		bankEntityRepo.save(bankEntity);

	}

	public Optional<BankEntity> getById(Long id) {
		return bankEntityRepo.findById(id);
	}
	
	public Optional<BankEntity> getById(String idData) {
	    try {
	        Long id = Long.parseLong(idData.split("=")[1].split(",")[0]);
	        return bankEntityRepo.findById(id);
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid admininstance format: " + idData);
	        return Optional.empty();
	    }
	}

	

	public BankEntity fetchByEmailId(String email) {
		
		return bankEntityRepo.fetchByEmailId(email);
	}
	
//	public BankEntity getUserDetailsById(Long adminId) {
//		Optional<BankEntity> admin=bankEntityRepo.findById(adminId);
//		JsonResponse resp=new JsonResponse();
//		resp.set (admin.get().getId());
//		resp.setEmail(admin.get().getEmail());
//		resp.setAdminName(admin.get().getAdminName());
//		
//		resp.setAddress(admin.get().getAddress());
//		resp.setPhone(admin.get().getPhone());
//		resp.setRole(admin.get().getRole());
//		resp.setUserStatus(admin.get().getUserStatus());
//		resp.setProfileImage(admin.get().getProfileImage());
//		
//		return resp;
//	}

	
//	public boolean sendEmail(String subject,String message,String to)
//	{
//		boolean f=false;
//		//variable for email...
//	//	System.out.println(fromEmail+" "+fromPassword);
//		String host="smtp.gmail.com";
//		
//		Properties properties=System.getProperties();
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.port", "465");
//		properties.put("mail.smtp.ssl.enable", "true");
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.transport.protocol", "smtp");
//		
//		// step-1 To get Session Object...
//		Session session= Session.getInstance(properties, new Authenticator() {
//
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication (fromEmail, fromPassword);
//			}
	
}
