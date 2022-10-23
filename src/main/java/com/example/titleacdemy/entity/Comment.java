package com.example.titleacdemy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(cascade =  CascadeType.PERSIST,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    public Comment(Member member,Post post,String content) {//**CommentReqDto에서 오류가 뜨면 여기의 순서를 확인할 것!

        this.member = member;
        this.post = post;
        this.content = content;

    }
}
