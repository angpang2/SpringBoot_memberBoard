package com.its.memberboard.dto;

import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private int boardHits;
    private int boardLike;

    private List<MultipartFile> boardFile;
    private int fileAttached;
    private List<String> originalFileName;
    private List<String> storedFileName;

    // 페이징 목록 변환을 위한 생성자
    // boardTitle, boardHits, boardWriter, boardCreatedTime, id
    public BoardDTO(Long id, String boardWriter, String boardTitle, LocalDateTime boardCreatedTime, int boardHits) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardCreatedTime = boardCreatedTime;
        this.boardHits = boardHits;
    }

    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardLike(boardEntity.getBoardLike());

        // 파일 관련된 내용 추가
        if (boardEntity.getFileAttached() == 1) {
            // 첨부파일 있음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            // 첨부파일 이름 가져옴
            for (BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()) {
                // BoardDTO의 originalFileName이 List이기 때문에 add()를 이용하여
                // boardFileEntity에 있는 originalFileName을 옮겨 담음.
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        } else {
            // 첨부파일 없음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        }
        return boardDTO;
    }
}
