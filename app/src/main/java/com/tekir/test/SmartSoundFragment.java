package com.tekir.test;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SmartSoundFragment extends Fragment {

    private DatabaseReference mDatabase;
    private HashMap<String,Object> mData;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private SeekBar seekBar;
    private TextView seekBarTv,tvNumber;
    private Switch switch2;
    private boolean lightSwitchString,toggleBtnBoolean,radioBtn1Boolean,radioBtn2Boolean,radioBtn3Boolean;
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2,radioButton3;
    int radioBtn1,radioBtn2,radioBtn3,seekBarSp;
    private ToggleButton toggleButton;
    private Button btnInc,btnDec,btnSendMessage;
    float number;
    private EditText messageText;
    private String messageStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_smart_sound, container, false);

        seekBar = (SeekBar)viewGroup.findViewById(R.id.seekBar);
        seekBarTv = (TextView)viewGroup.findViewById(R.id.seekBarTv);
        switch2 = (Switch)viewGroup.findViewById(R.id.switch2);
        radioGroup = (RadioGroup)viewGroup.findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton)viewGroup.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)viewGroup.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)viewGroup.findViewById(R.id.radioButton3);
        toggleButton = (ToggleButton)viewGroup.findViewById(R.id.toggleButton);
        btnSendMessage = (Button) viewGroup.findViewById(R.id.messageBtn);
        messageText = (EditText) viewGroup.findViewById(R.id.messageEditText);
        tvNumber = (TextView)viewGroup.findViewById(R.id.tvNumber);
        btnInc = (Button) viewGroup.findViewById(R.id.btnInc);
        btnDec = (Button)viewGroup.findViewById(R.id.btnDec);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        HomeTemperature();
        SwitchController();
        radioGrp();
        tbChecked();
        NumberFunc();
        SendMessage();

        return viewGroup;
    }
    public void SendMessage(){
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageStr = messageText.getText().toString();
                mData=new HashMap<>();
                mData.put("Message",messageStr);
                mDatabase.child(mUser.getUid()).child("Message").setValue(mData);
            }
        });
    }
    public void NumberFunc (){

        tvNumber.setText(String.format("%.1f",number));

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number -= 0.1;
                tvNumber.setText(String.format("%.1f",number));
                mData=new HashMap<>();
                mData.put("FloatNumber",number);
                mDatabase.child(mUser.getUid()).child("Float").setValue(mData);
            }
        });

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number += 0.1;
                tvNumber.setText(String.format("%.1f",number));
                mData=new HashMap<>();
                mData.put("FloatNumber",number);
                mDatabase.child(mUser.getUid()).child("Float").setValue(mData);
            }
        });
    }

    public void tbChecked(){

        toggleButton.setChecked(toggleBtnBoolean);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggleBtnBoolean=b;
                mData=new HashMap<>();
                mData.put("ToggleButton",toggleBtnBoolean);
                mDatabase.child(mUser.getUid()).child("ToggleButtonChecked").setValue(mData);
            }
        });
    }

    public void radioGrp (){
        radioButton1.setChecked(radioBtn1Boolean);
        radioButton2.setChecked(radioBtn2Boolean);
        radioButton3.setChecked(radioBtn3Boolean);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton1.isChecked()){
                    radioBtn1=1;
                    radioBtn1Boolean=true;
                    mData=new HashMap<>();
                    mData.put("asd",radioBtn1);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton1").setValue(mData);
                }
                else {
                    radioBtn1=0;
                    radioBtn1Boolean=false;
                    mData=new HashMap<>();
                    mData.put("asd",radioBtn1);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton1").setValue(mData);
                }
                if (radioButton2.isChecked()){
                    radioBtn2=1;
                    radioBtn2Boolean=true;
                    mData=new HashMap<>();
                    mData.put("asdf",radioBtn2);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton2").setValue(mData);
                }
                else {
                    radioBtn2=0;
                    radioBtn2Boolean=false;
                    mData=new HashMap<>();
                    mData.put("asdf",radioBtn2);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton2").setValue(mData);
                }
                if (radioButton3.isChecked()){
                    radioBtn3=1;
                    radioBtn3Boolean=true;
                    mData=new HashMap<>();
                    mData.put("asdfg",radioBtn3);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton3").setValue(mData);
                }
                else {
                    radioBtn3=0;
                    radioBtn3Boolean=false;
                    mData=new HashMap<>();
                    mData.put("asdfg",radioBtn3);
                    mDatabase.child(mUser.getUid()).child("RadioGroup").child("RadioButton3").setValue(mData);
                }
            }
        });
    }

    public void SwitchController (){

        switch2.setChecked(lightSwitchString);

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lightSwitchString=b;
                mData=new HashMap<>();
                mData.put("Light",lightSwitchString);
                mDatabase.child(mUser.getUid()).child("SwitchLight").setValue(mData);
            }
        });
    }

    public void HomeTemperature(){

        seekBarTv.setText(seekBarSp + "°");
        seekBar.setProgress(seekBarSp);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarSp=progress;
                seekBarTv.setText(seekBarSp + "°");
                mData=new HashMap<>();
                mData.put("Temp",seekBarSp);
                mDatabase.child(mUser.getUid()).child("homeTemp").setValue(mData);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    };
}