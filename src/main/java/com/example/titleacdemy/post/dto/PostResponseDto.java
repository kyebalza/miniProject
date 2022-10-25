package com.example.titleacdemy.post.dto;

import com.example.titleacdemy.Comment.dto.CommentResDto;
import com.example.titleacdemy.entity.Post;
import com.example.titleacdemy.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long Id;
    private String title;
    private String nickname;
    private String content;
    private String imgUrl;

    private List<CommentResDto> commentResDtoList;

    private Long likeCnt;

    private LocalDateTime createdAt;


    public PostResponseDto(Post post){
        this.Id = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.content = post.getContent();
        this.imgUrl = post.getImgUrl();
        this.createdAt = post.getCreatedAt();
    }
}
