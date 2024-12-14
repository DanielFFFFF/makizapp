package fr.makizart.common.database.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DatedEntityTest {

    private static class TestDatedEntity extends DatedEntity {
    }

    private TestDatedEntity datedEntity;

    @BeforeEach
    void setUp() {
        datedEntity = new TestDatedEntity();
    }

    @Test
    void testGetCreatedOn() {
        LocalDateTime simulatedCreationDate = LocalDateTime.of(2023, 11, 25, 10, 30);
        datedEntity.createdOn = simulatedCreationDate;

        assertEquals(simulatedCreationDate, datedEntity.getCreatedOn());
    }

    @Test
    void testDefaultCreatedOnIsNull() {
        assertNull(datedEntity.getCreatedOn());
    }
}
