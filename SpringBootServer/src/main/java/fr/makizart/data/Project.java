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
}
