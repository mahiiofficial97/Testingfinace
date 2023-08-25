package com.aspiremanagement.stockdepartment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.ItemResponse;
import com.aspiremanagement.modeladmin.Items;
import com.aspiremanagement.modeladmin.Product;
import com.aspiremanagement.repositoryadmin.ProductRepository;
import com.aspiremanagement.serviceadmin.ItemsService;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_Items_Json;

@RestController
@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RequestMapping("/admin")
public class StockManagerController {

	@Autowired
	StockManagerService stockManagerService;

	@Autowired
	ItemsService itemsService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	JwtUtils jwtUtils;

	@RequestMapping(value = "get-product-wise-data/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Items_Json getProductWiseData(@PathVariable String productId, HttpServletRequest request,
			HttpSession session) {
		Manage_Items_Json manage_Items_Json = new Manage_Items_Json();
		List<ItemResponse> list = new ArrayList<>();
		Object checkToken = session.getAttribute("adminJwtToken");
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				manage_Items_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Items_Json
						.setGetProductWiseItems(itemsService.fetchProductWiseAllItems(Long.parseLong(productId)));
				manage_Items_Json.setMessage("Fetch All Items Successfully");
			} else {
				manage_Items_Json.setGetProductWiseItems(list);
				manage_Items_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
				manage_Items_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);

			}
		} else {
			manage_Items_Json.setGetProductWiseItems(list);
			manage_Items_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Items_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Items_Json;
	}

	@RequestMapping(value = "get-product-wise-all-items-stock", method = RequestMethod.GET)
	@ResponseBody
	public Manage_Items_Json getStock(HttpServletRequest request, HttpSession session) {
		HashMap<String, HashMap<String, Long>> getStockData = new HashMap<String, HashMap<String, Long>>();

		Manage_Items_Json manage_Items_Json = new Manage_Items_Json();
		Object checkToken = session.getAttribute("adminJwtToken");
		if (checkToken != null) {
			if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) {

				List<Product> product = productRepository.findAll();

				for (int i = 0; i < product.size(); i++) {
					List<ItemResponse> listOfItemsByProduct = itemsService
							.fetchProductWiseAllItems(product.get(i).getId());
					//if (!listOfItemsByProduct.isEmpty()) {

						HashMap<String, Long> getData = new HashMap<>();
						for (int j = 0; j < listOfItemsByProduct.size(); j++) {

							getData.put(
									listOfItemsByProduct.get(j).getName() + "-"
											+ listOfItemsByProduct.get(j).getShortDescription(),
									listOfItemsByProduct.get(j).getQuantity());
						}

						getStockData.put(product.get(i).getProductName(), getData);

//					} else {
//
//					}

				}
				manage_Items_Json.setGetStock(getStockData);
				manage_Items_Json.setStatusCode(Constants.SUCCESS.constant);
				manage_Items_Json.setMessage("Sucdessfully Fetch Data");

			} else {
				manage_Items_Json.setGetStock(getStockData);
				manage_Items_Json.setMessage(Constants.INVALID_TOKEN_MESSAGE.constant);
				manage_Items_Json.setStatusCode(Constants.INVALID_TOKEN.constant);
			}
		} else {
			manage_Items_Json.setGetStock(getStockData);
			manage_Items_Json.setStatusCode(Constants.SESSION_EXPIRED.constant);
			manage_Items_Json.setMessage(Constants.SESSION_EXPIRED_MESSAGE.constant);
		}
		return manage_Items_Json;
	}

}
