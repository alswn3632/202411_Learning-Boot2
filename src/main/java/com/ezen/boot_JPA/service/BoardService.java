package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.BoardFileDTO;
import com.ezen.boot_JPA.dto.FileDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.entity.File;
import org.springframework.data.domain.Page;

public interface BoardService {
    // 인터페이스는 추상 메서드만 가능
    // 메서드가 default(접근제한자)라면 구현이 가능
    Long insert(BoardDTO boardDTO);

    // BoardDTO(class) : bno title writer content regAt modAt
    // Board(table) : bno title writer content
    // BoardDTO를 Board 형태로 변환
    // 화면에서 가져온 BoardDTO 객체를 저장을 위한 Board 객체로 변환
    default Board convertDtoToEntity(BoardDTO boardDTO){
        return Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .writer(boardDTO.getWriter())
                .content(boardDTO.getContent())
                .build();
    }


    // Board를 BoardDTO로 변환
    // DB에서 가져온 Board 객체를 화면에 뿌리기 위한 BoardDTO 객체로 변환
    default BoardDTO convertEntityToDto(Board board){
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .regAt(board.getRegAt())
                .modAt(board.getModAt())
                .build();
    }

    // FileDTO를 File로 변환
    default File convertDtoToEntity(FileDTO fileDTO){
        return File.builder()
                .uuid(fileDTO.getUuid())
                .saveDir(fileDTO.getSaveDir())
                .fileName(fileDTO.getFileName())
                .fileType(fileDTO.getFileType())
                .fileSize(fileDTO.getFileSize())
                .bno(fileDTO.getBno())
                .build();
    }

    // File을 FileDTO로 변환
    default FileDTO convertEntityToDto(File file){
        return FileDTO.builder()
                .uuid(file.getUuid())
                .saveDir(file.getSaveDir())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .bno(file.getBno())
                .regAt(file.getRegAt())
                .modAt(file.getModAt())
                .build();
    }

//    List<BoardDTO> getList(int pageNo);

    Page<BoardDTO> getList(int pageNo);

    BoardFileDTO getDetail(Long bno);

    Long modify(BoardFileDTO boardFileDTO);

    void delete(Long bno);

    Long insert(BoardFileDTO boardFileDTO);

    int deleteFile(String uuid);

    FileDTO getFile(String uuid);
}
