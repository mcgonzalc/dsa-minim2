package edu.upc.dsa;

import edu.upc.dsa.models.PointofInterest;
import edu.upc.dsa.models.User;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GameManagerTest {
    GameManager gm;

    @Before
    public void setUp() {
        this.gm = GameManagerImpl.getInstance();
        this.gm.createUser(0, "Jonathan", "Yolo", "g@g.com", "04/11/1964");
        this.gm.createUser(1, "Mary", "Pop", "e@e.com", "13/12/1985");
        this.gm.createPointofInterest(PointofInterest.ElementType.DOOR, 564, 98);
        this.gm.createPointofInterest(PointofInterest.ElementType.WALL, 45, 752);
        this.gm.createPointofInterest(PointofInterest.ElementType.BRIDGE, 14, -456);
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.gm.clear();
    }

    @Test
    public void addUserTest() {
        Assert.assertEquals(2, gm.sizeUsers());

        this.gm.createUser(2, "Peleo", "Trop", "e@e.com", "13/12/1985");

        Assert.assertEquals(3, gm.sizeUsers());

    }

    @Test
    public void getUsers() throws Exception {
        Assert.assertEquals(3, gm.sizeUsers());

        User u = this.gm.getUser(1);
        Assert.assertEquals("Mary", u.getName());
        Assert.assertEquals("Pop", u.getSurname());

        Assert.assertThrows(NotFoundException.class, () ->
                this.gm.getUser(-1));
    }
}
