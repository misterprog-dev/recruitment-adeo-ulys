package adeo.leroymerlin.cdp.builder;

import adeo.leroymerlin.cdp.model.Member;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberMB {
    private Member member;

    public MemberMB() {
        member = mock(Member.class);
    }

    public MemberMB withName(String name) {
        when(member.getName()).thenReturn(name);
        return this;
    }

    public Member build() {
        return member;
    }
}
