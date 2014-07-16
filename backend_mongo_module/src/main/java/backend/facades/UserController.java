package backend.facades;

import backend.connections.MongoFactory;
import backend.entities.UserEntity;
import backend.interfaces.UserControllerManager;
import backend.types.StatusTypes;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.springframework.stereotype.Service;

/**
 *
 * @author armen
 */
@Service("userController")
public class UserController implements Serializable, UserControllerManager {

    private static final long serialVersionUID = 1L;
    private DBCollection userCollection;
    private DB database = null;
    private MongoFactory mongoFactory;

    public UserController() {
        Mongo mongo;
        try {
            mongoFactory = new MongoFactory();
            int connectionsPerHost = 20;
            mongo = mongoFactory.createMongoInstance(connectionsPerHost);
            database = mongo.getDB("tmb16396");
            try {
                userCollection = database.getCollection("users");
            } catch (Exception e) {
                userCollection = database.createCollection("users", null);
            }
        } catch (MongoException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getMembersCount() {
        Integer listCount = 0;
        try {
            BasicDBObject query = new BasicDBObject();
            DBCursor cursor = userCollection.find(query);
            listCount = cursor.count();
            System.out.println("MEMBER COUNT backend" +listCount );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCount;
    }

    public boolean updateImageId(Long userId, String imageId) {
        try {
            BasicDBObject document = new BasicDBObject();
            document.append("$set", new BasicDBObject().append("imageId", imageId));
            userCollection.update(new BasicDBObject().append("_id", userId), document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<UserEntity> getUserList() {
        List<UserEntity> userList = new ArrayList<UserEntity>();
        String sort = "registeredDate";
        String order = "desc";
        DBObject sortCriteria = new BasicDBObject(sort, "desc".equals(order) ? -1 : 1);
        BasicDBObject query = new BasicDBObject();

        DBCursor cursor = userCollection.find(query).sort(sortCriteria);
        try {
            while (cursor.hasNext()) {
                DBObject document = cursor.next();
                UserEntity userEntity = new UserEntity();
                userEntity.setId((Long) document.get("_id"));
                userEntity.setUsername((String) document.get("username"));
                userEntity.setEmail((String) document.get("email"));
                userEntity.setFirstname((String) document.get("firstname"));
                userEntity.setPasswd((String) document.get("passwd"));
                userEntity.setLastname((String) document.get("lastname"));
                userEntity.setStatus((Integer) document.get("status"));
                userEntity.setImageId((String) document.get("imageId"));
                userEntity.setRegisteredDate((Date) document.get("registeredDate"));
                userEntity.setLastLoginDate((Date) document.get("lastLoginDate"));
                userEntity.setLanguageCode((String) document.get("languageCode"));
                userEntity.setUserRole((Integer) document.get("userRole"));
                userEntity.setModeratorValue((Integer) document.get("moderator"));
                Integer value = (Integer) document.get("subscribe");
                userEntity.setPersonalWebPage((String) document.get("webpage"));
                if (value != null && value.equals(StatusTypes.ACCEPT_LICENSE)) {
                    userEntity.setAcceptSubscr(true);
                } else {
                    userEntity.setAcceptSubscr(false);
                }
                userList.add(userEntity);
            }
        } finally {
            cursor.close();
        }
        return userList;
    }

    public void updatePassword(Long id, String password) {
        BasicDBObject document = new BasicDBObject();
        document.append("$set", new BasicDBObject()
                .append("passwd", hashPassword(password)));
        userCollection.update(new BasicDBObject().append("_id", id), document);
    }

    public List<UserEntity> getSubscribersList() {
        UserEntity userEntity = null;
        List<UserEntity> userList = new ArrayList<UserEntity>();
        BasicDBObject query = new BasicDBObject();
        query.put("subscribe", StatusTypes.ACCEPT_SUBSCRB);
        DBCursor cursor = userCollection.find(query);
        try {
            while (cursor.hasNext()) {
                DBObject document = cursor.next();
                userEntity = new UserEntity();
                userEntity.setId((Long) document.get("_id"));
                userEntity.setUsername((String) document.get("username"));
                userEntity.setEmail((String) document.get("email"));
                userEntity.setFirstname((String) document.get("firstname"));
                userEntity.setPasswd((String) document.get("passwd"));
                userEntity.setLastname((String) document.get("lastname"));
                userEntity.setStatus((Integer) document.get("status"));
                userEntity.setImageId((String) document.get("imageId"));
                userEntity.setRegisteredDate((Date) document.get("registeredDate"));
                userEntity.setLastLoginDate((Date) document.get("lastLoginDate"));
                userEntity.setLanguageCode((String) document.get("languageCode"));
                userEntity.setUserRole((Integer) document.get("userRole"));
                userEntity.setModeratorValue((Integer) document.get("moderator"));
                userEntity.setPersonalWebPage((String) document.get("webpage"));
                Integer value = (Integer) document.get("subscribe");
                if (value != null && value.equals(StatusTypes.ACCEPT_LICENSE)) {
                    userEntity.setAcceptSubscr(true);
                } else {
                    userEntity.setAcceptSubscr(false);
                }
                userList.add(userEntity);
            }
        } finally {
            cursor.close();
        }
        return userList;
    }

    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity = null;
        BasicDBObject query = new BasicDBObject();
        query.put("email", email);
        DBCursor cursor = userCollection.find(query);
        try {
            if (cursor.count() > 0) {
                DBObject document = cursor.next();
                userEntity = new UserEntity();
                userEntity.setId((Long) document.get("_id"));
                userEntity.setUsername((String) document.get("username"));
                userEntity.setEmail((String) document.get("email"));
                userEntity.setFirstname((String) document.get("firstname"));
                userEntity.setPasswd((String) document.get("passwd"));
                userEntity.setLastname((String) document.get("lastname"));
                userEntity.setStatus((Integer) document.get("status"));
                userEntity.setImageId((String) document.get("imageId"));
                userEntity.setRegisteredDate((Date) document.get("registeredDate"));
                userEntity.setLastLoginDate((Date) document.get("lastLoginDate"));
                userEntity.setLanguageCode((String) document.get("languageCode"));
                userEntity.setUserRole((Integer) document.get("userRole"));
                userEntity.setModeratorValue((Integer) document.get("moderator"));
                userEntity.setPersonalWebPage((String) document.get("webpage"));
                Integer value = (Integer) document.get("subscribe");
                if (value != null && value.equals(StatusTypes.ACCEPT_LICENSE)) {
                    userEntity.setAcceptSubscr(true);
                } else {
                    userEntity.setAcceptSubscr(false);
                }
            }
        } finally {
            cursor.close();
        }
        return userEntity;
    }

    public UserEntity getUserById(Long id) {
        UserEntity userEntity = null;
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        DBCursor cursor = userCollection.find(query);
        try {
            if (cursor.count() > 0) {
                DBObject document = cursor.next();
                userEntity = new UserEntity();
                userEntity.setId((Long) document.get("_id"));
                userEntity.setUsername((String) document.get("username"));
                userEntity.setEmail((String) document.get("email"));
                userEntity.setFirstname((String) document.get("firstname"));
                userEntity.setPasswd((String) document.get("passwd"));
                userEntity.setLastname((String) document.get("lastname"));
                userEntity.setStatus((Integer) document.get("status"));
                userEntity.setImageId((String) document.get("imageId"));
                userEntity.setRegisteredDate((Date) document.get("registeredDate"));
                userEntity.setLastLoginDate((Date) document.get("lastLoginDate"));
                userEntity.setLanguageCode((String) document.get("languageCode"));
                userEntity.setUserRole((Integer) document.get("userRole"));
                userEntity.setModeratorValue((Integer) document.get("moderator"));
                Integer value = (Integer) document.get("subscribe");
                userEntity.setPersonalWebPage((String) document.get("webpage"));
                if (value != null && value.equals(StatusTypes.ACCEPT_SUBSCRB)) {
                    userEntity.setAcceptSubscr(true);
                } else {
                    userEntity.setAcceptSubscr(false);
                }
            }
        } finally {
            cursor.close();
        }
        return userEntity;
    }

    public void updateUserProfile(UserEntity userEntity) {
        BasicDBObject document = new BasicDBObject();

        if (userEntity.getPasswd() != null) {
            document.append("$set", new BasicDBObject()
                    .append("email", userEntity.getEmail())
                    .append("userRole", userEntity.getUserRole())
                    .append("username", userEntity.getUsername())
                    .append("firstname", userEntity.getFirstname())
                    .append("lastname", userEntity.getLastname())
                    .append("passwd", hashPassword(userEntity.getPasswd() != null ? userEntity.getPasswd() : null))
                    .append("webpage", userEntity.getPersonalWebPage())
                    .append("imageId", userEntity.getImageId())
                    .append("subscribe", userEntity.isAcceptSubscr() ? StatusTypes.ACCEPT_SUBSCRB : StatusTypes.DISABLE_SUBSCRB)
                    .append("status", userEntity.getStatus()));
        } else {
            document.append("$set", new BasicDBObject()
                    .append("email", userEntity.getEmail())
                    .append("userRole", userEntity.getUserRole())
                    .append("username", userEntity.getUsername())
                    .append("firstname", userEntity.getFirstname())
                    .append("lastname", userEntity.getLastname())
                    .append("imageId", userEntity.getImageId())
                    .append("webpage", userEntity.getPersonalWebPage())
                    .append("subscribe", userEntity.isAcceptSubscr() ? StatusTypes.ACCEPT_SUBSCRB : StatusTypes.DISABLE_SUBSCRB)
                    .append("status", userEntity.getStatus()));
        }

        userCollection.update(new BasicDBObject().append("_id", userEntity.getId()), document);
    }

    public Long getUserIdByEmail(String email) {
        Long id = 0L;
        BasicDBObject query = new BasicDBObject();
        query.put("email", email);
        DBCursor cursor = userCollection.find(query);
        try {
            if (cursor.count() > 0) {
                DBObject document = cursor.next();
                id = (Long) document.get("_id");
            }
        } finally {
            cursor.close();
        }
        return id;
    }

    public boolean checkEmail(String email) {
        boolean retValue = false;
        BasicDBObject query = new BasicDBObject();
        query.put("email", email);
        DBCursor cursor = userCollection.find(query);
        try {
            if (cursor == null) {
                return false;
            }
            if (cursor != null && cursor.count() > 0) {
                retValue = true;
            } else {
                return false;
            }
        } finally {
            cursor.close();
        }
        return retValue;
    }

    public boolean checkUsername(String username) {
        boolean retValue = false;
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBCursor cursor = userCollection.find(query);

        try {
            if (cursor != null && cursor.count() > 0) {
                retValue = true;
            }
        } finally {
            cursor.close();
        }
        return retValue;
    }

    protected String hashPassword(String password) {
        if (password == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] bs;
            bs = messageDigest.digest(password.getBytes());
            for (int i = 0; i < bs.length; i++) {
                String hexVal = Integer.toHexString(0xFF & bs[i]);
                if (hexVal.length() == 1) {
                    sb.append("0");
                }
                sb.append(hexVal);
            }
        } catch (NoSuchAlgorithmException ex) {
            //log.debug(ex);
        }
        return sb.toString();
    }

    protected static String getNextId(DB db, String seq_name) {
        String sequence_collection = "seq"; // the name of the sequence collection
        String sequence_field = "seq"; // the name of the field which holds the sequence


        DBCollection seq = db.getCollection(sequence_collection); // get the collection (this will create it if needed)               

        if (seq == null) {
            seq = db.createCollection(sequence_collection, null);
        }

        // this object represents your "query", its analogous to a WHERE clause in SQL
        DBObject query = new BasicDBObject();
        query.put("_id", seq_name); // where _id = the input sequence name

        // this object represents the "update" or the SET blah=blah in SQL
        DBObject change = new BasicDBObject(sequence_field, 1);
        DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment

        // Atomically updates the sequence field and returns the value for you
        DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
        return res.get(sequence_field).toString();
    }
}
