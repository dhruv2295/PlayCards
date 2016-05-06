package com.example.dhruv.playcards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener{
    private boolean mShowingBack = false;
    private Handler mHandler = new Handler();

    float dX, dY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardBackFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);


    }


    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        // Flip to the back.
        mShowingBack = true;
        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        getFragmentManager().beginTransaction()
                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.container, new CardFrontFragment())

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }

    @SuppressLint("ValidFragment")
    public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View inflater2 =inflater.inflate(R.layout.card_front_fragment, container, false);
            final ImageView card = (ImageView) inflater2.findViewById(R.id.imageView2);
            card.setImageResource(images[(int)(Math.random() * images.length)]);

            card.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:

                            dX = card.getX() - event.getRawX();
                            dY = card.getY() - event.getRawY();
                            break;

                        case MotionEvent.ACTION_MOVE:

                            card.animate()
                                    .x(event.getRawX() + dX)
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            });
            return inflater2;
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    @SuppressLint("ValidFragment")
    public class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View inflater1 =inflater.inflate(R.layout.card_back_fragment, container, false);
            ImageView card = (ImageView) inflater1.findViewById(R.id.imageView);
            assert card != null;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flipCard();
                }
            });
            return inflater1;
        }
    }
   private Integer[] images = {R.drawable.ace_of_clubs,
                               R.drawable.ace_of_diamonds,
                               R.drawable.ace_of_hearts,
                               R.drawable.ace_of_spades
   };
}
