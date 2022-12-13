package com.its.memberboard.dto;

import com.its.memberboard.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private int memberAge;
    private String memberPhone;
    private LocalDateTime memberCreatedDate;
    private String memberProfile = "";
    private MultipartFile memberFile;

    public static MemberDTO toDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberAge(memberEntity.getMemberAge());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setMemberProfile(memberEntity.getMemberProfile());
        return memberDTO;
    }


}
