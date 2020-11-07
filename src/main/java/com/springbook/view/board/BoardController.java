package com.springbook.view.board;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.CommentVO;

@Controller
@SessionAttributes("board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//Board 컨트롤러 통합
	//글 리스트
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo,Model model) {
		System.out.println("글 목록 검색 처리");	
		System.out.println("★검색 키워드:"+ vo.getSearchKeyword());
		
		//글 리스트 데이터를  MODEL 객체에 저장  , 전송은 Spring DispatcherServlet이 알아서
		model.addAttribute("boardList",boardService.getBoardList(vo));
		return "getBoardList";
	}
		//글 상세  보기
		@RequestMapping("/getBoard.do")
		public String getBoard(BoardVO vo,Model model) {
			System.out.println("글 상세 조회 처리");
			model.addAttribute("board",boardService.getBoard(vo));
			List<CommentVO> cmt_list = boardService.getComment(vo);
			model.addAttribute("cmt_list",cmt_list);
			return "getBoard";		
		}
		
		//글 등록
		@RequestMapping(value="/insertBoard.do",method=RequestMethod.GET)
		public String insertBoardForm(BoardVO vo) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
			System.out.println("글 등록 처리");
			return "insertBoard";  //글 입력 완성 후 게시글리스트로 가기 위해
		}

		@RequestMapping(value="/insertBoard.do",method=RequestMethod.POST)
		public String insertBoard(BoardVO vo) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
			System.out.println("글 등록 처리");
			//파일 업로드 처리
			MultipartFile uploadFile = vo.getUploadFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!uploadFile.isEmpty()) {
				String fileName = uploadFile.getOriginalFilename();
				//System.out.println("업로드파일이름:"+ fileName);		
				vo.setImg(sdf.format(timestamp)+fileName);
				uploadFile.transferTo(new File("C:\\Users\\thdrl\\eclipse-workspace\\KOA\\src\\main\\webapp\\img\\"+sdf.format(timestamp)+fileName));
			}
			boardService.insertBoard(vo);
			return "redirect:getBoardList.do";  //글 입력 완성 후 게시글리스트로 가기 위해
		}

		//글 수정
		@RequestMapping(value="/updateBoard.do",method=RequestMethod.GET)
		public String updateBoardForm(@ModelAttribute("board") BoardVO vo) {
			System.out.println("글 수정 처리");	
			return "updateBoard";
		}
		
		@RequestMapping(value="/updateBoard.do",method=RequestMethod.POST)
		public String updateBoard(@ModelAttribute("board") BoardVO vo) throws IOException {
			//사용자가 이미지 수정 안하는 경우 ->
			//@SessionAttributes에서 글 상세 보기의 Model값을 가지고 있기때문에 글 수정안하는 부분은 그부분만 세션에서 불러내서 해결
			
			//사용자가 이미지 수정하는 경우->
			//파일 업로드 처리
			MultipartFile uploadFile = vo.getUploadFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!uploadFile.isEmpty()) {
				String fileName = uploadFile.getOriginalFilename();
				//System.out.println("업로드파일이름:"+ fileName);
				
				vo.setImg(sdf.format(timestamp)+fileName);
				uploadFile.transferTo(new File("C:\\Users\\thdrl\\eclipse-workspace\\KOA\\src\\main\\webapp\\img\\"+sdf.format(timestamp)+fileName));
			}
			
			boardService.updateBoard(vo);
			return "redirect:getBoardList.do";
		}
		
		//글 삭제
		@RequestMapping("/deleteBoard.do")
		public String deleteBoard(BoardVO vo) {
			System.out.println("글 삭제 처리");
			boardService.deleteBoard(vo);
			
			return "redirect:getBoardList.do";
		}
		
		//댓글 등록
		@RequestMapping(value="/insertComment.do")
		public String insertBoard(CommentVO cvo) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언
			System.out.println("댓글 등록 처리");
			boardService.insertComment(cvo);
			return "redirect:getBoard.do?bno="+cvo.getBno();  //글 입력 완성 후 게시글리스트로 가기 위해
		}
		
		//좋아요
		@ResponseBody
		@RequestMapping(value="/like.do")
		public String likeAction(@RequestParam(value="bno") int bno) throws IOException {   //사용자 입력값을 request가 아닌 command가 받게 하기 위해 boardVO를 매개변수로 선언	
			System.out.println("좋아요 업데이트 처리");
			
			boardService.updateLike(bno);  //좋아요 숫자 1 증가
			int like = boardService.selectLikeCount(bno); //좋아요 개수 가져오기
			
			JSONObject obj = new JSONObject(); 
			obj.put("like",like);
			
			return obj.toJSONString();  //글 입력 완성 후 게시글리스트로 가기 위해
		}	
		
}
