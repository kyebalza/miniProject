package com.example.titleacdemy.comment.repository;

import com.example.titleacdemy.entity.Comment;
import com.example.titleacdemy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.post.id = :postId")
    List<Comment> findAllById(@Param("postId") Long postId);
    //@Modifying
    //    @Query("delete from Comment c where c.post.id = :postId")
    //    void deleteAllByPostId(@Param("postId")Long postId);
    void deleteCommentsByPost(Post post);
}
