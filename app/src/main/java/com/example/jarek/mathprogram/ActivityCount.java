package com.example.jarek.mathprogram;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

public class ActivityCount extends AppCompatActivity {

    private String PREFERENCE_NAME = "mypreference";//nazwa pliku z wynikami

    private String sign, markersign;//znak działania i znacznik działania
    private double addition, substraction, multiplication, division, powers, roots;//zmienne poziomów trudności
    private TextView level, Equation, time, avgtext, goodbad;//komponenty działania i poziomu trudności
    private int eq, goodcounter, badcounter; //wynik działań, liczniki dobrych i złych odpowiedzi
    private EditText editText;//komponent wpisywania wyniku przez użytkownika
    private SharedPreferences sharedPreferences;//zmienna odczytu i zapisu
    private long start, avgtime;//zmienne czasowe, sumator czasów
    private DecimalFormat df;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_count);

        time = (TextView) findViewById(R.id.texttime);
        avgtext = (TextView) findViewById(R.id.avgtime_text);
        goodbad = (TextView) findViewById(R.id.good_bad_text);


        level = (TextView) findViewById(R.id.text_score);//powiązanie komponentu ze zmienną
        Equation = (TextView) findViewById(R.id.text_equation);//powiązanie komponentu ze zmienną
        editText = (EditText) findViewById(R.id.editText);//powiązanie komponentu ze zmienną

        Bundle b = getIntent().getExtras();// pobranie zamiaru i zmiennycyh dodatkowych
        sign = markersign = b.getString("znak");//pobranie znaku działania

        goodcounter = badcounter = 0;//zerowanie liczników
        avgtime = 0;

        df = new DecimalFormat("0.00");//zakrąglenie do dwóch miejsc po przecinku
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {//wywoływana zawsze po uruchomieniu aktywności
        super.onResume();
        int integer, rest;//zmienne pomocnicze

        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);//pobranie poziomów trudności
//dodawanie
        int f = getResources().getInteger(R.integer.add_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.add_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.add_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.add_score_rest), f);//pobranie poziomu trudności
        addition = integer + (double) rest / 100;
//odejmowanie
        f = getResources().getInteger(R.integer.sub_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.sub_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.sub_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.sub_score_rest), f);//pobranie poziomu trudności
        substraction = integer + (double) rest / 100;
//mnożenie
        f = getResources().getInteger(R.integer.mul_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.mul_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.mul_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.mul_score_rest), f);//pobranie poziomu trudności
        multiplication = integer + (double) rest / 100;
//dzielenie
        f = getResources().getInteger(R.integer.div_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.div_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.div_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.div_score_rest), f);//pobranie poziomu trudności
        division = integer + (double) rest / 100;
//potęgowanie
        f = getResources().getInteger(R.integer.pow_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.pow_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.pow_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.pow_score_rest), f);//pobranie poziomu trudności
        powers = integer + (double) rest / 100;
//pierwiastkowanie
        f = getResources().getInteger(R.integer.root_score);//pobranie zasobu
        integer = sharedPreferences.getInt(String.valueOf(R.integer.root_score), f);//pobranie poziomu trudności
        f = getResources().getInteger(R.integer.root_score_rest);//pobranie zasobu
        rest = sharedPreferences.getInt(String.valueOf(R.integer.root_score_rest), f);//pobranie poziomu trudności
        roots = integer + (double) rest / 100;

        ChooseTask();//wywołanie funkcji losującej pierwsze działanie
    }

    private void ChooseTask() {//funkcja wybierająca działanie
        sign = markersign;
        switch (sign) {//zmienna sign odpowiada za rodzaj przydzielonego działania
            case "+": {//dodawanie
                Addition();
                break;
            }
            case "-": {//odejmowanie
                Substraction();
                break;
            }
            case "*": {//mnożenie
                Multiplication();
                break;
            }
            case "/": {//dzielenie
                Division();
                break;
            }
            case "^": {//potęgowanie
                Square();
                break;
            }
            case "r": {//pierwiastkowanie
                RootSquare();
                break;
            }
            case "L": {//losowo
                Random random = new Random(System.currentTimeMillis());
                byte option;//pomocnicza zmienna losowości
                option = (byte) random.nextInt(6);//losowanie rodzaju działania 0-5
                switch (option) {
                    case 0: {//dodawanie
                        Addition();
                        sign = "+";
                        break;
                    }
                    case 1: {//odejmowanie
                        Substraction();
                        sign = "-";
                        break;
                    }
                    case 2: {//mnożenie
                        Multiplication();
                        sign = "*";
                        break;
                    }
                    case 3: {//dzielenie
                        Division();
                        sign = "/";
                        break;
                    }
                    case 4: {//potęgowanie
                        Square();
                        sign = "^";
                        break;
                    }
                    case 5: {//pierwiastkowanie
                        RootSquare();
                        sign = "r";
                        break;
                    }
                }
                break;
            }
        }
        start = System.currentTimeMillis();
    }

    private void Addition() {//dodawanie
        int max = (int) (addition * addition);//maksymalna liczba wylosowana
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losową głowicą
        int s1 = random.nextInt(max), s2 = random.nextInt(max);//losowanie dwóch zmiennych do max
        eq = s1 + s2;//obliczenie wyniku
        //za pomocą String.format można wpisywać liczby jak w C - pomocne
        //Locale.getDefault służy do pobierania lokalnego języka użytkownika,
        //w celach obróbki tekstu dla konkretnego języka.
        Equation.setText(String.format(Locale.getDefault(), "%d + %d =", s1, s2));//wyświetlenie działania
        String tem = df.format(addition) + "%";
        level.setText(tem);//wyświetlenie aktualnego poziomu trudności
    }

    private void Substraction() {//odejmowanie
        int max = (int) (substraction * substraction);//oblicznie poziomu trudności
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losowym kluczem
        int s1 = random.nextInt(max), s2 = random.nextInt(max);//losowanie zmiennych do max
        eq = s1 - s2;//oblicznie wyniku
        Equation.setText(String.format(Locale.getDefault(), "%d - %d =", s1, s2));//wyświetlenie działania
        String tem = df.format(substraction) + "%";
        level.setText(tem);//wyświetlenie poziomu trudności
    }

    private void Multiplication() {//mnożenie
        int max;
        if (multiplication < 50) {
            max = (int) multiplication;
        } else {
            max = (int) ((multiplication * multiplication) / 50);//ustawienie poziomu trudności
        }
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losowym kluczem
        int s1 = random.nextInt(max), s2 = random.nextInt(max);//losowanie zmiennych do max
        eq = s1 * s2;//obliczenie poprawnego wyniku
        Equation.setText(String.format(Locale.getDefault(), "%d * %d =", s1, s2));//wyświetlenie działania
        String tem = df.format(multiplication) + "%";
        level.setText(tem);//wyświetlenie poziomu trudności
    }

    private void Division() {//dzielenie
        int max;
        if (division < 50) {
            max = (int) division;
        } else {
            max = (int) ((division * division) / 50);//ustawienie poziomu trudności
        }
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losowym kluczem
        int s1 = random.nextInt(max), s2;//losowanie zmiennych do max
        do {//zabezpieczenie przed dzieleniem przez zero
            s2 = random.nextInt(max);
        } while (s2 == 0);
        eq = s1 * s2;//oblicznie wyniku, poprzez mnożenie, zawsze całkowite
        int pom = eq;//zamiana wyniku na jeden z czynników, aby powstało działanie dzielenia
        eq = s1;
        s1 = pom;
        Equation.setText(String.format(Locale.getDefault(), "%d / %d =", s1, s2));//wyświtlenie działania
        String tem = df.format(division) + "%";
        level.setText(tem);//wyświetlenie poziomu trudności
    }

    private void Square() {//potęgowanie
        int max;
        if (powers < 50) {
            max = (int) powers;
        } else {
            max = (int) ((powers * powers) / 50);//ustawienie poziomu trudności
        }
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losowym kluczem
        int s1 = random.nextInt(max);//losowanie zmiennej do max
        eq = s1 * s1;//obliczenie wyniku
        Equation.setText(String.format(Locale.getDefault(), "%d^2  =", s1));//wyświetlenie działania
        String tem = df.format(powers) + "%";
        level.setText(tem);//wyświetlenie poziomu trudności
    }

    private void RootSquare() {//pierwiastkowanie
        int max;
        if (roots < 50) {
            max = (int) roots;
        } else {
            max = (int) ((roots * roots) / 50);//ustawienie poziomu trudności
        }
        if (max == 0) max = 1;
        Random random = new Random(System.currentTimeMillis());//tworzenie obiektu Random z losowym kluczem
        int s1 = random.nextInt(max);//losowanie zmiennej
        eq = s1 * s1;//obliczenie wyniku, poprzez podniesienie do kwadratu
        int pom = eq;//zamiana wyniku z czynnikiem, aby powstało pierwiastkowanie
        eq = s1;
        s1 = pom;
        Equation.setText(String.format(Locale.getDefault(), "√%d  =", s1));//wyświetlenie działania
        String tem = df.format(roots) + "%";
        level.setText(tem);//wyświetlenie poziomu trudności
    }

    private void Good(double changeLevel) {//komunikat dobrze
        //wyświetlnie komunikatu, (context aplikacji, komunikat, długość wyświetlania).wyświetlenie
        String text;
        if (changeLevel == 0) {
            text = "Dobrze";
        } else {
            text = "Dobrze +" + df.format(changeLevel);
        }
        goodcounter++;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void Bad(double changeLevel) {//komunikat źle
        //wyświetlnie komunikatu, (context aplikacji, komunikat, długość wyświetlania).wyświetlenie
        String text;
        if (changeLevel == 0) {
            text = "Źle";
        } else {
            text = "Źle -" + df.format(changeLevel);
        }
        badcounter++;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void click_Check(View view) {//sprawdzenie poprawności wpisanego wyniku z wynikiem poprawnym
        long tim = System.currentTimeMillis() - start;//czas
        avgtime+=tim;//zliczanie ogólnego czasu
        double changeLevel;//zmiana wartości poziomu
        if (tim <= 5000) {//wyznaczanie zmiany poziomu trudności
            changeLevel = 1;//maksymalna wartość
        } else if (tim >= 30000) {
            changeLevel = (0.1);//minimalna wartość
        } else {
            changeLevel = -0.036 * (double) (tim / 1000) + 1.18;// y = -0,036x + 1,18
        }

        String download = editText.getText().toString();//pobranie tektu z EditText
        if (download.length() != 0) {//sprawdzenie czy EditText nie jest pusty, zabezpiecza przed błędem
            int check = Integer.parseInt(download);//rzutowanie Stringa na Int
            switch (sign) {
                case "+": {//dodawnie
                    if ((addition+changeLevel)>100 && eq == check) changeLevel = 100-addition;
                    if ((addition-changeLevel)<0 && eq != check) changeLevel = addition;
                    if (eq == check && addition < 100) {//dobrze i mniejszy od 100
                        addition += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && addition > 0) {//źle i większy od 0
                        addition -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {//dobrze
                        Good(0);
                    } else {//źle
                        Bad(0);
                    }
                    break;
                }
                case "-": {//odejmowanie
                    if ((substraction+changeLevel)>100 && eq == check) changeLevel = 100-substraction;
                    if ((substraction-changeLevel)<0 && eq != check) changeLevel = substraction;
                    if (eq == check && substraction < 100) {//dobrze i mniejsze od 100
                        substraction += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && substraction > 0) {//źle i większe od 0
                        substraction -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {//dobrze
                        Good(0);
                    } else {//źle
                        Bad(0);
                    }
                    break;
                }
                case "*": {//mnożenie
                    if ((multiplication+changeLevel)>100 && eq == check) changeLevel = 100-multiplication;
                    if ((multiplication-changeLevel)<0 && eq != check) changeLevel = multiplication;
                    if (eq == check && multiplication < 100) {//dobrze i mniejsze od 100
                        multiplication += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && multiplication > 0) {//źle i większe od 0
                        multiplication -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {
                        Good(0);
                    } else {
                        Bad(0);
                    }
                    break;
                }
                case "/": {//dzielenie
                    if ((division+changeLevel)>100 && eq == check) changeLevel = 100-division;
                    if ((division-changeLevel)<0 && eq != check) changeLevel = division;
                    if (eq == check && division < 100) {//dobrze i mniejsze od 100
                        division += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && division > 0) {//źle i większe od 0
                        division -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {//dobrze
                        Good(0);
                    } else {//źle
                        Bad(0);
                    }
                    break;
                }
                case "^": {//potęgowanie
                    if ((powers+changeLevel)>100 && eq == check) changeLevel = 100-powers;
                    if ((powers-changeLevel)<0 && eq != check) changeLevel = powers;
                    if (eq == check && powers < 100) {//dobrze i mniejsze od 100
                        powers += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && powers > 0) {//źle i większe od 0
                        powers -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {//dobrze
                        Good(0);
                    } else {//źle
                        Bad(0);
                    }
                    break;
                }
                case "r": {//pierwiastkowanie
                    if ((roots+changeLevel)>100 && eq == check) changeLevel = 100-roots;
                    if ((roots-changeLevel)<0 && eq != check) changeLevel = roots;
                    if (eq == check && roots < 100) {//dobrze i mniejsze od 100
                        roots += changeLevel;
                        Good(changeLevel);
                    } else if (eq != check && roots > 0) {//źle i większe od 0
                        roots -= changeLevel;
                        Bad(changeLevel);
                    } else if (eq == check) {//dobrze
                        Good(0);
                    } else {//źle
                        Bad(0);
                    }
                    break;
                }
            }
        } else {//pole jest puste poziom trudności zostaje obniżony
            Toast.makeText(getApplicationContext(), "Brak odpowiedzi. -1", Toast.LENGTH_LONG).show();
            switch (sign) {
                case "+": {//dodawanie
                    if (addition > 0)
                        addition--;
                    break;
                }
                case "-": {//odejmowanie
                    if (substraction > 0)
                        substraction--;
                    break;
                }
                case "*": {//mnożenie
                    if (multiplication > 0)
                        multiplication--;
                    break;
                }
                case "/": {//dzielenie
                    if (division > 0)
                        division--;
                    break;
                }
                case "^": {//potęgowanie
                    if (powers > 0)
                        powers--;
                    break;
                }
                case "r": {//pierwiastkowanie
                    if (roots > 0)
                        roots--;
                    break;
                }
            }
        }

        long number = tim / 1000, fraction = tim % 1000;//obliczenie sekund i milisekund
        time.setText(String.format(Locale.getDefault(), "%d,%ds", number, fraction));// wyświetlenie w formacie s,ms
        editText.setText("");//czyszczenie pola EditText, przygotowanie do kolejnego działania

        ChooseTask();//wywołanie generacji kolejengo działania

        long tempavg ;//zmienna tymczasowa średniej prędkości

        if (goodcounter+badcounter == 0) tempavg = avgtime;//zabezpieczenie przed dzieleniem przez zero
        else tempavg = avgtime / (goodcounter+badcounter);

        number = tempavg /1000;
        fraction = tempavg % 1000;
        avgtext.setText(String.format(Locale.getDefault(), "%d,%ds", number, fraction));//wyświetlenie średniego czasu

        goodbad.setText(String.format(Locale.getDefault(), "%d/%d", goodcounter, badcounter));//ustawienie dobrych/złych


        /* Po napisaniu całej tej metody wpadłem na pomysł, że można to zrobić znacznie krócej,
         * mianowicie sprawdzenie raz warunków if i wybranie tylko odpowieniego działanie
          * do obniżenia poziomu trudności, wyświetlenie mogłoby się wyświetlać za pomocą
          * zmiennej dodatkowej.*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        int integer, rest;//zmienne pomocnicze
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_APPEND);//zmienna zapisu i odczytu danych
        SharedPreferences.Editor editor = sharedPreferences.edit();//writer pozwala zapisać dane
        //zapis danych
//dodawanie
        integer = (int) addition;
        rest = (int) addition % 100;
        editor.putInt(String.valueOf(R.integer.add_score), integer);
        editor.putInt(String.valueOf(R.integer.add_score_rest), rest);
//odejmowanie
        integer = (int) substraction;
        rest = (int) substraction % 100;
        editor.putInt(String.valueOf(R.integer.sub_score), integer);
        editor.putInt(String.valueOf(R.integer.sub_score_rest), rest);
//mnożenie
        integer = (int) multiplication;
        rest = (int) multiplication % 100;
        editor.putInt(String.valueOf(R.integer.mul_score), integer);
        editor.putInt(String.valueOf(R.integer.mul_score_rest), rest);
//dzielenie
        integer = (int) division;
        rest = (int) division % 100;
        editor.putInt(String.valueOf(R.integer.div_score), integer);
        editor.putInt(String.valueOf(R.integer.div_score_rest), rest);
//potęgowanie
        integer = (int) powers;
        rest = (int) powers % 100;
        editor.putInt(String.valueOf(R.integer.pow_score), integer);
        editor.putInt(String.valueOf(R.integer.pow_score_rest), rest);
//pierwiastkowanie
        integer = (int) roots;
        rest = (int) roots % 100;
        editor.putInt(String.valueOf(R.integer.root_score), integer);
        editor.putInt(String.valueOf(R.integer.root_score_rest), rest);

        editor.apply();//zwolnienie writer'a, można użyć też commit
    }

    public void click_end(View view) {//funckcja zakończenia aktywności
        finish();//zakończenie działania aktywności
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActivityCount Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jarek.mathprogram/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActivityCount Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jarek.mathprogram/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}