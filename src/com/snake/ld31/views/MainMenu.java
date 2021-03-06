package com.snake.ld31.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.snake.ld31.View;
import com.snake.ld31.Main;

public class MainMenu extends View
{
	private long lastChange = 0;
	private boolean showText = false;
	
	@Override
	public void draw(Graphics2D draw)
	{
		draw.setColor(Color.ORANGE);
		draw.fillRect(0, 0, Main.instance.getScrWidth(), Main.instance.getScrWidth());
		
		draw.setColor( Color.BLACK );
		
		String gameName = "TOWER GAME";
		String tell = "Press ENTER to Play!";
		String res = "Press X and +/- to adjust the width and Y and +/- to adjust the height";
		draw.setFont(new Font("Arial", Font.PLAIN, 40));
		draw.drawString(gameName, Main.instance.centerText(gameName, draw), 100);
		draw.setFont(new Font("Arial", Font.PLAIN, 20));
		draw.drawString(res, Main.instance.centerText(res, draw), 475);
		
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastChange > 750)
		{
			lastChange = currentTime;
			showText = !showText;
		}
		if(showText)
		{
			draw.setFont(new Font("Arial", Font.PLAIN, 30));
			draw.drawString(tell, Main.instance.centerText(tell, draw), 400);
		}
	}

	@Override
	public void run( float deltaTime ){}

	@Override
	public void init(){}

	@Override
	public void keyPressed(KeyEvent e){}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Main.instance.saveLoad();
		}
		//for testing purposes the full release doesn't
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			Main.instance.goToLogo();
		}
	}

	@Override
	public void mouseClick(MouseEvent e){}
	
}
