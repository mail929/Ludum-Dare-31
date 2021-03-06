package com.snake.ld31;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public class IO
{
	static String saveDir = "save";
	
	public static File[] getSaves()
	{
		File dir = new File(saveDir);
		return dir.listFiles();
	}
	
	public static void loadConfig()
	{
		File file = new File("config.snake");
		if(file.exists())
		{
			JSONObject jconfig = new JSONObject(readFromFile(new File("config.snake")));
			DataContainer.xres = jconfig.getInt("x-res");
			DataContainer.yres = jconfig.getInt("y-res");
		}
		else
		{
			DataContainer.xres = 1280;
			DataContainer.yres = 768;
		}
	}
	
	public static void loadSave(File file)
	{
		JSONObject jsave = new JSONObject(readFromFile(file));
		DataContainer.worldName = jsave.getString("world-name");
		DataContainer.worldHeight = jsave.getInt("world-height");
		DataContainer.worldWidth = jsave.getInt("world-width");
		DataContainer.money = jsave.getDouble("money");
		DataContainer.hours = jsave.getDouble("hours");
		JSONArray jrooms = jsave.getJSONArray("rooms");
		JSONArray jguests = jsave.getJSONArray("guests");
		
		if (!DataContainer.loaded)
		{
			DataContainer.rooms = new Room[ DataContainer.worldWidth ][ DataContainer.worldHeight ];
			DataContainer.guests = new Vector<Guest>( );
			DataContainer.loaded = true;
		}
		
		for(int i = 0; i < jrooms.length(); i++)
		{	
			JSONObject jroom = jrooms.getJSONObject(i);
			int x = jroom.getInt("room-x");
			int y = jroom.getInt("room-y");
			DataContainer.rooms[jroom.getInt("room-x")][jroom.getInt("room-y")] = new Room(RoomType.values()[jroom.getInt("room-type")], x, y);
		}
		
		for (int i=0;i < jguests.length(); ++i)
		{
			JSONObject jguest = jguests.getJSONObject(i);
			int x = jguest.getInt("guest-x");
			int y = jguest.getInt("guest-y");
			
			int hx = jguest.getInt("guest-hotel-x");
			int hy = jguest.getInt("guest-hotel-y");
			
			Guest g = new Guest( DataContainer.rooms[hx][hy] );
			g.hasCheckedIn = true;
			g.x = x;
			g.setY( y );
			g.goToHotelRoom( );
			
			DataContainer.guests.add( g );
		}
		
		Main.instance.startGame();
	}
	
	public static void saveConfig()
	{
		JSONObject jconfig = new JSONObject();
		jconfig.put("x-res", DataContainer.xres);
		jconfig.put("y-res", DataContainer.yres);
		try
		{
			File saveFile = new File("config" + ".snake");
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			bw.write(jconfig.toString());
			bw.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveSave()
	{
		if (DataContainer.rooms == null)
			return;
		
		JSONObject jsave = new JSONObject();
		jsave.put("world-name", DataContainer.worldName);
		jsave.put("world-height", DataContainer.worldHeight);
		jsave.put("world-width", DataContainer.worldWidth);
		jsave.put("money", DataContainer.money);
		jsave.put("hours", DataContainer.hours);
		JSONArray jrooms = new JSONArray();
		for(int i = 0; i < DataContainer.rooms.length; i++)
		{
			for(int j = 0; j < DataContainer.rooms[i].length; j++)
			{
				JSONObject jroom = new JSONObject();
				jroom.put("room-type", DataContainer.rooms[i][j].getRoomType().ordinal());
				jroom.put("room-x", i);
				jroom.put("room-y", j);
				jrooms.put(jroom);
			}
		}
		jsave.put("rooms", jrooms);
		
		JSONArray jguests = new JSONArray();
		for (int i=0;i < DataContainer.guests.size();++i)
		{
			Guest g = DataContainer.guests.get(i);
			
			JSONObject jguest = new JSONObject( );
			jguest.put("guest-x", g.getX( ) );
			jguest.put("guest-y", g.getY( ) );
			jguest.put("guest-hotel-x", g.hotelRoom.getX( ) );
			jguest.put("guest-hotel-y", g.hotelRoom.getY( ) );
			
			jguests.put( jguest );
		}
		jsave.put("guests", jguests);
		
		try
		{
			File saveFile = new File("save" + File.separator + DataContainer.worldName + ".snake");
			saveFile.getParentFile().mkdirs();
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			bw.write(jsave.toString());
			bw.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    //gets a string of data from the save file
    public static String readFromFile(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            
            br.close( );
            
            return sb.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
