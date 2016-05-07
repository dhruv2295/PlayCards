package com.example.dhruv.playcards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener,GestureDetector.OnDoubleTapListener{
    private boolean mShowingBack = false;
    private Handler mHandler = new Handler();
Integer[] order;
    float dX, dY,centreX,centreY,screencx,screency;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.container, new CardBackFragment())
//                    .commit();
          order= shuffledeck.shuffle(images);
            for(int i=0;i<4;i++)
            newCard(i);

        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate.
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);


    }


    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;

        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.container, new CardFrontFragment())
                .addToBackStack(null).commit();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
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
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                            .replace(R.id.container, new CardFrontFragment())
                            .addToBackStack(null).commit();
                   // flipCard();
                }
            });
            return inflater1;
        }
    }

    @SuppressLint("ValidFragment")
    public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View inflater2 =inflater.inflate(R.layout.card_front_fragment, container, false);
            final ImageView card = (ImageView) inflater2.findViewById(R.id.imageView2);
            card.setImageResource(images[(int)(Math.random() * images.length)]);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                            .replace(R.id.container, new CardBackFragment())
                            .addToBackStack(null).commit();
                    // flipCard();
                }
            });


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
                            centreX=card.getX() + card.getWidth() / 2;
                            centreY=card.getY() + card.getHeight()/ 2;
                            screencx=container.getX()+container.getWidth() /2;
                            screency=container.getY()+container.getHeight() /2;
                            if(!(centreX==screencx&&centreY==screency))


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


    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }
   private Integer[] images = {R.drawable.ace_of_clubs,
                               R.drawable.ace_of_diamonds,
                               R.drawable.ace_of_hearts,
                               R.drawable.ace_of_spades
   };
    public void newCard(int i)
    {


        float density = this.getResources().getDisplayMetrics().density;
        int px = (int) (50 * density);
        int px2 = (int) (5 * density);

        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
        FrameLayout.LayoutParams lp =  new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout.LayoutParams lp2 =  new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(70,70,70,70);
        lp2.setMargins(50,50,50,50);

        final FrameLayout nestlayout = new FrameLayout(this);
        nestlayout.setBackgroundColor(0xD3D3D3D3);
        nestlayout.setLayoutParams(lp);

        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(lp2);
        imageView.setImageResource(order[i]);

        final ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(lp2);
        imageView2.setImageResource(R.drawable.card_back);
        imageView2.setVisibility(View.INVISIBLE);

        nestlayout.addView(imageView);
        nestlayout.addView(imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(imageView.getVisibility()==View.VISIBLE)
               {
                   imageView.setVisibility(View.INVISIBLE);
                   imageView2.setVisibility(View.VISIBLE);
               }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageView2.getVisibility()==View.VISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                }
            }
        });
        nestlayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {


                    case MotionEvent.ACTION_DOWN:

                        dX = nestlayout.getX() - event.getRawX();
                        dY = nestlayout.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        nestlayout.animate()
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

        layout.addView(nestlayout);
    }

}
