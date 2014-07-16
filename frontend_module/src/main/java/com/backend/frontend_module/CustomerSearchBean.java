package com.backend.frontend_module;

import backend.interfaces.UserControllerManager;
import com.media.factories.NewsActionFactory;
import com.media.interfaces.NewsService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "CustomerSearchBean")
@RequestScoped
public class CustomerSearchBean implements Serializable {     
 
   
    private NewsService newsAction = null;
    
    @ManagedProperty("#{userController}")
    private UserControllerManager userController;

    public CustomerSearchBean() {
        newsAction = NewsActionFactory.getInstance();
        
    }

   
   public Integer getUserListCount() {
        return newsAction.getNewsList().size();
    }

    public Integer getMemberCount() {
        return userController.getMembersCount();       
    }

    public void setUserController(UserControllerManager userController) {
        this.userController = userController;
    }

   
   
}
