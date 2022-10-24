package com.example.titleacdemy.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.titleacdemy.dto.ResponseDto;
import com.example.titleacdemy.entity.Member;
import com.example.titleacdemy.entity.Post;
import com.example.titleacdemy.S3.CommonUtils;
import com.example.titleacdemy.member.repository.MemberRepository;
import com.example.titleacdemy.post.dto.PostRequestDto;
import com.example.titleacdemy.post.dto.PostResponseDto;
import com.example.titleacdemy.post.repository.PostRepository;
import io.jsonwebtoken.RequiredTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
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

        return ResponseDto.success(new PostResponseDto(post));
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPostAll(){

       List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
       List<PostResponseDto> postResponseDtoList = new ArrayList<>();
       for(Post post : postList){
           PostResponseDto postResponseDto = PostResponseDto.builder()
                   .Id(post.getId())
                   .title(post.getTitle())
                   .content(post.getContent())
                   .nickname(post.getMember().getNickname())
                   .imgUrl(post.getImgUrl())
                   .build();
           postResponseDtoList.add(postResponseDto);
       }
       return ResponseDto.success(postResponseDtoList);
    }


    //상세 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPostOne(Long postId){
        Post post = postRepository.findById(postId).orElseThrow();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .Id(post.getId())
                .title(post.getTitle())
                .nickname(post.getMember().getNickname())
                .content(post.getContent())
                .imgUrl(post.getImgUrl())
                .build();
        return ResponseDto.success(postResponseDto);
    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));

        member.checkMember(post);
        if(!post.getMember().getEmail().equals(member.getEmail()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");

        postRepository.delete(post);

        return ResponseDto.success("게시글이 삭제되었습니다");
    }

    @Transactional
    public ResponseDto<?> updatePost(Long postId, Member member, PostRequestDto postRequestDto){
        //게시글 수정
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));

        member.checkMember(post);

        post.update(postRequestDto);
        return ResponseDto.success(new PostResponseDto(post));

    }

}
//member에서 email받아오기
//    public Member checkMember(Member member){
//        Optional<Member> mem = memberRepository.findByEmail(member.getEmail());
//        if(!mem.isPresent())
//            throw new IllegalArgumentException("사용자 정보가 없습니다");
//        return mem.get();
//    }

//        if(!member.equals(post.getMember())){
//        }
//        if(!post.getImgUrl().equals("")){
//            amazonS3Client.deleteObject(bucket, post.getImgUrl());
//        }
