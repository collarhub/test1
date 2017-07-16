package test2;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {
	@RequestMapping("input.do")
	public String input() {
		return "input";
	}
	@RequestMapping("output.do")
	public ModelAndView output(@RequestParam("title") String title) {
		ModelAndView mav = new ModelAndView();
		ArrayList<String> result = new MyService().search(title);
		mav.addObject("result", result);
		mav.setViewName("output");
		return mav;
	}
}
