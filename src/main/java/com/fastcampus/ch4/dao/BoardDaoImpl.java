package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.*;
import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

//boardMapper.xml의 sql을 다 작성한 다음에는 Dao를 작성해야한다. 먼저 sqlSession주입 받고, mapper보면서 하나씩 작성하면 된다.
@Repository //빈으로 등록
public class BoardDaoImpl implements BoardDao {
//    dao에서 SqlSession의 메서드를 호출하면 MyBatis가 Mapper의 SQL을 이용해서 DB에서 데이터를 처리합니다.
    @Autowired
    private SqlSession session;
    private static String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // T selectOne(String statement)

    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    } // int delete(String statement)

    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete", map);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)

    @Override
    public BoardDto select(Integer bno) throws Exception {
        //session 이용해서 한줄 바꾸면 selectOne쓰고 sql id와 input 타입 = parameterType (bno -> int)
        //이걸 호출하면 boardMapper.xml에 있는 select sql문이 실행되고 그 결과를 받게 된다.
        //sqlSession을 주입받아서 거기있는 메서드인 selectOne을 이용해서 데이터를 가져오는것이다.
        return session.selectOne(namespace + "select", bno);
    } // T selectOne(String statement, Object parameter)

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

    @Override
    public int update(BoardDto dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)

    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace+"increaseViewCnt", bno);
    } // int update(String statement, Object parameter)

   @Override
   public int searchResultCnt(SearchCondition sc) throws Exception {
      return session.selectOne(namespace+"searchResultCnt",sc);
   } // T selectOne(String statement, Object parameter)

    @Override
   public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace+"searchSelectPage",sc);
   } // List<E> selectList(String statement, Object parameter)

    @Override
    public int updateCommentCnt(Integer bno, int cnt) {
        Map map = new HashMap();
        map.put("cnt", cnt);
        map.put("bno",bno);
        return session.update(namespace + "updateCommentCnt",map);
    }
}