package com.example.titleacdemy.entity;

import com.example.titleacdemy.post.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imgUrl;

    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;

    public Post(Long id) {
        this.id = id;
    }
    public Post(Member member, String title, String content, String imgUrl){
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
