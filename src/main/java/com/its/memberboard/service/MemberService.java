package com.its.memberboard.service;
import com.its.memberboard.dto.MemberDTO;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long save(MemberDTO memberDTO) throws IOException {
        if (!memberDTO.getMemberFile().isEmpty()){
            MultipartFile memberFile = memberDTO.getMemberFile();
            String memberProfile = System.currentTimeMillis()+"-"+memberFile.getOriginalFilename();
            String savePath = "D:\\springboot_img\\" + memberProfile;
            memberFile.transferTo(new File(savePath));
            memberDTO.setMemberProfile(memberProfile);
            return memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        }else {
            return memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        }

    }

    public String emailDuplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "ok";
        } else {
            return null;
        }
    }

    public MemberDTO memberLogin(String memberEmail, String memberPassword) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (memberEntity.getMemberPassword().equals(memberPassword)){
                return MemberDTO.toDTO(memberEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity updateEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(updateEntity);
    }
}
