package adeo.leroymerlin.cdp.builder;

import adeo.leroymerlin.cdp.model.Band;
import adeo.leroymerlin.cdp.model.Member;

import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BandMB {
    private Band band;

    public BandMB() {
        band = mock(Band.class);
    }

    public BandMB withName(String name) {
        when(band.getName()).thenReturn(name);
        return this;
    }

    public BandMB withMembers(Set<Member> members) {
        when(band.getMembers()).thenReturn(members);
        return this;
    }

    public Band build() {
        return band;
    }
}
