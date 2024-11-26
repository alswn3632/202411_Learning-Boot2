package com.ezen.boot_JPA.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Setter
@Getter
public class PagingVO {
    private int totalPage;
    private int startPage;
    private int endPage;
    private boolean hasPrev;
    private boolean hasNext;
    private int pageNo;

    private String type;
    private String keyword;

    public PagingVO(Page<BoardDTO> list, String type, String keyword){

        this.type = type;
        this.keyword = keyword;

        this.pageNo = list.getNumber() + 1;
        this.totalPage = list.getTotalPages();

        // 만들어야하는 것 (이거때문에 PagingVO 만듦)
        this.endPage = ((int)Math.ceil(pageNo/10.0))*10;

        this.startPage = endPage - 9;
        this.hasPrev = this.startPage > 1;
        this.hasNext = this.endPage < this.totalPage;

        if(totalPage < endPage){
            this.endPage = totalPage;
        }

    }
}
