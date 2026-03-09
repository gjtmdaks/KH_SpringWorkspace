package com.kh.study.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

public class Utils {

    public static String saveFile(MultipartFile upfile, ServletContext application) {

        // 저장 경로
        String savePath = application.getRealPath("/resources/upload/");

        // 원본 파일명
        String originName = upfile.getOriginalFilename();

        // 시간 기반 파일명 생성
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        int random = (int)(Math.random()*90000 + 10000);

        // 확장자 추출
        String ext = originName.substring(originName.lastIndexOf("."));

        // 변경 파일명
        String changeName = currentTime + random + ext;

        try {
            upfile.transferTo(new File(savePath + changeName));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return changeName;
    }
}