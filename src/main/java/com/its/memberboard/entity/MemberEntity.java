package com.its.memberboard.entity;

import com.its.memberboard.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable = false,unique = true)
    private String memberEmail;

    @Column(length = 20,nullable = false)
    private String memberPassword;

    @Column(length = 20,nullable = false)
    private String memberName;

    @Column
    private int memberAge;

    @Column(length = 30)
    private String memberPhone;

    @Column(length = 50)
    private String memberProfile;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberProfile(memberDTO.getMemberProfile());
        return memberEntity;
    }

}
