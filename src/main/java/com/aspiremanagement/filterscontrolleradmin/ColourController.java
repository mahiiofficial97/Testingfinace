package com.aspiremanagement.filterscontrolleradmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspiremanagement.filtermodeladmin.Brands;
import com.aspiremanagement.filtermodeladmin.Colour;
import com.aspiremanagement.filtermodeladmin.ColourResponse;
import com.aspiremanagement.filterserviceadmin.ColourService;
import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.ManageColourJson;


@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class ColourController {

	@Autowired
	ColourService colourService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/fetch-all-colours", method = RequestMethod.GET)
	@ResponseBody
	public ManageColourJson fetchAllColours(HttpServletRequest request, HttpSession session) {
		ManageColourJson colourJson = new ManageColourJson();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<ColourResponse> colours = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				colourJson.setStatusCode(Constants.SUCCESS.constant);
				colourJson.setFetchAllColours(colourService.fetchAllColours());
				colourJson.setMessage("Fetch All Colours Successfully");
			} else {
				colourJson.setStatusCode(Constants.INVALID_TOKEN.constant);
				colourJson.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				colourJson.setFetchAllColours(colours);
			}
		} else {
			colourJson.setStatusCode(Constants.SESSION_EXPIRED.constant);
			colourJson.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			colourJson.setFetchAllColours(colours);
		}
		return colourJson;
	}

	@RequestMapping(value = "/fetch-category-wise-colours/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public ManageColourJson fetchCategoryWiseColours(@PathVariable String categoryId, HttpServletRequest request,
			HttpSession session) {
		ManageColourJson colourJson = new ManageColourJson();

		Object checkToken = session.getAttribute("adminJwtToken");
		List<ColourResponse> colours = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				colourJson.setFetchCategoryWiseAllColours(colourService.fetchAllCategoryWiseColours(categoryId));
				colourJson.setStatusCode(Constants.SUCCESS.constant);
				colourJson.setMessage("Fetch Category Wise Colours");
			} else {
				colourJson.setFetchCategoryWiseAllColours(colours);
				colourJson.setStatusCode(Constants.INVALID_TOKEN.constant);
				colourJson.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			colourJson.setStatusCode(Constants.SESSION_EXPIRED.constant);
			colourJson.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			colourJson.setFetchCategoryWiseAllColours(colours);

		}
		return colourJson;
	}

	@RequestMapping(value = "/save-colour/{categoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveColour(@PathVariable String categoryId, @RequestBody Colour colour,
			RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Colour c = colourService.getColourByNameAndCategoryId(colour.getColourName(),
							Long.parseLong(categoryId));
					if (c == null) {
						colour.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
								+ session.getAttribute("last_name").toString());
						colour.setCategoryId(Long.parseLong(categoryId));
						colourService.saveColour(colour);
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Added Admin Colour where Id is " + colour.getId());

						resp.setMessage("Colour Inserted Successfully");
						resp.setStatusCode(Constants.CREATED.constant);
					} else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("Colour is already exist");
					}

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

	@RequestMapping(value = "/fetch-colour/{colourId}", method = RequestMethod.GET)
	@ResponseBody
	public ManageColourJson fetchColour(@PathVariable String colourId, HttpServletRequest request,
			HttpSession session) {
		ManageColourJson colourJson = new ManageColourJson();

		Object checkToken = session.getAttribute("adminJwtToken");
		Optional<Colour> colours = Optional.empty();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				colourJson.setGetColour(colourService.getColour(Long.parseLong(colourId)));
				colourJson.setStatusCode(Constants.SUCCESS.constant);
				colourJson.setMessage("Colours Fetch Successfully");

			} else {
				colourJson.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				colourJson.setStatusCode(Constants.INVALID_TOKEN.constant);
				colourJson.setGetColour(colours);
			}
		} else {
			colourJson.setGetColour(colours);
			colourJson.setStatusCode(Constants.SESSION_EXPIRED.constant);
			colourJson.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return colourJson;
	}

	@RequestMapping(value = "/update-colour/{colourId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateColour(@PathVariable String colourId, @RequestBody Colour colour,
			RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					
					colour.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					colour.setId(Long.parseLong(colourId));
					colourService.saveColour(colour);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Colour where Id is " + colour.getId());

					resp.setMessage("Colour Updated Successfully");
					resp.setStatusCode(Constants.SUCCESS.constant);

				} else {
					resp.setStatusCode(Constants.INVALID_TOKEN.constant);
					resp.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				}
			}else {
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
	
	@RequestMapping(value = "/delete-colour/{colourId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteColour(@PathVariable String colourId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Optional<Colour> colour=colourService.getColour(Long.parseLong(colourId));
					if(colour.isPresent())
					{
						colourService.deleteColour(Long.parseLong(colourId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Colour where Id is " + colourId);
						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Colour Deleted Successfully !!!");
						
					}else {
						resp.setStatusCode(Constants.NOT_FOUND.constant);
						resp.setMessage("This Colour will doesn't exist");
					}
					
				}else {
					resp.setStatusCode(Constants.INVALID_TOKEN.constant);
					resp.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				}
			}else {
				resp.setStatusCode(Constants.SESSION_EXPIRED.constant);
				resp.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
				
			}
		}catch (

				Exception exception) {
					redirectAttributes.addFlashAttribute("message", "Error:" + exception.getMessage());

					exception.printStackTrace();
				}
				redirectAttributes.addFlashAttribute("message", Constants.INVALID_CREADINTIALS.constant);
				return resp;
			}

}
