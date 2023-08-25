package com.aspiremanagement.controlleradmin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspiremanagement.modeladmin.Constants;
import com.aspiremanagement.modeladmin.LogsResponse;
import com.aspiremanagement.serviceadmin.LogServiceImpl;
import com.aspiremanagement.springjwt.security.jwt.JwtUtils;
import com.aspiremanagement.springjwtAdminResponse.Manage_AdminLogs_Json;



@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class SuperAdminLogsController {

	@Autowired
	private LogServiceImpl logservice;
	
	 @Autowired
	    JwtUtils jwtUtils;

	
//	@RequestMapping("/admin/fetch-all-logs-view")
//	public String manage_transaction_charge(HttpSession session, Model model) {
//
//		//if (session.getAttribute("sign-in-user") != null) {
//	    Object checkToken = session.getAttribute("adminJwtToken");
//        if (checkToken != null) {
//            if (jwtUtils.validateJwtToken(checkToken.toString())) {
//
//                model.addAttribute("adminJwtToken", checkToken.toString());
//
//			
//			return "admin/fetch-all-logs-view";
//            }else {
//                return "redirect:/admin/sign-in";
//            }
//		}
//		return "redirect:/admin/sign-in";
//	}
	
	@RequestMapping(value = "/fetch-all-logs", method = RequestMethod.GET)
	@ResponseBody
	public Manage_AdminLogs_Json getAllLogs(HttpServletRequest request, HttpSession session) {
	    Object checkToken = session.getAttribute("adminJwtToken");
        if (checkToken != null) {
            if (jwtUtils.validateJwtToken(request.getHeader(Constants.AUTHORIZATION.constant))) { 
                Manage_AdminLogs_Json manageAdminLogsJson=new Manage_AdminLogs_Json();
                manageAdminLogsJson.setStatusCode(Constants.SUCCESS.constant);
                manageAdminLogsJson.setGetAllLogs(logservice.fetchAllLogs());
		return manageAdminLogsJson;
            }else {
                List<LogsResponse> logList=new ArrayList<>();
                Manage_AdminLogs_Json manageAdminLogsJson=new Manage_AdminLogs_Json();
                manageAdminLogsJson.setStatusCode(Constants.INVALID_TOKEN.constant);
                manageAdminLogsJson.setGetAllLogs(logList);
                return manageAdminLogsJson;
            }
        }else {
            List<LogsResponse> logList=new ArrayList<>();
            Manage_AdminLogs_Json manageAdminLogsJson=new Manage_AdminLogs_Json();
            manageAdminLogsJson.setStatusCode(Constants.SESSION_EXPIRED.constant);
            manageAdminLogsJson.setGetAllLogs(logList);
            return manageAdminLogsJson;
        }
	}
}
