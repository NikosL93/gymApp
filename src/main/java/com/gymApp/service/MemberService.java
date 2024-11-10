package com.gymApp.service;

import com.gymApp.payload.MemberDTO;
import com.gymApp.payload.MemberResponse;

public interface MemberService {

    MemberResponse getAllMembers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    MemberDTO createMember(MemberDTO memberDTO);
    MemberDTO deleteMember(Long memberId);
    MemberDTO updateMember(MemberDTO memberDTO, Long memberId);
    MemberDTO joinClass(Long memberId, Long gymClassId);
    MemberResponse searchMemberByName(String name);
}
