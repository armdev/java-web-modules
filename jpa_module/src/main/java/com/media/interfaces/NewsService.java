package com.media.interfaces;

import com.media.entities.NewsEntity;
import java.util.List;

/**
 * @author armdev
 *
 */
public interface NewsService {

    public List<NewsEntity> getNewsList();

    public void createNews(NewsEntity newsEntity);
}
