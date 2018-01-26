package com.example.fatima.calculatorwithhistory;

import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper Calculatordb;
    private Button btnzero;
    private Button btnone;
    private Button btntwo;
    private Button btnthree;
    private Button btnfour;
    private Button btnfive;
    private Button btnsix;
    private Button btnseven;
    private Button btneight;
    private Button btnnine;
    private Button add;
    private Button multiply;
    private Button divide;
    private Button subtract;
    private Button equal;
    private Button clear;
    private Button previous;
    private Button del_lastitem;
    private TextView result;
    private TextView control;
    private TextView history;
    private final char ADD = '+';
    private final char MUL = '*';
    private final char DIV = '/';
    private final char SUB = '-';
    private final char EQUAL = 0;
    private String invalid_input;
    private double first_input = Double.NaN;
    private double sec_input ;
    private char operation;
    private boolean btn_pressed=false;

    private double input1 =0;
    private double input2 =0;
    private String op="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calculatordb = new DatabaseHelper(this);

        initializeViews();


        btnzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;

                control.setText(control.getText().toString() + "0");
            }
        });

        btnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;

                control.setText(control.getText().toString() + "1");
            }
        });

        btntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;

                control.setText(control.getText().toString() + "2");
            }
        });

        btnthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pressed=true;

                control.setText(control.getText().toString() + "3");
            }
        });

        btnfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;

                control.setText(control.getText().toString() + "4");
            }
        });

        btnfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pressed=true;

                control.setText(control.getText().toString() + "5");
            }
        });

        btnsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pressed=true;

                control.setText(control.getText().toString() + "6");
            }
        });

        btnseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pressed=true;
                control.setText(control.getText().toString() + "7");
            }
        });

        btneight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;

                control.setText(control.getText().toString() + "8");
            }
        });

        btnnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=true;
                control.setText(control.getText().toString() + "9");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_pressed)
                {
                    invalid_input = control.getText().toString();
                }
                else {

                    computation();
                    operation = ADD;
                    input1=first_input;
                    op="+";
                    result.setText(String.valueOf(first_input) + "+");
                    control.setText(null);
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_pressed)
                {
                    invalid_input = control.getText().toString();
                }
                else {
                    computation();
                    operation = SUB;
                    input1=first_input;
                    op="-";
                    result.setText(String.valueOf(first_input) + "-");
                    control.setText(null);
                }
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_pressed)
                {
                    invalid_input = control.getText().toString();
                }
                else {
                    computation();
                    operation = MUL;
                    input1=first_input;
                    op="*";
                    result.setText(String.valueOf(first_input) + "*");
                    control.setText(null);
                }
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_pressed)
                {
                    invalid_input = control.getText().toString();
                }
                else {
                    computation();
                    operation = DIV;
                    input1=first_input;
                    op="/";
                    result.setText(String.valueOf(first_input) + "/");
                    control.setText(null);
                }
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computation();
                operation = EQUAL;
                result.setText(result.getText().toString() + String.valueOf(sec_input) + "=" + String.valueOf(first_input));  // e.g 1+2=3
                boolean isInserted = Calculatordb.insertData(input1,sec_input,op,first_input);
                if (isInserted)
                {

                    Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                }
                control.setText(null);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed=false;

                if (control.getText().length() > 0)
                {
                    CharSequence input = control.getText().toString();
                    control.setText(input.subSequence(0,input.length() - 1));
                }
                else
                {
                    first_input = Double.NaN;
                    sec_input = Double.NaN;
                    control.setText(null);
                    result.setText(null);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setText(null);
                Cursor data = Calculatordb.retrieveData();

                if (data.getCount() == 0)
                {
                    history.setText("No history");
                }

                else {

                    while (data.moveToNext()) {
                        history.setText(data.getString(1) + data.getString(3) + data.getString(2) + "=" + data.getString(4));
                    }
                }


            }
        });

        del_lastitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setText(null);
                Cursor data = Calculatordb.retrieveData();

                if (data.getCount() != 0)
                {
                    Calculatordb.deleteData();
                }



            }
        });

    }

    private void initializeViews()
    {
        btnzero = (Button) findViewById(R.id.b0);
        btnone = (Button) findViewById(R.id.b1);
        btntwo = (Button) findViewById(R.id.b2);
        btnthree = (Button) findViewById(R.id.b3);
        btnfour = (Button) findViewById(R.id.b4);
        btnfive = (Button) findViewById(R.id.b5);
        btnsix = (Button) findViewById(R.id.b6);
        btnseven = (Button) findViewById(R.id.b7);
        btneight = (Button) findViewById(R.id.b8);
        btnnine = (Button) findViewById(R.id.b9);
        add  = (Button) findViewById(R.id.badd);
        multiply = (Button) findViewById(R.id.bmul);
        divide = (Button) findViewById(R.id.bdiv);
        subtract = (Button) findViewById(R.id.bsub);
        equal = (Button) findViewById(R.id.beq);
        clear = (Button) findViewById(R.id.bclear);
        previous = (Button) findViewById(R.id.prev);
        del_lastitem = (Button) findViewById(R.id.dellastitem);
        result = (TextView) findViewById(R.id.tvResult);
        control = (TextView) findViewById(R.id.tvControl);
        history = (TextView) findViewById(R.id.tvHistory);

    }

    private void computation()
    {
        if(!Double.isNaN(first_input))
        {

            sec_input = Double.parseDouble(control.getText().toString());

            switch (operation)
            {
                case ADD:

                    first_input = first_input + sec_input;
                    break;

                case SUB:

                    first_input = first_input - sec_input;
                    break;

                case MUL:

                    first_input = first_input * sec_input;
                    break;

                case DIV:

                    first_input = first_input / sec_input;
                    break;

                case EQUAL:

                    break;
            }
        }
        else {
            first_input = Double.parseDouble(control.getText().toString());
        }
    }


}

