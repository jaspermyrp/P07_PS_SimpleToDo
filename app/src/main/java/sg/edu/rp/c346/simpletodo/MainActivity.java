package sg.edu.rp.c346.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Spinner spnTaskSelection;
    EditText etTask;
    Button btnAdd, btnDelete, btnClear, btnSort;
    ListView lvTask;

    ArrayList<String> alTaskList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI Components //
        spnTaskSelection = findViewById(R.id.spinner);
        etTask = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAddTask);
        btnDelete = findViewById(R.id.btnDeleteTask);
        btnClear = findViewById(R.id.btnClear);
        btnSort = findViewById(R.id.btnSort);
        lvTask = findViewById(R.id.lvTask);

        // ArrayAdapter //
        final ArrayAdapter<String> aaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alTaskList);
        lvTask.setAdapter(aaAdapter);

        // Listeners //
        spnTaskSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        etTask.setHint(R.string.et_hint1);

                        btnAdd.setEnabled(true);
                        btnDelete.setEnabled(false);

                        clearText();
                        break;
                    case 1:
                        etTask.setHint(R.string.et_hint2);

                        btnAdd.setEnabled(false);
                        btnDelete.setEnabled(true);

                        clearText();

                        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                alTaskList.remove(position);
                                aaAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etGetTask = etTask.getText().toString();

                if (etGetTask.length() == 0 || etGetTask.matches("\\s*")) {
                    Toast.makeText(MainActivity.this, "Enter a task.", Toast.LENGTH_SHORT).show();
                } else {
                    alTaskList.add(etGetTask);
                    aaAdapter.notifyDataSetChanged();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alTaskList.size() == 0) {
                    Toast.makeText(MainActivity.this, "You don't have any task to remove!", Toast.LENGTH_SHORT).show();
                } else {
                    String etGetTask = etTask.getText().toString();
                    int pos = 0;

                    try {
                        pos = Integer.parseInt(etGetTask);
                    } catch (Exception e) {
                        Log.d("Exception caught", e.getMessage());
                    }

                    if (pos > -1 && pos < alTaskList.size()) {
                        alTaskList.remove(pos);
                        aaAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MainActivity.this, "Wrong index number!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alTaskList.clear();
                aaAdapter.notifyDataSetChanged();
                clearText();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Collections.sort(alTaskList);
                aaAdapter.notifyDataSetChanged();
            }
        });


    }

    public void clearText() {
        etTask.setText("");
    }
}
