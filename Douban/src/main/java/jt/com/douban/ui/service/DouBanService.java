package jt.com.douban.ui.service;


import java.util.List;

import jt.com.douban.ui.model.ChannelBean;
import jt.com.douban.ui.model.PlayBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Devin.Jiang on 2016-07-08.
 */
public interface DouBanService {
    /**
     * 获取歌曲列表
     * @param type n
     * @param channel 0
     * @param from mainsite
     * @return
     */
    @GET("j/mine/playlist")
    Observable<List<PlayBean.SongBean>> getSongs(@Query("type") String type, @Query("channel") int channel,
                                                 @Query("from") String from);

    /**
     * 获取频道列表
     * @return
     */
    @GET("j/app/radio/channels")
    Observable<List<ChannelBean>> getChannels();

}
