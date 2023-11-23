package fr.makizart.common.database.table;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PROJECT")
public class Project extends DatedEntity {

    public Project(String name) {
        setName(name);
        arResource = Objects.requireNonNullElse(this.arResource, new ArrayList<>());
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(targetEntity = ArResource.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArResource> arResource;

    @Column(name="NAME", nullable = false)
    private String name;

    public Project() {
        arResource = Objects.requireNonNullElse(this.arResource, new ArrayList<>());
    }

    public List<ArResource> getArResource() {
        return arResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Project other = (Project) obj;

        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", arResource=" + arResource +
                ", name='" + name + '\'' +
                '}';
    }

    public void removeResource(ArResource res) {
        this.arResource.remove(res);
    }

    public void addResource(ArResource res) {
        this.arResource.add(res);
    }
}
