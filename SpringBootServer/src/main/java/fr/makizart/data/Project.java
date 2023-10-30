package fr.makizart.data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany()
    private List<ArRessource> arRessource;

    @Column(name="NAME", nullable = false)
    private String name;

    public List<ArRessource> getArRessource() {
        return arRessource;
    }

    public void setArRessource(List<ArRessource> arRessource) {
        this.arRessource = arRessource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
