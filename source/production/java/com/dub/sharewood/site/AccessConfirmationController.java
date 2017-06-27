package com.dub.sharewood.site;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dub.sharewood.config.annotations.WebController;

/**
 * Controller for retrieving the model and displaying the confirmation page for access to a protected resource.
 * Used by Spring OAuth2
 * @author Dominique Ubersfeld
 */
@WebController
@SessionAttributes("authorizationRequest")
public class AccessConfirmationController {

	@Resource
	private ClientDetailsService clientDetailsService;

	
	@RequestMapping("/oauth/myApproval")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		
		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}
		
		AuthorizationRequest clientAuth = (AuthorizationRequest) model.remove("authorizationRequest");
		
		ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
		
		model.put("auth_request", clientAuth);
		model.put("client", client);
		
		return new ModelAndView("accessConfirmation", model);
	}
	
	
	@RequestMapping("/oauth/error")
	public String handleError(Map<String, Object> model) throws Exception {
		// We can add more stuff to the model here for JSP rendering. If the client was a machine then
		// the JSON will already have been rendered.
		model.put("message", "There was a problem with the OAuth2 protocol");
		return "oauth_error";
	}


}
