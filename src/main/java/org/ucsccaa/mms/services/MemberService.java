package org.ucsccaa.mms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.ucsccaa.mms.repositories.MemberRepository;
import org.ucsccaa.mms.domains.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Long addMember(Member member) {
        if (member == null){
            throw new RuntimeException("argument cannot be NULL");
        }
        if (memberRepository.exists(Example.of(member))) {
            throw new RuntimeException("member already exists");
        }
        return memberRepository.save(member).getId();
    }

    public Optional<Member> updateMember(Member member) {
        if (member == null || member.getId() == null){
            throw new RuntimeException("argument cannot be NULL");
        }
        if (memberRepository.existsById(member.getId())) {
            return Optional.of(memberRepository.save(member));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Member> getMember(Long id) {
        if (id == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        return memberRepository.findById(id);
    }

    public Boolean deleteMember(Long id) {
        if (id == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Member> getMemberByEmail(String email) {
        if (email == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> getMemberByPhone(String phone) {
        if (phone == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        return memberRepository.findByPhone(phone);
    }

    public Optional<Member> getMemberByWechat(String wechat) {
        if (wechat == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        return memberRepository.findByWechat(wechat);
    }

    public Optional<Member> getMemberByStdId(String stdId) {
        if (stdId == null) {
            throw new RuntimeException("argument cannot be NULL");
        }
        return memberRepository.findByStdId(stdId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
