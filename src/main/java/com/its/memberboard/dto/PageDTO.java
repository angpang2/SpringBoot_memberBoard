package com.its.memberboard.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDTO {
    private String selectView = "board_id";//정렬기준
    private String q = ""; //검색어
    private String type = "title"; //검색기준
}
