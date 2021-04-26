package dev.mrz3t4.dokiost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private ArrayList<Result> results;
    private Context context;

    public ResultAdapter(ArrayList<Result> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_result, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.MyViewHolder holder, int position) {

        holder.title_tv.setText(results.get(position).getTitle());
        Picasso.get().load(results.get(position).getImg()).into(holder.img_iv);
        holder.description_tv.setText(results.get(position).getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetSoundtracks getSoundtracks = new GetSoundtracks(context);
                getSoundtracks.getDataFromQuery(results.get(position).getUrl());

            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_iv;
        private TextView title_tv, description_tv;
        private MaterialCardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title_tv = itemView.findViewById(R.id.title_textView);
            description_tv = itemView.findViewById(R.id.description_textView);
            img_iv = itemView.findViewById(R.id.img_imageView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
