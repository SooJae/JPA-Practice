package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("멤버 테스트")
@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em;
  @Test
  void 회원가입() throws Exception {
    //given
    Member member = new Member();
    member.setName("soo");
    //when
    Long saveId = memberService.join(member);

    //then
    em.flush();
    assertEquals(member, memberRepository.findOne(saveId));
  }

  @Test()
  @DisplayName("중복_회원_예외")
  void duplicate_exception() throws Exception {
    assertThrows(IllegalStateException.class, () -> {

      //given
    Member member1 = new Member();
    member1.setName("soo1");

    Member member2 = new Member();
    member2.setName("soo1");
    //when
    memberService.join(member1);
    memberService.join(member2);

    //then
    fail("예외가 발생해야 한다.");
    });
  }
}
