package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class MemberRepository {

    // MemberService와 동일하게 스프링Data JPA에선 지원으로
    // @PersistenceContext 대신 @Autowired 사용가능 그러므로 MemberService와 처럼 @RequiredArgsConstructor 사용가능(인강 - 회원 서비스개발 17분)
    @PersistenceContext
    private EntityManager em;

    // 엔티티매니저 팩토리를 직접 주입 받는 어노테이션
//    @PersistenceUnit
//    private EntityManagerFactory emf;

    public void save(Member member) {
        em.persist(member);
    }

    //단건조회 find(타입, pk)
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //리스트조회
    public List<Member> findAll() {
        //                                  JPQL              ,     반환타입
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
