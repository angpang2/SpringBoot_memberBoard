package com.its.memberboard.service;

import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.CommentEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveEntity(boardEntity, commentDTO);
        Long id = commentRepository.save(commentEntity).getId();
        return id;
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        // select * from comment_table where board_id = ?
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        // 1. comment_table에서 직접 해당 게시글의 댓글 목록을 가져오기
        List<CommentEntity> commentEntityList =
                commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // 2. BoardEntity를 조회해서 댓글 목록 가져오기
//        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }
}










