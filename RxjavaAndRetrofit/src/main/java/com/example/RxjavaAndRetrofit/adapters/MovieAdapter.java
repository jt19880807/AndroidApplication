package com.example.RxjavaAndRetrofit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.RxjavaAndRetrofit.Model.MovieModel;
import com.example.RxjavaAndRetrofit.R;

import java.util.List;

/**
 * Created by Devin.Jiang on 2016-05-31.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private Context context;
    private List<MovieModel.SubjectsBean> mDataList;

    public MovieAdapter(Context context,List<MovieModel.SubjectsBean> dataList) {
        this.context=context;
        this.mDataList=dataList;
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieHolder movieHolder=new MovieHolder(LayoutInflater.from(context).inflate(R.layout.dataitem,parent,false));
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        MovieModel.SubjectsBean subjectsBean=mDataList.get(position);
        StringBuilder stringBuilder=new StringBuilder();
        if (null==subjectsBean) {
            return;
        }
        if(subjectsBean.getCasts().size()>0) {
            stringBuilder.setLength(0);
            stringBuilder.append("主演:");
            for (MovieModel.SubjectsBean.CastsBean cast:subjectsBean.getCasts()) {
                stringBuilder.append(cast.getName()+"/");
            }
            holder.tv_casts.setText(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        }
        if(subjectsBean.getDirectors().size()>0) {
            stringBuilder.setLength(0);
            stringBuilder.append("导演:");
            for (MovieModel.SubjectsBean.DirectorsBean director:subjectsBean.getDirectors()) {
                stringBuilder.append(director.getName()+"/");
            }
            holder.tv_directors.setText(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        }
        if(subjectsBean.getGenres().size()>0) {
            stringBuilder.setLength(0);
            stringBuilder.append("类型:");
            for (String genres:subjectsBean.getGenres()) {
                stringBuilder.append(genres+"/");
            }
            holder.tv_genres.setText(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        }
        holder.tv_moviename.setText(subjectsBean.getTitle()+"("+subjectsBean.getYear()+")");
        Glide.with(context)
                .load(subjectsBean.getImages().getMedium())
                .into(holder.movieimg);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        ImageView movieimg;
        TextView tv_moviename,tv_directors,tv_casts,tv_genres;
        public MovieHolder(View itemView) {
            super(itemView);
            movieimg=(ImageView)itemView.findViewById(R.id.img_movie);
            tv_moviename= (TextView) itemView.findViewById(R.id.tv_moviename);
            tv_directors= (TextView) itemView.findViewById(R.id.tv_directors);
            tv_casts= (TextView) itemView.findViewById(R.id.tv_casts);
            tv_genres= (TextView) itemView.findViewById(R.id.tv_genres);
        }
    }
}
