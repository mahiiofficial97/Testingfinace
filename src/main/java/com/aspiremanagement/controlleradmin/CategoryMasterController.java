package com.aspiremanagement.controlleradmin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspiremanagement.modeladmin.CategoryMaster;
import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.repositoryadmin.CategoryMasterRepository;
import com.aspiremanagement.serviceadmin.CategoryMasterService;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_Category_Master_Json;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class CategoryMasterController {

	@Autowired
	CategoryMasterService categoryMasterService;

	@Autowired
	CategoryMasterRepository categoryMasterRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	String uploadCategoryImageDirectory = Constants.UPLOADCATEGORYIMAGEDIRECTORY.constant;

	@RequestMapping(value = "/fetch-all-categories", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Category_Master_Json getAllCategories(HttpServletRequest request, HttpSession session) {
		Manage_Category_Master_Json category_Master_Json = new Manage_Category_Master_Json();
		List<CategoryMaster> categoryList = new ArrayList<>();
		Object checkToken = session.getAttribute("adminJwtToken");
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				category_Master_Json.setStatusCode(Constants.SUCCESS.constant);
				category_Master_Json.setMessage("Successfully Fetch all the Data...");
				category_Master_Json.setGetAllCategory(categoryMasterService.fetchAllCategories());
			} else {
				category_Master_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				category_Master_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				category_Master_Json.setGetAllCategory(categoryList);
			}
		} else {
			category_Master_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			category_Master_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			category_Master_Json.setGetAllCategory(categoryList);
		}
		return category_Master_Json;

	}

	@RequestMapping(value = "/fetch-category/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Category_Master_Json getCategory(@PathVariable String categoryId, HttpServletRequest request,
			HttpSession session) {
		Manage_Category_Master_Json category_Master_Json = new Manage_Category_Master_Json();

		Object checkToken = session.getAttribute("adminJwtToken");
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				category_Master_Json.setStatusCode(Constants.SUCCESS.constant);
				category_Master_Json.setGetCategory(categoryMasterService.findById(Long.parseLong(categoryId)));
				category_Master_Json.setMessage("Successfully fetch the Data...");
			} else {
				Optional<CategoryMaster> category = Optional.empty();
				category_Master_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				category_Master_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				category_Master_Json.setGetCategory(category);
			}
		} else {
			Optional<CategoryMaster> category = Optional.empty();
			category_Master_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			category_Master_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			category_Master_Json.setGetCategory(category);

		}
		return category_Master_Json;

	}

	@RequestMapping(value = "/save-categories", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveCategoryMaster(CategoryMaster categoryMaster, BindingResult bindingResult,
			@RequestParam("categoryImage") MultipartFile categoryImage, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

					CategoryMaster categoryDetails = categoryMasterService
							.findByCategoryName(categoryMaster.getCategoryName());

					if (categoryDetails == null) {

						categoryMaster.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString()
								+ " " + session.getAttribute("last_name").toString());
						categoryMasterService.savecategoryMaster(categoryMaster);
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Added Admin Category Master where Id is " + categoryMaster.getId());
						if (!categoryImage.isEmpty() && categoryImage != null) {
							String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
							String originalFileName = dateName + "-" + categoryMaster.getId() + "-"
									+ categoryImage.getOriginalFilename().replace(" ", "-").toLowerCase();
							Path fileNameAndPath = Paths.get(uploadCategoryImageDirectory, originalFileName);
							try {
								Files.write(fileNameAndPath, categoryImage.getBytes());
							} catch (IOException e) {
								e.printStackTrace();
							}
							categoryMaster.setCategoryImage(originalFileName);
						} else {
							categoryMaster.setCategoryImage("");
						}
						categoryMaster.setCategoryName(categoryMaster.getCategoryName());
						categoryMaster.setId(categoryMaster.getId());
						categoryMaster.setUpdatedDate(categoryMaster.getUpdatedDate());
						categoryMaster.setUpdatedName(categoryMaster.getUpdatedName());
						categoryMasterService.savecategoryMaster(categoryMaster);
						resp.setMessage("Category Inserted Successfully");
						resp.setStatusCode(Constants.SUCCESS.constant);
					} else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("Category is already exist");
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

	@RequestMapping(value = "/delete-categories/{categoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteCategoryMaster(@PathVariable String categoryId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

					Optional<CategoryMaster> categoryMasterDetails = categoryMasterService
							.findById(Long.parseLong(categoryId));
					if (categoryMasterDetails.isEmpty()) {
						resp.setStatusCode(Constants.NOT_FOUND.constant);
						resp.setMessage("This category will doesn't exist");
					} else {
						Path path = Paths
								.get(uploadCategoryImageDirectory + categoryMasterDetails.get().getCategoryImage());
						try {

							Files.delete(path);
						} catch (Exception e) {
							e.printStackTrace();
						}
						categoryMasterService.deleteUser(Long.parseLong(categoryId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Category Master where Id is " + categoryId);
						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Categoty Deleted Successfully !!!");
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
	
	

	@RequestMapping(value = "/update-categories/{categoryId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateCategoryMaster(@RequestBody CategoryMaster categoryMaster,
			@RequestParam("categoryImage") MultipartFile categoryImage, @PathVariable String categoryId,
			RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Long id = categoryMaster.getId();
					Optional<CategoryMaster> data = categoryMasterService.findById(id);
					categoryMaster.setUpdatedName(session.getAttribute(Constants.FIRST_NAME.constant).toString() + " "
							+ session.getAttribute("last_name").toString());
					categoryMaster.setId(Long.parseLong(categoryId));
					if (!categoryImage.isEmpty() && categoryImage != null) {

						String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
						String originalFileName = dateName + "-" + categoryMaster.getId() + "-"
								+ categoryImage.getOriginalFilename().replace(" ", "-").toLowerCase();
						Path fileNameAndPath = Paths.get(uploadCategoryImageDirectory, originalFileName);
						Path path = Paths.get(uploadCategoryImageDirectory + data.get().getCategoryImage());
						try {

							Files.delete(path);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Files.write(fileNameAndPath, categoryImage.getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
						categoryMaster.setCategoryImage(originalFileName);
					} else {

						categoryMaster.setCategoryImage(data.get().getCategoryImage());
					}
					categoryMasterService.savecategoryMaster(categoryMaster);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Category Master where Id is " + categoryMaster.getId());

					resp.setMessage("Category Updated Successfully");
					resp.setStatusCode(Constants.SUCCESS.constant);

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
	
	
	@RequestMapping("/get-category-image/{categoryImage}")
    public String getAdminProfileImage(@PathVariable("categoryImage") String categoryImage, HttpServletResponse response,HttpSession session) {
	 Object checkToken = session.getAttribute("adminJwtToken");
	 System.out.println(checkToken);
	 String message;
		if (checkToken != null) {
			try {
                byte b[] = Files.readAllBytes(Paths.get(uploadCategoryImageDirectory + categoryImage));
                response.setContentLength(b.length);
                response.setContentType("image/jpg");
                ServletOutputStream os = response.getOutputStream();
                os.write(b);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
			message="Image Fetch Successfully....";
            
		}else {
			message=Constants.SESSION_EXPIRED_MESSAGE.constant;
		}
		return message;
 }
	

}
