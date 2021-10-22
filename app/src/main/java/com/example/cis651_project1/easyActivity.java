package com.example.cis651_project1;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class easyActivity extends AppCompatActivity {

    public int lastimageid = 100;
    public int score = 0; //tracks the score
    public int lastimagebox_id=100; //tracks the id of the previously clicked box
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.easy_activity);

        //final int[] images = {
        //        R.drawable.One, R.drawable.Two, R.drawable.Three,
        //        R.drawable.Four, R.drawable.Five, R.drawable.Six,
        //        R.drawable.Seven, R.drawable.Nine,
        //        R.drawable.Ten, R.drawable.Eleven, R.drawable.Twelve};

        final int[] images = {
                        R.drawable.one, R.drawable.two, R.drawable.three,
                        R.drawable.four};
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

            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random rng = new Random();
                    List<Integer> generated = new ArrayList<Integer>();
                        while(true)
                        {
                            Integer next = rng.nextInt(3) ;
                            //If there are no previously open imageboxes, proceed
                            if (lastimagebox_id==100)
                            {
                                //generated.add(next);
                                imageView.setImageResource(images[next]);
                                lastimageid = next;
                                lastimagebox_id = imageView.getId(); //get the id of the imageview
                                Log.d("firs:lastimageid",String.valueOf(lastimageid));
                                Log.d("first:Current Image",String.valueOf(next));
                                Log.d("first:lastimagebox_id",String.valueOf(lastimagebox_id));
                                break;
                            }
                            //There is already an image open
                            else{
                                //Open the second image
                                imageView.setImageResource(images[next]);

                                if(lastimageid == next)
                                {
                                    //Matching numbers opened
                                    Log.d("match:lastimageid",String.valueOf(lastimageid));
                                    Log.d("match:Current Image",String.valueOf(next));
                                    Log.d("match:lastimagebox_id",String.valueOf(lastimagebox_id));

                                    Toast t = Toast.makeText(getApplicationContext(),"Match!!!",Toast.LENGTH_LONG);
                                    t.show();

                                    //Increment the score
                                    score++;
                                    TextView tx = (TextView) findViewById(R.id.easyScoretxtView);
                                    tx.setText("Your Score: " + score);

                                    //Reset the image IDs since this is a match
                                    lastimageid = 100;

                                    //Replace matching images with check mark
                                    imageView.setImageResource(R.drawable.cmark);
                                    imageView.setEnabled(false); //Disable further clicks

                                    ImageView previmage = (ImageView) findViewById(lastimagebox_id);
                                    previmage.setImageResource(R.drawable.cmark);
                                    previmage.setEnabled(false);

                                    lastimagebox_id=100;

                                }
                                else {
                                    //Not a match
                                    Toast t = Toast.makeText(getApplicationContext(),"No Match!!!",Toast.LENGTH_LONG);
                                    t.show();
                                    Log.d("fail:lastimageid",String.valueOf(lastimageid));
                                    Log.d("fail:Current Image",String.valueOf(next));
                                    Log.d("fail:lastimagebox_id",String.valueOf(lastimagebox_id));

                                    lastimageid = 100; //Reset the image IDs to check for next pair

                                    //Replace matching images with X mark
                                    imageView.setImageResource(R.drawable.xmark);
                                    imageView.setEnabled(false);

                                    ImageView previmage = (ImageView) findViewById(lastimagebox_id);
                                    previmage.setImageResource(R.drawable.xmark);
                                    previmage.setEnabled(false);
                                    lastimagebox_id=100;
                                }
                                break;
                            }
                        }
                }
                }
            );

        }
    }

}
