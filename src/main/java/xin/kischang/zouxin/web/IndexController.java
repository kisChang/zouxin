package xin.kischang.zouxin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * 
 * @author KisChang
 * @version 1.0
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "/index";
    }

}
