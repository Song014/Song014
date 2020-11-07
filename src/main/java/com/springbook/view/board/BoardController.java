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
	
	//Board ��Ʈ�ѷ� ����
	//�� ����Ʈ
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo,Model model) {
		System.out.println("�� ��� �˻� ó��");	
		System.out.println("�ڰ˻� Ű����:"+ vo.getSearchKeyword());
		
		//�� ����Ʈ �����͸�  MODEL ��ü�� ����  , ������ Spring DispatcherServlet�� �˾Ƽ�
		model.addAttribute("boardList",boardService.getBoardList(vo));
		return "getBoardList";
	}
		//�� ��  ����
		@RequestMapping("/getBoard.do")
		public String getBoard(BoardVO vo,Model model) {
			System.out.println("�� �� ��ȸ ó��");
			model.addAttribute("board",boardService.getBoard(vo));
			List<CommentVO> cmt_list = boardService.getComment(vo);
			model.addAttribute("cmt_list",cmt_list);
			return "getBoard";		
		}
		
		//�� ���
		@RequestMapping(value="/insertBoard.do",method=RequestMethod.GET)
		public String insertBoardForm(BoardVO vo) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
			System.out.println("�� ��� ó��");
			return "insertBoard";  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
		}

		@RequestMapping(value="/insertBoard.do",method=RequestMethod.POST)
		public String insertBoard(BoardVO vo) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
			System.out.println("�� ��� ó��");
			//���� ���ε� ó��
			MultipartFile uploadFile = vo.getUploadFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!uploadFile.isEmpty()) {
				String fileName = uploadFile.getOriginalFilename();
				//System.out.println("���ε������̸�:"+ fileName);		
				vo.setImg(sdf.format(timestamp)+fileName);
				uploadFile.transferTo(new File("C:\\Users\\thdrl\\eclipse-workspace\\KOA\\src\\main\\webapp\\img\\"+sdf.format(timestamp)+fileName));
			}
			boardService.insertBoard(vo);
			return "redirect:getBoardList.do";  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
		}

		//�� ����
		@RequestMapping(value="/updateBoard.do",method=RequestMethod.GET)
		public String updateBoardForm(@ModelAttribute("board") BoardVO vo) {
			System.out.println("�� ���� ó��");	
			return "updateBoard";
		}
		
		@RequestMapping(value="/updateBoard.do",method=RequestMethod.POST)
		public String updateBoard(@ModelAttribute("board") BoardVO vo) throws IOException {
			//����ڰ� �̹��� ���� ���ϴ� ��� ->
			//@SessionAttributes���� �� �� ������ Model���� ������ �ֱ⶧���� �� �������ϴ� �κ��� �׺κи� ���ǿ��� �ҷ����� �ذ�
			
			//����ڰ� �̹��� �����ϴ� ���->
			//���� ���ε� ó��
			MultipartFile uploadFile = vo.getUploadFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!uploadFile.isEmpty()) {
				String fileName = uploadFile.getOriginalFilename();
				//System.out.println("���ε������̸�:"+ fileName);
				
				vo.setImg(sdf.format(timestamp)+fileName);
				uploadFile.transferTo(new File("C:\\Users\\thdrl\\eclipse-workspace\\KOA\\src\\main\\webapp\\img\\"+sdf.format(timestamp)+fileName));
			}
			
			boardService.updateBoard(vo);
			return "redirect:getBoardList.do";
		}
		
		//�� ����
		@RequestMapping("/deleteBoard.do")
		public String deleteBoard(BoardVO vo) {
			System.out.println("�� ���� ó��");
			boardService.deleteBoard(vo);
			
			return "redirect:getBoardList.do";
		}
		
		//��� ���
		@RequestMapping(value="/insertComment.do")
		public String insertBoard(CommentVO cvo) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����
			System.out.println("��� ��� ó��");
			boardService.insertComment(cvo);
			return "redirect:getBoard.do?bno="+cvo.getBno();  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
		}
		
		//���ƿ�
		@ResponseBody
		@RequestMapping(value="/like.do")
		public String likeAction(@RequestParam(value="bno") int bno) throws IOException {   //����� �Է°��� request�� �ƴ� command�� �ް� �ϱ� ���� boardVO�� �Ű������� ����	
			System.out.println("���ƿ� ������Ʈ ó��");
			
			boardService.updateLike(bno);  //���ƿ� ���� 1 ����
			int like = boardService.selectLikeCount(bno); //���ƿ� ���� ��������
			
			JSONObject obj = new JSONObject(); 
			obj.put("like",like);
			
			return obj.toJSONString();  //�� �Է� �ϼ� �� �Խñ۸���Ʈ�� ���� ����
		}	
		
}
