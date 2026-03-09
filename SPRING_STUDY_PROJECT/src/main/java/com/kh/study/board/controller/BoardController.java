package com.kh.study.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;
import com.kh.study.board.service.BoardService;
import com.kh.study.common.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bs;

    @GetMapping("/list")
    public String list() {
        return "/border/list";
    }

    @GetMapping("/detail/2")
    public String detail() {
        return "/border/detail";
    }

    @GetMapping("/insert")
    public String insert() {
        return "/border/insert";
    }

    @PostMapping("/insert")
    public String insertBoard(
            @ModelAttribute Board b,
            RedirectAttributes ra,
            HttpSession session,
            @RequestParam(value="images", required=false) List<MultipartFile> upfiles
    ) {

        ServletContext application = session.getServletContext();

        List<BoardImg> imgList = new ArrayList<>();

        if(upfiles != null) {
            for(MultipartFile upfile : upfiles) {

                if(upfile.isEmpty()) continue;

                String changeName = Utils.saveFile(upfile, application);

                BoardImg bi = new BoardImg();
                bi.setOriginName(upfile.getOriginalFilename());
                bi.setChangeName(changeName);
                bi.setUploadPath("/resources/upload/");

                imgList.add(bi);
            }
        }

        log.debug("board : {}", b);
        log.debug("imgList : {}", imgList);

        int result = bs.insertBoard(b, imgList);

        if(result <= 0) {
            throw new RuntimeException("게시글 작성 실패");
        }

        ra.addFlashAttribute("alertMsg", "게시글 작성 성공");

        return "redirect:/board/list";
    }
}