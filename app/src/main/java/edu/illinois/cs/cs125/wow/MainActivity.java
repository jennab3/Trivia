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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "WOW:Main";

    public static RequestQueue requestQ;

    public Button answerOne, answerTwo, answerThree, answerFour, nextQuestion ;

    public TextView question;

    public boolean correctOne, correctTwo, correctThree, correctFour = false;

    public int randomNum;

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


        answerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start API Call called");
                answerOne.setText("blah");

            }
        });

        answerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"answer2");
            }
        });

        answerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"answer3");
            }
        });

        answerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"answer4");
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"nextQuestion");
                startAPICall();
            }
        });

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

    public void reset() {
        correctOne = false;
        correctTwo = false;
        correctThree = false;
        correctFour = false;
    }

    public static String getQuestion(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String question = result.getAsJsonArray("results").get(0).getAsJsonObject().get("question").getAsString();

            return question;
        } catch (Exception e) {
            Log.d(TAG,"Catch");
            return "";
        }
    }

    public static String getCorrect(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String correct = result.getAsJsonArray("results").get(0).getAsJsonObject().get("correct_answer").getAsString();

            return correct;
        } catch (Exception e) {
            return "caught";
        }
    }

    public static String getOneIncorrect(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String incorrectOne = result.getAsJsonArray("results").get(0).getAsJsonObject().get("incorrect_answers").getAsJsonArray().get(0).getAsString();
            Log.d(TAG, incorrectOne);

            return incorrectOne;
        } catch (Exception e) {
            Log.d(TAG, "Catch");
            return "caught";
        }
    }

    public static String getTwoIncorrect(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String incorrectOne = result.getAsJsonArray("results").get(0).getAsJsonObject().get("incorrect_answers").getAsJsonArray().get(1).getAsString();
            Log.d(TAG, incorrectOne);

            return incorrectOne;
        } catch (Exception e) {
            Log.d(TAG, "Catch");
            return "caught";
        }
    }

    public static String getThreeIncorrect(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String incorrectOne = result.getAsJsonArray("results").get(0).getAsJsonObject().get("incorrect_answers").getAsJsonArray().get(2).getAsString();
            Log.d(TAG, incorrectOne);

            return incorrectOne;
        } catch (Exception e) {
            Log.d(TAG, "Catch");
            return "caught";
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

                            // Sets the question
                            String help = getQuestion(response.toString());
                            question.setText(help);

                            // Sets the correct answer
                            whichQuestionRight();
                            String correct = getCorrect(response.toString());
                            if (correctOne) {
                                answerOne.setText(correct);
                            } else if (correctTwo) {
                                answerTwo.setText(correct);
                            } else if (correctThree) {
                                answerThree.setText(correct);
                            } else if (correctFour) {
                                answerFour.setText(correct);
                            }

                            // Sets incorrect answers
                            String incorrectOne = getOneIncorrect(response.toString());
                            String incorrectTwo = getTwoIncorrect(response.toString());
                            String incorrectThree = getThreeIncorrect(response.toString());

                            if (correctOne) {
                                answerTwo.setText(incorrectOne);
                                answerThree.setText(incorrectTwo);
                                answerFour.setText(incorrectThree);
                            } else if (correctTwo) {
                                answerOne.setText(incorrectOne);
                                answerThree.setText(incorrectTwo);
                                answerFour.setText(incorrectThree);
                            } else if (correctThree) {
                                answerOne.setText(incorrectOne);
                                answerTwo.setText(incorrectTwo);
                                answerFour.setText(incorrectThree);
                            } else if (correctFour) {
                                answerOne.setText(incorrectOne);
                                answerTwo.setText(incorrectTwo);
                                answerThree.setText(incorrectThree);
                            }




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
