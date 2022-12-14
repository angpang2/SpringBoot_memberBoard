package com.its.memberboard.service;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.BoardFileEntity;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.BoardFileRepository;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        MemberEntity memberEntity =
                memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).get();
//        if (boardDTO.getBoardFile().isEmpty()) {
        if (boardDTO.getBoardFile().get(0).isEmpty()){
            System.out.println("파일없음");
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            return boardRepository.save(boardEntity).getId();
        } else {
            System.out.println("파일있음");
            // 게시글 정보를 먼저 저장하고 해당 게시글의 entity를 가져옴
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity entity = boardRepository.findById(savedId).get();
            // 파일이 담긴 list를 반복문으로 접근하여 하나씩 이름 가져오고, 저장용 이름 만들고
            // 로컬 경로에 저장하고 board_file_table에 저장
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
//                MultipartFile boardFile = boardDTO.getBoardFile();
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\springboot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFileEntity(entity, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return savedId;
        }
    }
}