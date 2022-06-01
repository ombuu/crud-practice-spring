package com.fastcampus.ch4.domain;

import java.util.Date;
import java.util.Objects;

public class BoardDto {
    //board 테이블의 컬럼들을 참고해서 작성
    private Integer bno; //게시물 번호
    private String title; //게시물 제목
    private String content; //게시물 내용
    private String writer; //작성자
    private int view_cnt; //조회수
    private int comment_cnt; // 댓글 갯수
    private Date reg_date; // 등록일
//    private Date up_date;

    //생성자와 기본 생성자
    public BoardDto(){}
    public BoardDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    //toString과 equlas생성
    //equals에서는 bno,title,content,writer만 해준다.
    //view-counter나 다른것들은 바뀔 수 있는것들이다. --> 같은 게시물인데 다르다고 나오면 안돼므로
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return Objects.equals(bno, boardDto.bno) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content) && Objects.equals(writer, boardDto.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno, title, content, writer);
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", view_cnt=" + view_cnt +
                ", comment_cnt=" + comment_cnt +
                ", reg_date=" + reg_date +
                '}';
    }

    //getter setter --> 이게 있어야 myBatis가 자동으로 값을 읽어오고 채워주고 한다.
    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }
}

