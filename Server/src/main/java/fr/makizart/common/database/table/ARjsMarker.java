package fr.makizart.common.database.table;

import fr.makizart.common.storageservice.dto.MarkerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.net.URI;

@Entity
@Table(name = "MARKER")
public class ARjsMarker extends Media{

	@Column(name="MARKERPATH1")
	private URI marker1Path;

	@Column(name="MARKERPATH2")
	private URI marker2Path;
	@Column(name="MARKERPATH3")
	private URI marker3Path;

	@Column(name = "marker_data1", columnDefinition="BYTEA")
	private byte[] markerData1;

	@Column(name = "marker_data2", columnDefinition="BYTEA")
	private byte[] markerData2;

	@Column(name = "marker_data3", columnDefinition="BYTEA")
	private byte[] markerData3;



	public URI getMarker1Path() {
		return marker1Path;
	}

	public byte[] getMarkerData1() {
		return markerData1;
	}

	public void setMarkerData1(byte[] markerData1) {
		this.markerData1 = markerData1;
	}

	public byte[] getMarkerData2() {
		return markerData2;
	}

	public void setMarkerData2(byte[] markerData2) {
		this.markerData2 = markerData2;
	}

	public byte[] markerData3() {
		return markerData3;
	}

	public void setMarkerData3(byte[] markerData3) {
		this.markerData3 = markerData3;
	}

	public void setMarker1Path(URI marker1Path) {
		this.marker1Path = marker1Path;
	}

	public URI getMarker2Path() {
		return marker2Path;
	}

	public void setMarker2Path(URI marker2Path) {
		this.marker2Path = marker2Path;
	}

	public URI getMarker3Path() {
		return marker3Path;
	}

	public void setMarker3Path(URI marker3Path) {
		this.marker3Path = marker3Path;
	}

}
