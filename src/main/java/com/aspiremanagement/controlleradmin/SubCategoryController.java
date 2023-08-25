package com.aspiremanagement.controlleradmin;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.modeladmin.SubCategory;
import com.aspiremanagement.modeladmin.SubCategoryResponse;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.serviceadmin.SubCategoryService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_SubCategory_Json;
@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class SubCategoryController {

	@Autowired
	SubCategoryService subCategoryService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/fetch-all-sub-categories", method = RequestMethod.GET)
	@ResponseBody
	public Manage_SubCategory_Json getAllSubCategories(HttpServletRequest request, HttpSession session) {

		Manage_SubCategory_Json manage_SubCategory_Json = new Manage_SubCategory_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<SubCategoryResponse> subCategories = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_SubCategory_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_SubCategory_Json.setFectchAllSubCategories(subCategoryService.fetchAllSubCategories());
				manage_SubCategory_Json.setMessage("Fetch All Sub Categories Successfully");
			} else {
				manage_SubCategory_Json.setFectchAllSubCategories(subCategories);
				manage_SubCategory_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_SubCategory_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_SubCategory_Json.setFectchAllSubCategories(subCategories);
			manage_SubCategory_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_SubCategory_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_SubCategory_Json;
	}

	@RequestMapping(value = "/fetch-all-category-wise-subcategory/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_SubCategory_Json getAllCategoryWiseSubCategory(@PathVariable String categoryId,HttpServletRequest request, HttpSession session) {

		Manage_SubCategory_Json manage_SubCategory_Json = new Manage_SubCategory_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<SubCategoryResponse> subCategories = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_SubCategory_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_SubCategory_Json.setFetchCategoryWiseSubCategory(subCategoryService.fetchCategoryWiseSubCategory(Long.parseLong(categoryId)));
				manage_SubCategory_Json.setMessage("Fetch All Category Wise Sub-Categories Successfully");
			} else {
				manage_SubCategory_Json.setFetchCategoryWiseSubCategory(subCategories);
				manage_SubCategory_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_SubCategory_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_SubCategory_Json.setFetchCategoryWiseSubCategory(subCategories);
			manage_SubCategory_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_SubCategory_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_SubCategory_Json;
	}
	@RequestMapping(value = "/save-sub-categories/{categoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveSubCategory(@PathVariable String categoryId,@RequestBody SubCategory subCategory, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

					SubCategory category = subCategoryService.getCategory(subCategory.getSubCatogotyName());
					if (category == null) {
						subCategory.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
								+ session.getAttribute("last_name").toString());
						subCategory.setCategoryId(Long.parseLong(categoryId));
						subCategoryService.saveSubCategory(subCategory);
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Added Admin Sub Category where Id is " + subCategory.getId());

						resp.setMessage("Sub-Category Inserted Successfully");
						resp.setStatusCode(Constants.CREATED.constant);
					} else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("Sub Category is already exist");
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

	@RequestMapping(value = "/fetch-sub-category/{subCategoryId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_SubCategory_Json getCategory(@PathVariable String subCategoryId, HttpServletRequest request,
			HttpSession session) {
		Manage_SubCategory_Json manage_SubCategory_Json = new Manage_SubCategory_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		Optional<SubCategory> subCategory=Optional.empty();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				manage_SubCategory_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_SubCategory_Json
						.setGetSubCategory(subCategoryService.fetchSubCategory(Long.parseLong(subCategoryId)));
			} else {
				manage_SubCategory_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_SubCategory_Json.setGetSubCategory(subCategory);
				manage_SubCategory_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_SubCategory_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_SubCategory_Json.setGetSubCategory(subCategory);
			manage_SubCategory_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_SubCategory_Json;
	}
	@RequestMapping(value = "/update-subcategories/{subCategoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateSubCategory(@RequestBody SubCategory subCategory,
			@PathVariable String subCategoryId, RedirectAttributes redirectAttributes, HttpSession session,
			HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					
					subCategory.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
//					Optional<SubCategory> subCat=subCategoryService.fetchSubCategory(Long.parseLong(subCategoryId));
//					subCategory.setCategoryId(subCat.get().getCategoryId());
					subCategory.setId(Long.parseLong(subCategoryId));
					subCategoryService.saveSubCategory(subCategory);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Sub Category where Id is " + subCategory.getId());

					resp.setMessage("Sub Category Updated Successfully");
					resp.setStatusCode(Constants.SUCCESS.constant);
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
	@RequestMapping(value = "/delete-sub-categories/{subCategoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteCategoryMaster(@PathVariable String subCategoryId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Optional<SubCategory> subCategory=subCategoryService.fetchSubCategoryById(Long.parseLong(subCategoryId));
					if(subCategory!=null)
					{
						subCategoryService.deteteSubCategory(Long.parseLong(subCategoryId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Sub Category where Id is " + subCategoryId);
						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Sub Categoty Deleted Successfully !!!");
						
					}else {
						resp.setStatusCode(Constants.NOT_FOUND.constant);
						resp.setMessage("This Sub Category will doesn't exist");
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
