package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })

//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

   
   @Autowired
   @Qualifier("productServiceImpl")
   private ProductService productService;
   
   @Test
   public void testAddProduct() throws Exception  {
      
      System.out.println("-------------testAddProduct 시작-------------");
      
      Product product = new Product();
      
      //product.setProdNo();   
      
      product.setProdName("만석희");
      product.setProdDetail("1111111111");
      product.setManuDate("201010");
      product.setPrice(111);
      product.setFileName("111111111");
      
      productService.addProduct(product);
      
      //product = productService.getProduct();
      
      //System.out.println(product);
      
      Assert.assertEquals("만석희", product.getProdName());
      Assert.assertEquals("1111111111", product.getProdDetail());
      Assert.assertEquals("201010", product.getManuDate());
      Assert.assertEquals(111, product.getPrice());
      Assert.assertEquals("111111111", product.getFileName());
      
      
      System.out.println("-------------testAddProduct 끝-------------");
   }
    //  @Test
      public void testGetProduct() throws Exception{
    	  System.out.println("-------------testGetProduct 진입-------------");
    	  
    	  Product product = new Product();
    	  
    	  product = productService.getProduct(10042);
    	  
    	  System.out.println(product);
    	  
    	  Assert.assertEquals("만석희", product.getProdName());
          Assert.assertEquals("1111111111", product.getProdDetail());
          Assert.assertEquals("20001010", product.getManuDate());
          Assert.assertEquals(111, product.getPrice());
          Assert.assertEquals("111111111", product.getFileName());
      
   }
      
 //  @Test
   public void testUpdateProduct() throws Exception{
	   Product product = productService.getProduct(10042);
	   Assert.assertNotNull(product);
	   
	   Assert.assertEquals("만석희", product.getProdName());
	   Assert.assertEquals("1111111111", product.getProdDetail());
	   Assert.assertEquals("20001010", product.getManuDate());
	   Assert.assertEquals(111, product.getPrice());
	   Assert.assertEquals("111111111", product.getFileName());
	   
	   product.setProdName("억석희");
	   product.setProdDetail("에에엥");
	   product.setManuDate("202020");
	   product.setPrice(1111);
	   product.setFileName("후에에에");
	   
	   productService.updateProduct(product);
	   
	   product = productService.getProduct(10042);
	   Assert.assertNotNull(product);
	   
	   System.out.println(product);
	   
	   Assert.assertEquals("억석희", product.getProdName());
	   Assert.assertEquals("에에엥", product.getProdDetail());
	   Assert.assertEquals("202020", product.getManuDate());
	   Assert.assertEquals(1111, product.getPrice());
	   Assert.assertEquals("후에에에", product.getFileName());
	   
   }
   
   //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
      
   
 //  @Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("억석희");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}
