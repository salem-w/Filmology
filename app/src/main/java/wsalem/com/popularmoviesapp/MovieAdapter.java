package wsalem.com.popularmoviesapp;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Acer on 2/21/2016.
 */
public class MovieAdapter extends BaseAdapter {
        private final String LOG_TAG = MovieAdapter.class.getSimpleName();
        private final Context context;
        private final ArrayList<Movie> urls;
        private final int height;
        private final int width;

        /**
         * This method adds all movie poster paths into the urls arraylist
         *
         * @param context - global context equals paramter context
         */
        public MovieAdapter(Context context) {
            urls = new ArrayList<>();
            this.context = context;
            height = Math.round(this.context.getResources().getDimension(R.dimen.poster_height));
            width = Math.round(this.context.getResources().getDimension((R.dimen.poster_width)));
            //  Collections.addAll(urls, moviePosterPath);

        }

    public void addAll(Collection<Movie> movies){

        urls.addAll(movies);
        notifyDataSetChanged();
    }

    public int getCount() {
        return urls.size();
    }

    @Override
    public Movie getItem(int position) {
        if(position < 0 || position >= urls.size()){
            return null;
        }
        Movie movie = urls.get(position);
        return movie;
    }

    @Override
    public long getItemId(int position) {
        Movie movie = getItem(position);
        if(movie == null) return -1L;

        return movie.id;
    }

    /**
         * This method gets the image form the url and sets it into the imageview
         *
         * @param position
         * @param convertView
         * @param parent
         * @return - returns the imageView imageview
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Movie movie = getItem(position);
          if  ( movie == null) return null;


            ImageView imageView;
            /**
             * if convertView is empty, set it equal to ImageView
             */
            if (convertView == null) {
                imageView = new ImageView(context);


                imageView.setLayoutParams(new GridView.LayoutParams(width, height));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                imageView = (ImageView) convertView;
            }

            Uri coverUrl = movie.getMovieCover();
            Picasso.with(context)
                    .load(coverUrl)
                    .into(imageView);

            //    Log.e(LOG_TAG, "URL " + url);


            return imageView;
        }




}
