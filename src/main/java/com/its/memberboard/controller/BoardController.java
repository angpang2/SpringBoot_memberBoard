package com.its.memberboard.controller;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.service.BoardService;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CommentService commentService;
    @GetMapping("/save")
    public String saveForm(){
        return "boardPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }


    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/boardList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        if (commentDTOList.size() > 0) {
            model.addAttribute("commentList", commentDTOList);
        } else {
            model.addAttribute("commentList", "empty");
        }
        model.addAttribute("board", boardDTO);
        return "boardPages/boardDetail";
    }


}
