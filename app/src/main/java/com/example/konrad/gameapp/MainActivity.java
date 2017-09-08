package com.example.konrad.gameapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    final String TAG = "KonradApp";
    MediaPlayer myFirstSound;
    MediaPlayer mySecondSound;
    MediaPlayer mySeventhSound;
    MediaPlayer myThirdSound;
    MediaPlayer myFifthSound;
    @BindView(R.id.imageButton1)
    ImageButton papier;
    @BindView(R.id.imageButton2)
    ImageButton kamien;
    @BindView(R.id.imageButton3)
    ImageButton nozyce;
    @BindView(R.id.imageView5)
    ImageView mojRuch;
    @BindView(R.id.imageView6) ImageView komputerRuch;
    @BindView(R.id.finalpic) ImageView finalPicture;
    @BindView(R.id.dd2) LinearLayout buttonsContainer;
    @BindView(R.id.lwey) TextView lewyLabel;
    @BindView(R.id.prawy) TextView prawyLabel;
    static String SAVED_CLICK_COUNT = "wow";
    static final int PAPIER = 1;
    static final int KAMIEN = 2;
    static final int NOZYCE = 3;
    int computerResult;
    int playerResult;

    static String WYNIKKOMPUTERA = "WYNIKKOMPUTERA";
    static String WYNIKPLAYERA = "WYNIKPLAYERA";

    static String myPicture1 = "myPicture1";
    static String computerPicture = "computerPicture";

    static int zmiennaPomocnicza1 = 0;
    static int zmiennaPomocnicza2 = 0;
    int redColorForSnackBar = Color.parseColor("#ff0000");
    int greenColorForSnackBar = Color.parseColor("#66ff33");
    boolean undoComputerResult;


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(WYNIKKOMPUTERA, computerResult);
        outState.putInt(WYNIKPLAYERA, playerResult);
        outState.putInt(myPicture1, zmiennaPomocnicza1);
        outState.putInt(computerPicture, zmiennaPomocnicza2);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int computerResultt = savedInstanceState.getInt(WYNIKKOMPUTERA);
        int mineResult = savedInstanceState.getInt(WYNIKPLAYERA);
        computerResult = computerResultt;
        playerResult = mineResult;

        lewyLabel.setText(String.valueOf(mineResult));
        prawyLabel.setText(String.valueOf(computerResult));

        int obrazek1 = savedInstanceState.getInt(myPicture1);
        int zmienna = zmiennaPomocnicza1;

        if (zmiennaPomocnicza1 == PAPIER) {
            mojRuch.setBackgroundResource(R.drawable.ee);
        } else if (zmiennaPomocnicza1 == NOZYCE) {
            mojRuch.setBackgroundResource(R.drawable.nozyczki);
        } else if (zmiennaPomocnicza1 == KAMIEN) {
            mojRuch.setBackgroundResource(R.drawable.ss);
        }

        int obrazekKomputera = savedInstanceState.getInt(computerPicture);
        int zmienna2 = zmiennaPomocnicza2;
        if (zmiennaPomocnicza2 == PAPIER) {
            komputerRuch.setBackgroundResource(R.drawable.ee);
        } else if (zmiennaPomocnicza2 == NOZYCE) {
            komputerRuch.setBackgroundResource(R.drawable.nozyczki);
        } else if (zmiennaPomocnicza2 == KAMIEN) {
            komputerRuch.setBackgroundResource(R.drawable.ss);
        }
    }

    private int drawRandomly(){
        Random myRandom = new Random();
        String pictureOne = "img_" + myRandom.nextInt(3);
        int coZwroci = 0;
        // Log.d(TAG, pictureOne);
        switch (pictureOne){
            case "img_0":
                komputerRuch.setBackgroundResource(R.drawable.ee);
                coZwroci = PAPIER;
                break;
            case "img_1":
                komputerRuch.setBackgroundResource(R.drawable.ss);
                coZwroci = KAMIEN;
                break;
            case "img_2":
                komputerRuch.setBackgroundResource(R.drawable.nozyczki);
                coZwroci = NOZYCE;
                break;
            default:
                break;
        }
        return coZwroci;
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton1:
                    mySecondSound.start();
                    mojRuch.setBackgroundResource(R.drawable.ee);
                    int zwrcone = drawRandomly();
                    finalResult(PAPIER, zwrcone);
                    whoIsTheWinner();
                    checkIfStopGame();
                    break;
                case R.id.imageButton2:
                    mySecondSound.start();
                    mojRuch.setBackgroundResource(R.drawable.nozyczki);
                    int zwrocone3 = drawRandomly();
                    finalResult(NOZYCE, zwrocone3);
                    whoIsTheWinner();
                    checkIfStopGame();
                    break;
                case R.id.imageButton3:
                    mySecondSound.start();
                    mojRuch.setBackgroundResource(R.drawable.ss);
                    int zwrocone2 = drawRandomly();
                    finalResult(KAMIEN, zwrocone2);
                    whoIsTheWinner();
                    checkIfStopGame();
                    break;
                default:
                    break;
            }
        }
    };
    public int generateResultForFinalScore(){
        Random rand = new Random();
        int result = rand.nextInt(3)+1;
        return result;
    }
    public void finalResult(int clickedOne, int wybraneKomputer){

        if(clickedOne == PAPIER) {
            if (wybraneKomputer == PAPIER) {
                Toast.makeText(getApplicationContext(), "DRAW", Toast.LENGTH_SHORT).show();
                zmiennaPomocnicza1 = clickedOne;
                zmiennaPomocnicza2 = wybraneKomputer;

            } else if (wybraneKomputer == NOZYCE) {
                mySeventhSound.start();
                computerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = true;

                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Komputera", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(redColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();

            } else if (wybraneKomputer == KAMIEN) {
                myThirdSound.start();
                playerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = false;
                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Ciebie", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(greenColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();

            }
        }
        else if(clickedOne == NOZYCE) {
            if (wybraneKomputer == NOZYCE) {
                Toast.makeText(getApplicationContext(), "DRAW", Toast.LENGTH_SHORT).show();
                zmiennaPomocnicza1 = clickedOne;
                zmiennaPomocnicza2 = wybraneKomputer;


            } else if (wybraneKomputer == PAPIER) {
                myThirdSound.start();
                playerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = false;
                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Ciebie", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(greenColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();

            } else if (wybraneKomputer == KAMIEN) {
                mySeventhSound.start();
                computerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = true;
                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Komputera", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(redColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();

            }
        }
        else if(clickedOne == KAMIEN){
            if(wybraneKomputer == KAMIEN){
                Toast.makeText(getApplicationContext(), "DRAW", Toast.LENGTH_SHORT).show();
                zmiennaPomocnicza1 = clickedOne;
                zmiennaPomocnicza2 = wybraneKomputer;

            }
            else if(wybraneKomputer == PAPIER){
                mySeventhSound.start();
                computerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = true;
                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Komputera", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(redColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();
                ;
            }
            else if(wybraneKomputer == NOZYCE){
                myThirdSound.start();
                playerResult++;
                zmiennaPomocnicza2 = wybraneKomputer;
                undoComputerResult = false;
                Snackbar mySnackBar = Snackbar.make(buttonsContainer, "Punkt dla Ciebie", Snackbar.LENGTH_SHORT);
                View view = mySnackBar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.yellowish));
                mySnackBar.getView().setBackgroundColor(greenColorForSnackBar);
                mySnackBar.setAction("COFNIJ WYNIK", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(undoComputerResult==true){
                            myFifthSound.start();
                            computerResult -= 1;
                            updateResults();
                        }
                        else{
                            playerResult -= 1;
                            updateResults();
                        }

                    }
                });
                mySnackBar.show();

            }
        }


        updateResults();
        checkIfStopGame();
    }
    public void whoIsTheWinner(){
        if(computerResult==5){

            finalPicture.setBackgroundResource(R.drawable.looser);
        }
        else if(playerResult==5){

            finalPicture.setBackgroundResource(R.drawable.winner);
        }
        else{

        }
    }


    public void checkIfStopGame( ){
        if(computerResult==5 || playerResult==5){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle("Koniec gry")
                    .setMessage("Czy chcesz zagrać jeszcze raz?")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearGame();
                        }
                    })
                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        else{

        }
    }


    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Czy Chcesz Opóścić Grę");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.create();
        alertDialog.show();
        return;
    }


    public void updateResults(){
        lewyLabel.setText(String.valueOf(playerResult));
        prawyLabel.setText(String.valueOf(computerResult));

    }
    public void clearGame(){
        lewyLabel.setText("");
        prawyLabel.setText("");
        mojRuch.setBackgroundResource(R.drawable.drogi);
        komputerRuch.setBackgroundResource(R.drawable.drogi);
        finalPicture.setBackgroundResource(0);
        computerResult = 0;
        playerResult = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        papier.setOnClickListener(myListener);
        kamien.setOnClickListener(myListener);
        nozyce.setOnClickListener(myListener);
        myFirstSound = MediaPlayer.create(this, R.raw.gun);
        mySecondSound = MediaPlayer.create(this, R.raw.klikanie);
        // Utwór w formacie mp3 o nazwie Click On na licencji Attribution 3.0 pobrany dnia 04.09.2017 godz. 20:00 ze strony https://soundbible.com/
        mySeventhSound = MediaPlayer.create(this, R.raw.error);
        // Utwór w formacie mp3 o nazwie Computer Error na licencji Attribution 3.0 pobrany dnia 04.09.2017 godz. 20:00 ze strony https://soundbible.com/
        myThirdSound = MediaPlayer.create(this, R.raw.bell);
        // Utwór w formacie mp3 o nazwie Front Desk Bell Sound na licencji Attribution 3.0 pobrany dnia 06.09.2017 godz. 17:00 ze strony https://soundbible.com/
        myFifthSound = MediaPlayer.create(this, R.raw.pickgraphic);
        // Utwór  w formacie mp3 o nazwie Finger Breaking na licencji Attribution 3.0 pobrany dnia 04.09.2017 godz. 20:00 ze strony https://soundbible.com/

        // Obrazek papieru wykorzystany w aplikacji został pobrany dnia 06.09.2017 o godz. 17:00 ze strony http://i.iplsc.com/zloty-papier-toaletowy-to-totalne-szalenstwo-za-4-mln-zl/0002JTOFV8L9L5W6-C122-F4.jpg

        // Obrazek kamienia wykorzystany w aplikacji został pobrany dnia 06.09.2017 o godz. 17:00 ze strony http://www.zst.bytom.pl/images/jpg/g1.jpg

        // Obrazek nożyczek wykorzystany w aplikacji został pobrany dnia 06.09.2017 o godz. 17:00 ze strony http://static8.depositphotos.com/1126128/803/i/450/depositphotos_8030090-stock-photo-holding-golden-scissors.jpg

        // Obrazek trawy w tle wykorzystany w aplikacji został pobrany dnia 06.09.2017 o godz. 17:00 ze strony https://uwalls.pl/gallery/40/2952_thumb_b400.jpg
    }
}
