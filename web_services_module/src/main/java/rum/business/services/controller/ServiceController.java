/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rum.business.services.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import rum.business.services.client.ServiceClient;

/**
 *
 * @author armen
 */
@Controller
@RequestMapping(value = "/request",
consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {

    @Autowired
    @Qualifier("serviceClient")
    private ServiceClient client;
    
   
    
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate rest;

//    @RequestMapping(value = "/groups/search/{searchKey}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody
//    List<GroupsListBean> SearchGroups(@PathVariable String searchKey) {
//        client.setRest(rest);
//        //Logger.getLogger(ServiceController.class.getName()).log(Level.INFO, "Inside the GetAllGroups - call to send get all groups request to adapter");
//        List<GroupsListBean> groupList = client.sendSearchRequestToAdapter(searchKey);
//        return groupList;
//    }
//
//   
//    @RequestMapping(value = "/joingroup/{groupName}/{productId}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody
//    void JoinGroup(@PathVariable String groupName, @PathVariable Integer productId, @RequestBody JoinGroupBean bean) {
//        client.setRest(rest);
//        //Logger.getLogger(ServiceController.class.getName()).log(Level.INFO, "Inside the JoinGroup - call to sendJoinGroupRequestToAdapter");
//        client.sendJoinGroupRequestToAdapter(bean, groupName, productId);
//    }

    
}
