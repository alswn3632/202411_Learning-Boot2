package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.BoardFileDTO;
import com.ezen.boot_JPA.dto.FileDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.entity.File;
import com.ezen.boot_JPA.repository.BoardRepository;
import com.ezen.boot_JPA.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    @Override
    public Long insert(BoardDTO boardDTO) {
        // 저장 객체는 Board
        // save() : insert 후 저장 객체의 id를 리턴
        // save() : Entity 객체를 파라미터로 전송
        return boardRepository.save(convertDtoToEntity(boardDTO)).getBno();
    }

//    @Override
//    public List<BoardDTO> getList() {
//        // Controller로 보내야하는 return은 BoardDTO 형식
//        // DB에서 가져오는 리스트는 Board 형식
//        // findAll() 메서드 사용
//        // 정렬은 Sort.by(Sort.Direction.DESC, "정렬기준 칼럼명")
//        List<Board> boardList = boardRepository
//                .findAll(Sort.by(Sort.Direction.DESC, "bno"));
//
////        List<BoardDTO> boardDTOList = new ArrayList<>();
////
////        for(Board board : boardList){
////            boardDTOList.add(convertEntityToDto(board));
////        }
//
//        List<BoardDTO> boardDTOList = boardList
//                .stream()
//                .map(b -> convertEntityToDto(b))
//                .toList();
//
//        return boardDTOList;
//    }

    @Override
    public Page<BoardDTO> getList(int pageNo) {
        // pakeNo = 0부터 시작
        // 0은 limit 0,10 할 때의 0을 의미함 (따라서 1일수없지!)
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("bno").descending());
        Page<Board> list = boardRepository.findAll(pageable);
        Page<BoardDTO> dtoList = list.map(b -> convertEntityToDto(b));
        return dtoList;
    }

    @Override
    public BoardFileDTO getDetail(Long bno) {
        // findById : 아이디(PK)를 주고 해당 객체를 리턴
        // findById의 리턴타입은 Optional<Boaord> 타입으로 리턴
        // Optional<T> : nullPointException이 발생하지 않도록 도와줌
        // Optional.isEmpty() : null일 경우 확인 가능 return true/false
        // Optional.isPresent() : 값이 있는지 확인 return true/false
        // Optional.get() : 내부 객체 가져오기

        // Board 가져오기
        Optional<Board> optional = boardRepository.findById(bno);

        // Board가 잘 가져와 졌다면..
        if(optional.isPresent()){

            // Board > BoardDTO
            BoardDTO boardDTO = convertEntityToDto(optional.get());

            // File List 가져오기
            List<File> fileList = fileRepository.findAllByBno(bno);

            // FileDTO List로 변환하기
            List<FileDTO> fileDTOList = new ArrayList<>();
            for(File file : fileList){
                fileDTOList.add(convertEntityToDto(file));
            }

            // BoardFileDTO 반환
            BoardFileDTO boardFileDTO = new BoardFileDTO(boardDTO, fileDTOList);
            return boardFileDTO;
        }
        return null;
    }

    @Override
    public Long modify(BoardFileDTO boardFileDTO) {
        // update : jpa는 업데이트가 없음
        // 보통 지우고 다시 넣거나
        // 기존 객체를 가져와서 set 수정 후 다시 저장
        Optional<Board> optional = boardRepository.findById(boardFileDTO.getBoardDTO().getBno());
        Long bno = 0L;
        if(optional.isPresent()){
            optional.get().setTitle(boardFileDTO.getBoardDTO().getTitle());
            optional.get().setContent(boardFileDTO.getBoardDTO().getContent());
            boardRepository.save(optional.get());
            bno = optional.get().getBno();
        }

        if(!bno.equals(0L) && boardFileDTO.getFileDTOList() != null){
            List<FileDTO> flist = boardFileDTO.getFileDTOList();
            for(FileDTO fileDTO : flist){
                fileDTO.setBno(bno);
                bno = fileRepository.save(convertDtoToEntity(fileDTO)).getBno();
            }
        }

        return bno;
    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public Long insert(BoardFileDTO boardFileDTO) {
        Long bno = insert(boardFileDTO.getBoardDTO());
        if(bno>0 && boardFileDTO.getFileDTOList() != null){
            List<FileDTO> flist = boardFileDTO.getFileDTOList();
            for(FileDTO fileDTO : flist){
                fileDTO.setBno(bno);
                bno = fileRepository.save(convertDtoToEntity(fileDTO)).getBno();
            }
        }
        return bno;
    }

    @Override
    public int deleteFile(String uuid) {
        fileRepository.deleteById(uuid);
        Optional<File> optional = fileRepository.findById(uuid);
        if (optional.isEmpty()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public FileDTO getFile(String uuid) {
        Optional<File> optional = fileRepository.findById(uuid);
        if(optional.isPresent()){
            FileDTO fileDTO = convertEntityToDto(optional.get());
            return fileDTO;
        }
        return null;
    }
}
