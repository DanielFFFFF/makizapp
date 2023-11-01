package fr.makizart.database.table;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PROJECT")
public class Project {

    public Project() {
        arRessource= Objects.requireNonNullElse(this.arRessource, new ArrayList<>());
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ArRessource> arRessource;

    @Column(name="NAME", nullable = false)
    private String name;

    public List<ArRessource> getArRessource() {
        return arRessource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
