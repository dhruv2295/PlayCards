package com.example.dhruv.playcards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener{
//    ,GestureDetector.OnGestureListener
//        ,GestureDetector.OnDoubleTapListener{
    private boolean mShowingBack = false;
    private Handler mHandler = new Handler();
Integer[] order;
    Context c;
    float dX, dY,centreX,centreY,screencx,screency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
////            getFragmentManager()
////                    .beginTransaction()
////                    .add(R.id.container, new CardBackFragment())
////                    .commit();
//
//        } else {
//            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
//        }

        order= shuffledeck.shuffle(images);
        for(int i=0;i<52;i++)
            newCard(i);

//        newCard(0);
        // Monitor back stack changes to ensure the action bar shows the appropriate.
        // button (either "photo" or "info").
//        getFragmentManager().addOnBackStackChangedListener(this);

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

   private Integer[] images = {R.drawable.ace_of_clubs,R.drawable.clubs_2,R.drawable.clubs_3,R.drawable.clubs_4,R.drawable.clubs_5,
           R.drawable.clubs_6,R.drawable.clubs_7,R.drawable.clubs_8,R.drawable.clubs_9,R.drawable.clubs_10,R.drawable.jack_of_clubs2,
           R.drawable.queen_of_clubs2,R.drawable.king_of_clubs2,

                               R.drawable.ace_of_diamonds,R.drawable.diamond_2,R.drawable.diamond_3,R.drawable.diamond_4,R.drawable.diamond_5,
           R.drawable.diamond_6,R.drawable.diamond_7,R.drawable.diamond_8,R.drawable.diamond_9,R.drawable.diamond_10,R.drawable.jack_of_diamonds2,
           R.drawable.queen_of_diamonds2,R.drawable.king_of_diamonds2,

                               R.drawable.ace_of_hearts,R.drawable.hearts_2,R.drawable.hearts_3,R.drawable.hearts_4,R.drawable.hearts_5,
           R.drawable.hearts_6,R.drawable.hearts_7,R.drawable.hearts_8,R.drawable.hearts_9,R.drawable.hearts_10,R.drawable.jack_of_hearts2
           ,R.drawable.queen_of_hearts2,R.drawable.king_of_hearts2,
                               R.drawable.ace_of_spades,R.drawable.spades_2,R.drawable.spades_3,R.drawable.spades_4,R.drawable.spades_5
           ,R.drawable.spades_6,R.drawable.spades_7,R.drawable.spades_8,R.drawable.spades_9,R.drawable.spades_10,R.drawable.jack_of_spades2
           ,R.drawable.queen_of_spades2,R.drawable.king_of_spades2
   };


    public void newCard(int i)
    {
        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
      final FrameLayout view ;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (FrameLayout) inflater.inflate(R.layout.cardview,layout,false);

        final  ImageView imageView;
        imageView = (ImageView) view.findViewById(R.id.front);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(order[i]);

      final  ImageView imageView2 = (ImageView) view.findViewById(R.id.back);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setImageResource(R.drawable.card_back);

        layout.addView(view);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()&MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_UP:

                        long time= System.currentTimeMillis();

                        //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
                        if (startMillis==0 || (time-startMillis>200) ) {
                            startMillis=time;
                            count=1;
//                               Log.d("Single","Single"+count);
                        }
                        //it is not the first, and it has been  less than 3 seconds since the first
                        else{ //  time-startMillis< 3000
                            count++;
//                             Log.d("Single","Double"+count);
                        }

                        if (count==2) {
//                            Log.d("Double","Double"+count);
                            if(imageView.getVisibility()==View.VISIBLE) {
                                imageView.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                imageView.setVisibility(View.VISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_DOWN:

                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();


                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
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


    }

    static int count=0;
    static long startMillis=0;


//    public void newCard(int i)
//    {
////        new DownloadImageTask().execute(order[i]);
//
//        final float density = this.getResources().getDisplayMetrics().density;
//        int px = (int) (50 * density);
//
//        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
//        FrameLayout.LayoutParams lp =  new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//
//        FrameLayout.LayoutParams lp2 =  new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        lp.setMargins(px,px,px,px);
//      //  lp2.setMargins(50,50,50,50);
//        final FrameLayout nestlayout;
//        nestlayout = new FrameLayout(this);
//        nestlayout.setBackgroundColor(0xe0e0e0);
//        nestlayout.setLayoutParams(lp);
////        nestlayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//       final ImageView imageView = new ImageView(this);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView.setBackgroundColor(0xe0e0e0);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(order[i]);
////        Picasso.with(c).load(order[i]).fit().into(imageView);
//
//       final ImageView imageView2 = new ImageView(this);
//        imageView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView2.setBackgroundColor(0xe0e0e0);
//        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView2.setImageResource(R.drawable.card_back);
//        imageView2.setVisibility(View.INVISIBLE);
//
//        nestlayout.addView(imageView);
//        nestlayout.addView(imageView2);
//
//        layout.addView(nestlayout);
//
////        imageView.setOnTouchListener(new OnDoubleTapListener(this) {
////
////            @Override
////            public void onDoubleTap(MotionEvent e) {
////
////                if(imageView.getVisibility()==View.VISIBLE) {
////                    imageView.setVisibility(View.INVISIBLE);
////                    imageView2.setVisibility(View.VISIBLE);
////                }
////                else
////                {
////                    imageView.setVisibility(View.VISIBLE);
////                    imageView2.setVisibility(View.INVISIBLE);
////                }
////            }
////
////        });
//
////        nestlayout.setOnTouchListener(new OnDoubleTapListener(this) {
////
////            @Override
////            public void onDoubleTap(MotionEvent event) {
////                Log.d("Event",""+event.getAction());
////
////                if(imageView.getVisibility()==View.VISIBLE) {
////                    imageView.setVisibility(View.INVISIBLE);
////                    imageView2.setVisibility(View.VISIBLE);
////                }
////                else
////                {
////                    imageView.setVisibility(View.VISIBLE);
////                    imageView2.setVisibility(View.INVISIBLE);
////                }
////            }
//////                @Override
//////            public void onLongPress(MotionEvent event)
//////            {
//////                Log.d("Down","Down");
//////                switch (event.getAction()) {
//////
//////                    case MotionEvent.ACTION_DOWN:
//////
//////                        dX = nestlayout.getX() - event.getRawX();
//////                        dY = nestlayout.getY() - event.getRawY();
//////
//////                        Log.d("Event","Start");
//////
//////                        break;
//////
//////                    case MotionEvent.ACTION_MOVE:
//////                        nestlayout.animate()
//////                                .x(event.getRawX() + dX)
//////                                .y(event.getRawY() + dY)
//////                                .setDuration(0)
//////                                .start();
//////
//////                        break;
//////
//////
//////                    default:
////
//////                        return false;
//////                }
//////            }
////        });
//
//
//        nestlayout.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()&MotionEvent.ACTION_MASK) {
//
//                    case MotionEvent.ACTION_UP:
//
//                        long time= System.currentTimeMillis();
//
//                        //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
//                        if (startMillis==0 || (time-startMillis>200) ) {
//                            startMillis=time;
//                            count=1;
//                         //   Log.d("Single","Single"+count);
//                        }
//                        //it is not the first, and it has been  less than 3 seconds since the first
//                        else{ //  time-startMillis< 3000
//                            count++;
//                           // Log.d("Single","Double"+count);
//                        }
//
//                        if (count==2) {
//                            //Log.d("Double","Double"+count);
//                            if(imageView.getVisibility()==View.VISIBLE) {
//
//                                imageView.setVisibility(View.INVISIBLE);
//                                imageView2.setVisibility(View.VISIBLE);
//                            }
//                            else
//                            {
//                                imageView.setVisibility(View.VISIBLE);
//                                imageView2.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                        break;
//
//
//
//
//                    case MotionEvent.ACTION_DOWN:
//
//                        dX = nestlayout.getX() - event.getRawX();
//                        dY = nestlayout.getY() - event.getRawY();
//
//
//                        break;
//
//
//
//                    case MotionEvent.ACTION_MOVE:
//                        nestlayout.animate()
//                                .x(event.getRawX() + dX)
//                                .y(event.getRawY() + dY)
//                                .setDuration(0)
//                                .start();
//                        break;
//
//
//                    default:
//
//                        return false;
//                }
//
//                return true;
//            }
//        });
//
//
//    }


}
