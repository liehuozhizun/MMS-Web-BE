package org.ucsccaa.mms.controllers;

import org.springframework.web.bind.annotation.*;
import org.ucsccaa.mms.services.MemberService;
import org.ucsccaa.mms.domains.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<Object> addMember(@RequestBody Member member, HttpServletRequest req) throws URISyntaxException {
        Long id;
        try {
            id = memberService.addMember(member);
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.created(new URI(req.getRequestURI() + id)).build();
    }

    @PutMapping
    public ResponseEntity<Member> updateMember(@RequestBody Member member) throws URISyntaxException {
        Optional<Member> newMember = memberService.updateMember(member);
        return newMember.isPresent() ? ResponseEntity.ok(newMember.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long id) {
        boolean delete;
        try {
           delete = memberService.deleteMember(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return delete ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Member> getMember(@PathVariable("id") Long id) {
        Optional<Member> member;
        try {
            member = memberService.getMember(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return member.isPresent() ? ResponseEntity.ok(member.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable("email") String email) {
        Optional<Member> member;
        try {
            member = memberService.getMemberByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return member.isPresent() ? ResponseEntity.ok(member.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Member> getMemberByPhone(@PathVariable("phone") String phone) {
        Optional<Member> member;
        try {
            member = memberService.getMemberByPhone(phone);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return member.isPresent() ? ResponseEntity.ok(member.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/wechat/{wechat}")
    public ResponseEntity<Member> getMemberByWechat(@PathVariable("wechat") String wechat) {
        Optional<Member> member;
        try {
            member = memberService.getMemberByWechat(wechat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return member.isPresent() ? ResponseEntity.ok(member.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/stdid/{stdid}")
    public ResponseEntity<Member> getMemberByStdId(@PathVariable("stdid") String stdId) {
        Optional<Member> member;
        try {
            member = memberService.getMemberByStdId(stdId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return member.isPresent() ? ResponseEntity.ok(member.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }
}
