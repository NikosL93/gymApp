package com.gymApp.service;

import com.gymApp.exceptions.ApiException;
import com.gymApp.exceptions.ResourceNotFoundException;
import com.gymApp.model.*;
import com.gymApp.payload.MemberDTO;
import com.gymApp.payload.MemberResponse;
import com.gymApp.repositories.GymClassRepository;
import com.gymApp.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.gymApp.repositories.UserRepository;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GymClassRepository gymClassRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MemberResponse getAllMembers(Integer pageNumber,Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        //Παίρνω όλα τα members από DB
        Page<Member> memberPage  = memberRepository.findAll(pageDetails);
        List<Member> members = memberPage.getContent();
        if (members.isEmpty()) {
            throw new ApiException("No members created till now!");
        }
        //Παίρνω τη λίστα απο members από DB
        List<MemberDTO> memberDTOS = members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .toList();
        MemberResponse memberResponse = new MemberResponse();
        //Αποθηκεύω τα members στη λίστα
        memberResponse.setContent(memberDTOS);
        memberResponse.setPageNumber(memberPage.getNumber());
        memberResponse.setPageSize(memberPage.getSize());
        memberResponse.setTotalElements(memberPage.getTotalElements());
        memberResponse.setTotalPages(memberPage.getTotalPages());
        memberResponse.setLastPage(memberPage.isLast());
        return memberResponse;
    }
    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        // Έλεγχος αν υπάρχει ήδη το username
        if(memberRepository.existsByUserName(memberDTO.getUserName())) {
            throw new ApiException("Username already exists: " + memberDTO.getUserName());
        }
        // Έλεγχος αν υπάρχει ήδη το email
        if(memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new ApiException("Email already exists: " + memberDTO.getEmail());
        }
        // Δημιουργώ τον αντίστοιχο User
        User user = new User();
        user.setUserName(memberDTO.getUserName());
        user.setEmail(memberDTO.getEmail());
        user.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        user.setRole(Role.ROLE_MEMBER);
        //Δημιουργώ το member
        Member member =  modelMapper.map(memberDTO, Member.class);
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        //Αντιστοίχιση με user
        member.setUser(user);
        member.setRole(Role.ROLE_MEMBER);
        //Το σώζω στην DB
        Member savedMember = memberRepository.save(member);
        return modelMapper.map(savedMember, MemberDTO.class);
    }
    @Override
    public MemberDTO deleteMember(Long memberId) {
        //Παίρνω το member από DB
        Optional<Member> savedMemberOptional = memberRepository.findById(memberId);
        Member savedMember = savedMemberOptional
                .orElseThrow(()->new ResourceNotFoundException("member", "memberId", memberId)); // H orElseThrow επενεργεί πάνω στο Optional αντικ savedMemberOptional και επιστρέφει την τιμή του (αντικ member) αν υπάρχει
        //Το διαγράφω απ την DB
        memberRepository.delete(savedMember); //χρησιμοποιώ το memberRepository που δήλωσα ως field στην αρχή
        return modelMapper.map(savedMember, MemberDTO.class);
    }
    @Override
    public MemberDTO updateMember(MemberDTO memberDTO, Long memberId) {
        //Παίρνω το member από DB
        Optional<Member> savedMemberOptional = memberRepository.findById(memberId);
        Member savedMember = savedMemberOptional.orElseThrow(  //Έλεγχος αν υπάρχει, δε χρησιμοποιώ κάπου το savedMember απλά για να δώ αν υπάρχει
                () -> new ResourceNotFoundException("member", "memberId", memberId)); // H orElseThrow επενεργεί πάνω στο Optional αντικ savedMemberOptional και επιστρέφει την τιμή του (τύπου:Member) αν υπάρχει
        Member member = modelMapper.map(memberDTO, Member.class);
        //Ενημερώνω το memberId του δοθέντος member μ' αυτό που βρήκε στην DB ώστε να αντικατασταθεί
        member.setMemberId(memberId);
        //Το σώζω στην DB
        Member updatedMember = memberRepository.save(member); //Η memberRepository.save() επιστρέφει και το member
        return modelMapper.map(updatedMember, MemberDTO.class);
    }

    @Override
    public MemberDTO joinClass(Long memberId, Long gymClassId) {
        //Παίρνω το member από DB
        Optional<Member> savedMemberOptional = memberRepository.findById(memberId);
        Member savedMember = savedMemberOptional.orElseThrow(
                () -> new ResourceNotFoundException("member", "memberId", memberId));
        //Παίρνω το gymClass από DB
        Optional<GymClass> gymClassOptional = gymClassRepository.findById(gymClassId);
        GymClass gymClass = gymClassOptional.orElseThrow(
                () -> new ResourceNotFoundException("GymClass", "classId", gymClassId));
        //Ενημέρωση πεδίου gymCLass του Member
        savedMember.setGymClass(gymClass);
        //Το σώζω στην DB
        Member updatedMember = memberRepository.save(savedMember);
        return modelMapper.map(updatedMember, MemberDTO.class);
    }
    @Override
    public MemberResponse searchMemberByName(String name) {
        //Παίρνω τη λίστα απο members από DB
        List<Member> members = memberRepository.findByMemberNameLikeIgnoreCase("%" + name + "%");
        List<MemberDTO> memberDTOS = members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .toList();
        MemberResponse memberResponse = new MemberResponse();
        //Αποθηκεύω τα members στη λίστα
        memberResponse.setContent(memberDTOS);
        return memberResponse;
    }
}
