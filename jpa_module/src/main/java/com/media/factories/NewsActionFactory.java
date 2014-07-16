package com.media.factories;

import com.media.controllers.NewsController;


/**
 *
 * @author Armen Arzumanyan
 */
public class NewsActionFactory {

  private static NewsController instance = new NewsController();

  public static NewsController getInstance() {      
    return instance;
  }
}
