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
import com.example.titleacdemy.post.dto.PostRequestDto;
import com.example.titleacdemy.post.dto.PostResponseDto;
import com.example.titleacdemy.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AmazonS3Client amazonS3Client;

    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public ResponseDto<?> createPost(List<MultipartFile> multipartFile, PostRequestDto postRequestDto, Member member) throws IOException {

        String imgurl = null;

        Post post = null;

        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();

                post = Post.builder()
                        .member(member)
                        .title(postRequestDto.getTitle())
                        .content(postRequestDto.getContent())
                        .imgUrl(imgurl)
                        .build();

                postRepository.save(post);
            }
        }
        return ResponseDto.success(new PostResponseDto(post));
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPostAll(){
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        return null;
    }
}
