package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointofInterest;
import edu.upc.dsa.models.User;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameManagerTest {
    GameManager gm;

    @Before
    public void setUp() {
        this.gm = GameManagerImpl.getInstance();
        this.gm.createUser(0, "Jonathan", "Yolo", "g@g.com", "04/11/1964");
        this.gm.createUser(1, "Mary", "Pop", "e@e.com", "13/12/1985");
        this.gm.createPointofInterest(ElementType.DOOR, 564, 98);
        this.gm.createPointofInterest(ElementType.WALL, 45, 752);
        this.gm.createPointofInterest(ElementType.BRIDGE, 14, -456);
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.gm.clear();
    }

    @Test
    public void addUser() {
        assertEquals(2, gm.sizeUsers());

        this.gm.createUser(2, "Peleo", "Trop", "e@e.com", "13/12/1985");

        assertEquals(3, gm.sizeUsers());

    }

    @Test
    public void getUser() throws Exception {
        assertEquals(2, gm.sizeUsers());

        User u = this.gm.getUser(1);
        assertEquals("Mary", u.getName());
        assertEquals("Pop", u.getSurname());
    }
    
    @Test
    public void listUsersDescending() {
        gm.createUser(2, "Alex", "Smith", "alex@feral.com", "01/01/1990");
        gm.createUser(3, "Bob", "El Manetes", "bob@elmanetes.com", "02/02/1991");

        List<User> sortedUsers = gm.listUsersDescending();

        assertEquals(4, sortedUsers.size());
        assertEquals("El Manetes", sortedUsers.get(0).getSurname());
        assertEquals("Pop", sortedUsers.get(1).getSurname());
        assertEquals("Smith", sortedUsers.get(2).getSurname());
        assertEquals("Yolo", sortedUsers.get(3).getSurname());
    }

    @Test
    public void addUserPointofInterest() {
        // Test adding a valid point of interest to a user
        List<PointofInterest> history = this.gm.addUserPointofInterest(0, 564, 98);
        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(ElementType.DOOR, history.get(0).getType());
        assertEquals(564, history.get(0).getHorizontal().intValue());
        assertEquals(98, history.get(0).getVertical().intValue());

        //Test adding another point of interest to the same user
        history = this.gm.addUserPointofInterest(0, 45, 752);
        assertNotNull(history);
        assertEquals(2, history.size());
        assertEquals(ElementType.WALL, history.get(1).getType());

        //Test adding a point of interest to a different user
        List<PointofInterest> history2 = this.gm.addUserPointofInterest(1, 14, -456);
        assertNotNull(history2);
        assertEquals(1, history2.size());
        assertEquals(ElementType.BRIDGE, history2.get(0).getType());

        //Test adding a non-existent point of interest
        List<PointofInterest> invalidHistory = this.gm.addUserPointofInterest(0, 999, 999);
        assertNull(invalidHistory);

        // Test adding a point of interest to a non-existent user
        List<PointofInterest> nonExistentUserHistory = this.gm.addUserPointofInterest(99, 564, 98);
        assertNull(nonExistentUserHistory);
    }

    @Test
    public void listUserPointsofInterest() {
        //Add points of interest in a specific order
        gm.addUserPointofInterest(0, 564, 98);  // DOOR
        gm.addUserPointofInterest(0, 45, 752);  // WALL
        gm.addUserPointofInterest(0, 14, -456); // BRIDGE

        List<PointofInterest> history = gm.listUserPointsofInterest(0);
        assertNotNull(history);
        assertEquals(3, history.size());

        //Check if the order is maintained
        assertEquals(ElementType.DOOR, history.get(0).getType());
        assertEquals(564, history.get(0).getHorizontal().intValue());
        assertEquals(98, history.get(0).getVertical().intValue());

        assertEquals(ElementType.WALL, history.get(1).getType());
        assertEquals(45, history.get(1).getHorizontal().intValue());
        assertEquals(752, history.get(1).getVertical().intValue());

        assertEquals(ElementType.BRIDGE, history.get(2).getType());
        assertEquals(14, history.get(2).getHorizontal().intValue());
        assertEquals(-456, history.get(2).getVertical().intValue());
    }

    @Test
    public void addPointofInterest() {
        // Create a new point of interest
        PointofInterest newPoint = new PointofInterest(ElementType.POTION, 100, 200);

        // Add the point of interest
        PointofInterest addedPoint = gm.addPointofInterest(newPoint);

        // Check if the returned point is not null and matches the input
        assertNotNull(addedPoint);
        assertEquals(ElementType.POTION, addedPoint.getType());
        assertEquals(Integer.valueOf(100), addedPoint.getHorizontal());
        assertEquals(Integer.valueOf(200), addedPoint.getVertical());

        // Verify that the point was actually added to the list
        List<PointofInterest> potionPoints = gm.listPointsofInterest(ElementType.POTION);
        assertTrue(potionPoints.contains(newPoint));
        assertEquals(1, potionPoints.size());

        // Verify that other types still have their original points
        assertEquals(1, gm.listPointsofInterest(ElementType.DOOR).size());
        assertEquals(1, gm.listPointsofInterest(ElementType.WALL).size());
        assertEquals(1, gm.listPointsofInterest(ElementType.BRIDGE).size());
    }

    @Test
    public void createPointofInterest() {
        // Create a new point of interest
        PointofInterest newPoint = gm.createPointofInterest(ElementType.POTION, 100, 200);

        // Check if the returned point is not null and has correct values
        assertNotNull(newPoint);
        assertEquals(ElementType.POTION, newPoint.getType());
        assertEquals(Integer.valueOf(100), newPoint.getHorizontal());
        assertEquals(Integer.valueOf(200), newPoint.getVertical());

        // Verify that the point was actually added to the list
        List<PointofInterest> potionPoints = gm.listPointsofInterest(ElementType.POTION);
        assertTrue(potionPoints.contains(newPoint));
        assertEquals(1, potionPoints.size());
    }

    @Test
    public void getPointofInterest() {
        // Test getting an existing point of interest
        PointofInterest point = gm.getPointofInterest(564, 98);
        assertNotNull(point);
        assertEquals(ElementType.DOOR, point.getType());
        assertEquals(Integer.valueOf(564), point.getHorizontal());
        assertEquals(Integer.valueOf(98), point.getVertical());

        // Test getting another existing point of interest
        point = gm.getPointofInterest(45, 752);
        assertNotNull(point);
        assertEquals(ElementType.WALL, point.getType());
        assertEquals(Integer.valueOf(45), point.getHorizontal());
        assertEquals(Integer.valueOf(752), point.getVertical());

        // Test getting a point of interest with negative coordinates
        point = gm.getPointofInterest(14, -456);
        assertNotNull(point);
        assertEquals(ElementType.BRIDGE, point.getType());
        assertEquals(Integer.valueOf(14), point.getHorizontal());
        assertEquals(Integer.valueOf(-456), point.getVertical());

        // Test getting a non-existent point of interest
        point = gm.getPointofInterest(100, 100);
        assertNull(point);

        // Test getting a point of interest with null coordinates
        point = gm.getPointofInterest(null, null);
        assertNull(point);

        // Test getting a point of interest with one null coordinate
        point = gm.getPointofInterest(564, null);
        assertNull(point);
        point = gm.getPointofInterest(null, 98);
        assertNull(point);
    }

    @Test
    public void listPointsofInterest() {
        // Test listing points of interest of type DOOR
        List<PointofInterest> doorPoints = gm.listPointsofInterest(ElementType.DOOR);
        assertNotNull(doorPoints);
        assertEquals(1, doorPoints.size());
        assertEquals(ElementType.DOOR, doorPoints.get(0).getType());
        assertEquals(Integer.valueOf(564), doorPoints.get(0).getHorizontal());
        assertEquals(Integer.valueOf(98), doorPoints.get(0).getVertical());

        // Test listing points of interest of type WALL
        List<PointofInterest> wallPoints = gm.listPointsofInterest(ElementType.WALL);
        assertNotNull(wallPoints);
        assertEquals(1, wallPoints.size());
        assertEquals(ElementType.WALL, wallPoints.get(0).getType());
        assertEquals(Integer.valueOf(45), wallPoints.get(0).getHorizontal());
        assertEquals(Integer.valueOf(752), wallPoints.get(0).getVertical());

        // Test listing points of interest of type BRIDGE
        List<PointofInterest> bridgePoints = gm.listPointsofInterest(ElementType.BRIDGE);
        assertNotNull(bridgePoints);
        assertEquals(1, bridgePoints.size());
        assertEquals(ElementType.BRIDGE, bridgePoints.get(0).getType());
        assertEquals(Integer.valueOf(14), bridgePoints.get(0).getHorizontal());
        assertEquals(Integer.valueOf(-456), bridgePoints.get(0).getVertical());

        // Test listing points of interest of a type that doesn't exist
        List<PointofInterest> potionPoints = gm.listPointsofInterest(ElementType.POTION);
        assertNull(potionPoints);
    }

    @Test
    public void listUsersPointofInterest() {
        // Add points to users' history
        gm.addUserPointofInterest(0, 564, 98);  // Jonathan visits DOOR
        gm.addUserPointofInterest(1, 564, 98);  // Mary visits DOOR
        gm.addUserPointofInterest(0, 45, 752);  // Jonathan visits WALL

        // Test listing users who visited the DOOR
        List<User> doorVisitors = gm.listUsersPointofInterest(564, 98);
        assertNotNull(doorVisitors);
        assertEquals(2, doorVisitors.size());
        assertTrue(doorVisitors.stream().anyMatch(u -> u.getName().equals("Jonathan")));
        assertTrue(doorVisitors.stream().anyMatch(u -> u.getName().equals("Mary")));

        // Test listing users who visited the WALL
        List<User> wallVisitors = gm.listUsersPointofInterest(45, 752);
        assertNotNull(wallVisitors);
        assertEquals(1, wallVisitors.size());
        assertEquals("Jonathan", wallVisitors.get(0).getName());

        // Test listing users who visited the BRIDGE (should be empty but not null)
        List<User> bridgeVisitors = gm.listUsersPointofInterest(14, -456);
        assertNotNull(bridgeVisitors);
        assertTrue(bridgeVisitors.isEmpty());

        // Test listing users for a non-existent point of interest
        List<User> nonExistentVisitors = gm.listUsersPointofInterest(999, 999);
        assertNull(nonExistentVisitors);
    }
    
}
