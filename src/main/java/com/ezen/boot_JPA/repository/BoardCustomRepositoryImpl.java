package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ezen.boot_JPA.entity.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{
    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> searchBoards(String type, String keyword, Pageable pageable) {
        // 조건이 없을 경우
        // select * from board where isDel = 'N' and title = '%aaa%';
        // BooleanExpression condition = board.isDel.eq('N');
        BooleanExpression condition = null;

        if(type != null && keyword != null){
            String[] typearr = type.split("");
            for(String t : typearr){
                switch (t){
                    case "t" :
                        if(condition == null){
                            condition = board.title.containsIgnoreCase(keyword);
                        }else{
                            condition = condition.or(board.title.containsIgnoreCase(keyword));
                        }
                        break;
                    case "w" :
                        if(condition == null){
                            condition = board.writer.containsIgnoreCase(keyword);
                        }else{
                            condition = condition.or(board.writer.containsIgnoreCase(keyword));
                        }
                        break;
                    case "c" :
                        if(condition == null){
                            condition = board.content.containsIgnoreCase(keyword);
                        }else{
                            condition = condition.or(board.content.containsIgnoreCase(keyword));
                        }
                        break;
                }
            }
        }

        // 동적 검색 추가
//        if(type != null && keyword != null){
//            switch (type){
//                case "t" : condition = board.title.containsIgnoreCase(keyword);
//                break;
//                case "w" : condition = board.writer.containsIgnoreCase(keyword);
//                break;
//                case "c" : condition = board.content.containsIgnoreCase(keyword);
//                break;
//                case "tw" : condition = (board.title.containsIgnoreCase(keyword)).or(board.writer.containsIgnoreCase(keyword));
//                break;
//                case "wc" : condition = (board.writer.containsIgnoreCase(keyword)).or(board.content.containsIgnoreCase(keyword));
//                break;
//                case "tc" : condition = (board.title.containsIgnoreCase(keyword)).or(board.content.containsIgnoreCase(keyword));
//                break;
//                case "twc" : condition = (board.title.containsIgnoreCase(keyword)).or(board.content.containsIgnoreCase(keyword)).or(board.writer.containsIgnoreCase(keyword));
//                break;
//            }
//        }

        // 쿼리 삭성 및 페이징 적용
        List<Board> result = queryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.bno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 검색된 데이터의 전체 개수
        long total = queryFactory
                .selectFrom(board)
                .where(condition)
                .fetch().size();

        // .fetchCount(); >> .fetch().size()

        return new PageImpl<>(result, pageable, total);
    }
}
