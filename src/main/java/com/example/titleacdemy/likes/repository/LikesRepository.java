package com.example.titleacdemy.likes.repository;

import com.example.titleacdemy.entity.Comment;
import com.example.titleacdemy.entity.Likes;
import com.example.titleacdemy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    //좋아요 여부 받아오기
    Optional<Likes> findByPostIdAndMemberId(Long postId, Long memberId);
    //게시글의 좋아요 수 가져오기
    Long countByPostId(Long postId);

    //게시글의 좋아요 누른 유저의 리스트 받아오기
    List<Likes> findByPostId(Long postId);


    void deleteLikesByPost(Post post);
}
