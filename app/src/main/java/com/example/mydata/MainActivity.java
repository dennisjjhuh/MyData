package com.example.mydata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    ListView listView = null;

    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter = null;

    ArrayList<Member> members = new ArrayList<Member>();
    ArrayAdapter<Member> memberAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        /*parserFromFile();
        showListViewFromFile();*/

        /*parserFromXML();
        showListViewFromXML();*/

        parserFromJSON();
    }

    private void parserFromFile() {
        Log.i(TAG, "parser()");

        InputStream is= getResources().openRawResource(R.raw.tokenex); // returns the next byte of input.
        InputStreamReader isr = new InputStreamReader(is); //It reads bytes and decodes them into characters
        BufferedReader reader = new BufferedReader(isr); // Reads text from a character-input stream

        StringBuffer sb = new StringBuffer();
        String line = null;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            Log.i(TAG, "sb:"+sb.toString());

            String str = sb.toString();
            StringTokenizer tokenizer = new StringTokenizer(str, "|");
            Log.i(TAG, "Token count: "+tokenizer.countTokens());
            while(tokenizer.hasMoreElements()){
                String tokenStr = tokenizer.nextToken();
                Log.i(TAG, "Token string(|): "+tokenStr);

                StringTokenizer tokenizer2 = new StringTokenizer(tokenStr, ",");
                Log.i(TAG, "Token count: "+tokenizer2.countTokens());
                while(tokenizer2.hasMoreElements()){
                    String tokenStr2 = tokenizer2.nextToken();
                    arrayList.add(tokenStr2);
                    Log.i(TAG, "Token string(,): "+tokenStr2);
                }
                Log.i(TAG, "======================================");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null) reader.close();
                if(isr != null) isr.close();
                if(is != null) is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showListViewFromFile(){
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    private void parserFromXML() {
        Log.i(TAG, "parser()");

        InputStream is= getResources().openRawResource(R.raw.xmlex); // returns the next byte of input.
        InputStreamReader isr = new InputStreamReader(is); //It reads bytes and decodes them into characters
        BufferedReader reader = new BufferedReader(isr); // Reads text from a character-input stream

        StringBuffer sb = new StringBuffer();
        String line = null;

        XmlPullParserFactory factory = null;
        XmlPullParser xmlPullParser = null;

        try{
            factory = XmlPullParserFactory.newInstance();
            xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(reader);

            int eventType = xmlPullParser.getEventType();

            String strComp="";
            String age="";
            String[] hobbys = null;
            String name="";
            String no="";
            String u_id="";
            String u_pw="";

            //members.add(new Member(age, hobbys, name, no, u_id, u_pw));
            Member member = null;

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        Log.i(TAG, "Start Document");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.i(TAG, "Start TAG: " + xmlPullParser.getName());

                        //To create member class
                        if(xmlPullParser.getName().equals("members_info")) {
                            member = new Member(age, hobbys, name, no, u_id, u_pw);
                        }

                        if(xmlPullParser.getName().equals("member")){
                            int count = xmlPullParser.getAttributeCount();
                            for(int i=0; i<count;i++){
                                Log.i(TAG, "Start TAG AttributeName(" + i + "): " + xmlPullParser.getAttributeName(i));
                                Log.i(TAG, "Start TAG AttributeValue("+i+"): "+xmlPullParser.getAttributeValue(i));

                                strComp = xmlPullParser.getAttributeName(i);
                                if(strComp.equals("name"))
                                    name = xmlPullParser.getAttributeValue(i);

                                if(strComp.equals("age"))
                                    age = xmlPullParser.getAttributeValue(i);

                                if(strComp.equals("hobby"))
                                    hobbys[i] = xmlPullParser.getAttributeValue(i);

                                if(strComp.equals("no"))
                                    no = xmlPullParser.getAttributeValue(i);

                                if(strComp.equals("id"))
                                    u_id = xmlPullParser.getAttributeValue(i);

                                if(strComp.equals("pw"))
                                    u_pw = xmlPullParser.getAttributeValue(i);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.i(TAG, "End TAG: " + xmlPullParser.getName());

                        //To save member array
                        if(xmlPullParser.getName().equals("member")){
                            members.add(member);
                            member = new Member(age, hobbys, name, no, u_id, u_pw);
                        }

                        break;
                    case XmlPullParser.TEXT:
                        Log.i(TAG, "TEXT: "+xmlPullParser.getText());
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null) reader.close();
                if(isr != null) isr.close();
                if(is != null) is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showListViewFromXML(){
        memberAdapter = new ArrayAdapter<Member>(MainActivity.this, android.R.layout.simple_list_item_1, members);
        listView.setAdapter(memberAdapter);
    }

    private void parserFromJSON(){
        Log.i(TAG, "parserFromJSON()");

        InputStream is = getResources().openRawResource(R.raw.jsonex2);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);

        StringBuffer sb = new StringBuffer();
        String line = null;

        try{
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            Log.i(TAG, "sb: "+sb.toString());

            JSONObject jsonObject = new JSONObject(sb.toString());

            String name = jsonObject.getString("name");
            Log.i(TAG, "name: "+name);

            int age = jsonObject.getInt("age");
            Log.i(TAG, "age: "+age);

            JSONArray jsonArray = jsonObject.getJSONArray("hobbys");
            for(int i=0;i<jsonArray.length();i++){
                String hobby = jsonArray.getString(i);
                Log.i(TAG, "hobbys[" + i + "]: " + hobby);
            }

            JSONObject jsonObject2 = jsonObject.getJSONObject("info");

            int no = jsonObject2.getInt("no");
            Log.i(TAG, "no: "+no);

            String id = jsonObject2.getString("id");
            Log.i(TAG, "id: "+id);

            int pw = jsonObject2.getInt("pw");
            Log.i(TAG, "pw: "+pw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null) reader.close();
                if(isr != null) isr.close();
                if(reader != null) reader.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

}
