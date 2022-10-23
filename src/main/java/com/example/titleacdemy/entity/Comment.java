package com.example.titleacdemy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    public void update(String content){

        this.content = content;
    }

    public boolean checkOwnerByMemberId(Long memberId){

        return this.member.getId().equals(memberId);
    }
    public boolean checkPostByPostId(Long postId) {
        return post.getId().equals(postId);
    }

}
