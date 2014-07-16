package com.backend.mongo.facades;

import com.backend.mongo.interfaces.UserManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Armen.Arzumanyan
 */
@Service("userManagerFacade")
public class UserManagerFacade implements UserManager {  
    
   @Override
   public String getUsername(){
     return "this is your username";
   }
    protected String hash(String password) {
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
}
