package com.aspiremanagement.filterscontrolleradmin;

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

import com.aspiremanagement.filtermodeladmin.BrandResponse;
import com.aspiremanagement.filtermodeladmin.Brands;
import com.aspiremanagement.filterserviceadmin.BrandsService;
import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_Brands_Json;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class BrandController {

	@Autowired
	BrandsService brandsService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/fetch-all-brands", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Brands_Json getAllBrands(HttpServletRequest request, HttpSession session) {

		Manage_Brands_Json manage_Brands_Json = new Manage_Brands_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<BrandResponse> brands = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_Brands_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Brands_Json.setFetchAllBrands(brandsService.fetchAllBrands());
				manage_Brands_Json.setMessage("Fetch All Brands Successfully");
			} else {
				manage_Brands_Json.setFetchAllBrands(brands);
				manage_Brands_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Brands_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_Brands_Json.setFetchAllBrands(brands);
			manage_Brands_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Brands_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Brands_Json;
	}

	@RequestMapping(value = "/fetch-category-wise-brands/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Brands_Json getAllCategoryWiseBrands(@PathVariable String categoryId,HttpServletRequest request, HttpSession session) {

		Manage_Brands_Json manage_Brands_Json = new Manage_Brands_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<BrandResponse> brands = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_Brands_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Brands_Json.setGetAllCategoryWiseBrands(brandsService.getAllCategoryWiseBrands(Long.parseLong(categoryId)));
				manage_Brands_Json.setMessage("Fetch All Category Wise Brands Successfully");
			} else {
				manage_Brands_Json.setGetAllCategoryWiseBrands(brands);
				manage_Brands_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Brands_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_Brands_Json.setGetAllCategoryWiseBrands(brands);
			manage_Brands_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Brands_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Brands_Json;
	}

	
	@RequestMapping(value = "/save-brand/{categoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveBrand(@PathVariable String categoryId,@RequestBody Brands brands, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Brands brand=brandsService.getBrandsByName(brands.getBrandName(),Long.parseLong(categoryId));
					if(brand==null)
					{ brands.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					brands.setCategoryId(Long.parseLong(categoryId));
					brandsService.saveBrand(brands);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Added Admin Brand where Id is " + brands.getId());

					resp.setMessage("Brand Inserted Successfully");
					resp.setStatusCode(Constants.CREATED.constant);
					}else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("Brand is already exist");	
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

	@RequestMapping(value = "/fetch-brand/{brandId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Brands_Json getBrand(@PathVariable String brandId,HttpServletRequest request, HttpSession session) {

		Manage_Brands_Json manage_Brands_Json = new Manage_Brands_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		Optional<Brands> brand=Optional.empty();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_Brands_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Brands_Json.setGetBrand(brandsService.getBrand(Long.parseLong(brandId)));
				manage_Brands_Json.setMessage("Fetch Brand Successfully");
				
			}else {
				manage_Brands_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Brands_Json.setGetBrand(brand);
				manage_Brands_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				
			}
		}else {
			manage_Brands_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Brands_Json.setGetBrand(brand);
			manage_Brands_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Brands_Json;
	}
	
	@RequestMapping(value = "/update-brand/{brandId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateSubCategory(@PathVariable String brandId,@RequestBody Brands brands, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					
					brands.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					brands.setId(Long.parseLong(brandId));
					brandsService.saveBrand(brands);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Brand where Id is " + brands.getId());

					resp.setMessage("Brand Updated Successfully");
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
	
	@RequestMapping(value = "/delete-brand/{brandId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteCategoryMaster(@PathVariable String brandId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Optional<Brands> brand=brandsService.getBrand(Long.parseLong(brandId));
					if(brand!=null)
					{
						brandsService.deleteBrand(Long.parseLong(brandId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Brand where Id is " + brandId);
						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Brand Deleted Successfully !!!");
					}else {
						resp.setStatusCode(Constants.NOT_FOUND.constant);
						resp.setMessage("This Brand will doesn't exist");
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
