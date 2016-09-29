import java.applet.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

@SuppressWarnings("serial")
public class TTT extends Applet implements MouseListener
{
	Label L[][][]=new Label[6][4][4];
	Label M[]=new Label[3];
	Label LN=new Label();
	
	Font F1=new Font("Courier",Font.BOLD,40);
	Font F2=new Font("Courier",Font.BOLD,30);
	
	Player P1=new Player("Sexy",true,"0");
	Player P2=new Player("Danger",false,"X");
	
	final int sizeofbox=60;
	int startx=-300,starty=100,dn=-1;;
	boolean draw,win,single=true;
	Design D[]=new Design[5];
	public void newgame()
	{
		
		P2.turn=win=false;
		P1.turn=draw=true;
		
		try
		{
			Thread.sleep(50);
		}
		catch (InterruptedException e)
		{
			System.out.println("In newgame:"+e);
		}
		
		dn++;
		if(dn==5)
		{
			System.exit(0);
		}
		startx+=400;
		if(startx>1100)
		{
			startx=300;
			starty+=250;
		}
		
		
		D[dn]=new Design(startx,starty,sizeofbox);
		D[dn].active=true;
		for(short i=0;i<4 && D[dn].active;i++)
			add(D[dn].Line[i]);
		
		for(int i=1;i<4;i++)
			for(int j=1;j<4;j++)
			{
				L[dn][i][j]=new Label();
				L[dn][i][j].setText(" ");
			}
		for(short i=1;i<4;i++)
		{
			for(short j=1;j<4;j++)
			{
				L[dn][i][j].setBounds(startx+(i-1)*sizeofbox,starty+(j-1)*sizeofbox,sizeofbox,sizeofbox);
				add(L[dn][i][j]);
				L[dn][i][j].addMouseListener(this);
				requestFocus(true);
				L[dn][i][j].setFont(F1);
				L[dn][i][j].setAlignment(Label.CENTER);
			}
		}
		LN.setForeground(new Color(0,0,0));
		LN.setBounds(startx-50,starty-60,300,50);
		LN.setFont(F2);
		LN.setAlignment(Label.CENTER);
		add(LN);
		LN.setText("Game Started");
		try
		{
			Thread.sleep(400);
		}
		catch (InterruptedException e)
		{
			System.out.println("In newgame:"+e);
		}
		if(single)
			LN.setText("Your's turn");
		else
			LN.setText(P1.name+"'s turn");
	}
	
	public void init()
	{
		this.resize(1350,650);
		setBackground(new Color(163,163,163));
			
		newgame();
		
		setLayout(null);
		M[0]=new Label("SINGLE");
		M[1]=new Label("NEW GAME");
		M[2]=new Label("QUIT");
		
		for(short i=0;i<3;i++)
		{
			M[i].setFont(F2);
			M[i].setAlignment(Label.CENTER);
			M[i].setBounds(300+250*i,600,250,50);
			add(M[i]);
			M[i].addMouseListener(this);
			requestFocus(true);
		}
		
		
		if(P1.turn)
		{
			if(single)
				LN.setText("Your's turn");
			else
				LN.setText(P1.name+"'s turn");
		}
		else if(P2.turn)
		{
			if(single)
				LN.setText("Computer's turn");
			else
				LN.setText(P2.name+"'s turn");
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		//Printing Whatever Player Enter
		for(int i=1;i<4;i++)
		{
			for(int j=1;j<4;j++)
			{
				if(!win&&e.getSource()==L[dn][i][j]&&L[dn][i][j].getText()==" ")
				{
				
					if(P1.turn)
					{
						L[dn][i][j].setText(P1.choice);
						if(single)
							LN.setText("Computer's turn");
						else
							LN.setText(P2.name+"'s turn");
						P1.turn=false;
						P2.turn=true;
						check();
						if(single&&!win)
						{
							try
							{
								Thread.sleep(400);
							}
							catch (InterruptedException ee)
							{
								ee.printStackTrace();
							}
							
							computer();
							LN.setText("Your's turn");
							P1.turn=true;
						}
						else if(P1.turn)
							LN.setText(P1.name+"'s turn");
					}
					else
					{
							
						LN.setText(P1.name+"'s turn");
						L[dn][i][j].setText(P2.choice);
						P2.turn=false;
						P1.turn=true;
					}	
				}
			}//j
		}//i
		
		//Checking for Win a TTT
		check(); 
		 
		//Menu: Single  New-game Quit
		if(e.getSource()==M[0])
		{
			if(!single)
			{
				single=true;
				//dn=-1;
				//startx=-300;
				//starty=100;
				newgame();
				M[0].setText("MULTI");
			}
			else
			{
				single=false;
				//dn=-1;
				//startx=-300;
				//starty=100;
				newgame();
				M[0].setText("SINGLE");
			}
		}
			
		if(e.getSource()==M[1])
		{
			newgame();
		}	
		
		if(e.getSource()==M[2])
			System.exit(0);
	}

	public void mousePressed(MouseEvent e)
	{
			
	}
	public void mouseReleased(MouseEvent e)
	{
		
	}
	public void mouseEntered(MouseEvent e)
	{
		for(int i=0;i<3;i++)
			if(e.getSource()==M[i])
				M[i].setForeground(new Color(255,255,255));
	}
	public void mouseExited(MouseEvent e)
	{
		for(int i=0;i<3;i++)
			if(e.getSource()==M[i])
				M[i].setForeground(new Color(0,0,0));
	}
	
	public void check()
	{
		for(int h=1;h<4;h++)
	     {
			 if(L[dn][1][h].getText()==L[dn][2][h].getText()&&L[dn][3][h].getText()==L[dn][2][h].getText())
			 {
				 if(L[dn][1][h].getText()==P1.choice)
				 {
					 if(single)
						 LN.setText("You Win");
					 else
					 	LN.setText(P1.name+" Win");
					 win=true;
				 }
				else if(L[dn][1][h].getText()==P2.choice) 
				{
					if(single)
						LN.setText("Computer Win");
					else
						LN.setText(P2.name+" Win");
					win=true;
				}
			 }
			 else if(L[dn][h][1].getText()==L[dn][h][2].getText()&&L[dn][h][3].getText()==L[dn][h][2].getText())
			 {
				 if(L[dn][h][1].getText()==P1.choice)
				 {
					 if(single)
						 LN.setText("You Win");
					 else
					 	LN.setText(P1.name+" Win");
					 win=true;
				 }
				 else if(L[dn][h][1].getText()==P2.choice)
				 {
					 if(single)
						LN.setText("Computer Win");
					 else
						LN.setText(P2.name+" Win");
					win=true;
				 }
			 }
	     }
		 
		 if((L[dn][1][1].getText()==L[dn][2][2].getText())&&(L[dn][3][3].getText()==L[dn][2][2].getText()))
		 {
			 if(L[dn][1][1].getText()==P1.choice)
			 {
				 if(single)
					 LN.setText("You Win");
				 else
				 	LN.setText(P1.name+" Win");
				 win=true;
			 }
			 else if(L[dn][1][1].getText()==P2.choice)
			 {
				 if(single)
					LN.setText("Computer Win");
			 	 else
					LN.setText(P2.name+" Win");
				 win=true;
			 }
		 }
		 
		 if((L[dn][1][3].getText()==L[dn][2][2].getText())&&(L[dn][3][1].getText()==L[dn][2][2].getText()))
		 {
			 if(L[dn][1][3].getText()==P1.choice)
			 {
				 if(single)
					 LN.setText("You Win");
				 else
				 	LN.setText(P1.name+" Win");
				 win=true;
			 }
			 else if(L[dn][1][3].getText()==P2.choice)
			 {
				 if(single)
					LN.setText("Computer Win");
				 else
					LN.setText(P2.name+" Win");
				 win=true;
			 }
		 }
		 
		 for(int i=1;i<4;i++)
				for(int j=1;j<4;j++)
					if(L[dn][i][j].getText()==" ")
						draw=false;
		 if(win||draw)
				LN.setForeground(new Color(0,0,255));
		 
		 if(draw&&!win)
			 LN.setText("Draw");
		 else
			 draw=true;
	}
	
	public void computer()
	{
		//logic for not win to player
		String x[]=new String[2];
		x[0]=new String();
		x[1]=new String();
		x[0]=P2.choice;
		x[1]=P1.choice;
			
		for(int i=0;i<2;i++)
		{
	
		for(int h=1;h<4 && P2.turn ;h++)
	    {
			if(L[dn][3][h].getText()==" "&&L[dn][2][h].getText()==x[i]&&L[dn][1][h].getText()==x[i])
			{
				 L[dn][3][h].setText(P2.choice);
				 P2.turn=false;
			}
			else if(L[dn][2][h].getText()==" "&&L[dn][3][h].getText()==x[i]&&L[dn][1][h].getText()==x[i])
			{
				 L[dn][2][h].setText(P2.choice);
				 P2.turn=false;
			}
			else if(L[dn][1][h].getText()==" "&&L[dn][2][h].getText()==x[i]&&L[dn][3][h].getText()==x[i])
			{
				 L[dn][1][h].setText(P2.choice);
				 P2.turn=false;
			}
			else if(L[dn][h][3].getText()==" "&&L[dn][h][1].getText()==x[i]&&L[dn][h][2].getText()==x[i])
			{
				 L[dn][h][3].setText(P2.choice);
				 P2.turn=false;
			}
			else if(L[dn][h][2].getText()==" "&&L[dn][h][3].getText()==x[i]&&L[dn][h][1].getText()==x[i])
			{
				 L[dn][h][2].setText(P2.choice);
				 P2.turn=false;
			}
			else if(L[dn][h][1].getText()==" "&&L[dn][h][2].getText()==x[i]&&L[dn][h][3].getText()==x[i])
			{
				 L[dn][h][1].setText(P2.choice);
				 P2.turn=false;
			}
	    } //for(P2.turn) finished
		
		if(L[dn][1][1].getText()==" "&&L[dn][2][2].getText()==x[i]&&L[dn][3][3].getText()==x[i]&&P2.turn)
		{
			 L[dn][1][1].setText(P2.choice);
			 P2.turn=false;
		}
		else if(L[dn][2][2].getText()==" "&&L[dn][1][1].getText()==x[i]&&L[dn][3][3].getText()==x[i]&&P2.turn)
		{
			 L[dn][2][2].setText(P2.choice);
			 P2.turn=false;
		}
		else if(L[dn][3][3].getText()==" "&&L[dn][2][2].getText()==x[i]&&L[dn][1][1].getText()==x[i]&&P2.turn)
		{
			 L[dn][3][3].setText(P2.choice);
			 P2.turn=false;
		}
		else if(L[dn][1][3].getText()==" "&&L[dn][2][2].getText()==x[i]&&L[dn][3][1].getText()==x[i]&&P2.turn)
		{
			 L[dn][1][3].setText(P2.choice);
			 P2.turn=false;
		}
		else if(L[dn][2][2].getText()==" "&&L[dn][1][3].getText()==x[i]&&L[dn][3][1].getText()==x[i]&&P2.turn)
		{
			 L[dn][2][2].setText(P2.choice);
			 P2.turn=false;
		}
		else if(L[dn][3][1].getText()==" "&&L[dn][2][2].getText()==x[i]&&L[dn][1][3].getText()==x[i]&&P2.turn)
		{
			 L[dn][3][1].setText(P2.choice);
			 P2.turn=false;
		}
		
		}//for(int i=1;i<3;i++) finished
		
		//randomly
		for(int i=0;i<5 && P2.turn;i++)
		{
			Random R=new Random();
			switch((int) (R.nextDouble()*5))
			{
			case 1:
				if(L[dn][2][2].getText()==" ")
				{
					L[dn][2][2].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 2:
				if(L[dn][1][1].getText()==" ")
				{
					L[dn][1][1].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 3:
				if(L[dn][1][3].getText()==" ")
				{
					L[dn][1][3].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 4:
				if(L[dn][3][1].getText()==" ")
				{
					L[dn][3][1].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 5:
				if(L[dn][3][3].getText()==" ")
				{
					L[dn][1][1].setText(P2.choice);
					P2.turn=false;
				}
				break;
			}
		}
		for(int i=0;i<4 && P2.turn;i++)
		{
			Random R=new Random();
			switch((int) (R.nextDouble()*4))
			{
			case 1:
				if(L[dn][1][2].getText()==" ")
				{
					L[dn][1][2].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 2:
				if(L[dn][2][1].getText()==" ")
				{
					L[dn][2][1].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 3:
				if(L[dn][2][3].getText()==" ")
				{
					L[dn][2][3].setText(P2.choice);
					P2.turn=false;
				}
				break;
			case 4:
				if(L[dn][3][2].getText()==" ")
				{
					L[dn][3][2].setText(P2.choice);
					P2.turn=false;
				}
				break;
			}
		}
	}
}
