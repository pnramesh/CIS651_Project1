package com.example.cis651_project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class easyActivity extends AppCompatActivity {

    public int lastimageboxid = 100;
    public int score = 0; //tracks the score
    public int lastimage_id=100; //tracks the id of the previously clicked box
    public int totalimages = 4; //to track if any images are still open and go to results when none are open
    public List<Integer> generated = new ArrayList<Integer>();

    //18 images loaded as Array
    final int[] imagesource = {R.drawable.one, R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five, R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine, R.drawable.ten,R.drawable.eleven,R.drawable.twelve,R.drawable.thirteen, R.drawable.fourteen,R.drawable.fifteen,R.drawable.sixteen,R.drawable.seventeen,R.drawable.eighteen};
    //This will be the random image order assigned to imageviews. This array size equals the number of images for the complexity
    public int[] images = new int[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.easy_activity);

        //Generate random array of numbers from the total number of columns
        //This will be used to retrieve images later
        //The order in this list corresponds to the id of the imageview controls
        //first number in this list corresponds to image for imageview with id 0
        //Only four images (2 pairs) are needed for this complexity level
        for(int i=0;i<2;i++){
            Random rng = new Random();
            int count=0;
            //random number to choose a random source. This needs to be updated to match the number of images in source
            Integer pic = rng.nextInt(18);
            while(count < 2){
                //4 is the maximum number of imageviews for this difficulty level
                //Each image from imagesource will be placed in two locations in images
                //After the end of this loop images will have four images (2 pairs) to load
                Integer p1 = rng.nextInt(4) ;
                //if the number was already used, don't use it
                if(!generated.contains(p1)){
                    generated.add(p1);
                    images[p1] = imagesource[pic];
                    Log.d("ImagePosition",p1.toString());
                    count++;
                }
            }
        }

        GridLayout gridLayout = findViewById(R.id.easyLayout);
        int column = getIntent().getIntExtra("column",2);
        gridLayout.setColumnCount(column);
        for (int i=0; i<column*column; i++){
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(param);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.qmark, null));
            imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.border, null));
            imageView.setId(i);
            gridLayout.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Disable further clicks of the same image

                    imageView.setEnabled(false);

                    //Retrieve the number in the generated array in the position referred to by the id of the image view
                    //This number will correspond to the image name in the image array. Since this number is generated
                    //randomly every time, the images will also differ
                    //Integer next = generated.get(imageView.getId());
                    Integer next = imageView.getId();
                    //Increment the score
                    score++;
                    TextView tx = (TextView) findViewById(R.id.easyScoretxtView);
                    tx.setText("Your Score: " + score);

                    //If there are no previously open imageboxes, proceed
                    if (lastimage_id==100)
                    {
                        //retrieve the image from the random list that correspond to the imageview ID
                        imageView.setImageResource(images[next]);
                        lastimageboxid = next; //tracks the id of the last opened imagebox
                        //lastimagebox_id = imageView.getId(); //get the id of the imageview
                        lastimage_id = images[next]; //get the id of the image opened
                        //Log.d("firs:lastimageid",String.valueOf(lastimageid));
                        Log.d("first:Current Image",String.valueOf(next));
                        Log.d("first:lastimagebox_id",String.valueOf(lastimage_id));
                    }
                    //There is already an image open
                    else{
                        //Open the second image
                        imageView.setImageResource(images[next]);

                        if(lastimage_id == images[next])
                        {
                            //Matching numbers opened
                            Log.d("match:lastimageid",String.valueOf(lastimageboxid));
                            Log.d("match:Current Image",String.valueOf(next));
                            Log.d("match:lastimagebox_id",String.valueOf(lastimage_id));
                            //Show Success Message
                            Toast t = Toast.makeText(getApplicationContext(),"Match!!!",Toast.LENGTH_SHORT);
                            t.show();

                            //Disable further clicks
                            imageView.setEnabled(false);
                            ImageView previmage = (ImageView) findViewById(lastimageboxid);
                            previmage.setEnabled(false);

                            //Remove from the tile by hiding after a delay of 1 second
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setVisibility(View.INVISIBLE);
                                    previmage.setVisibility(View.INVISIBLE);
                                }
                            },1000);


                            //Reset the image IDs since this is a match
                            lastimageboxid = 100;
                            lastimage_id=100;

                            //reduce the count of images open by 2 since two images are removed
                            totalimages-=2;
                            //if no images are left, open the score activity
                            if(totalimages == 0){
                                String scorestr = Integer.toString(score);
                                Intent result = new Intent(easyActivity.this,activity_score.class);
                                result.putExtra("Level","Easy");
                                result.putExtra("BestScore","4");
                                result.putExtra("TotalScore",scorestr);
                                startActivity(result);
                            }

                        }
                        else {
                            //Not a match
                            Toast t = Toast.makeText(getApplicationContext(),"No Match!!!",Toast.LENGTH_SHORT);
                            t.show();
                            Log.d("fail:lastimageid",String.valueOf(lastimageboxid));
                            Log.d("fail:Current Image",String.valueOf(next));
                            Log.d("fail:lastimagebox_id",String.valueOf(lastimage_id));

                            //Switch the tiles back to question mark after 1 second and enable them again
                            //Instantiate the previous imagebox
                            ImageView previmage = (ImageView) findViewById(lastimageboxid);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageResource(R.drawable.qmark);
                                    imageView.setEnabled(true);
                                    previmage.setImageResource(R.drawable.qmark);
                                    previmage.setEnabled(true);
                                }
                            },1000);
                            lastimageboxid = 100; //Reset the image IDs to check for next pair
                            lastimage_id=100;

                        }
                    }
                }
                }
            );

        }
    }

}
