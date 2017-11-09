package cn.reinforce.web.fly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 幻幻Fate
 * @create 2017/11/8
 * @since
 */
@Controller
public class IndexCtl {

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("index");


        mv.addObject("hello", "1addasd133");
        return mv;
    }
}
