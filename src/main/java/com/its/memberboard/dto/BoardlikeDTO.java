package com.its.memberboard.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardlikeDTO {
    Long boardLikeId;
    Long boardId = null;
    Long memberId = null;
    private Timestamp regdate;
}
