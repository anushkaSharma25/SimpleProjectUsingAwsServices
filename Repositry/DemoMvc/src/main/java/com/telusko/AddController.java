package com.telusko;



import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dao.*;
import com.model.StudentDataModel;

@RestController
@Component
public class AddController {

	//to store in main db and add in redis cache
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(@ModelAttribute StudentDataModel newStudent){		
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigBeansClass.class);

		String result = null;
		
		StudentDataDao f = (StudentDataDao) ctx.getBean("giveLogic");
		ModelAndView m = new ModelAndView();
		
		//to check valid input
		if (f.storeData(newStudent.getId(), newStudent.getName()) == true) {			
	
			result = "Data entry success"; 			
			
		}
		
		else {
			
			result = "Data entry Not successful";
			
		}
		
		m.addObject("result",result);		
		m.setViewName("displayEntryResult.jsp");
		((ConfigurableApplicationContext) ctx).close();
		
		return m;
	}
	
	
	
	
	//TO CHECK AND FETCH
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView check(@RequestParam int id) throws Throwable {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigBeansClass.class);
		
		//for getting student data object
		StudentDataModel student = (StudentDataModel)ctx.getBean("giveStuObj");
		StudentDataDao f = (StudentDataDao) ctx.getBean("giveLogic");
		
		//to call the method which further calls the method with aop implementation
		student=(StudentDataModel) f.checkData(id);
		
		ModelAndView m = new ModelAndView();
		
		if (null == student) {
			m.addObject("result","Invalid Request");
			m.setViewName("displayEntryResult.jsp");	
		} else {

			//change code to manage invalid testcase like id with no name
			m.addObject("result",student);
			m.setViewName("ind.jsp");
		}
		
		((ConfigurableApplicationContext) ctx).close();
		return m;		
	}
	
	

}

/*
 * When used as a method argument, it indicates the argument should be retrieved
 * from the model. When not present, it should be first instantiated and then
 * added to the model and once present in the model, the arguments fields should
 * be populated from all request parameters that have matching names.
 * 
 * In the code snippet that follows the employee model attribute is populated
 * with data from a form submitted to the addEmployee endpoint. Spring MVC does
 * this behind the scenes before invoking the submit method:
 */
