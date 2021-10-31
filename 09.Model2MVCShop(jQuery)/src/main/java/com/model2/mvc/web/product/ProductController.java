package com.model2.mvc.web.product;

import java.awt.Menu;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception {
	@RequestMapping(value = "addProduct" , method = RequestMethod.GET)
	public String addProduct() throws Exception{
		System.out.println("/addProductView : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value= "addProduct" , method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product")Product product) throws Exception {

		System.out.println("/product/addProduct:POST");
		//Business Logic		
		productService.addProduct(product);
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model, HttpServletRequest request) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		model.addAttribute("menu",  request.getParameter("menu"));
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping(value = "updateProductView", method = RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model,HttpServletRequest request ) throws Exception{

		System.out.println("/product/updateProduct:GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product , Model model) throws Exception{

		System.out.println("/product/updateProduct");
		//Business Logic
		productService.updateProduct(product);
		
		
		/*String sessionId=((Product)session.getAttribute("product")).getProdNo()+"";
		if(sessionId.equals((product.getProdNo())+"")){
			session.setAttribute("product", product);
	
		}*/
		model.addAttribute("product",product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping(value = "listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct:GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		System.out.println("menu : "+request.getParameter("menu"));
		// Model 과 View 연결
		model.addAttribute("menu",  request.getParameter("menu"));
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		System.out.println(request.getParameter("menu"));
		
		return "forward:/product/listProduct.jsp";
	}
	

	
	
	
	

}
