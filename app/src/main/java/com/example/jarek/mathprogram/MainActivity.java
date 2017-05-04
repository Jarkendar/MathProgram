package com.example.jarek.mathprogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/* Program w założeniu poprawiający zdolności liczenia, w szczególności
 * takie umiejętności jak dodawanie, odejmowanie, mnożenie, dzielenie,
  * podnoszenie do kwadratu i pierwiastkowanie kwadratowe. Program przechowuje
  * wyniki poszczególnych działań arytmetycznych. Oblicza średni poziom
  * użytkownika.*/

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCE_NAME = "mypreference";//zmienna nazwy pliku

    private boolean marker = false;
    private TextView Tlev;//tekst liczby poziomu trudności

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tlev = (TextView)findViewById(R.id.textView_percent);//powiązanie komponentu TextViev z nazwą przy starcie aktywności
    }

    @Override
    protected void onResume() {//odpalana za każdym razem gdy aktywność powraca na pierwszy plan
        super.onResume();
        SharedPreferences sharedPreferences;//umożliwia zapisywanie i odczytywanie danych z plików
        double addition, substraction, multiplication, division, powers, roots;//poziomy trudności
        double random;//średnia z poziomów, poziom losowy
        int integer, rest;

        //getSharedPreferences(string z nazwą pliku, tryb otwarcia)
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME,MODE_PRIVATE);//pobranie poziomów trudności

        int f = getResources().getInteger(R.integer.add_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.add_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.add_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.add_score_rest),f);//pobranie poziomu trudności
        addition = integer + (double)rest/100;

        f = getResources().getInteger(R.integer.sub_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.sub_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.sub_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.sub_score_rest),f);//pobranie poziomu trudności
        substraction = integer + (double) rest/100;

        f = getResources().getInteger(R.integer.mul_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.mul_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.mul_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.mul_score_rest),f);//pobranie poziomu trudności
        multiplication = integer + (double)rest/100;

        f = getResources().getInteger(R.integer.div_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.div_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.div_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.div_score_rest),f);//pobranie poziomu trudności
        division = integer + (double)rest/100;

        f = getResources().getInteger(R.integer.pow_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.pow_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.pow_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.pow_score_rest),f);//pobranie poziomu trudności
        powers = integer + (double)rest/100;

        f = getResources().getInteger(R.integer.root_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.root_score),f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.root_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.root_score_rest),f);//pobranie poziomu trudności
        roots = (double)integer + (double)rest/100;

        random = (addition+substraction+multiplication+division+powers+roots)/6;//obliczenie średniego poziomu
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");//zakrąglenie do dwóch miejsc po przecinku
        String tem = df.format(random)+"%";
        Tlev.setText(tem);//wyświetlenie poziomu

        if (random == 100){
            marker = true;
        }
        if (marker){
            Toast.makeText(getApplicationContext(),"Gratulacje, wspaniale liczysz!!!",Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("text_author_full","by Jarosław \"Jarkendar\" Skrzypczak");
            editor.apply();
        }
    }

    public void click_finish(View view) {
        finish();
    }//zakończenie działania programu

    public void click_button(View view) {//metoda kliknięcia w przycisk
        String a="+", s="-", m="*", d="/", p="^", r="r";//baza znaków przesyłanych do aktywności
        int id = view.getId();//ściągnięcie ID komponentu klikniętego
        /* Każda z wywołanych aktywności jest identyczna pod względem budowy.
         * Różnica jest tylko w rodzaju działania jakie program musi wygenerować,
          * dlatego wraz z wywołaniem aktywności przesyłany jest znak działania.
          * Metoda wie, kiedy dodać jaki znak, przez wyciągnięcie ID klikniętego
          * komponentu i porównywanie go z ID komponentów ze spisu.*/
        switch (id) {
            case R.id.button_add: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", a);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_sub: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", s);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_mul: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", m);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_div: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", d);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_pow: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", p);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_root: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", r);//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
            case R.id.button_rand: {//wywołanie aktywności
                Intent intent = new Intent(this, ActivityCount.class);
                intent.putExtra("znak", "L");//dodanie znaku przy wywołaniu aktywności
                startActivity(intent);
                break;
            }
        }
    }
}
/* Jarosław Skrzypczak twórca pomysłu, kodu, logo i opisu. 2 sierpnia 2016*/
