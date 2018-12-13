package com.example.nicolas.myapplication;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adapter extends PagerAdapter {
    private List<model> models;
    private LayoutInflater layoutInflater;
    private Context context;

     adapter(List<model> models, Context context){
        this.models = models;
        this.context = context;
     }
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        final int pos = position;
        final ImageView imageView,img;
        final  TextView tit;
        TextView title, desc;
        imageView = view.findViewById(R.id.image);
        img = (ImageView) view.findViewById(R.id.image);
        tit = (TextView) view.findViewById(R.id.title) ;
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detalhe = new Intent(context, detail.class);
                Activity activity = (Activity) context;
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(img, "imageTransition");
                pairs[1] = new Pair<View, String>(tit, "titTransition");
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                detalhe.putExtra("byteArray", bs.toByteArray());
                detalhe.putExtra("tit", models.get(pos).getTitle());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                context.startActivity(detalhe, options.toBundle());

            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)object);
    }

}
