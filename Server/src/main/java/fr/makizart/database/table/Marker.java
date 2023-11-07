package fr.makizart.database.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.net.URI;

@Entity
@Table(name = "MARKER")
public class Marker extends Media{
	@Column(name="PATH", nullable=false)
	private URI pathToRessource;
}
