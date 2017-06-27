package com.dub.sharewood.site;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dub.sharewood.config.annotations.WebController;
import com.dub.sharewood.photos.PhotoServices;
import com.dub.sharewood.site.entities.Photo;
import com.dub.sharewood.site.entities.UserAuthority;
import com.dub.sharewood.site.entities.UserPrincipal;
import com.dub.sharewood.users.UserService;

/**
 * Helper controller used to handle all admin specific HTTP requests
 * @author Dominique Ubersfeld
 */
@WebController
@RequestMapping("admin")
public class AdminController {
	
	@Resource 
	private PhotoServices photoServices;
	
	@Resource 
	private UserService userService;
			
	
	@RequestMapping(
			value ="allPhotos", 
			method = RequestMethod.GET)
	public String getMyPhotos(ModelMap model, Principal principal) 
	{
			
		if (this.getPrincipal() != null) {
			List<Photo> photos = photoServices.getAllPhotos();
		
			model.addAttribute("photos", photos);
				
			return "photos/myPhotos";
		} else {
			return "accessDenied";
		}
	}
	
	@RequestMapping(
			value = "admin", 
			method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	
	@RequestMapping(
			value = "userAuthorities", 
			method = RequestMethod.GET)
	public String userAuthorities(ModelMap model) {
		model.addAttribute("getUser", new UsernameForm());
		
		return "getUser";
	}
		
	@RequestMapping(
			value = "userAuthorities", 
			method = RequestMethod.POST)
	public String userAuthorities(
			 @Valid @ModelAttribute("getUser") UsernameForm form, 
             BindingResult result, ModelMap model) {    
		if (result.hasErrors()) {
            return "getUser";
		}
		if (this.getPrincipal() != null) {
			UserPrincipal user = 
				userService.loadUserByUsername(form.getUsername());

			Set<UserAuthority> auths = user.getAuthorities();
		
			model.addAttribute("username", form.getUsername());
			model.addAttribute("authorities", auths);

			return "user";
		} else {
			return "accessDenied";
		}
	}
	
	@RequestMapping(
			value = "allUsers", 
			method = RequestMethod.GET)
	public String allUsers(ModelMap model) {
		
		if (this.getPrincipal() != null) {
			List<UserPrincipal> users = this.userService.allUsers();
		
			model.addAttribute("users", users);
			return "allUsers";
		} else {
			return "accessDenied";
		}
	}
		
	
	
	
	public static class UsernameForm {
	    @NotBlank(message = "{validate.authenticate.username}")
	    private String username;
	    
	     public UsernameForm() { }
		

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}			
	}
	
	
	@RequestMapping(
			value = "/register", 
			method = RequestMethod.GET)
	public String register(ModelMap model) {
		
		if (this.getPrincipal() != null) {
			List<UserPrincipal> users = this.userService.allUsers();
		
			model.addAttribute("users", users);
			return "allUsers";
		} else {
			return "accessDenied";
		}
	}
		
	
	private UserDetails getPrincipal() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();

		if (authentication.getPrincipal() instanceof UserDetails) {
			return (UserDetails)authentication.getPrincipal();
		} else {
			return null;
		}
	}
	
	
}
