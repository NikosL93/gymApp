package com.gymApp.controller;

import com.gymApp.config.AppConstants;
import com.gymApp.payload.MemberDTO;
import com.gymApp.payload.MemberResponse;
import com.gymApp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    private MemberService memberService;
    // Autowired by constructor
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @Operation(summary = "Get all Members")
    @GetMapping("api/admin/members")
    public ResponseEntity<MemberResponse> getAllMembers(
            //pagination
            @RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name= "sortBy", defaultValue = AppConstants.SORT_MEMBERS_BY, required = false) String sortBy,
            @RequestParam(name= "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        MemberResponse memberResponse = memberService.getAllMembers(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }
    @Operation(summary = "Create a member")
    @PostMapping("api/admin/members")
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) { //H jackson μετατρέπει το json σε κλάση MemberDTO
        MemberDTO savedMemberDTO = memberService.createMember(memberDTO);
        return new ResponseEntity<>(savedMemberDTO, HttpStatus.CREATED);
    }
    @Operation(summary = "Delete a member by ID")
    @DeleteMapping("api/admin/members/{memberId}")
    public ResponseEntity<MemberDTO> deleteMember(@PathVariable Long memberId) {
        MemberDTO deletedMember = memberService.deleteMember(memberId);
        return new ResponseEntity<>(deletedMember, HttpStatus.OK);
    }
    @Operation(summary = "Update a member by ID")
    @PutMapping("api/admin/members/{memberId}")
    public ResponseEntity<MemberDTO> updateMember(@Valid @RequestBody MemberDTO memberDTO, @PathVariable Long memberId) {
        MemberDTO updatedMember = memberService.updateMember(memberDTO, memberId);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
    @Operation(summary = "Join a gym class by ID")
    @PutMapping("api/public/members/{memberId}/joinClass/{gymClassId}")
    public ResponseEntity<MemberDTO> joinClass(@PathVariable Long memberId, @PathVariable Long gymClassId) {
        MemberDTO updatedMember = memberService.joinClass(memberId, gymClassId);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
    @Operation(summary = "Get/search a member by name")
    @GetMapping("api/admin/members/name/{name}")
    public ResponseEntity<MemberResponse> getMemberByName(@PathVariable String name) {
        MemberResponse foundMembers = memberService.searchMemberByName(name);
        return new ResponseEntity<>(foundMembers, HttpStatus.OK);
    }
}