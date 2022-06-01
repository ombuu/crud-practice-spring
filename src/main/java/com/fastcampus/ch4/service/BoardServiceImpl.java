package com.fastcampus.ch4.service;

import com.fastcampus.ch4.dao.*;
import com.fastcampus.ch4.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

//Dao를 주입받아서 메서드 작성하기
//여기서는 transaction을 적용할게 없기때문에 예외를 다 Controller한테 던지고 있다.
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardDao boardDao;

    //searchSelectPage를 호출하는 메서드
    @Override
    public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception{
        return boardDao.searchSelectPage(sc);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception{
        return boardDao.searchResultCnt(sc);
    }

    //Dao의 count호출하는 메서드
    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    }

    //삭제할 때에는 bno하고 wirter일치하는지 확인해야 하니까 그 두개 받아서 dao의 delete호출
    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }

    @Override
    public int  write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }

    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }

    //게시물 읽기에서 가져와서 viewCnt 업뎃함
    @Override
    public BoardDto read(Integer bno) throws Exception {
        BoardDto boardDto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);

        return boardDto;
    }

    //페이징떄 사용할건데,
    @Override
    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }

    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    }

}