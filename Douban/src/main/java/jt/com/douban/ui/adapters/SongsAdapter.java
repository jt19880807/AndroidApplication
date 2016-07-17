package jt.com.douban.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import jt.com.douban.R;
import jt.com.douban.ui.model.PlayBean;

/**
 * Created by JiangTao on 2016/7/16.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    private List<PlayBean.SongBean> mDataList;
    private Context mContext;
    public  SongsAdapter(Context context, List<PlayBean.SongBean> dataList) {
        mContext=context;
        mDataList=dataList;
    }
    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SongsViewHolder viewHolder=new SongsViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.songitem,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        PlayBean.SongBean songBean=mDataList.get(position);
        if (null==songBean) {
            return;
        }
        Glide.with(mContext)
                .load("https://img1.doubanio.com/lpic/s3012979.jpg")
                .into(holder.img_song);
        holder.tv_title.setText(songBean.getTitle());
        holder.tv_singer.setText(songBean.getSingers().get(0).getName());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder {
        ImageView img_song;
        TextView tv_title,tv_singer;
        public SongsViewHolder(View itemView) {
            super(itemView);
            img_song=(ImageView)itemView.findViewById(R.id.img_song);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_singer=(TextView)itemView.findViewById(R.id.tv_singer);
        }
    }
}
