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
	
	/*소셜 로그인 */
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
 
	
	//회원가입
	@RequestMapping(value="/join.userdo",method=RequestMethod.GET)
	public String insertUserForm(UserVO vo) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
		System.out.println("회원 가입 폼");
	
		return "join";  //글 입력 완성 후 게시글리스트로 가기 위해
	}

	@RequestMapping(value="/join.userdo",method=RequestMethod.POST)
	public String insertUser(@Valid UserVO vo,BindingResult bindingResult) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
		System.out.println("회원 가입 처리");
		
		if(bindingResult.hasErrors()){ // 에러 있다면
			System.out.println("회원가입 에러발생!");
			return "join"; // 처리 후 페이지로
		}else {
		
			userService.insertUser(vo);
			//System.out.println("회원가입 성공@@@@@@@@@@@@@@");
			return "redirect:index.jsp"; 
		}
	}
	
	//로그인 폼 요청 (기본로그인 , 소셜 로그인)
	@RequestMapping(value="/login.userdo",method=RequestMethod.GET)
	public String loginForm(Model model, HttpSession session) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
		System.out.println("로그인 폼");
		
		// 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		System.out.println("네이버 url :" + naverAuthUrl);
		model.addAttribute("url", naverAuthUrl);
		
		// 구글 아이디로 로그인 인증URL
		String url = googleOAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
        System.out.println("구글 url : " + url);
        model.addAttribute("google_url", url);	

        // 페이스북 아이디로 로그인 인증 URL
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String facebook_url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, FacebookoAuth2Parameters);
        System.out.println("/facebook" + facebook_url);
        model.addAttribute("facebook_url", facebook_url);
       

		/* 생성한 인증 URL을 View로 전달 */
		return "loginForm";  
	}
	
	//기본 로그인 성공시
	@RequestMapping(value="/login.userdo",method=RequestMethod.POST)
	public String loginAction(UserVO vo,HttpSession session) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
			System.out.println("로그인 처리");
			
			vo.getPwd(); //폼에서 보낸 비번 
			userService.loginAction(vo);//디비에서 가져온 비번
			
			//로그인 성공
			if(vo.getPwd().equals(userService.loginAction(vo))) {	
				session.setAttribute("sessionId", vo.getId()); //세션 생성
				return "redirect:index.jsp";
			}
			session.setAttribute("message", "로그인에 실패하였습니다. ID와 PW를 확인해주세요!");
			return "loginForm";  //글 입력 완성 후 게시글리스트로 가기 위해
		}
		
	//네이버 로그인 성공시
	@RequestMapping(value = "/callback.userdo", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);

        //1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken);  //String형식의 json데이터
		
		/** apiResult json 구조
		{"resultcode":"00",
		 "message":"success",
		 "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"shinn0608@naver.com","name":"\uc2e0\ubc94\ud638"}}
		**/
		
		//2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		
		//3. 데이터 파싱 
		//Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject)jsonObj.get("response");
		//response의 nickname값 파싱
		String nickname = (String)response_obj.get("nickname");

		System.out.println(nickname);
		
		//4.파싱 닉네임 세션으로 저장
		session.setAttribute("sessionId",nickname); //세션 생성
		
		model.addAttribute("result", apiResult);
	     
		/* 네이버 로그인 성공 페이지 View 호출 */
		return "redirect:index.jsp";
		}

	//구글 로그인 성공시
	@RequestMapping(value = "/googlecallback.userdo")
	public String doSessionAssignActionPage(HttpServletRequest request, HttpSession session) throws Exception {
	 
	        String code = request.getParameter("code");
	        System.out.println(code);
	        
	        //RestTemplate을 사용하여 Access Token 및 profile을 요청한다.
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
	 
	        // id_token 라는 키에 사용자가 정보가 존재한다.
	        // 받아온 결과는 JWT (Json Web Token) 형식으로 받아온다. 콤마 단위로 끊어서 첫 번째는 현 토큰에 대한 메타 정보, 두 번째는 우리가 필요한 내용이 존재한다.
	        // 세번째 부분에는 위변조를 방지하기 위한 특정 알고리즘으로 암호화되어 사이닝에 사용한다.
	        //Base 64로 인코딩 되어 있으므로 디코딩한다.
	 
	        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
	        Base64 base64 = new Base64(true);
	        //String body = new String(base64.decode(tokens[1]));
	        String body =new String(Base64.decodeBase64(tokens[1]), "utf-8");
	        
	        
	        System.out.println(tokens.length);
	        //System.out.println(new String(Base64.decodeBase64(tokens[0]), "utf-8"));
	        //System.out.println(new String(Base64.decodeBase64(tokens[1]), "utf-8"));
	 
	        System.out.println("body가 무엇인지..:"+body);
	        
	        //Jackson을 사용한 JSON을 자바 Map 형식으로 변환
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, String> result = mapper.readValue(body, Map.class);
	        System.out.println(result.get("name"));
	        
	        String nickname = result.get("name");
	        //세션 생성
	        session.setAttribute("sessionId", nickname);
	        

	        return "redirect:index.jsp";
	 
	    }
	
	//페이스북 로그인 성공 시
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
	                
	                System.out.println("유저이메일 : " + userProfile.getEmail());
	                System.out.println("유저 id : " + userProfile.getId());
	                System.out.println("유저 name : " + userProfile.getName());
	                
	                session.setAttribute("sessionId", userProfile.getName());
	                
	                
	            } catch (MissingAuthorizationException e) {
	                e.printStackTrace();
	            }
	 
	        
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	 
	        }
	        return "redirect:index.jsp";
	 
	    }
	
	
	 //로그아웃
	@RequestMapping(value="/logout.userdo",method=RequestMethod.GET)
	public String logoutAction(HttpSession session) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
		System.out.println("로그아웃 처리");
		
		session.invalidate();	
		return "redirect:index.jsp";  
	}
	

}
