package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointofInterest;
import edu.upc.dsa.models.User;

import java.util.List;

public interface GameManager {

    public User createUser(Integer id, String name, String surname, String email, String birthday);
    public User addUser(User user);
    public User getUser(Integer id);

    public List<User> listUsersDescending();
    public List<PointofInterest> listUserPointsofInterest(Integer id);
    public List<PointofInterest> addUserPointofInterest(Integer id, Integer horizontal, Integer vertical);

    public int sizeUsers();

    public PointofInterest createPointofInterest(ElementType type, Integer horizontal, Integer vertical);
    public PointofInterest addPointofInterest(PointofInterest point);
    public PointofInterest getPointofInterest(Integer horizontal, Integer vertical);
    public List<PointofInterest> listPointsofInterest(ElementType type);
    public List<User> listUsersPointofInterest(Integer horizontal, Integer vertical);

    public void clear();
    public int sizePointsofInterest();
}
