
package com.hacktiv8.travelling3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageButton homeBtnKembali, homeBtnAccount;
    private Spinner homeInputFrom, homeInputTo;
    private TextView homeTextInputDateGo, homeTextInputDateReturn;
    private ImageButton homeBtnInputDateGo, homeBtnInputDateReturn;
    private MaterialButton homeBtnSearch,mBtnMyTicket;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        homeBtnKembali = findViewById(R.id.homeBtnKembali);
        homeBtnAccount = findViewById(R.id.homeBtnAccount);
        homeInputFrom = findViewById(R.id.homeInputFrom);
        homeInputTo = findViewById(R.id.homeInputTo);
        homeTextInputDateGo = findViewById(R.id.homeTextInputDateGo);
        homeTextInputDateReturn = findViewById(R.id.homeTextInputDateReturn);
        homeBtnInputDateGo = findViewById(R.id.homeBtnInputDateGo);
        homeBtnInputDateReturn = findViewById(R.id.homeBtnInputDateReturn);
        homeBtnSearch = findViewById(R.id.homeBtnSearch);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(today);

        auth = FirebaseAuth.getInstance();

        mBtnMyTicket = findViewById(R.id.btnMyTicket);
        mBtnMyTicket.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, YourTicketActivity.class);
            startActivity(intent);
        });

        homeBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lakukan logout dari Firebase
                auth.signOut();

                // Pindahkan ke SplashScreenActivity
                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                // Tambahkan flag untuk membersihkan tumpukan aktivitas sebelumnya
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Akhiri aktivitas saat ini
            }
        });

        homeBtnInputDateGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogGo();
            }
        });
        homeBtnInputDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogReturn();
            }
        });

        homeBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTicket();
            }
        });
    }

    private void searchTicket() {
        String mInputFrom = homeInputFrom.getSelectedItem().toString();
        String mInputTo = homeInputTo.getSelectedItem().toString();
        String mHomeTextInputDateGo = homeTextInputDateGo.getText().toString();
        String mHomeTextInputDateReturn = homeTextInputDateReturn.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        Calendar selectedDateGo = Calendar.getInstance();
        Calendar selectedDateReturn = Calendar.getInstance();
        try {
            selectedDateGo.setTime(sdf.parse(mHomeTextInputDateGo));
            selectedDateReturn.setTime(sdf.parse(mHomeTextInputDateReturn));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isDateBeforeToday(selectedDateGo) || isDateBeforeToday(selectedDateReturn)) {
            Toast.makeText(MainActivity.this, "Pilih tanggal hari ini atau esok hari", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mInputFrom.equals("Pilih") || mInputTo.equals("Pilih") || mHomeTextInputDateGo.equals("DD MMM YYYY") ){
            Toast.makeText(MainActivity.this, "Silahkan pilih Tujuan dan tanggal", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, ListTicketActivity.class);
            intent.putExtra("mInputFrom", mInputFrom);
            intent.putExtra("mInputTo", mInputTo);
            intent.putExtra("mHomeTextInputDateGo", mHomeTextInputDateGo);
            startActivity(intent);
        }

    }

    private void showDateDialogGo(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                homeTextInputDateGo.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void showDateDialogReturn(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                homeTextInputDateReturn.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private boolean isDateBeforeToday(Calendar selectedDate) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return selectedDate.before(today);
    }
}