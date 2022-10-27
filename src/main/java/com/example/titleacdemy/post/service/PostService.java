package com.example.titleacdemy.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.titleacdemy.comment.dto.CommentResDto;
import com.example.titleacdemy.comment.repository.CommentRepository;
import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.entity.Comment;
import com.example.titleacdemy.entity.Likes;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.Post;
import com.example.titleacdemy.S3.CommonUtils;
import com.example.titleacdemy.likes.repository.LikesRepository;
import com.example.titleacdemy.member.repository.MemberRepository;
import com.example.titleacdemy.post.dto.AllPostResponseDto;
import com.example.titleacdemy.post.dto.PostRequestDto;
import com.example.titleacdemy.post.dto.PostResponseDto;
import com.example.titleacdemy.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final AmazonS3Client amazonS3Client;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    private final LikesRepository likesRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    //게시글 작성
    @Transactional
    public ResponseDto<?> createPost(MultipartFile multipartFile, PostRequestDto postRequestDto, Member member) throws IOException {

        String imgurl = null;

        if (!multipartFile.isEmpty()) {
            String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();
        }

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .member(member)
                .imgUrl(imgurl)
                .build();

        postRepository.save(post);

        return ResponseDto.success(new AllPostResponseDto(post));
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPostAll(){

       List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
       List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();
       for(Post post : postList){
           AllPostResponseDto allPostResponseDto = AllPostResponseDto.builder()
                   .Id(post.getId())
                   .title(post.getTitle())
                   .content(post.getContent())
                   .nickname(post.getMember().getNickname())
                   .imgUrl(post.getImgUrl())
                   .createdAt(post.getCreatedAt())
                   .build();
           allPostResponseDtoList.add(allPostResponseDto);
       }
       return ResponseDto.success(allPostResponseDtoList);
    }


    //상세 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPostOne(Long postId, Long memberId){
        Post post = postRepository.findById(postId).orElseThrow();

        List<Comment> commentList = commentRepository.findAllById(postId);
        List<CommentResDto> commentResDtoList = new ArrayList<>();
        Long cntLike = likesRepository.countByPostId(postId);
        Optional<Likes> likes = likesRepository.findByPostIdAndMemberId(postId, memberId);
        boolean likeCheck;
        if (likes.isPresent()){
            likeCheck = true;
        }else {
            likeCheck = false;
        }
//        List<Likes> likesList = likesRepository.findByPostId(postId);
//        List<LikeListResponseDto> likeListResponseDtoList = new ArrayList<>();



        for (Comment comment : commentList){
            commentResDtoList.add(
                    CommentResDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .author(comment.getMember().getNickname())
                            .createdAt(comment.getCreatedAt())
                            .build()
            );
        }
//        for (Likes likes : likesList){
//            likeListResponseDtoList.add(
//                    LikeListResponseDto.builder()
//                            .id(likes.getId())
//                            .post_id(likes.getPost().getId())
//                            .member_id(likes.getMember().getId())
//                            .build()
//            );
//        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .Id(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getMember().getNickname())
                        .content(post.getContent())
                        .imgUrl(post.getImgUrl())
                        .likeCnt(cntLike)
                        .commentResDtoList(commentResDtoList)
                        .likeCheck(likeCheck)
                        .createdAt(post.getCreatedAt())
                        .build()
        );
    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));


        //댓글 삭제
        commentRepository.deleteCommentsByPost(post);

        //게시글 좋아요 삭제
        likesRepository.deleteLikesByPost(post);

        member.checkMember(post);
        if(!post.getMember().getEmail().equals(member.getEmail()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");

        postRepository.delete(post);

        return ResponseDto.success("게시글이 삭제되었습니다");
    }

    //member에서 email받아오기
//    public Member checkMember(Member member){
//        Optional<Member> mem = memberRepository.findByEmail(member.getEmail());
//        if(!mem.isPresent())
//            throw new IllegalArgumentException("사용자 정보가 없습니다");
//        return mem.get();
//    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long postId, Member member, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));

        member.checkMember(post);

        post.update(postRequestDto);
        return ResponseDto.success(new AllPostResponseDto(post));
    }



}
