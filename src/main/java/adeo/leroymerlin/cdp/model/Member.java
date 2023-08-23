package adeo.leroymerlin.cdp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    public String getName() {
        return name;
    }

    public boolean hasNameContains(String value) {
        return getName().toLowerCase().contains(value.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        final Member other = (Member) o;
        return Objects.equals(id, other.id) && name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return id.hashCode() * this.name.hashCode();
    }
}
