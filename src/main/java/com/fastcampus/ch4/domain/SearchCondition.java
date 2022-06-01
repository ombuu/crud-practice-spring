package com.fastcampus.ch4.domain;
// SearchCondition --> 검색 조건
import org.springframework.web.util.UriComponentsBuilder;

//컨트롤러가 매개변수로 받는걸 묶는게 SearchCondition
//offset 값은 page 와 pageSize값으로 계산하는거니깐 iv로 필요없다.
public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
//    private Integer offset = 0;
    private String keyword = "";
    private String option = "";

    public SearchCondition(){}
    public SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }

    //검색 결과 내용을 받다가 목록으로 돌아올떄 값들을 유지 해야한다. 쿼리스트링 메서드이용해서
    //요청이 있을때 쿼리스트링으로 ex) ?page=1&pageSize=10&option=T&keyword="title" 이런식으로 만들어줘야 하는데
    //일일이 만들어주기 귀찮으니깐 getQueryString 메서드 호출하면 자동적으로 쿼리스트링을 만들어 줘서 반환하는 작업
    //page를 지정해주면 그 페이지 쓰고 지정 안해주는것도 만들어야 한다.
     public String getQueryString(Integer page){
        return UriComponentsBuilder.newInstance()
                .queryParam("page",page)
                .queryParam("pageSize",pageSize)
                .queryParam("option",option)
                .queryParam("keyword",keyword)
                .build().toString();
    }

    public String getQueryString(){
        return getQueryString(page);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (page-1)*pageSize;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", offset=" + getOffset() +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
