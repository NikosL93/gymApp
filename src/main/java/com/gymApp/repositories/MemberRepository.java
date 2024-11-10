package com.gymApp.repositories;

import com.gymApp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {//Long Είναι ο τυπος του ID
    List<Member> findByMemberNameLikeIgnoreCase(String name); //To like είναι για το query της SQL ενώ το IgnoreCase είναι για κεφαλαία-μικρά
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<Member> findByUserName(String username);
}
