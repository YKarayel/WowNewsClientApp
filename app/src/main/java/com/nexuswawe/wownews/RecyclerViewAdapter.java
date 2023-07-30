package com.nexuswawe.wownews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    private String sortingOption;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
    private DBHandler dbHandler;

    private List<WowheadNews> wowheadNewsList;

    public RecyclerViewAdapter(List<WowheadNews> wowheadNewsList) {


        this.wowheadNewsList = wowheadNewsList;
    }

    public void setSortingOption(String sortingOption) {
        this.sortingOption = sortingOption;
        notifyDataSetChanged(); // RecyclerView'i güncelle
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView titleView;
        public TextView dateView;
        public LinearLayout parentLayout;
        public WowheadNews wowheadNews;
        public ImageButton saveBtn;
        public Button linkbtn;
        public Button dateStr;
        public Button imageUrl;
        public TextView tag;

        public DBHandler dbHandler;

        


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleView = itemView.findViewById(R.id.title);
            dateView = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            saveBtn = itemView.findViewById(R.id.savebtn);
            linkbtn = itemView.findViewById(R.id.linkbtn);
            dateStr = itemView.findViewById(R.id.dateStr);
            imageUrl = itemView.findViewById(R.id.imageUrl);
            tag = itemView.findViewById(R.id.tag);
            itemView.setOnClickListener(this);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = wowheadNewsList.get(getAdapterPosition()).getPostUrl();
                    Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                    intent.putExtra("url", url);
                    v.getContext().startActivity(intent);
                }
            });
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = wowheadNewsList.get(getAdapterPosition()).getPostUrl();
                    Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                    intent.putExtra("url", url);
                    v.getContext().startActivity(intent);
                }
            });
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setSelected(!view.isSelected());
                    if (view.isSelected()) {
                        Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();


                        String titledb = titleView.getText().toString();
                        String datedb = dateStr.getText().toString();
                        String post_urldb = linkbtn.getText().toString();
                        String image_urldb = imageUrl.getText().toString();


                        DBHandler dbHandler = new DBHandler(context);
                        dbHandler.addNewPost(titledb, datedb, post_urldb, image_urldb);

                        Toast.makeText(view.getContext(), "Post has been added.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(view.getContext(), "Unsaved", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    private static Context getContext() {
        return getContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Glide ile resmi işleme
        WowheadNews wowheadNews = wowheadNewsList.get(position);
        Glide.with(holder.imageView.getContext())
                .load(wowheadNews.getImageUrl())
                //.apply(new RequestOptions().override(500,500))
                .into(holder.imageView);
        holder.titleView.setText(wowheadNews.getTitle());
        holder.tag.setText(wowheadNews.getTag().toUpperCase(Locale.ENGLISH));

        //Bu kod sadece linkbtn'ın sqlite'a atılabilmesi için yaratılmıştır.
        holder.linkbtn.setText(wowheadNews.getPostUrl());
        holder.dateStr.setText(wowheadNews.getDateStr());
        holder.imageUrl.setText(wowheadNews.getImageUrl());
        //Bu kod sadece linkbtn'ın sqlite'a atılabilmesi için yaratılmıştır.


        // TAGG İÇERİĞİNE GÖRE BACKGROUNDTİNT DEĞİŞTİRME
        String tagText = wowheadNews.getTag().toUpperCase(Locale.ENGLISH);
        Log.d("TAGS", wowheadNews.getTag().toUpperCase(Locale.ENGLISH));
        if (tagText.equals("DIABLO") || tagText.equals("DIABLO IV") || tagText.equals("DIABLO II")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.diablo));
        }
        else if (tagText.equals("PTR")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.ptr));
        }
        else if (tagText.equals("LIVE")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.live));
        }
        else if (tagText.equals("OVERWATCH")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.overwatch));
        }
        else if (tagText.equals("BLIZZARD")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.blizzard));
        }
        else if (tagText.equals("WRATH")) {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.wrath));
        }
        else {
            holder.tag.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.blizzard));
        }
        holder.tag.setText(tagText);


        // Parse news date in UTC timezone
        TimeZone wowheadTimeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd 'at' h:mm a", Locale.ENGLISH);
        inputDateFormat.setTimeZone(wowheadTimeZone);
        Date newsDate;

        try {
            newsDate = inputDateFormat.parse(wowheadNews.getDateStr());
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Convert news date to milliseconds
        long newsMillis = newsDate.getTime();

        // Add 3 hours and 40 minutes to account for time difference
        newsMillis += TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(0);

        // Get current time in UTC
        Calendar currentCal = Calendar.getInstance(wowheadTimeZone);
        long currentMillis = currentCal.getTimeInMillis();

        // Calculate time difference
        long diffMillis = currentMillis - newsMillis;
        int diffMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffMillis);
        int diffHours = (int) TimeUnit.MILLISECONDS.toHours(diffMillis);

        String relativeDate;
        if (diffHours > 0) {
            relativeDate = diffHours + " hours ago";
        } else {
            relativeDate = diffMinutes + " minutes ago";
        }

        holder.dateView.setText(relativeDate);
    }

    @Override
    public int getItemCount() {
        return wowheadNewsList.size();
    }
}
