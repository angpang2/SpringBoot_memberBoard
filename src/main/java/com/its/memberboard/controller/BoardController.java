package com.its.memberboard.controller;

import com.its.memberboard.dto.BoardDTO;
import com.its.memberboard.dto.CommentDTO;
import com.its.memberboard.dto.PageDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.repository.BoardRepository;
import com.its.memberboard.service.BoardService;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model , @RequestParam(value = "q", required = false) String q, @RequestParam(value = "type", required = false) String type) {
        System.out.println("page" + pageable.getPageNumber());
        if (q == null) {
            Page<BoardDTO> boardDTOList = boardService.paging(pageable);
            model.addAttribute("boardList", boardDTOList);
            int blockLimit = 3;
            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            PageDTO pageDTO = new PageDTO();
            pageDTO.setType(type);
            pageDTO.setQ(q);
            model.addAttribute("pageDTO",pageDTO);
            return "boardPages/boardList";
        }else {
            Page<BoardDTO> boardDTOList = boardService.searchPaging(pageable, q, type);
            model.addAttribute("boardList", boardDTOList);
            int blockLimit = 3;
            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            PageDTO pageDTO = new PageDTO();
            pageDTO.setType(type);
            pageDTO.setQ(q);
            model.addAttribute("pageDTO",pageDTO);
            return "boardPages/boardList";
        }


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

    @GetMapping("/search")
    public String search(@PageableDefault(page = 1) Pageable pageable,@RequestParam("type")String type,@RequestParam("q")String q,Model model){
        System.out.println("type = " + type + ", q = " + q + ", model = " + model);
        Page<BoardDTO>searchList = boardService.searchPaging(pageable,type,q);
        model.addAttribute("boardList",searchList);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < searchList.getTotalPages()) ? startPage + blockLimit - 1 : searchList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setType(type);
        pageDTO.setQ(q);
        model.addAttribute("pageDTO", pageDTO);
        return "boardPages/boardList";
    }


}
