package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    //BoardController에서는 기본적인 로그인 체크를 해줘야 한다. 게시물 쓸때 누가 썼는지 저장해야하니깐.
    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id") != null;
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);

            if (rowCnt != 1) throw new Exception("Modify failed");

            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    //board.jsp에서 글쓰고 등록 누르면 /board/wirte 로 postmapping된다.
    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr) {
        //boardDto로 입력한 내용 받아야 하고, boardDto에다가 wirter넣어줘야한다. writer는 session에서 받아온다.
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            //boardDto를 boardService에 넣어주기
            int rowCnt = boardService.write(boardDto); //write는 insert 결과받아서 rowCnt

            //예외가 발생하면 다시 예외를 던져줘서 글쓰기 화면을 보여주기
            if (rowCnt != 1) throw new Exception("Write failed");

            //RedirectAttributes 사용해서 한번만 사용
            rattr.addFlashAttribute("msg", "WRT_OK");

            //성공하면 게시물 목록으로 돌아가기
            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            //예외발생했을때는 그냥 글쓰기 화면을 보여줄께 아니라 모델에 입력했던 내용을 돌려줘야한다. 그리고 메세지도 주기
            m.addAttribute(boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    //boardList.jsp에서 글쓰기 눌렀을때의 board.jsp
    @GetMapping("/write")
    public String write(Model m) {
        //모델에 mode= new를 담아서 같이 보냄
        m.addAttribute("mode", "new");
        //빈화면 보여주면 된다. board.jsp로 보내벌임
        //board.jsp는 읽기와 쓰기에 사용한다. 여기서는 쓰기! --> mode=new 일떄는 쓰기
        return "board";
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        try {

            //모델에 page하고 pageSize 담아서 보내줘야 redirect:/board/list?page= $pageSize= 이렇게 자동으로 붙여준다.
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer);

            //삭제에 예외가 발생하면 예외를 던져준다.
            if (rowCnt != 1)
                throw new Exception("board remove error");

            //삭제가 성공적으로 되면 msg를 보여준다. jsp로 보내줌. 모델에 담으면 새로고침 할떄마다 메세지가 뜨므로
            //메세지를 한번만 뜰때 쓰는거 --> RedirectAttributes rattr.addFlashAttribute 1회성 -- 한번만 쓰고 없어짐.
            rattr.addFlashAttribute("msg", "DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            //삭제할때 예외가 발생할떄 메세지
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }
        //삭제된 후에는 list로 간다.
        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            //bno를 boardService의 read에 보내주고 , 읽어온걸 받아서 boardDto에 담는다.
            BoardDto boardDto = boardService.read(bno);
            //읽어온걸 board.jsp에 전달해야되서 view로 전달할 모델이 필요하다.
            //boardService를 통해서 읽어온 게시물 내용이 board.jsp로 전달이 된다.
            m.addAttribute("boardDto", boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //읽어서 board.jsp에다 보여줘야한다.
        return "board";
    }

    //list의 매개변수로는 page, pageSize, option, keyword 을 줘야하는데 그걸 searchCondition 객체로 보내준다.
    //SearchCondition 앞에 @ModelAttribute 붙는데 생략가능
    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        // 로그인을 안했으면 로그인 화면으로 이동
        if (!loginCheck(request))
            return "redirect:/login/login?toURL=" + request.getRequestURL();

        try {

            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            System.out.println("totalCnt = " + totalCnt);

            //pageHandler가 값들을 다 계산해서 갖고있다
            PageHandler pageHandler = new PageHandler(totalCnt,sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            //pageHandler를 jsp로 넘겨주면, 이 pageHandler 값들을 가지고 페이징 해준다.
            m.addAttribute("ph", pageHandler);


            System.out.println("list = " + list);
            System.out.println("pageHandler = " + pageHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }





}