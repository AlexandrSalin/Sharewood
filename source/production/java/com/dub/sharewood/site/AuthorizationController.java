package com.dub.sharewood.site;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dub.sharewood.config.annotations.WebController;
 
/** Helper controller only used to handle access rejection
 * */
@WebController
public class AuthorizationController 
{     
	
	
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", SecurityUtils.getPrincipal());        
        return "accessDenied";
    }
}