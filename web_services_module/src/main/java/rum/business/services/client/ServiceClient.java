/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rum.business.services.client;

//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.map.DeserializationConfig;
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author armen
 */
public class ServiceClient {

    private RestTemplate rest;

//    public List<GroupsListBean> sendSearchRequestToAdapter(String searchKey) {
//        List<GroupsListBean> groupsBeanList = null;
//        try {
//
//            StringBuilder url = new StringBuilder();
//            url.append(SEARCH_GROUPS);
//
//            url.append(searchKey);
//
//           // Logger.getLogger(ServiceClient.class.getName()).log(Level.FINE, "Start search groups");
//
//            ResponseEntity<List> response = rest.getForEntity(url.toString(), List.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                //Logger.getLogger(ServiceClient.class.getName()).log(Level.FINE, "Search groups request response is OK");
//                groupsBeanList = response.getBody();
//            } else {
//                //Logger.getLogger(ServiceClient.class.getName()).log(Level.WARNING, "Search groups  request respons is FAILS");
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(ServiceClient.class.getName()).log(Level.SEVERE, "Unable to send Search groups  request", ex);
//        }
//
//        return groupsBeanList;
//    }

 
    public void setRest(RestTemplate _rest) {
        rest = _rest;
    }

    private RestTemplate getTemplate() {
        RestTemplate template = null;
        if (rest == null) {
            ApplicationContext ctx = new FileSystemXmlApplicationContext(
                    "web/WEB-INF/servlet-context.xml", "web/WEB-INF/applicationContext.xml");
            template = (RestTemplate) ctx.getBean("restTemplate");
        }
        return template;
    }
}
