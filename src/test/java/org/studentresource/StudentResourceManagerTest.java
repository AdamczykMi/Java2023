package org.studentresource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentResourceManagerTest {

    // changed from <Course> to <StudentResource>
    private StudentResourceManager<StudentResource> manager;

    @BeforeEach
    void setUp() {
        manager = new StudentResourceManager<>();
    }

    @Test
    void addAndRetrieveResourceTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        manager.addResource(course);

        Course retrieved = (Course) manager.getResource("CS101");
        assertNotNull(retrieved, "Retrieved course should not be null.");
        assertEquals("Introduction to Computer Science", retrieved.getName(), "Course name should match.");
    }

    // added RemoveResourceTest() on StudyMaterial
    @Test
    void removeResourceTest() {
        StudyMaterial material = new StudyMaterial("CS101", "Introduction to Computer Science");
        manager.addResource(material);
        assertNotNull(manager.getResource("CS101"), "Resource should exist before removal.");

        manager.removeResource(material);
        assertNull(manager.getResource("CS101"), "Resource removed");
    }
}
