package com.springbook.view.user;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleOAuth2Template;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.springbook.biz.user.UserService;
import com.springbook.biz.user.UserVO;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/*�Ҽ� �α��� */
	// NaverLoginBO 
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;
	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	// GoogleLogin
	@Inject
	private GoogleAuthInfo authInfo;
	@Autowired
	private GoogleOAuth2Template googleOAuth2Template;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
	//Facebook Login
    @Autowired
    private FacebookConnectionFactory connectionFactory;
    @Autowired
    private OAuth2Parameters FacebookoAuth2Parameters;
 
	
	//ȸ������
	@RequestMapping(value="/join.userdo",method=RequestMethod.GET)
	public String insertUserForm(UserVO vo) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
		System.out.println("ȸ�� ���� ��");
	
		return "join";  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
	}

	@RequestMapping(value="/join.userdo",method=RequestMethod.POST)
	public String insertUser(@Valid UserVO vo,BindingResult bindingResult) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
		System.out.println("ȸ�� ���� ó��");
		
		if(bindingResult.hasErrors()){ // ���� �ִٸ�
			System.out.println("ȸ������ �����߻�!");
			return "join"; // ó�� �� ��������
		}else {
		
			userService.insertUser(vo);
			//System.out.println("ȸ������ ����@@@@@@@@@@@@@@");
			return "redirect:index.jsp"; 
		}
	}
	
	//�α��� �� ��û (�⺻�α��� , �Ҽ� �α���)
	@RequestMapping(value="/login.userdo",method=RequestMethod.GET)
	public String loginForm(Model model, HttpSession session) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
		System.out.println("�α��� ��");
		
		// ���̹����̵�� ���� URL�� �����ϱ� ���Ͽ� naverLoginBOŬ������ getAuthorizationUrl�޼ҵ� ȣ�� 
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		System.out.println("���̹� url :" + naverAuthUrl);
		model.addAttribute("url", naverAuthUrl);
		
		// ���� ���̵�� �α��� ����URL
		String url = googleOAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
        System.out.println("���� url : " + url);
        model.addAttribute("google_url", url);	

        // ���̽��� ���̵�� �α��� ���� URL
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String facebook_url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, FacebookoAuth2Parameters);
        System.out.println("/facebook" + facebook_url);
        model.addAttribute("facebook_url", facebook_url);
       

		/* ������ ���� URL�� View�� ���� */
		return "loginForm";  
	}
	
	//�⺻ �α��� ������
	@RequestMapping(value="/login.userdo",method=RequestMethod.POST)
	public String loginAction(UserVO vo,HttpSession session) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
			System.out.println("�α��� ó��");
			
			vo.getPwd(); //������ ���� ��� 
			userService.loginAction(vo);//��񿡼� ������ ���
			
			//�α��� ����
			if(vo.getPwd().equals(userService.loginAction(vo))) {	
				session.setAttribute("sessionId", vo.getId()); //���� ����
				return "redirect:index.jsp";
			}
			session.setAttribute("message", "�α��ο� �����Ͽ����ϴ�. ID�� PW�� Ȯ�����ּ���!");
			return "loginForm";  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
		}
		
	//���̹� �α��� ������
	@RequestMapping(value = "/callback.userdo", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		System.out.println("����� callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);

        //1. �α��� ����� ������ �о�´�.
		apiResult = naverLoginBO.getUserProfile(oauthToken);  //String������ json������
		
		/** apiResult json ����
		{"resultcode":"00",
		 "message":"success",
		 "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"shinn0608@naver.com","name":"\uc2e0\ubc94\ud638"}}
		**/
		
		//2. String������ apiResult�� json���·� �ٲ�
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		
		//3. ������ �Ľ� 
		//Top���� �ܰ� _response �Ľ�
		JSONObject response_obj = (JSONObject)jsonObj.get("response");
		//response�� nickname�� �Ľ�
		String nickname = (String)response_obj.get("nickname");

		System.out.println(nickname);
		
		//4.�Ľ� �г��� �������� ����
		session.setAttribute("sessionId",nickname); //���� ����
		
		model.addAttribute("result", apiResult);
	     
		/* ���̹� �α��� ���� ������ View ȣ�� */
		return "redirect:index.jsp";
		}

	//���� �α��� ������
	@RequestMapping(value = "/googlecallback.userdo")
	public String doSessionAssignActionPage(HttpServletRequest request, HttpSession session) throws Exception {
	 
	        String code = request.getParameter("code");
	        System.out.println(code);
	        
	        //RestTemplate�� ����Ͽ� Access Token �� profile�� ��û�Ѵ�.
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	        parameters.add("code", code);
	        parameters.add("client_id", authInfo.getClientId());
	        parameters.add("client_secret", authInfo.getClientSecret());
	        parameters.add("redirect_uri", googleOAuth2Parameters.getRedirectUri());
	        parameters.add("grant_type", "authorization_code");
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

	        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);
	        Map<String, Object> responseMap = responseEntity.getBody();
	 
	        // id_token ��� Ű�� ����ڰ� ������ �����Ѵ�.
	        // �޾ƿ� ����� JWT (Json Web Token) �������� �޾ƿ´�. �޸� ������ ��� ù ��°�� �� ��ū�� ���� ��Ÿ ����, �� ��°�� �츮�� �ʿ��� ������ �����Ѵ�.
	        // ����° �κп��� �������� �����ϱ� ���� Ư�� �˰������� ��ȣȭ�Ǿ� ���̴׿� ����Ѵ�.
	        //Base 64�� ���ڵ� �Ǿ� �����Ƿ� ���ڵ��Ѵ�.
	 
	        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
	        Base64 base64 = new Base64(true);
	        //String body = new String(base64.decode(tokens[1]));
	        String body =new String(Base64.decodeBase64(tokens[1]), "utf-8");
	        
	        
	        System.out.println(tokens.length);
	        //System.out.println(new String(Base64.decodeBase64(tokens[0]), "utf-8"));
	        //System.out.println(new String(Base64.decodeBase64(tokens[1]), "utf-8"));
	 
	        System.out.println("body�� ��������..:"+body);
	        
	        //Jackson�� ����� JSON�� �ڹ� Map �������� ��ȯ
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, String> result = mapper.readValue(body, Map.class);
	        System.out.println(result.get("name"));
	        
	        String nickname = result.get("name");
	        //���� ����
	        session.setAttribute("sessionId", nickname);
	        

	        return "redirect:index.jsp";
	 
	    }
	
	//���̽��� �α��� ���� ��
	@RequestMapping(value = "/Facebookcallback.userdo", method = { RequestMethod.GET, RequestMethod.POST })
	public String facebookSignInCallback(@RequestParam String code,HttpSession session) throws Exception {
	 
	        try {
	             String redirectUri = FacebookoAuth2Parameters.getRedirectUri();
	            System.out.println("Redirect URI : " + redirectUri);
	            System.out.println("Code : " + code);
	 
	            OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	            AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, redirectUri, null);
	            String accessToken = accessGrant.getAccessToken();
	            System.out.println("AccessToken: " + accessToken);
	            Long expireTime = accessGrant.getExpireTime();
	        
	            
	            if (expireTime != null && expireTime < System.currentTimeMillis()) {
	                accessToken = accessGrant.getRefreshToken();
	                logger.info("accessToken is expired. refresh token = {}", accessToken);
	            };
	            
	        
	            Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
	            Facebook facebook = connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
	            UserOperations userOperations = facebook.userOperations();
	            
	            try
	 
	            {            
	                String [] fields = { "id", "email",  "name"};
	                User userProfile = facebook.fetchObject("me", User.class, fields);
	                
	                System.out.println("�����̸��� : " + userProfile.getEmail());
	                System.out.println("���� id : " + userProfile.getId());
	                System.out.println("���� name : " + userProfile.getName());
	                
	                session.setAttribute("sessionId", userProfile.getName());
	                
	                
	            } catch (MissingAuthorizationException e) {
	                e.printStackTrace();
	            }
	 
	        
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	 
	        }
	        return "redirect:index.jsp";
	 
	    }
	
	
	 //�α׾ƿ�
	@RequestMapping(value="/logout.userdo",method=RequestMethod.GET)
	public String logoutAction(HttpSession session) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
		System.out.println("�α׾ƿ� ó��");
		
		session.invalidate();	
		return "redirect:index.jsp";  
	}
	

}
