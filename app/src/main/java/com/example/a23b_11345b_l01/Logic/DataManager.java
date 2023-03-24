package com.example.a23b_11345b_l01.Logic;

import com.example.a23b_11345b_l01.Models.Question;
import com.example.a23b_11345b_l01.R;

import java.util.ArrayList;

public class DataManager {
    private static int[] images = new int[]{
            R.drawable.australia,
            R.drawable.azerbaijan,
            R.drawable.bahrain,
            R.drawable.belgium,
            R.drawable.bolivia,
            R.drawable.bulgaria,
            R.drawable.canada,
            R.drawable.costa_rica,
            R.drawable.france,
            R.drawable.germany,
            R.drawable.hungary,
            R.drawable.ireland,
            R.drawable.poland,
            R.drawable.romania,
            R.drawable.ukraine,
            R.drawable.israel,
            R.drawable.switzerland,
            R.drawable.netherlands,
            R.drawable.singapore,
            R.drawable.italy,
            R.drawable.thailand,
            R.drawable.south_korea,
            R.drawable.spain,
            R.drawable.qatar,
            R.drawable.japan,
            R.drawable.united_states,
            R.drawable.russia
    };
    private static String[] correctAnswers = {
            "australia",
            "azerbaijan",
            "bahrain",
            "belgium",
            "bolivia",
            "bulgaria",
            "canada",
            "costa rica",
            "france",
            "germany",
            "hungary",
            "ireland",
            "poland",
            "romania",
            "ukraine",
            "israel",
            "switzerland",
            "netherlands",
            "singapore",
            "italy",
            "thailand",
            "south korea",
            "spain",
            "qatar",
            "japan",
            "united states",
            "russia"
    };
    private static String[][] answers = {
            new String[]{"australia", "romania", "azerbaijan", "ukraine"},
            new String[]{"azerbaijan", "ukraine", "qatar", "poland"},
            new String[]{"bahrain", "israel", "qatar", "poland"},
            new String[]{"belgium", "switzerland", "france", "south korea"},
            new String[]{"bolivia", "netherlands", "belgium", "switzerland"},
            new String[]{"bulgaria", "singapore", "costa rica", "thailand"},
            new String[]{"canada", "italy", "hungary", "qatar"},
            new String[]{"costa rica", "thailand", "israel", "netherlands"},
            new String[]{"france", "south korea", "netherlands", "spain"},
            new String[]{"germany", "spain", "ukraine", "australia"},
            new String[]{"hungary", "qatar", "south korea", "netherlands"},
            new String[]{"ireland", "japan", "france", "south korea"},
            new String[]{"poland", "united states", "russia", "hungary"},
            new String[]{"romania", "russia", "hungary", "qatar"},
            new String[]{"ukraine", "australia", "france", "bahrain"},
            new String[]{"israel", "azerbaijan", "switzerland", "bahrain"},
            new String[]{"switzerland", "bahrain", "ukraine", "australia"},
            new String[]{"netherlands", "belgium", "hungary", "qatar"},
            new String[]{"singapore", "bolivia", "russia", "bahrain"},
            new String[]{"italy", "bulgaria", "ireland", "japan"},
            new String[]{"thailand", "canada", "australia", "costa rica"},
            new String[]{"south korea", "costa rica", "azerbaijan", "spain"},
            new String[]{"spain", "france", "bahrain", "thailand"},
            new String[]{"qatar", "poland", "belgium", "bahrain"},
            new String[]{"japan", "hungary", "bolivia", "south korea"},
            new String[]{"united states", "ireland", "bulgaria", "japan"},
            new String[]{"russia", "poland", "switzerland", "bahrain"}
    };

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < correctAnswers.length; i++) {
            Question q = new Question()
                    .setAnswers(answers[i])
                    .setCorrectAnswer(correctAnswers[i])
                    .setImageResource(images[i]);
            questions.add(q);
        }

        return questions;
    }
}
