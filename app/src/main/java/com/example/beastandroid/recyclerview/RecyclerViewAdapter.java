package com.example.beastandroid.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.beastandroid.R;
import com.example.beastandroid.model.User;
import com.example.beastandroid.model.Video;

import java.util.List;

import javax.inject.Inject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<User> userList;
    private LayoutInflater layoutInflater;
    private Context context;
    private static final int FOOTER_VIEW = 1;
    private ClickListener mClickListener;
    private static int pageNo=1;

    @Inject
    RequestManager requestManager;

    public RecyclerViewAdapter(Context context,List<User> userList,ClickListener mClickListener){

        this.context = context;
        this.userList = userList;
        this.mClickListener = mClickListener;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == FOOTER_VIEW){
            view = layoutInflater.inflate(R.layout.fotter,parent,false);
            FotterViewHolder fotterViewHolder = new FotterViewHolder(view,mClickListener);
            return fotterViewHolder;
        }

        view = layoutInflater.inflate(R.layout.item_view,parent,false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerViewHolder){
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder)holder;
            User user = userList.get(position);
            Video video = user.getVideo();
            recyclerViewHolder.populate(video);
        }
        else if (holder instanceof FotterViewHolder){
            FotterViewHolder fotterViewHolder = (FotterViewHolder)holder;
        }

    }

  /**  @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view = layoutInflater.inflate(R.layout.item_view,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        User user = userList.get(position);
        Video video = user.getVideo();
        holder.populate(video);
    }**/

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        }

        if (userList.size() == 0) {
            return 1;
        }

        // Add extra view to show the footer view
        return userList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {

        if (position == userList.size()){

            return FOOTER_VIEW;
        }


        return super.getItemViewType(position);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView tvTitle,tvViews,tvDuration;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvViews = itemView.findViewById(R.id.tvViews);
            tvDuration = itemView.findViewById(R.id.tvDuration);

            itemView.setOnClickListener(this);

        }

        public void populate(Video video){
            Glide.with(context).load(video.getDefaultThub()).into(imageView);
            tvTitle.setText(video.getTitle());
            tvViews.setText(video.getViews());
            tvDuration.setText(video.getDuration());
        }

        @Override
        public void onClick(View view) {
            mClickListener.itemClickListener(getAdapterPosition());
        }
    }


    public class FotterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageButton btnNext, btnPrevious;
        private TextView tvPageNo;
        private ClickListener clickListener;


        public FotterViewHolder(@NonNull View itemView, ClickListener mListener) {
            super(itemView);

            btnNext = itemView.findViewById(R.id.btnNext);
            btnPrevious = itemView.findViewById(R.id.btnImgBack);
            tvPageNo = itemView.findViewById(R.id.tvPageNumber);
            tvPageNo.setText(Integer.toString(pageNo));

            btnNext.setOnClickListener(this);
            btnPrevious.setOnClickListener(this);
            clickListener = mListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.btnNext:
                    pageNo = pageNo+1;
                    tvPageNo.setText(Integer.toString(pageNo));
                    clickListener.btnNextClickListener(pageNo);
                    break;

                case R.id.btnImgBack:
                    if (pageNo!=1){
                        pageNo = pageNo-1;
                        tvPageNo.setText(Integer.toString(pageNo));
                        clickListener.btnBackClickListener(pageNo);
                    }
                    break;

            }



        }
    }

    public interface ClickListener{
        void btnNextClickListener(int pageNo);
        void btnBackClickListener(int pageNo);
        void itemClickListener(int position);
    }
}
