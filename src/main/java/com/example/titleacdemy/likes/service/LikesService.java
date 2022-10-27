package com.example.titleacdemy.likes.service;

import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.entity.Likes;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.Post;
import com.example.titleacdemy.likes.dto.LikeRequestDto;
import com.example.titleacdemy.likes.dto.LikeResponseDto;
import com.example.titleacdemy.likes.repository.LikesRepository;
import com.example.titleacdemy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    private final MemberRepository memberRepository;




    //맴버 받아오기
    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return member;
    }

    //좋아요
    public ResponseDto<?> likeUp(LikeRequestDto likeRequestDto, Long memberId) {
        //1. postId 와 userEmail로 좋아요 여부 판단하기
        //boolean likes = likesRepository.existsByPostIdAndMemberId(postId, memberId);
        Optional<Likes> likes = likesRepository.findByPostIdAndMemberId(Long.parseLong(likeRequestDto.getPostId()), memberId);
        //Exists 메소드
        // id 만 가져오기
        Member member = getMember(memberId);
        Post post = new Post(Long.parseLong(likeRequestDto.getPostId()));
        boolean likeCheck;
        if (likes.isPresent()){
            likeCheck = false;
            //2-1. 있으면 삭제
            likesRepository.delete(likes.get());
            //id 만 받아와서 삭제!!
//            likeResult = "좋아요 취소";
//            return ResponseDto.success(
//                    likeResult
//            );
        }else {
            likeCheck = true;
            //2-2. 없으면 등록
            Likes like = new Likes(post, member);
            likesRepository.save(like);
//            likeResult = "좋아요 등록";
        }
        Long LikeCnt = likesRepository.countByPostId(Long.parseLong(likeRequestDto.getPostId()));
        return ResponseDto.success(
                LikeResponseDto.builder()
                        .likeCnt(LikeCnt)
                        .likeCheck(likeCheck)
                        .build()
        );


    }
}
