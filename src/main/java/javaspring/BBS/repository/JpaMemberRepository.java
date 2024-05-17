    package javaspring.BBS.repository;

    import jakarta.persistence.EntityManager;
    import javaspring.BBS.domain.Member;

    import java.util.List;
    import java.util.Optional;

    public class JpaMemberRepository implements MemberRepository{
        private final EntityManager em;
        public JpaMemberRepository(EntityManager em){
            this.em=em;
        }
        public Member save(Member member){
            em.persist(member);
            return member;
        }
        public Optional<Member> findByMember_id(Long id){
            Member member = em.find(Member.class,id);
            return Optional.ofNullable(member);
        }

        public Optional<Member> findByMember_name(String member_name) {
            List<Member> result = em.createQuery("select m from Member m where m.member_name = :member_name",Member.class)
                    .setParameter("member_name",member_name)
                    .getResultList();
            return result.stream().findAny();
        }


        public List<Member> findAll() {
            return em.createQuery("select m from Member m",Member.class)
                    .getResultList();
        }
        @Override
        public Optional<Member> findByMemberNameAndMemberPassword(String member_name,String member_password){
            List<Member> result = em.createQuery("select m from Member m where m.member_name=:member_name and m.member_password=:member_password",Member.class)
                    .setParameter("member_name",member_name)
                    .setParameter("member_password",member_password)
                    .getResultList();
            return result.stream().findAny();
        }
    }
