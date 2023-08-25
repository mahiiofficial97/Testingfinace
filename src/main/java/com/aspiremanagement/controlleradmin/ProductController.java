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
import com.aspiremanagement.modeladmin.Product;
import com.aspiremanagement.modeladmin.ProductResponse;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.serviceadmin.ProductService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_Products_Json;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/fetch-all-products", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Products_Json getAllProducts(HttpServletRequest request, HttpSession session) {
		Manage_Products_Json manage_Products_Json = new Manage_Products_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<ProductResponse> products = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				manage_Products_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Products_Json.setFetchAllProducts(productService.fetchAllProducts());
				manage_Products_Json.setMessage("Fetch All Products Successfully");
			} else {
				manage_Products_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Products_Json.setFetchAllProducts(products);
				manage_Products_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_Products_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Products_Json.setFetchAllProducts(products);
			manage_Products_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Products_Json;
	}
	@RequestMapping(value = "/fetch-subcategory-wise-products/{subCategoryId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Products_Json getSubCategoryWiseProducts(@PathVariable String subCategoryId,HttpServletRequest request, HttpSession session) {
		Manage_Products_Json manage_Products_Json = new Manage_Products_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<ProductResponse> products = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				manage_Products_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Products_Json.setFetchSubCategoryWiseProducts(productService.fetchSubCategoryWiseProducts(Long.parseLong(subCategoryId)));
				manage_Products_Json.setMessage("Fetch All Sub-Category wise Products Successfully");
			} else {
				manage_Products_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Products_Json.setFetchSubCategoryWiseProducts(products);
				manage_Products_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		} else {
			manage_Products_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Products_Json.setFetchSubCategoryWiseProducts(products);
			manage_Products_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Products_Json;
	}
	
	@RequestMapping(value = "/save-product/{subCategoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveProduct(@PathVariable String subCategoryId,@RequestBody Product product, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Product prod=productService.getProductByName(product.getProductName());
					if(prod==null)
					{
					product.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					product.setSubCategoryId(Long.parseLong(subCategoryId));
					productService.saveProduct(product);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Added Admin Product where Id is " + product.getId());
					resp.setMessage("Product Inserted Successfully");
					resp.setStatusCode(Constants.CREATED.constant);
					}else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("Product is already exist");
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
	
	@RequestMapping(value = "/fetch-product/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Products_Json getProduct(@PathVariable String productId,HttpServletRequest request, HttpSession session) {
		Manage_Products_Json manage_Products_Json = new Manage_Products_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		Optional<Product> product=Optional.empty();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_Products_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Products_Json.setGetProduct(productService.getProduct(Long.parseLong(productId)));
				manage_Products_Json.setMessage("Fetch Product Successfully");
			}else {
				manage_Products_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Products_Json.setGetProduct(product);
				manage_Products_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
			}
		}else {
			manage_Products_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Products_Json.setGetProduct(product);
			manage_Products_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Products_Json; 
	}
	
	@RequestMapping(value = "/update-product/{productId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateProduct(@PathVariable String productId,@RequestBody Product product, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					
					product.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					product.setId(Long.parseLong(productId));
					productService.saveProduct(product);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Product where Id is " + product.getId());
					resp.setMessage("Product Updated Successfully");
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
	
	@RequestMapping(value = "/delete-product/{productId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteProduct(@PathVariable String productId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Optional<Product>product=productService.getProduct(Long.parseLong(productId));
					
					if(product.isPresent())
					{
						productService.deleteProduct(Long.parseLong(productId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Product where Id is " + productId);
						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Product Deleted Successfully !!!");
					}else {
						resp.setStatusCode(Constants.NOT_FOUND.constant);
						resp.setMessage("This Product will doesn't exist");
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
