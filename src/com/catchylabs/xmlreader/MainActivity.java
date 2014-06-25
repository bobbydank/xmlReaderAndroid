package com.catchylabs.xmlreader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.catchylabs.xmlreader.Item;
import com.rufflez.xmlreader.R;

public class MainActivity extends ListActivity {
	
	String item;
	List<Item> items = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try{
			Resources res = this.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.new_items);
			items = parseXML(xpp);
		}catch (XmlPullParserException e){
		}catch (IOException e){
		}
		
		List<String> titleString = new ArrayList<String>();
		for (Item i : items){
		    titleString.add(i.title);
		}
		//String[] items = item.split("\n");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleString));
	}
	
	private List<Item> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException{
		System.out.println("Running parseXML");
	    
		List<Item> entries = null;
	    int eventType = parser.getEventType();
	    Item currentItem = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	entries = new ArrayList<Item>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("Item")){
                    	currentItem = new Item();
                    } else {
                        if (name.equalsIgnoreCase("title")){
                        	currentItem.title = parser.nextText();
                        } else if (name.equalsIgnoreCase("link")){
                        	currentItem.link = "Test";
                        }  
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("Item") && currentItem != null){
                    	entries.add(currentItem);
                    } 
                    break;
            }
            eventType = parser.next();
        }
	    return entries;
	}

}
