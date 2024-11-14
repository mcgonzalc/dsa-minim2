package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointofInterest;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.List;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected List<User> users;
    protected List<PointofInterest> pointsofinterest;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.pointsofinterest = new LinkedList<>();
        this.users = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    //Function that adds the generated user from the function addUser to the list of Users
    public User addUser(User user) {
        logger.info("Petition for a new User: " + user);
        this.users.add(user);
        logger.info("User added successfully");
        return user;
    }

    //Functions that takes all the values necessaries to construct a new User and
    //passes them as a user object to be initialized
    public User createUser(Integer id, String name, String surname, String email, String birthday){
        return this.addUser(new User(id, name, surname, email, birthday));
    }

    //Function that returns the user with a specific ID number
    public User getUser(Integer id)
    {
        for (User user: this.users)
        {
            if (user.getId().equals(id))
            {
                logger.info("Obtained User with ID " + id + ": " + user);
                return user;
            }
        }

        logger.warn("User with ID " + id + " not found");
        return null;
    }

    //List all the Users in descending order
    public List<User> listUsersDescending()
    {
        List<User> orderedList = new LinkedList<>(this.users);
        Collections.sort(orderedList,
                new Comparator<User>()
                {
                    @Override
                    public int compare(User u1, User u2)
                    {
                        int surnameComparison = u1.getSurname().compareToIgnoreCase(u2.getSurname());
                        if (surnameComparison != 0) {
                            return surnameComparison;
                        }
                        return u1.getName().compareToIgnoreCase(u2.getName());
                    }
                });
        logger.info("Ordered list of users: " + orderedList);
        return orderedList;
    }

    //Function that adds a point of interest to the list of points of interest of a user
    public List<PointofInterest> addUserPointofInterest(Integer id, Integer horizontal, Integer vertical)
    {
        logger.info("Petition to add to the history of user with ID " + id + " a point of interest in coordinates (" + horizontal + ", " + vertical + ")");
        User user = getUser(id);
        if (user == null)
        {
            return null;
        }
        PointofInterest point = getPointofInterest(horizontal, vertical);
        if (point == null)
        {
            logger.warn("Point of interest not added to the list of user with ID " + id + " because of incorrect coordinates");
            return null;
        }
        else
        {
            user.addHistory(point);
            logger.info("Point of interest with coordinates (" + horizontal + "," + vertical + ") added to user with ID " + id);
            return user.getHistory();
        }
    }

    //List all the points of interest a User has passed through in chronological order
    public List<PointofInterest> listUserPointsofInterest(Integer id)
    {
        User user = getUser(id);
        if (user == null)
        {
            return null;
        }
        else
        {
            List<PointofInterest> orderedList = new ArrayList<>(user.getHistory());
            logger.info("Complete travel history of user with ID " + id + ": " + orderedList);
            return orderedList;
        }
    }

    public int sizePointsofInterest() {
        int ret = this.pointsofinterest.size();
        logger.info("There are " + ret + " points of interest in total");
        return ret;
    }

    public int sizeUsers() {
        int ret = this.users.size();
        logger.info("There are " + ret + " users in total");
        return ret;
    }

    //Function that adds the generated point of interest from the function addPointofInterest to the list of
    //points of interest
    public PointofInterest addPointofInterest(PointofInterest p) {
        logger.info("Petition for a new point of interest: " + p);
        this.pointsofinterest.add(p);
        logger.info("Point of interest added successfully");
        return p;
    }

    //Functions that takes all the values necessaries to construct a new point of interest and
    //passes them as a PointofInterest object to be initialized
    public PointofInterest createPointofInterest(ElementType type, Integer horizontal, Integer vertical){
        return this.addPointofInterest(new PointofInterest(type, horizontal, vertical));
    }

    //Function that returns the PointofIntesrest with a specific coordinates
    public PointofInterest getPointofInterest(Integer horizontal, Integer vertical)
    {
        for (PointofInterest p: this.pointsofinterest)
        {
            if (p.getHorizontal().equals(horizontal) && p.getVertical().equals(vertical))
            {
                logger.info("Obtained point of interest (" + p.getType() + ") with coordinates (" + horizontal + "," + vertical + ")");
                return p;
            }
        }

        logger.warn("Point of interest with coordinates (" + horizontal + "," + vertical + ") not found");
        return null;
    }

    //List all the points of interest with a specific type
    public List<PointofInterest> listPointsofInterest(ElementType type)
    {
        List<PointofInterest> orderedList = new LinkedList<>();
        for(PointofInterest p : this.pointsofinterest)
        {
            if(p.getType().equals(type))
            {
                orderedList.add(p);
            }
        }
        if (orderedList.isEmpty())
        {
            logger.info("There are no points of interest of the type " + type);
            return null;
        }
        else
        {
            logger.info("Ordered list of points of interest: " + orderedList);
            return orderedList;
        }
    }

    //List all the users that have gone through a specific point of interest
    public List<User> listUsersPointofInterest(Integer horizontal, Integer vertical)
    {
        PointofInterest point = getPointofInterest(horizontal, vertical);
        ArrayList<User> list = new ArrayList<>();
        if (point == null)
        {
            return null;
        }
        else
        {
            for (User user : this.users)
            {
                for (PointofInterest pointuser : user.getHistory())
                {
                    if(pointuser.equals(point))
                    {
                        list.add(user);
                        break;
                    }
                }
            }
            if (list.isEmpty())
            {
                logger.warn("No users have passed through the point of interest with coordinates (" + horizontal + "," + vertical + ")");
                return list;
            }
            else
            {
                logger.info("List of users tha have passed through " + point + ": " + list);
                return list;
            }
        }
    }

    //Function that serves to clear the entire database
    public void clear() {
        this.pointsofinterest.clear();
        this.users.clear();
    }
}
