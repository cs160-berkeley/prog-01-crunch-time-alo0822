package com.example.fangyulo.crunchtime;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    final Map<String, Integer> conversions = new HashMap<String, Integer>();

    EditText exercise_amt;
    TextView exercise_unit;
    Spinner spinner;
    Button btn_calc;
    String spinner_selected;

    String[] Types = {"Calories", "Cycling", "Jogging", "Jumping Jacks", "Leg-lift", "Plank",
            "Pullups", "Pushups", "Sit ups", "Squats", "Stair-climbing", "Swimming", "Walking"};
    String[] Reps = {"Pushups", "Pullups", "Sit ups", "Squats"};
    String[] Min = {"Cycling", "Jogging", "Jumping Jacks", "Leg-lift", "Plank", "Stair-climbing",
            "Swimming", "Walking"};
    ListView listview;
    ListAdapter listadapter;

    int list_img_cardio = R.drawable.ic_directions_run_indigo_500_24dp;
    int list_img_weights = R.drawable.ic_fitness_center_indigo_500_24dp;
    int list_img_cal = R.drawable.ic_whatshot_indigo_500_24dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHash();
        exercise_amt = (EditText)findViewById(R.id.exercise_amt);
        exercise_amt.setSelection(exercise_amt.getText().length());
        exercise_unit = (TextView)findViewById(R.id.exercise_unit);
        btn_calc = (Button)findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyCustomAdapter(MainActivity.this, R.layout.spinner_layout, Types));
        spinner.setOnItemSelectedListener(this);

        listview = (ListView) findViewById(R.id.main_listview);
        listadapter = new ListAdapter(getApplicationContext(), R.layout.list_layout);
        listadapter.setHidden(-1);
        listview.setAdapter(listadapter);

        exercise_amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) v).setSelection(0, exercise_amt.getText().length());
                exercise_amt.setCursorVisible(true);
            }
        });
    }

    private void setHash() {
        conversions.put("Calories", 100);
        conversions.put("Cycling", 12);
        conversions.put("Jogging", 12);
        conversions.put("Jumping Jacks", 10);
        conversions.put("Leg-lift", 25);
        conversions.put("Plank", 25);
        conversions.put("Pullups", 100);
        conversions.put("Pushups", 350);
        conversions.put("Sit ups", 200);
        conversions.put("Squats", 225);
        conversions.put("Stair-climbing", 15);
        conversions.put("Swimming", 13);
        conversions.put("Walking", 20);
    }

    private void updateList(List<String> amount) {
        System.out.println("updating list");
        System.out.println("hidden is: " + listadapter.getHidden());
        int i = 0;
        int img = list_img_cal;
        int unit = R.string.unit_cal;
        for (String type : Types) {
            if (i == listadapter.getHidden()) {
                i++;
                continue;
            }
            if (Arrays.asList(Reps).contains(type) || type.equals("Leg-lift") || type.equals("Plank")){
                img = list_img_weights;
                unit = R.string.unit_reps;
            }
            else if (Arrays.asList(Min).contains(type)) {
                img = list_img_cardio;
                unit = R.string.unit_min;
            }
            Exercise obj = new Exercise(img, type, amount.get(i), unit);
            listadapter.add(obj);
            i++;
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        exercise_amt.setCursorVisible(false);
        spinner_selected = spinner.getSelectedItem().toString();
        if (exercise_amt.getText().toString().equals("")) {
            listadapter.clear();
            listadapter.notifyDataSetChanged();
            return;
        }
        int amt = Integer.parseInt(exercise_amt.getText().toString());
        List<String> converted = convert(amt, spinner_selected);
        listadapter.setHidden(Arrays.asList(Types).indexOf(spinner_selected));
        if (listadapter.getCount() > 0)
            System.out.println(">0 list items");
            listadapter.clear();
        updateList(converted);
        listadapter.notifyDataSetChanged();
    }

    private List<String> convert (int amt, String selected) {
        int value;
        List<String> converted = new ArrayList<>();
        for (String toConvert : Types) {
            value = (int) ((double)conversions.get(toConvert) / conversions.get(selected) * amt);
            converted.add(Integer.toString(value));
        }
        return converted;
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_layout, parent, false);
            TextView label=(TextView)row.findViewById(R.id.txt);
            label.setText(Types[position]);

            ImageView icon = (ImageView)row.findViewById(R.id.icon);

            if (Arrays.asList(Reps).contains(Types[position]) || Types[position].equals("Leg-lift") || Types[position].equals("Plank")){
                icon.setImageResource(list_img_weights);
            }
            else if (Arrays.asList(Min).contains(Types[position])){
                icon.setImageResource(list_img_cardio);
            }
            else if (Types[position].equals("Calories")) {
                icon.setImageResource(list_img_cal);
            }

            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String exercise = Types[position];
        if (exercise.equals("Calories")) {
            exercise_unit.setText(R.string.unit_cal);
        }
        else if (Arrays.asList(Reps).contains(exercise)){
            exercise_unit.setText(R.string.unit_reps);
        }
        else if (Arrays.asList(Min).contains(exercise)){
            exercise_unit.setText(R.string.unit_min);
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
