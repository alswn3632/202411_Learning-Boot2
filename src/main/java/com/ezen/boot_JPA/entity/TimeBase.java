package com.ezen.boot_JPA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
public class TimeBase {
    // 등록일, 수정일만 따로 빼서 관리하는 슈퍼 테이블
    // 별도의 Entity가 아닌 다른 Entity에 포함시켜줄 것임 : @MappedSuperclass
    // private로 제한된 변수를 사용하기 위해 @Getter 어노테이션을 붙임

    @CreatedDate
    @Column(name = "reg_at", updatable = false)
    private LocalDateTime regAt;

    @LastModifiedDate
    @Column(name = "mod_at")
    private LocalDateTime modAt;

}
