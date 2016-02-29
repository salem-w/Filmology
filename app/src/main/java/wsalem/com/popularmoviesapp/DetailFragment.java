package wsalem.com.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Acer on 2/27/2016.
 */
public class DetailFragment extends Fragment {
    //  int  height = Math.round(this.getActivity().getResources().getDimension(R.dimen.poster_height));
    //  int width = Math.round(this.getActivity().getResources().getDimension((R.dimen.poster_width)));

    private String movieString;

    public void DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Movie movie = new Movie(intent.getBundleExtra(Intent.EXTRA_TEXT));
            ((TextView) rootView.findViewById(R.id.summaryTextView))
                    .setText(movie.overview);
            ((TextView) rootView.findViewById(R.id.summaryTextView))
                    .setMovementMethod(new ScrollingMovementMethod());
            ((TextView) rootView.findViewById(R.id.titleTextView))
                    .setText(" " + movie.movie_title);
            ((TextView) rootView.findViewById(R.id.ratingTextView))
                    .setText(movie.rating + " / 10");
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new GridView.LayoutParams(185, 277));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            Picasso.with(this.getActivity()).load(movie.getMovieCover()).into(imageView);
        }
        return rootView;
    }

}
