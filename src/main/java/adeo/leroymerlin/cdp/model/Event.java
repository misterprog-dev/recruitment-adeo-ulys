package adeo.leroymerlin.cdp.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    private String imgUrl;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<Band> bands = new HashSet<>();

    private Integer nbStars;

    private String comment;

    public void update(Integer nbStars, String comment) {
        this.nbStars = nbStars;
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setBands(Set<Band> bands) {
        this.bands = bands;
    }

    public void setNbStars(Integer nbStars) {
        this.nbStars = nbStars;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Band> getBandsWithMembersNameContains(String value) {
        return getBands().stream()
                .map(b -> constructBand(value, b))
                .filter(b -> !b.getMembers().isEmpty())
                .collect(toSet());
    }

    private Band constructBand(String value, Band b) {
        Band band = new Band();
        Set<Member> members = b.getMembersNameContains(value);
        band.setName(b.getName() + " [" + members.size() + "]");
        band.setMembers(members);
        return band;
    }

    public List<String> bandsName() {
        return getBands().stream()
                .map(Band::getName)
                .collect(toList());
    }

    public List<Member> bandsMembers() {
        return getBands().stream()
                .map(Band::getMembers)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Set<Band> getBands() {
        return bands;
    }

    public Integer getNbStars() {
        return nbStars;
    }

    public String getComment() {
        return comment;
    }
}
