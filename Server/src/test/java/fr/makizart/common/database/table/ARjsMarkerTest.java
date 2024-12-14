package fr.makizart.common.database.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ARjsMarkerTest {

    private ARjsMarker arjsMarker;

    @BeforeEach
    void setUp() {
        arjsMarker = new ARjsMarker();
    }

    @Test
    void testSetAndGetMarker1Path() throws Exception {
        URI marker1Path = new URI("http://example.com/marker1");
        arjsMarker.setMarker1Path(marker1Path);
        assertEquals(marker1Path, arjsMarker.getMarker1Path());
    }

    @Test
    void testSetAndGetMarker2Path() throws Exception {
        URI marker2Path = new URI("http://example.com/marker2");
        arjsMarker.setMarker2Path(marker2Path);
        assertEquals(marker2Path, arjsMarker.getMarker2Path());
    }

    @Test
    void testSetAndGetMarker3Path() throws Exception {
        URI marker3Path = new URI("http://example.com/marker3");
        arjsMarker.setMarker3Path(marker3Path);
        assertEquals(marker3Path, arjsMarker.getMarker3Path());
    }

    @Test
    void testSetAndGetMarkerData1() {
        byte[] markerData1 = {1, 2, 3, 4};
        arjsMarker.setMarkerData1(markerData1);
        assertArrayEquals(markerData1, arjsMarker.getMarkerData1());
    }

    @Test
    void testSetAndGetMarkerData2() {
        byte[] markerData2 = {5, 6, 7, 8};
        arjsMarker.setMarkerData2(markerData2);
        assertArrayEquals(markerData2, arjsMarker.getMarkerData2());
    }

    @Test
    void testSetAndGetMarkerData3() {
        byte[] markerData3 = {9, 10, 11, 12};
        arjsMarker.setMarkerData3(markerData3);
        assertArrayEquals(markerData3, arjsMarker.markerData3());
    }

    @Test
    void testDefaultValues() {
        assertNull(arjsMarker.getMarker1Path());
        assertNull(arjsMarker.getMarker2Path());
        assertNull(arjsMarker.getMarker3Path());
        assertNull(arjsMarker.getMarkerData1());
        assertNull(arjsMarker.getMarkerData2());
        assertNull(arjsMarker.markerData3());
    }
}
