package com.snake.ld31;

import java.util.Vector;

public class DataContainer
{
	public static boolean loaded = false;
	
	public static String worldName;
	public static int worldWidth;
	public static int worldHeight;
	
	public static Room[][] rooms;
	public static Vector<Guest> guests;
	
	public static Vector<Room> drenthlist;
	public static Vector<Room> plumbinglist;
	
	public static double money;
	
	public static double hours;
	
	public static int xres;
	public static int yres;
}
