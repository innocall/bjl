package com.rhino.bjl.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/private/main")
public class MainControl extends BaseControl {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("main/index");
        return mav;
    }

    /**
     * 数据统计
     * @return
     */
    @RequestMapping(value = "/dataStatistics",method = RequestMethod.GET)
    public ModelAndView dataStatistics() {
        ModelAndView mav = new ModelAndView("main/dataStatistics");
        return mav;
    }

    /**
     * 数据分析
     * @return
     */
    @RequestMapping(value = "/dataAnalysis",method = RequestMethod.GET)
    public ModelAndView dataAnalysis() {
        ModelAndView mav = new ModelAndView("main/dataAnalysis");
        return mav;
    }

}
