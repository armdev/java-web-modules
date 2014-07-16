package backend.facades;

import backend.connections.MongoFactory;
import backend.entities.FileEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import frontend.utils.Util;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author Armen Arzumanyan
 */
@ApplicationScoped
@ManagedBean(name = "FileManagerFacade")
public class FileManagerFacade implements Serializable {

    private DB database = null;
    private DBCollection fileCollection;
    private GridFS gfsPhoto;    
    private MongoFactory mongoFactory;

    public FileManagerFacade() {
        Mongo mongo;
        try {
           mongoFactory = new MongoFactory();
           int connectionsPerHost = 20;
           mongo = mongoFactory.createMongoInstance(connectionsPerHost);
           database = mongo.getDB("tmb16396");                     
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String addFile(FileEntity file) {

        final String fileName = System.currentTimeMillis() + file.getTitle();
        try {
             gfsPhoto = new GridFS(database, "imagetable");

            InputStream in = new ByteArrayInputStream(file.getContent());
            BufferedImage originalImage = ImageIO.read(in);

            BufferedImage thumbnail = Thumbnails.of(originalImage)
                    .size(400, 400)
                    .asBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            GridFSInputFile gfsFile = gfsPhoto.createFile(imageInByte);
            Long fileId = (Util.longValue(getNextId(database, "imagesGenSeq")));
            gfsFile.setId(fileId);
            gfsFile.setContentType(file.getMimetype());
            gfsFile.setFilename(fileName);
            gfsFile.save();          
          
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public GridFSDBFile getFile(String fileName) {
        gfsPhoto = new GridFS(database, "imagetable");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(fileName);
        return imageForOutput;
    }

//    public FileEntity getFile(Long id) {
//        FileEntity file = new FileEntity();
//        BasicDBObject query = new BasicDBObject();
//        query.put("_id", id);
//        DBCursor cursor = fileCollection.find(query);
//        FileEntity fileEntity = null;
//        try {
//            while (cursor.hasNext()) {
//                DBObject document = cursor.next();
//                fileEntity = new FileEntity();
//                String path = (String) document.get("filepath");
//                byte[] content = fs.readFile(path);
//                fileEntity.setId(((Long) document.get("_id")));
//                fileEntity.setUserId((Long) document.get("userId"));
//                fileEntity.setTitle((String) document.get("title"));
//                fileEntity.setMimetype((String) document.get("mimetype"));
//                fileEntity.setFilesize((Long) document.get("filesize"));
//                fileEntity.setFilepath((String) document.get("filepath"));
//                fileEntity.setStatus((Integer) document.get("status"));
//                fileEntity.setType((Integer) document.get("type"));
//                fileEntity.setContent(content);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            cursor.close();
//        }
//        return fileEntity;
//    }
    public void removeFile(Long fileId) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", fileId);
        fileCollection.remove(document);
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
