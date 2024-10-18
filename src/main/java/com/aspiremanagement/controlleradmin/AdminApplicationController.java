package com.aspiremanagement.controlleradmin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.aspiremanagement.modeladmin.Admin;
import com.aspiremanagement.modeladmin.AdminResponse;
import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.repositoryadmin.AdminApplicationRepository;
import com.aspiremanagement.serviceadmin.AdminApplicationService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.ManageAdminJson;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminApplicationController {

	@Autowired
	AdminApplicationService adminApplicationService;

	@Autowired
	AdminApplicationRepository adminApplicationRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@RequestMapping("/login")
	public String login()
	{
		return "Admin/login";
	}
	
	
	String uploadprofileDirectory = Constants.UPLOADPROFILEIMAGEDIRECTORY.constant;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}

	public static boolean passwordValidator(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return password.matches(pattern);
	}

	public boolean mobNumberValidator(String mobNo) {
		String mobpattern = "^[0-9]{10}$";
		return mobNo.matches(mobpattern);
	}
	
	@RequestMapping(value = "/resistration", method = RequestMethod.POST)
	
	@ResponseBody
	public JsonResponse saveUser(Admin user, BindingResult bindingResult,
			@RequestParam("profileImage") MultipartFile profilePicture, RedirectAttributes redirectAttributes) {
		JsonResponse resp = new JsonResponse();
		try {

			Admin userManager = adminApplicationService.fetchByEmailId(user.getEmail());
			if (userManager == null) {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

				if (!profilePicture.isEmpty() && profilePicture != null) {
					String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
					String originalFileName = dateName + "-"
							+ profilePicture.getOriginalFilename().replace(" ", "-").toLowerCase();
					Path fileNameAndPath = Paths.get(uploadprofileDirectory, originalFileName);
					try {
						Files.write(fileNameAndPath, profilePicture.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					user.setProfileImage(originalFileName);
				} else {
					user.setProfileImage("");
				}

				adminApplicationService.saveUser(user);
				resp.setStatusCode(Constants.CREATED.constant);
				resp.setResult("Registration successful");
				resp.setMessage("Admin Registered Successfully !!!");
			} else {
				resp.setStatusCode(Constants.ALREADY_EXIST.constant);
				resp.setResult("Registration Unsuccessful");
				resp.setMessage("Admin with this email ID already exists");
			}
		} catch (Exception exception) {
			redirectAttributes.addFlashAttribute("message", "Error:" + exception.getMessage());
			exception.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "Invalid Credintials");
		return resp;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse adminLogin(@RequestBody Admin user, HttpSession session) {
		Admin adminDetails = adminApplicationService.fetchByEmailId(user.getEmail());

		JsonResponse jsonResponse = new JsonResponse();
		if (adminDetails != null) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(adminDetails.getId().toString(), user.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwt = jwtUtils.generateJwtToken(authentication);
			System.out.println("JWT is " + jwt);
			if (bCryptPasswordEncoder.matches(user.getPassword(), adminDetails.getPassword())) {
				session.setAttribute("sign-in-user", adminDetails.getId());
				session.setAttribute("first_name", adminDetails.getFirstName());
				session.setAttribute("last_name", adminDetails.getLastName());
				session.setAttribute("adminEmail", adminDetails.getEmail());
				session.setAttribute("adminJwtToken", jwt);
				session.setMaxInactiveInterval(2592000);
				jsonResponse.setStatusCode(Constants.SUCCESS.constant);
				jsonResponse.setResult("Login Successful");
				jsonResponse.setMessage("Admin Logged in Successfully !!!");
			} else {
				jsonResponse.setStatusCode(Constants.BAD_REQUEST.constant);
				jsonResponse.setResult("Bad Credentials");
				jsonResponse.setMessage("Incorrect Password Entered !!!");
			}
		} else {
			jsonResponse.setStatusCode(Constants.NOT_FOUND.constant);
			jsonResponse.setResult("Admin Doesn't exists");
			jsonResponse.setMessage("Admin with this email ID does not exists");
		}
		return jsonResponse;
	}
	
	@RequestMapping(value = "/get-admin-details", method = RequestMethod.GET)
	@ResponseBody
	public ManageAdminJson getAdminDetails(HttpServletRequest request,HttpSession session)
	{
		ManageAdminJson manageAdminJson=new ManageAdminJson();
		AdminResponse response=null;
		Object jwtToken = session.getAttribute("adminJwtToken");
		
		if (jwtToken != null) {

			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				
				Long adminId=Long.parseLong(session.getAttribute("sign-in-user").toString());
				manageAdminJson.setGetAdminDetails(adminApplicationService.getUserDetailsById(adminId));
				
			}else {
				manageAdminJson.setGetAdminDetails(response);
				manageAdminJson.setStatusCode(Constants.INVALID_TOKEN.constant);
				manageAdminJson.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manageAdminJson.setGetAdminDetails(response);
			manageAdminJson.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manageAdminJson.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manageAdminJson;
	}
	

	@RequestMapping(value = "/change-admin-password", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeAdminPassword( HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request, @RequestParam("newPass") String newPass) {

		Object email = session.getAttribute("adminEmail");
		Object checkToken = session.getAttribute("adminJwtToken");
		JsonResponse resp = new JsonResponse();
		try {
			if (checkToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

					Admin user = adminApplicationService.fetchByEmailId(email.toString());
					BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
					user.setPassword(bCryptPasswordEncoder.encode(newPass));

					adminApplicationService.saveUser(user);

					resp.setStatusCode(Constants.SUCCESS.constant);
					resp.setMessage("Password Change Successfully...");

				} else {
					resp.setStatusCode(Constants.INVALID_TOKEN.constant);
					resp.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				}
			} else {
				resp.setStatusCode(Constants.SESSION_EXPIRED.constant);
				resp.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			}
		} catch (

		Exception exception) {
			redirectAttributes.addFlashAttribute("message", "Error:" + exception.getMessage());

			exception.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", Constants.INVALID_CREADINTIALS.constant);
		return resp;
	}

	@RequestMapping(value = "/check-old-password", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changePassword(HttpServletRequest request, String oldPass, Model model,
			RedirectAttributes redirectAttributes, HttpSession session) {

		String email = (String) session.getAttribute("adminEmail");
		Admin user = adminApplicationService.fetchByEmailId(email.toString());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		JsonResponse jsonResponse = new JsonResponse();

		Object checkToken = session.getAttribute("adminJwtToken");
		try {
			if (checkToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					if (bCryptPasswordEncoder.matches(oldPass, user.getPassword())) {

						jsonResponse.setMessage("Old Password match Successfully...");
						jsonResponse.setStatusCode(Constants.SUCCESS.constant);
					} else {
						jsonResponse.setMessage("Please enter valid old Password");
						jsonResponse.setStatusCode(Constants.UNAUTHORIZED.constant);
					}
				} else {
					jsonResponse.setStatusCode(Constants.INVALID_TOKEN.constant);
					jsonResponse.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				}
			} else {
				jsonResponse.setStatusCode(Constants.SESSION_EXPIRED.constant);
				jsonResponse.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			}

		} catch (

		Exception exception) {
			redirectAttributes.addFlashAttribute("message", "Error:" + exception.getMessage());

			exception.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", Constants.INVALID_CREADINTIALS.constant);
		return jsonResponse;
	}

	@RequestMapping("/logout")
	public JsonResponse logout(HttpSession session) {
		Object checkToken = session.getAttribute("adminJwtToken");
		JsonResponse jsonResponse = new JsonResponse();
		if (checkToken != null) {
			session.setAttribute("sign-in-user", null);
			session.invalidate();
			jsonResponse.setStatusCode(Constants.SUCCESS.constant);
			jsonResponse.setResult("Logout Successful");
			jsonResponse.setMessage("Admin Logout Successfully !!!");
			return jsonResponse;
		} else {

			jsonResponse.setStatusCode(Constants.SESSION_EXPIRED.constant);
			jsonResponse.setResult("Fail Logout");
			jsonResponse.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			return jsonResponse;
		}

	}



	@RequestMapping(value = "/send-otp", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sendOtp(Admin user, @RequestParam("email") String email, HttpSession session) {

		JsonResponse jsonResponse = new JsonResponse();
		Admin userManager = adminApplicationService.fetchByEmailId(user.getEmail());
		if (userManager != null) {

			// generating 4- digit otp...
			// System.out.println(email);
			
			int otp = RandomUtils.nextInt(111111, 999999);
			session.setAttribute("email", email);
			session.setMaxInactiveInterval(180);
			Admin setOtp = new Admin();
			setOtp.setFirstName(userManager.getFirstName());
			setOtp.setLastName(userManager.getLastName());
			setOtp.setEmail(userManager.getEmail());
			setOtp.setCreatedOn(userManager.getCreatedOn());
			setOtp.setUserStatus(userManager.getUserStatus());
			setOtp.setProfileImage(userManager.getProfileImage());
			setOtp.setPassword(userManager.getPassword());
			setOtp.setPhone(userManager.getPhone());
			setOtp.setId(userManager.getId());
			setOtp.setResetPasswordToken(Integer.toString(otp));
			adminApplicationService.saveUser(setOtp);
			// write code for send otp to your email...
			// System.out.println(otp);

			String name = userManager.getFirstName() + " " + userManager.getLastName();

			String subject = "Email Otp(One Time Password) from " + Constants.COMPANY_NAME.constant
					+ " for an reset password";
			String message = "Dear " + "<b>" + name + "</b>" + ",<br>" + // "\r\n" +
					"For security reasons, you're required to use the following One Time Password(OTP) to login into "
					+ "<b>" + Constants.COMPANY_NAME.constant + "</b>" + " admin dashboard...<br>" + "<br>" + "OTP: "
					+ "<b>" + otp + "</b>" + "<br>" + "<br>" + "Thanks & Regards,<br>" +

					"Customer Service<br>" + " " + Constants.CUSTOMER_SERVICE_MOB.constant + "<br>"
					+ Constants.CUSTOMER_SERVICE_EMAIL.constant + "<br>" + "                    <br>" +

					"<br>" + "<br>" + "";
			String to = email;

			Boolean flag = adminApplicationService.sendEmail(subject, message, to);
			// System.out.println(flag);
			if (flag) {
				jsonResponse.setStatusCode(Constants.SUCCESS.constant);
				jsonResponse.setResult("Send Otp");
				jsonResponse.setMessage("Send Otp Successfully on your Resistered emailId !!!");
			} else {
				jsonResponse.setStatusCode(Constants.BAD_REQUEST.constant);
				jsonResponse.setResult("Doesn't Send Otp");
				jsonResponse.setMessage("Something Went Wrong !!!");

			}
		} else {
			jsonResponse.setStatusCode(Constants.NOT_FOUND.constant);

			jsonResponse.setMessage("Email is not Resistered!!!");
		}
		return jsonResponse;
	}

	@RequestMapping(value = "/verify-otp", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse verifyOtp(@RequestParam("otp") String emailotp, HttpSession session) {

		JsonResponse jsonResponse = new JsonResponse();

		Object email = session.getAttribute("email");
		if (email != null) {
			Admin user = adminApplicationService.fetchByEmailId(email.toString());
			String otp = user.getResetPasswordToken();
			if (otp.equals(emailotp)) {

				jsonResponse.setStatusCode(Constants.SUCCESS.constant);
				jsonResponse.setResult("Send Otp");
				jsonResponse.setMessage("Otp verify Successfully !!!");
			} else {
				jsonResponse.setStatusCode(Constants.UNAUTHORIZED.constant);
				jsonResponse.setMessage("Otp does not verify !!!");
			}
		} else {
			jsonResponse.setStatusCode(Constants.SESSION_EXPIRED.constant);
			jsonResponse.setResult("Session Expired");
			jsonResponse.setMessage("Session Expired !!! please Resend the opt.");
		}
		return jsonResponse;
	}

	@RequestMapping(value = "/set-new-pass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changePassword(Admin setNewPassword, @RequestParam("password") String password,
			HttpSession session) {
		    Object email = session.getAttribute("email");
	     	JsonResponse jsonResponse = new JsonResponse();
		    if (email != null) {

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

			Admin user = adminApplicationService.fetchByEmailId(email.toString());
			setNewPassword.setFirstName(user.getFirstName());
			setNewPassword.setLastName(user.getLastName());
			setNewPassword.setEmail(user.getEmail());
			setNewPassword.setCreatedOn(user.getCreatedOn());
			setNewPassword.setUserStatus(user.getUserStatus());
			setNewPassword.setProfileImage(user.getProfileImage());
			setNewPassword.setPassword(user.getPassword());
			setNewPassword.setPhone(user.getPhone());
			setNewPassword.setId(user.getId());
			setNewPassword.setPassword(bCryptPasswordEncoder.encode(password));
			adminApplicationService.saveUser(setNewPassword);
			session.setAttribute("email", null);
			session.invalidate();
			jsonResponse.setStatusCode(Constants.SUCCESS.constant);
			jsonResponse.setResult("Change Password");
			jsonResponse.setMessage("Change Password Successfully !!!");
		} else {
			jsonResponse.setStatusCode(Constants.SESSION_EXPIRED.constant);
			jsonResponse.setResult("Session Expired");
			jsonResponse.setMessage("Session Expired !!! please Resend the opt.");
		}
		return jsonResponse;
	}
	
	 @RequestMapping("/get-profile-image/{profileImage}")
	    public String getAdminProfileImage(@PathVariable("profileImage") String profileImage, HttpServletResponse response,HttpSession session) {
		 Object checkToken = session.getAttribute("adminJwtToken");
		 System.out.println(checkToken);
		 String message;
			//if (checkToken != null) {
				try {
	                byte b[] = Files.readAllBytes(Paths.get(uploadprofileDirectory + profileImage));
	                response.setContentLength(b.length);
	                response.setContentType("image/jpg");
	                ServletOutputStream os = response.getOutputStream();
	                os.write(b);
	                os.flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
				message="Image Fetch Successfully....";
	            
//			}else {
//				message=Constants.SESSION_EXPIRED_MESSAGE.constant;
//			}
			return null;
	 }

}
