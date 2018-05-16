package com.model2.mvc.web.purchase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.NaverUser;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Response;
import com.model2.mvc.service.domain.ResponseCode;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;




//==> ȸ������ Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//setter Method ���� ����
		
	public PurchaseController(){
		System.out.println(this.getClass());
		System.out.println("purchase::::agsadg");
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value="/addPurchaseView", method=RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/addPurchaseView.do");
		
		Product prod = productService.getProduct(prodNo);
		
		/*model.addAttribute("prodVO",prod);*/
		
		String viewName = "/purchase/addPurchaseView.jsp";
		
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("prodVO",prod);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchase")
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase) throws Exception {

		System.out.println("/addPurchase.do");
		//Business Logic
		
		//���� ���� ����
		Product product = productService.getProduct(purchase.getPurchaseProd().getProdNo());
		productService.updateEA(purchase.getsEA(), product);
		
		System.out.println("������� ���� �Ϸ�0");
		purchaseService.addPurchase(purchase);
		
		/*request.setAttribute("purchase", purchase);*/
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		modelAndView.addObject(purchase);
		
		return modelAndView;
	}
	@RequestMapping(value="/listPurchase")
	public ModelAndView GetPurchaseListAll(@ModelAttribute("search") Search search,HttpServletRequest request) throws Exception{
		 
	 	
	 	System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
	 	
		HttpSession session = request.getSession(false);
		
		/*if(session.isNew()) {
			
		}*/
		User user = (User)session.getAttribute("user");
	 	
	 	Map<String,Object> map = purchaseService.getPurchaseList(search,user.getUserId());
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	for (int i = 0; i < list.size(); i++) {
			System.out.println("===========");
	 		System.out.println(list.get(i));
	 		System.out.println("===========");
		}
	 	
	 	Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	 	
	 	
	 	ModelAndView modelAndView = new ModelAndView();
	 	modelAndView.setViewName("/purchase/listPurchase.jsp");
	 	modelAndView.addObject("list",list);
	 	modelAndView.addObject("resultPage",resultPage);
	 	modelAndView.addObject("search",search);
	 	
	 	
	 	
	 	return modelAndView;
	 }
	
	@RequestMapping(value="/getPurchase", method=RequestMethod.GET)
	public ModelAndView getPurchase( @RequestParam("tranNo")int tranNo) throws Exception {
		
		System.out.println(tranNo+"getPurchase���°�!!!!!");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		System.out.println(purchase+"�̰� ���Ծ�!");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		modelAndView.addObject("pur",purchase);
		
		return modelAndView;
	}	
	
	@RequestMapping(value="/updatePurchaseView")
	public ModelAndView UpdatePurchaseView(@RequestParam("tranNo")int tranNo) throws Exception{
		
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchaseVO",purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/updatePurchase")
	public ModelAndView UpdatePurchase(@ModelAttribute("purchase") Purchase purchase ,@RequestParam("tranNo")int tranNo) throws Exception{
		
		purchaseService.updatePurcahse(purchase);
		
		purchase = purchaseService.getPurchase(purchase.getTranNo());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		modelAndView.addObject("pur",purchase);
		
		return modelAndView;
	}
		
	
	
	/*@RequestMapping("/updateTranCode.do")	
	public ModelAndView UpdateTranCode(@RequestParam("tranCode") int tranCode,HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
				
		String role = "";
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		if (user != null) {
			role = user.getRole();
		}
		
		System.out.println(":::UPcode------"+user);
		
		String uri="";
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = new PurchaseServiceImpl().getPurchase(tranNo);
		if (role.equals("admin")) {
			//admin���� ���������� ��ǰ������ ������� ���
			uri += "/listSale.do";
			
		} else if (role.equals("user")) {
			uri += "/listPurchase.do";
		}
		System.out.println("�����ڵ�::" + tranCode);
		tranCode++;
		System.out.println("������ڵ�::" + tranCode);

		String tran = tranCode+"";
		System.out.println(tran+"=====tran��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		purchase.setTranCode(tran);
		purchaseService.updateTranCode(purchase);
		
		modelAndView.setViewName(uri);
		
		
		return modelAndView;
	}*/
	
	
	@RequestMapping(value="/updateTranCodeByProd")	
	public ModelAndView updateTranCodeByProd(@RequestParam("tranCode") int tranCode,@RequestParam("tranNo") int tranNo,HttpServletRequest request) throws Exception{
		
		
		System.out.println("updateTranCode=====================");
		
		ModelAndView modelAndView = new ModelAndView();
				
		String role = "";
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		if (user != null) {
			role = user.getRole();
		}
		
		System.out.println(":::UPcode------"+user);
		String uri="";
		System.out.println(tranCode+"::::::::::::::::::::::::::::");
		System.out.println("=====================1");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		System.out.println("=====================3");
		if (role.equals("admin")) {
			//admin���� ���������� ��ǰ������ ������� ���
			uri += "/purchase/listSale";
			
		} else if (role.equals("user")) {
			uri += "/purchase/listPurchase";
		}
		System.out.println("�����ڵ�::" + tranCode);
		tranCode++;
		System.out.println("������ڵ�::" + tranCode);

		String tran = tranCode+"";
		
		purchase.setTranCode(tran);
		
	
		purchaseService.updateTranCode(purchase);
		System.out.println("�����???");
		modelAndView.setViewName(uri);
		
		
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/cancel")
	public String updateCancelCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int cancelCode = Integer.parseInt(request.getParameter("cancelCode"));
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		System.out.println("cancelCode value::"+cancelCode);
		cancelCode++;
		System.out.println("cancelCode value::"+cancelCode);
		
		purchase.setCancelCode(cancelCode);
		
		purchaseService.updateCancelCode(purchase);
		
		HttpSession session = request.getSession(false);
		
		User user = (User)session.getAttribute("user");
		String uri="";
		if(user.getRole()!=null&&user.getRole().equals("admin")) {
			uri="/purchase/cancelList";
		}else {
			uri="/purchase/listPurchase";
		}
		
		
		return "forward:/"+uri;
	}
	
	@RequestMapping(value="/cancelList")
	public String cancelList(@ModelAttribute("search") Search search,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("/CancellistPurchase.do");
		
		/*if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);*/
		
		
		
		Map<String, Object> map = purchaseService.cancelList(search);
		
		System.out.println(map.get("list")+"::cancel Ȯ��");
		/*System.out.println(map.get("totalCount")+"::page total Ȯ��");*/
		
				
		request.setAttribute("list", map.get("list"));
		/*request.setAttribute("totalCount", map.get("totalCount"));	*/	
		
		return "forward:/purchase/cancelList.jsp";
	}
	
	@RequestMapping(value="/listSale")
	public String salelList(@ModelAttribute("search") Search search,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("/saleListPurchase.do");
		
		/*if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);*/
		
		
		
		Map<String, Object> map = purchaseService.saleList(search);
		
		System.out.println("============================================");
		System.out.println(map.get("list")+"::cancel Ȯ��");
		System.out.println("============================================");
		
				
		request.setAttribute("list", map.get("list"));
		/*request.setAttribute("totalCount", map.get("totalCount"));	*/	
		
		return "forward:/purchase/listSale.jsp";
	}
	
	@RequestMapping(value="/mainView")
	public String MainAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		Map<String,Object> map = productService.getMainList();
		request.setAttribute("list", map.get("list"));
		//��¥�� ��ȸ�� ����Ʈ�� ���� ����
		
		System.out.println(map.get("list")+"sdflkjsdfljk");
		
		//��¥ üũ�ؼ� ���� ��¥�� �ٸ��� ������ ����� ��ȸ���� lookup table �� �ű��� �ٽ� 1���� ����
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    
	    String currentday = date.format(today);


	    
	    List<Object> list = (List<Object>)map.get("list");
	    
	    System.out.println(map.get("list")+"::::::::::::::::::::::::::::");
	 	for (int i = 0; i < list.size(); i++) {
			Product product = (Product)list.get(i);
			if(product.getToday()!=null && !product.getToday().equals(currentday)) {
				//insert �� update �Ѵ� ������ service���� ������ ����
				productService.daycheck(currentday, product);
			}else {
				
			}
			 		
		}
		/////////////////////////////////
	 	
		if(request.getParameter("manuDate")!=null) {
		//��ȸ������Ʈ ����� ��¥
		request.setAttribute("day", request.getParameter("manuDate"));
		
		String day = request.getParameter("manuDate").replaceAll("-", "");
		//�޷°� ��¥
		
			
		request.setAttribute("pday", day);
		
		
		map = productService.getLookupList(day);
		
		
		request.setAttribute("lookuplist", map.get("lookuplist"));
		
		System.out.println();
		
		System.out.println(day+"=====::parsing�� ��¥1");
		System.out.println("====="+map.get("lookuplist"));
		
		//Calender
		
		}else {
			today = new Date();
			date = new SimpleDateFormat("yyyyMMdd");
			
			String day = date.format(today);
			//�޷°� ��¥
			
				
			request.setAttribute("pday", day);
			
			
			map = productService.getLookupList(day);
			
			
			request.setAttribute("lookuplist", map.get("lookuplist"));
			System.out.println(day+"=====::parsing�� ��¥2");
		}
		
	    HttpSession session = request.getSession(false);
	    User user = (User)session.getAttribute("user");
	    
	    if(user== null) {
	    	user = new User();
	    	user.setRole("user");
	    }
	    
	    System.out.println(user.getRole()+"::::adminCheck");
	    //��Ʈ�� ��ȸ�� ����
	    String start = request.getParameter("start");
	    
	    System.out.println(start+":::startüũ");
	    
	    if(start!=null && start.equals("yes")) {
	    request.setAttribute("start", start);
	    }else {
	    request.setAttribute("start", start);
	    }
	    
	    System.out.println(request.getAttribute("start")+":::startüũ2");
	    
	    
	    ///��Ʈ�� ����Ʈ ��¥ �� ��ȸ
	    System.out.println(request.getParameter("manuDate")+"::::��¥ üũ!!!");
	    request.setAttribute("manuDate", request.getParameter(date.format(today)));
	    String ipday = date.format(today);
	    
	    
	    //������ client ip systemlog io�� txt�� �Է��ϱ�
	      System.out.println(request.getRemoteAddr()+"::������ Ŭ���̾�Ʈ IP ����");
  	      String ip = request.getRemoteAddr();
  		
  	      BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Bit\\git\\07.ModelMVC\\07.Model2MVCShop(URI,pattern)\\IPlog\\"+ipday+"IpLog.txt",true));
  	      //true ���� ���� ���ϳ��뿡 �ڿ� �߰��Ǵ� ������ append�ȴ�. default�� false�̹Ƿ� ���ذ�
  	      
  	      String logday = date.format(today)+"-"+time.format(today);
  	      
  	      String ipLog = ip+"��¥-�ð�:"+logday+"//����ID:"+user.getUserId();
  	      
  	      bw.write(ipLog);
  	      bw.newLine();
  	      bw.flush();
  	      
  	      bw.close();
		
		System.out.println("main End====================================");
		return "forward:/main/mainView.jsp";
	}
	
	////////////////////////////////////////////////////////////////
	
	/*@RequestMapping(value = "kakaopay")

	public String isComplete(HttpSession session,HttpServletRequest request) throws Exception{
		
		String clientId = "R6scw7nmGLqAnHi_vcj3";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
	    String redirectURI = URLEncoder.encode("http://127.0.0.1:8080/user/callback", "UTF-8");
	    SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();
	    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
	    apiURL += "&client_id=" + clientId;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&state=" + state;
	    session.setAttribute("state", state);
		
	    session.setAttribute("apiURL", apiURL);
		
		
		

	return "forward:/user/login";

	}
	@RequestMapping(value = "callback")
	public String navLogin(HttpServletRequest request) throws Exception {  
		
		System.out.println("========================================1241235235callback");
		
		String clientId = "R6scw7nmGLqAnHi_vcj3";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
	    String clientSecret = "SFqKnM6OyE";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    String redirectURI = URLEncoder.encode("http://127.0.0.1:8080/user/callback", "UTF-8");
	    String apiURL;
	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    apiURL += "client_id=" + clientId;
	    apiURL += "&client_secret=" + clientSecret;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&code=" + code;
	    apiURL += "&state=" + state;
	    String access_token = "";
	    String refresh_token = "";
	    System.out.println("apiURL="+apiURL);
	    try {
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("GET");
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
	      System.out.print("responseCode="+responseCode);
	      
	      
	      
	      if(responseCode==200) { // ���� ȣ��
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // ���� �߻�
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      String inputLine;
	      StringBuffer res = new StringBuffer();
	      while ((inputLine = br.readLine()) != null) {
	        res.append(inputLine);
	      }
	      br.close();
	      if(responseCode==200) {
	        
	        System.out.print("\n"+"responseCodeAll="+res.toString());
			///////////////////////////////access_token �ޱ�
			ObjectMapper objMap = new ObjectMapper();
			System.out.println();
			System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\123");
			ResponseCode allcode = objMap.readValue(res.toString(), ResponseCode.class);
	        
			System.out.println(allcode.getAccess_token()+"///////////////////////////////////!!");
			
			request.setAttribute("accesstoken", allcode.getAccess_token());
	        
						
	      }
	    } catch (Exception e) {
	      System.out.println(e);
	    }
		
		
	    return "/user/Info";
	} 

	@RequestMapping(value = "Info")
	public String personalInfo(HttpServletRequest request) throws Exception {
	      System.out.println(); 
		System.out.println("Info Start=========================================");
			
			HttpSession session = request.getSession(false);
	        
			String temp = (String)session.getAttribute("state");
	        		
			System.out.println(temp);
		
			System.out.println(request.getAttribute("accesstoken")+"----------------------------------!!!");
			
			String access = (String)request.getAttribute("accesstoken");
			
			String token = access;// ���̹� �α��� ���� ��ū; ���⿡ ������ ��ū���� �־��ݴϴ�.
	        				
			String header = "bearer " + token; // Bearer ������ ���� �߰�
	        
	      
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");//���޹��
            con.setRequestProperty("Authorization", header);//������ �� 
            
            int responseCode = con.getResponseCode();
            
            
            BufferedReader br;
            if(responseCode==200) { // ���� ȣ��
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // ���� �߻�
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
           //���۵Ǿ�°� Json ����� ObjectMapper ����ؼ� json �����ο� setting�ϱ�
            System.out.println(response.toString());
            ObjectMapper objMap = new ObjectMapper();
            //���⼭ ���ε��� �ϰ� �Ǵµ� �̶� �����ڵ��� ��� JSON���� �ڵ����� �ѱۺ�ȯ�� ������ �־��ش�.
            NaverUser naverLogin = objMap.readValue(response.toString(), NaverUser.class);
            
            System.out.println(naverLogin.getResponse().getId()+"!!!!!!!!!!!!!!!!!!!!!!!!�̰ź��� ���ÿϷ�");
            
            System.out.println(naverLogin.getResponse().toString());
            
            Response naveruser = userService.getNaverUser(naverLogin.getResponse().getId());
            String uri ="";
            if(naveruser==null) {
            	userService.addNaverUser(naverLogin.getResponse());
            }
            
            System.out.println("==+=========================================1");
            System.out.println(naveruser.getId().trim());
            System.out.println(naverLogin.getResponse().getId());
            System.out.println("==+=========================================2");
            
            String naverdb = naveruser.getId().trim();
            String navernumid = naverLogin.getResponse().getId();
            
            
            if(naverdb.equals(navernumid)&&userService.getNaverUser(navernumid)==null) {
            	String userId = naveruser.getEmail().substring(0, naveruser.getEmail().indexOf("@"));
            	System.out.println(userId+"+++++++++++++++++");
            	
            	naveruser.setId(userId);
            	
            	request.setAttribute("user", naveruser);
            	uri ="forward:/user/addNaverUserView.jsp";
            }else {
            	User user = userService.getUser(naveruser.getEmail().substring(0, naveruser.getEmail().indexOf("@")));
            	session.setAttribute("user", user);
            	
            	uri="forward:/user/login";
            }
	                
	        
	        return uri;
	        
	}*/
		
	
}