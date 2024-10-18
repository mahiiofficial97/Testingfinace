package com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

//import com.controller.springjwt.security.jwt.JwtUtils;

//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;


//import org.apache.commons.lang3.RandomUtils;
//import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.enumm.Constants;
import com.model.BankEntity;
import com.model.JsonResponse;
import com.repository.BankEntityRepo;
import com.service.BankEntityService;

import ch.qos.logback.core.boolex.Matcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bank")
public class BankEntityController {

	@Autowired
	BankEntityService bankEntityService;

	@Autowired
	BankEntityRepo bankEntityRepo;

//	SAVE

	@PostMapping("/savee")

	public JsonResponse savedata(@RequestBody BankEntity bankEntity) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		JsonResponse resp = new JsonResponse();

		BankEntity name = bankEntityService.findbyemail(bankEntity.getEmail());

		if (name == null) {

			bankEntity.setPass(passwordEncoder.encode(bankEntity.getPass()));

			bankEntityService.saved(bankEntity);

			resp.setStatus("200");
			resp.setMessage("Admin created successfully");
			resp.setResult("success");

		} else {
			resp.setStatus("400");
			resp.setMessage("this email is already exist ");
			resp.setResult("Unsuccessful");
		}
		return resp;
	}

	@RequestMapping("/login")
	public String login() {
		return "Admin/login";
	}

	String uploadprofileDirectory = Constants.UPLOADPROFILEIMAGEDIRECTORY.Constant;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

//	public static boolean validateEmail(String emailStr) {
//
//		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
//		return matcher;
//	}

	public static boolean passwordValidator(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return password.matches(pattern);
	}

	public boolean mobNumberValidator(String mobNo) {
		String mobpattern = "^[0-9]{10}$";
		return mobNo.matches(mobpattern);
	}

//	REGISTRATION

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody

	public JsonResponse register(BankEntity bank, BindingResult bindingResult,
			@RequestParam("registration") MultipartFile profilepicture, RedirectAttributes redirectAttributes) {

		JsonResponse response = new JsonResponse();

		try {

			BankEntity registr = bankEntityService.fetchByEmailId(bank.getEmail());

			if (registr == null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				bank.setPass(passwordEncoder.encode(bank.getPass()));

				if (!profilepicture.isEmpty() && profilepicture != null) {

					String datename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
					String filename = datename + "-"
							+ profilepicture.getOriginalFilename().replace(" ", "-").toLowerCase();



					Path filepath = Paths.get(uploadprofileDirectory, filename);
					try {
						Files.write(filepath, profilepicture.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					bank.setProfileImage(filename);
				} else {
					bank.setProfileImage(" ");
				}
				bankEntityRepo.save(bank);
				response.setStatus(Constants.CREATED.Constant);
				response.setResult("Registration successful");
				response.setMessage("Admin Registered Successfully !!!");
			} else {
				response.setStatus(Constants.ALREADY_EXIST.Constant);
				response.setResult("Registration Unsuccessful");
				response.setMessage("Admin with this email ID already exists");
			}
		} catch (Exception exception) {
			redirectAttributes.addFlashAttribute("message", "Error:" + exception.getMessage());
			exception.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "Invalid Credintials");
		return response;
	}

	// LOGIN api

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse loginAdmin(@RequestBody BankEntity bank, HttpSession session) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");
		

//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		System.out.println("JWT is " + jwt);
		

		session.setMaxInactiveInterval(300);

		if (adminSession != null) {
			response.setMessage("You are already logged in the System");
			response.setStatus("200");
			response.setResult("success");
			return response;
		}

		BankEntity existingAdmin = bankEntityService.findbyemail(bank.getEmail());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (existingAdmin != null && existingAdmin.getStatus().equals("Active")) {
			boolean passwordVerify = passwordEncoder.matches(bank.getPass(), existingAdmin.getPass());

			if (passwordVerify) {
				session.setAttribute("admindataa", existingAdmin);
				response.setStatus("200");
				response.setMessage("Login successfully");
				response.setResult("success");
			} else {
				response.setStatus("300");
				response.setMessage("Enter valid password");
				response.setResult("failure");
			}
		} else {
			response.setStatus("400");
			response.setMessage("This email does not exist.or account is not active ");
			response.setResult("failure");
		}

		return response;
	}
	
//	GET ADMIN DETAILS
	
//	@RequestMapping(value = "/get-admin-details", method = RequestMethod.GET)
//	@ResponseBody
//	public JsonResponse getAdminDetails(HttpServletRequest request, HttpSession session) {
//		JsonResponse response = new JsonResponse();
//		
////		AdminResponse response = null;
//		Object jwtToken = session.getAttribute("adminJwtToken");
//		System.out.println(jwtToken);
//
//		if (jwtToken != null) {
//
//			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.Constant))) {
//
//				Long adminId = Long.parseLong(session.getAttribute("admindataa").toString());
//				response.setGetAdminDetails(bankEntityService.getUserDetailsById(adminId));
//
//			} else {
////				response.setGetAdminDetails(response);
//				response.setStatus(Constants.INVALID_TOKEN.Constant);
//				response.setMessage(Constants.INVALID_TOKEN_MESSAGE.Constant);
//			}
//		} else {
////			response.setGetAdminDetails(response);
//			response.setStatus(Constants.SESSION_EXPIRED.Constant);
//			response.setMessage(Constants.SESSION_EXPIRED_MESSAGE.Constant);
//		}
//		return response;
//	}


//	VERIFY PASSWORD

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse verifypass(HttpSession session, @RequestBody BankEntity bank) {

		JsonResponse response = new JsonResponse();

		String oldpass = bank.getPass();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		System.out.println("user id : " + session.getAttribute("admindataa"));

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession != null) {
			Optional<BankEntity> passwordd = bankEntityService.getById(adminSession.toString());

			if (passwordd.isPresent()) {
				boolean verify = passwordEncoder.matches(oldpass, passwordd.get().getPass());

				if (verify) {
					response.setStatus(Constants.SUCCESS.Constant);
					response.setMessage("Password verified");
					response.setResult("success");
				} else {
					response.setStatus(Constants.BAD_REQUEST.Constant);
					response.setMessage("Enter correct password");
					response.setResult("fail");
				}

			} else {
				response.setStatus(Constants.BAD_REQUEST.Constant);
				response.setMessage("user details not found");
				response.setResult(Constants.BAD_REQUEST.Constant);
			}

		} else {
			response.setStatus(Constants.BAD_REQUEST.Constant);
			response.setMessage("Session expired");
			response.setResult("failture");
		}

		return response;

	}
	
//	SEND OTP BY MAIL
	
//	@RequestMapping(value = "/send-otp", method = RequestMethod.POST)
//	@ResponseBody
//	public JsonResponse sendOtp(@RequestBody BankEntity user, HttpSession session) {
//		String email = user.getEmail();
//		JsonResponse jsonResponse = new JsonResponse();
//		BankEntity userManager = bankEntityService.fetchByEmailId(user.getEmail());
//		if (userManager != null) {
//
//			// generating 4- digit otp...
//			// System.out.println(email);
//
//			int otp = RandomUtils.nextInt(111111, 999999);
//			session.setAttribute("email", email);
//			session.setMaxInactiveInterval(180);
//			BankEntity setOtp = new BankEntity();
//			setOtp.setName(userManager.getName());
//			setOtp.setEmail(userManager.getEmail());
//			setOtp.setCity(userManager.getCity());
//			setOtp.setLastdate(userManager.getLastdate());
//			setOtp.setPass(userManager.getPass());
//			setOtp.setProfileImage(userManager.getProfileImage());
//			setOtp.setStatus(userManager.getStatus());
//			setOtp.setId(userManager.getId());
//			setOtp.setResetPasswordToken(Integer.toString(otp));
//			bankEntityService.saved(setOtp);
//			// write code for send otp to your email...
//			// System.out.println(otp);
//
//			String name = userManager.getName() ;
//
//			String subject = "Email Otp(One Time Password) from " + Constants.COMPANY_NAME.Constant
//					+ " for an reset password";
//			String message = "Dear " + "<b>" + name + "</b>" + ",<br>" + // "\r\n" +
//					"For security reasons, you're required to use the following One Time Password(OTP) to login into "
//					+ "<b>" + Constants.COMPANY_NAME.Constant + "</b>" + " admin dashboard...<br>" + "<br>" + "OTP: "
//					+ "<b>" + otp + "</b>" + "<br>" + "<br>" + "Thanks & Regards,<br>" +
//
//					"Customer Service<br>" + " " + Constants.CUSTOMER_SERVICE_MOB.Constant + "<br>"
//					+ Constants.CUSTOMER_SERVICE_EMAIL.Constant + "<br>" + "                    <br>" +
//
//					"<br>" + "<br>" + "";
//			String to = email;
//
//			Boolean flag = bankEntityService.sendEmail(subject, message, to);
//			// System.out.println(flag);
//			if (flag) {
//				jsonResponse.setStatus(Constants.SUCCESS.Constant);
//				jsonResponse.setResult("Send Otp");
//				jsonResponse.setMessage("Send Otp Successfully on your Resistered emailId !!!");
//			} else {
//				jsonResponse.setStatus(Constants.BAD_REQUEST.Constant);
//				jsonResponse.setResult("Doesn't Send Otp");
//				jsonResponse.setMessage("Something Went Wrong !!!");
//
//			}
//		} else {
//			jsonResponse.setStatus(Constants.NOT_FOUND.Constant);
//
//			jsonResponse.setMessage("Email is not Resistered!!!");
//		}
//		return jsonResponse;
//	}

//	SET NEW PASSWORD

	@RequestMapping(value = "/set_new", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse setpass(HttpSession session, @RequestParam("newpassword") String newpassword) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession != null) {
			Optional<BankEntity> admin = bankEntityService.getById(adminSession.toString());

			if (admin.isPresent()) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				admin.get().setPass(passwordEncoder.encode(newpassword));
				bankEntityService.saved(admin.get());

				response.setStatus(Constants.SUCCESS.Constant);
				response.setMessage("Password change successfully");
				response.setResult(Constants.SUCCESS.Constant);

			} else {
				response.setStatus(Constants.BAD_REQUEST.Constant);
				response.setMessage("user not found");

			}
		} else {
			response.setStatus(Constants.BAD_REQUEST.Constant);
			response.setMessage("Session expired");
			response.setResult(Constants.SUCCESS.Constant);
		}

		return response;
	}

//	TO CHECK ACTIVE OR INACTIVE

	@RequestMapping(value = "/status/{statusid}", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse checkstatus(HttpSession session, @PathVariable("statusid") String statusId) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession != null) {
			Long id = Long.parseLong(statusId);

			Optional<BankEntity> status = bankEntityService.getById(id);

			if (status.isPresent()) {
				BankEntity entity = status.get();

				String currentstatus = entity.getStatus();

				response.setStatus(Constants.SUCCESS.Constant);
				response.setMessage("Employee is: " + currentstatus);
				response.setResult(Constants.SUCCESS.Constant);

			} else {
				response.setStatus(Constants.BAD_REQUEST.Constant);
				response.setMessage("user not found");
				response.setResult(Constants.SUCCESS.Constant);

			}

		} else {
			response.setStatus(Constants.BAD_REQUEST.Constant);
			response.setMessage("Session expired");
			response.setResult(Constants.SUCCESS.Constant);
		}
		return response;
	}

//	CHANGE STATUS

//	1.ACTIVE	

	@RequestMapping(value = "/Active/{statusid}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changestatus(HttpSession session, @PathVariable("statusid") String statusId) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession != null) {
			Long id = Long.parseLong(statusId);

			Optional<BankEntity> update = bankEntityService.getById(id);

			if (update.isPresent()) {
				BankEntity entity = update.get();

				entity.setStatus(Constants.ACTIVE.Constant);
				bankEntityService.saved(entity);

				response.setStatus(Constants.SUCCESS.Constant);
				response.setMessage("Employee  with id  " + id + "is: " + entity.getStatus());
				response.setResult(Constants.SUCCESS.Constant);

			} else {
				response.setStatus(Constants.BAD_REQUEST.Constant);
				response.setMessage("user not found");
				response.setResult(Constants.SUCCESS.Constant);

			}

		} else {
			response.setStatus(Constants.BAD_REQUEST.Constant);
			response.setMessage("Session expired");
			response.setResult(Constants.SUCCESS.Constant);

		}

		return response;
	}

//	2.INACTIVE

	@RequestMapping(value = "/Inactive/{statusid}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeinactive(HttpSession session, @PathVariable("statusid") String statusId) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession != null) {
			Long id = Long.parseLong(statusId);

			Optional<BankEntity> update = bankEntityService.getById(id);

			if (update.isPresent()) {
				BankEntity entity = update.get();

				entity.setStatus(Constants.INACTIVE.Constant);
				bankEntityService.saved(entity);

				response.setStatus(Constants.SUCCESS.Constant);
				response.setMessage("Employee  with id  " + id + "is: " + entity.getStatus());
				response.setResult(Constants.SUCCESS.Constant);

			} else {
				response.setStatus(Constants.BAD_REQUEST.Constant);
				response.setMessage("user not found");
				response.setResult(Constants.SUCCESS.Constant);

			}

		} else {
			response.setStatus(Constants.BAD_REQUEST.Constant);
			response.setMessage("Session expired");
			response.setResult(Constants.SUCCESS.Constant);

		}

		return response;
	}

	// LOGOUT

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse logoutAdmin(HttpSession session) {

		JsonResponse response = new JsonResponse();

		Object adminSession = session.getAttribute("admindataa");

		if (adminSession == null) {
			response.setStatus("300");
			response.setMessage("Session already expired or logged out");
			response.setResult("failure");
		} else {
			session.removeAttribute("admindataa");
			response.setStatus("200");
			response.setMessage("Logout successfully");
			response.setResult("success");
		}

		return response;
	}

}
