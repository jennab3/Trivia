package edu.illinois.cs.cs125.wow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "WOW:Main";

    public static RequestQueue requestQ;

    public Button answerOne, answerTwo, answerThree, answerFour, nextQuestion ;

    public TextView question;

    public boolean correctOne, correctTwo, correctThree, correctFour = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQ = Volley.newRequestQueue(this);

        startAPICall();

        answerOne = (Button) findViewById(R.id.option1);
        answerTwo = (Button) findViewById(R.id.option2);
        answerThree = (Button) findViewById(R.id.option3);
        answerFour = (Button) findViewById(R.id.option4);
        nextQuestion = (Button) findViewById(R.id.next);
        question = (TextView) findViewById(R.id.question);

//        JsonParser parser = new JsonParser();
//        JsonObject result = parser.parse(json).getAsJsonObject();
//        int weight = result.getAsJsonObject("metadata").get("width").getAsInt();

        answerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start API Call called");
                startAPICall();

            }
        });

        answerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        answerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        answerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void correctAnswer() {
        startAPICall();
    }

    public void whichQuestionRight() {
        int random = (int) (Math.random() * (4) + 1);
        if (random == 1) {
            correctOne = true;
        } else if (random == 2) {
            correctTwo = true;
        } else if (random == 3) {
            correctThree = true;
        } else if (random == 4) {
            correctFour = true;
        }

    }

    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://opentdb.com/api.php?amount=1&type=multiple",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQ.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
