package authentication.jong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import authentication.jong.Entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    Optional<Member> findByEmail(String email);
} 
