package com.model2.mvc.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//1021///
	@RequestMapping( value="json/addUser", method=RequestMethod.POST )
	public User addUser(@RequestBody User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		
		userService.addUser(user);
		System.out.println("--------------user--------------"+user);
		
		
		return userService.getUser(user.getUserId());
		
	}
	
	@RequestMapping( value="json/updateUser/{userId}", method=RequestMethod.GET )
	public User updateUserTest( @PathVariable String userId  ) throws Exception{

		System.out.println("/user/updateUser : GET");
		//Business Logic
	
		return userService.getUser(userId);
	}
	
	@RequestMapping( value="json/updateUser", method=RequestMethod.POST )
	public User updateUserTest(@RequestBody User user , HttpSession session) throws Exception{

		System.out.println("/user/updateUser : POST");
		//Business Logic
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}

		return userService.getUser(user.getUserId());
	}
	
	@RequestMapping( value="json/listUser/{menu}" )
	public Map<String,Object> listUserTest(@PathVariable String menu, Search search ,HttpServletRequest request) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");//left.jsp 에서는 get방식으로 넘겨주기 때문에 post는 page 넘어갈 때 
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		map.put("resultPage",resultPage);
		map.put("Search",search);
		
		return map ;
	}
	
	////////////////
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		//Business Logic
		return userService.getUser(userId); 
	}	

	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
}