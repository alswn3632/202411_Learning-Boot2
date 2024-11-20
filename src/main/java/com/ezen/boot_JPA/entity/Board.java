package com.ezen.boot_JPA.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Board extends TimeBase{
    // @Entity : DB의 테이블 클래스를 의미함
    // DTO : 객체를 생성하는 클래스를 의미함
    // 자주 쓰는 코드를 base class로 별도 관리함
    // ex) regDate / modDate / isDel

    // id = 기본키
    // 기본키 생성 전략 : GeneratedValue

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 생성
    private Long bno;
    // BoardRepository에서 id의 자료형을 클래스로 넣어줘야하기 때문에 Board Entity의 자료형 또한 클래스형식으로 넣어줘야한다.

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String writer;

    @Column(length = 2000, nullable = false)
    private String content;

    // 생성 시 초기화 값을 지정 : 객체가 생성될 때 객체의 기본 값 생성
    // @Builder.Default
    // private int point = 0;
}
