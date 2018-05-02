package edu.illinois.cs.cs125.wow;

import android.graphics.Color;
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

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




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

        answerOne.setBackgroundColor(Color.rgb(220,209,231));
        answerTwo.setBackgroundColor(Color.rgb(220,209,231));
        answerThree.setBackgroundColor(Color.rgb(220,209,231));
        answerFour.setBackgroundColor(Color.rgb(220,209,231));
        nextQuestion.setBackgroundColor(Color.rgb(194,213,225));
        question.setTextColor(Color.rgb(70,70,70));


        answerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctOne) {
                    answerOne.setBackgroundColor(Color.rgb(168,232,172));
                    answerOne.setTextColor(Color.BLACK);
                    answerOne.setText("Hurray! That's correct!");
                } else {
                    answerOne.setBackgroundColor(Color.rgb(232,157,169));
                    answerOne.setTextColor(Color.BLACK);
                    answerOne.setText(randomIncorrect());
                }

            }
        });

        answerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctTwo) {
                    answerTwo.setBackgroundColor(Color.rgb(168,232,172));
                    answerTwo.setTextColor(Color.BLACK);
                    answerTwo.setText("Hurray! That's correct!");
                } else {
                    answerTwo.setBackgroundColor(Color.rgb(232,157,169));
                    answerTwo.setTextColor(Color.BLACK);
                    answerTwo.setText(randomIncorrect());
                }
            }
        });

        answerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctThree) {
                    answerThree.setBackgroundColor(Color.rgb(168,232,172));
                    answerThree.setTextColor(Color.BLACK);
                    answerThree.setText("Hurray! That's correct!");
                } else {
                    answerThree.setBackgroundColor(Color.rgb(232,157,169));
                    answerThree.setTextColor(Color.BLACK);
                    answerThree.setText(randomIncorrect());
                }
            }
        });

        answerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctFour) {
                    answerFour.setBackgroundColor(Color.rgb(168,232,172));
                    answerFour.setTextColor(Color.BLACK);
                    answerFour.setText("Hurray! That's correct!");
                } else {
                    answerFour.setBackgroundColor(Color.rgb(232,157,169));
                    answerFour.setTextColor(Color.BLACK);
                    answerFour.setText(randomIncorrect());
                }
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"nextQuestion");
                reset();
                answerOne.setBackgroundColor(Color.rgb(220,209,231));
                answerTwo.setBackgroundColor(Color.rgb(220,209,231));
                answerThree.setBackgroundColor(Color.rgb(220,209,231));
                answerFour.setBackgroundColor(Color.rgb(220,209,231));
                answerOne.setTextColor(Color.BLACK);
                answerTwo.setTextColor(Color.BLACK);
                answerThree.setTextColor(Color.BLACK);
                answerFour.setTextColor(Color.BLACK);
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

    public String[] incorrect = {"Aww, too bad", "Oops! Try again!", "It's okay, you can try again", "That's wrong", "That's incorrect", "Whoops!", "Oh no!", "Not again!", "Oopsy daisy"};

    public String randomIncorrect() {
        int random = (int) (Math.random() * (incorrect.length - 1));
        return incorrect[random];
    }

    public static String getQuestion(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String question = result.getAsJsonArray("results").get(0).getAsJsonObject().get("question").getAsString();

            String quote = question.replaceAll("&quot;","\"");
            String appo = quote.replaceAll("&#039;","'");
            String e = appo.replaceAll("&eacute;", "e");
            String amp = e.replaceAll("&amp;","&");

            return amp;
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

            String quote = correct.replaceAll("&quot;","\"");
            String appo = quote.replaceAll("&#039;","'");
            String e = appo.replaceAll("&eacute;", "e");
            String amp = e.replaceAll("&amp;","&");

            return amp;
        } catch (Exception e) {
            return "caught";
        }
    }

    public static String getOneIncorrect(final String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            String incorrectOne = result.getAsJsonArray("results").get(0).getAsJsonObject().get("incorrect_answers").getAsJsonArray().get(0).getAsString();

            String quote = incorrectOne.replaceAll("&quot;","\"");
            String appo = quote.replaceAll("&#039;","'");
            String e = appo.replaceAll("&eacute;", "e");
            String amp = e.replaceAll("&amp;","&");

            return amp;
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

            String quote = incorrectOne.replaceAll("&quot;","\"");
            String appo = quote.replaceAll("&#039;","'");
            String e = appo.replaceAll("&eacute;", "e");
            String amp = e.replaceAll("&amp;","&");

            return amp;
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

            String quote = incorrectOne.replaceAll("&quot;","\"");
            String appo = quote.replaceAll("&#039;","'");
            String e = appo.replaceAll("&eacute;", "e");
            String amp = e.replaceAll("&amp;","&");

            return amp;
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
