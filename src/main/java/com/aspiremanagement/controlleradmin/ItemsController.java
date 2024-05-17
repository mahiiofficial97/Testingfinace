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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.ItemResponse;
import com.aspiremanagement.modeladmin.Items;
import com.aspiremanagement.modeladmin.JsonResponse;
import com.aspiremanagement.serviceadmin.ItemsService;
import com.aspiremanagement.serviceadmin.LogService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_Items_Json;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class ItemsController {
	@Autowired
	ItemsService itemsService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LogService logService;

	String uploadItemsImageDirectory = Constants.UPLOADITEMSIMAGEDIRECTORY.constant;

	@RequestMapping(value = "/save-item", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveItems(Items items, BindingResult bindingResult,
			@RequestParam("itemImages") MultipartFile[] itemImages, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {

		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {
				
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Items item = itemsService.getItemByName(items.getName(),items.getShortDescription());
					if (item == null) {

						List<String> list = new ArrayList<>();

						itemsService.save(items);
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Added Admin Item where Id is " + items.getId());
						resp.setMessage("Items Add Successfully");
						resp.setStatusCode(Constants.CREATED.constant);
						resp.setResult("Success");

						if (itemImages.length > 0 && itemImages != null) {
							for (int i = 0; i < itemImages.length; i++) {
								if (!itemImages[i].isEmpty()) {
									String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
									String originalFileName = dateName + "-" + items.getId() + "-"
											+ itemImages[i].getOriginalFilename().replace(" ", "-").toLowerCase();
									Path fileNameAndPath = Paths.get(uploadItemsImageDirectory, originalFileName);
									try {
										Files.write(fileNameAndPath, itemImages[i].getBytes());
									} catch (IOException e) {
										e.printStackTrace();
									}
									list.add(originalFileName);
								} else {
									items.setItemImages(list);
								}
							}
							items.setItemImages(list);

						} else {

							items.setItemImages(list);
						}
						items.setBrandId(items.getBrandId());
						items.setId(items.getId());
						items.setColourId(items.getColourId());
						items.setName(items.getName());
						items.setProductId(items.getProductId());
						items.setQuantity(items.getQuantity());
						items.setShortDescription(items.getShortDescription());
						itemsService.save(items);

					} else {
						resp.setStatusCode(Constants.ALREADY_EXIST.constant);
						resp.setMessage("This Item is already Exist");
						resp.setResult("Un Successful");
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

	@RequestMapping(value = "/delete-item/{itemId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteColour(@PathVariable String itemId, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {
				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					Optional<Items> item = itemsService.getItem(Long.parseLong(itemId));
					if (item.isPresent()) {

						List<String> images = item.get().getItemImages();
						for (int i = 0; i < images.size(); i++) {

							Path path = Paths.get(uploadItemsImageDirectory + images.get(i));

							try {

								Files.delete(path);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						resp.setStatusCode(Constants.SUCCESS.constant);
						resp.setMessage("Item Deleted Successfully !!!");
						resp.setResult("Successful");
						itemsService.deleteItem(Long.parseLong(itemId));
						logService.logInsert(session.getAttribute("sign-in-user").toString(),
								" Deleted Admin Item where Id is " + itemId);
					} else {
						resp.setMessage("This Item will doesn't exist");
						resp.setResult("Un Successful");
						resp.setStatusCode(Constants.NOT_FOUND.constant);
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

	@RequestMapping(value = "/fetch-all-items", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Items_Json fetchAllItems(HttpServletRequest request, HttpSession session) {
		Manage_Items_Json manage_Items_Json = new Manage_Items_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		List<ItemResponse> items = new ArrayList<>();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				manage_Items_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Items_Json.setFetchAllItems(itemsService.fetchAllItems());
				manage_Items_Json.setMessage("Fetch All Items Successfully");

			} else {
				manage_Items_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Items_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				manage_Items_Json.setFetchAllItems(items);

			}
		} else {
			manage_Items_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Items_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			manage_Items_Json.setFetchAllItems(items);

		}
		return manage_Items_Json;
	}

	@RequestMapping(value = "/get-item/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Items_Json getItem(@PathVariable String itemId, HttpServletRequest request, HttpSession session) {
		Manage_Items_Json manage_Items_Json = new Manage_Items_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		Optional<Items> items = Optional.empty();
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
				manage_Items_Json.setGetItem(itemsService.getItem(Long.parseLong(itemId)));
			} else {
				manage_Items_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Items_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				manage_Items_Json.setGetItem(items);
			}
		} else {
			manage_Items_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Items_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
			manage_Items_Json.setGetItem(items);
		}
		return manage_Items_Json;
	}

	@RequestMapping(value = "/update-item/{itemId}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateItem(@PathVariable String itemId, Items items, BindingResult bindingResult,
			@RequestParam("itemImages") List<MultipartFile> itemImages, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		JsonResponse resp = new JsonResponse();
		Optional<Items> data = itemsService.getItem(Long.parseLong(itemId));
		try {
			Object jwtToken = session.getAttribute("adminJwtToken");
			if (jwtToken != null) {

				if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {
					List<String> list = new ArrayList<>();
					items.setId(Long.parseLong(itemId));

					
					if (! itemImages.get(0).isEmpty()) {
						List<String> images = data.get().getItemImages();
						for (int i = 0; i < images.size(); i++) {
							Path path = Paths.get(uploadItemsImageDirectory + images.get(i));
							try {

								Files.delete(path);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						for (int i = 0; i < itemImages.size(); i++) {
							String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
							String originalFileName = dateName + "-" + items.getId() + "-"
									+ itemImages.get(i).getOriginalFilename().replace(" ", "-").toLowerCase();
							Path fileNameAndPath = Paths.get(uploadItemsImageDirectory, originalFileName);
							try {
								Files.write(fileNameAndPath, itemImages.get(i).getBytes());
							} catch (IOException e) {
								e.printStackTrace();
							}
							list.add(originalFileName);
						}
						items.setItemImages(list);
					} else {
						items.setItemImages(data.get().getItemImages());
					}
					itemsService.save(items);
					logService.logInsert(session.getAttribute("sign-in-user").toString(),
							" Updated Admin Item where Id is " + itemId);
					resp.setMessage("Items" + " Updated Successfully");
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
	
	@RequestMapping("/get-items-image/{itemImages}")
    public String getAdminProfileImage(@PathVariable("itemImages") String itemImages, HttpServletResponse response,HttpSession session) {
	 Object checkToken = session.getAttribute("adminJwtToken");
	 System.out.println(checkToken);
	 String message;
		//if (checkToken != null) {
			try {
                byte b[] = Files.readAllBytes(Paths.get(uploadItemsImageDirectory + itemImages));
                response.setContentLength(b.length);
                response.setContentType("image/jpg");
                ServletOutputStream os = response.getOutputStream();
                os.write(b);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
			message="Image Fetch Successfully....";
            
//		}else {
//			message=Constants.SESSION_EXPIRED_MESSAGE.constant;
//		}
		return null;
 }

}



