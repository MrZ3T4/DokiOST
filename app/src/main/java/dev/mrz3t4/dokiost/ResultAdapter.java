package dev.mrz3t4.dokiost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        /*Picasso.get().load(results.get(position).getImg()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                assert holder.img_iv != null;
                holder.img_iv.setImageBitmap(bitmap);
                Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                if (textSwatch == null) {
                                    System.out.println("Null Swatch :(");
                                    return;
                                }

                                ObjectAnimator card = ObjectAnimator.ofInt(holder.cardView, "cardBackgroundColor", holder.cardView.getSolidColor(), textSwatch.getRgb());
                                ObjectAnimator text = ObjectAnimator.ofInt(holder.title_tv, "textColor", holder.title_tv.getCurrentTextColor(), textSwatch.getTitleTextColor());
                                ObjectAnimator cardStroke = ObjectAnimator.ofInt(holder.cardView, "strokeColor", holder.cardView.getSolidColor(), textSwatch.getRgb());

                                text.setDuration(300);
                                text.setEvaluator(new ArgbEvaluator());
                                AnimatorSet set0 = new AnimatorSet();
                                set0.play(text);
                                set0.start();

                                cardStroke.setDuration(300);
                                cardStroke.setEvaluator(new ArgbEvaluator());
                                AnimatorSet set1 = new AnimatorSet();
                                set1.play(cardStroke);
                                set1.start();


                                card.setDuration(300);
                                card.setEvaluator(new ArgbEvaluator());
                                AnimatorSet set = new AnimatorSet();
                                set.play(card);
                                set.start();
                                holder.description_tv.setTextColor(textSwatch.getBodyTextColor());
                            }
                        });


            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

         */

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
