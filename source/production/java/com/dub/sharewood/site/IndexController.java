package com.dub.sharewood.site;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.dub.sharewood.config.annotations.WebController;

/** 
 * This controller handles the home page 
 * */
@WebController
public class IndexController
{
	
    @RequestMapping({"/", "backHome", "index"})
    public String index(HttpServletRequest request)
    {
        return "index";
    }
}
